package biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Biblioteca {

    private static final Locale BR = Locale.forLanguageTag("pt-BR");

    private Map<String, Usuario> usrs = new HashMap<>();
    private Map<String, Livro> lvs = new HashMap<>();
    private List<Emprestimo> emps = new ArrayList<>();
    private Notificador notif = new NotificadorPorEmail();

    public void cadastrarUsuario(String mat, String nome, String email, String tipo) {
        Usuario u = new Usuario();
        u.setMatricula(mat);
        u.setNome(nome);
        u.setEmail(email);
        u.setTipo(tipo);
        u.setDebito(0.0);
        usrs.put(mat, u);
    }

    public void cadastrarLivro(String cod, String titulo) {
        Livro l = new Livro();
        l.setCodigo(cod);
        l.setTitulo(titulo);
        l.setEmprestado(false);
        lvs.put(cod, l);
    }

    public void emprestar(String mat, String cod, LocalDate data) {
        Usuario u = usrs.get(mat);
        Livro l = lvs.get(cod);
        if (u != null) {
            if (l != null) {
                if (!l.isEmprestado()) {
                    if (u.getDebito() == 0) {
                        int qtd = 0;
                        for (Emprestimo e : emps) {
                            if (e.getUsuario().getMatricula().equals(mat) && e.getDevolucao() == null) {
                                qtd++;
                            }
                        }
                        int lim;
                        int prz;
                        if (u.getTipo().equals("ALUNO")) {
                            lim = 3;
                            prz = 7;
                        } else if (u.getTipo().equals("PROFESSOR")) {
                            lim = 5;
                            prz = 14;
                        } else {
                            lim = 1;
                            prz = 3;
                        }
                        if (qtd < lim) {
                            Emprestimo e = new Emprestimo();
                            e.setUsuario(u);
                            e.setLivro(l);
                            e.setRetirada(data);
                            e.setPrevista(data.plusDays(prz));
                            emps.add(e);
                            l.setEmprestado(true);
                            System.out.println("Empréstimo: " + l.getTitulo() + " para " + u.getNome()
                                    + " (devolver até " + e.getPrevista() + ")");
                        } else {
                            throw new IllegalStateException("limite de empréstimos atingido");
                        }
                    } else {
                        throw new IllegalStateException("usuário com multa pendente");
                    }
                } else {
                    throw new IllegalStateException("livro já emprestado");
                }
            } else {
                throw new IllegalArgumentException("livro não encontrado");
            }
        } else {
            throw new IllegalArgumentException("usuário não encontrado");
        }
    }

    public void devolver(String cod, LocalDate data) {
        Emprestimo emp = null;
        for (Emprestimo e : emps) {
            if (e.getLivro().getCodigo().equals(cod) && e.getDevolucao() == null) {
                emp = e;
            }
        }
        if (emp == null) {
            throw new IllegalStateException("livro não está emprestado");
        }
        emp.setDevolucao(data);
        emp.getLivro().setEmprestado(false);

        long atr = ChronoUnit.DAYS.between(emp.getPrevista(), data);
        if (atr < 0) {
            atr = 0;
        }
        double tx;
        if (emp.getUsuario().getTipo().equals("ALUNO")) {
            tx = 1.00;
        } else if (emp.getUsuario().getTipo().equals("PROFESSOR")) {
            tx = 0.50;
        } else {
            tx = 2.00;
        }
        double mlt = atr * tx;
        if (mlt > 20.00) {
            mlt = 20.00;
        }
        emp.getUsuario().setDebito(emp.getUsuario().getDebito() + mlt);

        System.out.println("Devolução: " + emp.getLivro().getTitulo() + " — atraso de " + atr
                + " dia(s) — multa " + String.format(BR, "R$ %.2f", mlt));
        if (mlt > 0) {
            notif.enviarEmail(emp.getUsuario().getEmail(), "Olá, " + emp.getUsuario().getNome()
                    + ". Sua devolução gerou multa de " + String.format(BR, "R$ %.2f", mlt) + ".");
        }
    }

    public void pagarMulta(String mat, double valor) {
        Usuario u = usrs.get(mat);
        u.setDebito(u.getDebito() - valor);
        System.out.println("Pagamento: " + u.getNome() + " pagou " + String.format(BR, "R$ %.2f", valor)
                + " — débito restante " + String.format(BR, "R$ %.2f", u.getDebito()));
    }

    public void relatorio(LocalDate data) {
        System.out.println("=== Relatório em " + data + " ===");
        System.out.println("Débitos:");
        List<Usuario> ord = new ArrayList<>(usrs.values());
        ord.sort((a, b) -> a.getNome().compareTo(b.getNome()));
        for (Usuario u : ord) {
            if (u.getDebito() > 0) {
                System.out.println("  " + u.getNome() + ": " + String.format(BR, "R$ %.2f", u.getDebito()));
            }
        }
        System.out.println("Empréstimos em aberto:");
        for (Emprestimo e : emps) {
            if (e.getDevolucao() == null) {
                String sit;
                if (e.getPrevista().isBefore(data)) {
                    sit = "ATRASADO";
                } else {
                    sit = "no prazo";
                }
                System.out.println("  " + e.getLivro().getTitulo() + " (" + e.getUsuario().getNome()
                        + ") — previsto " + e.getPrevista() + " — " + sit);
            }
        }
    }
}
