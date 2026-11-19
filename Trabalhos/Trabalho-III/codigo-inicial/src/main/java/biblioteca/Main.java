package biblioteca;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Biblioteca b = new Biblioteca();

        b.cadastrarUsuario("2026001", "Ana", "ana@aluno.ifrs.edu.br", "ALUNO");
        b.cadastrarUsuario("P001", "Bruno", "bruno@ifrs.edu.br", "PROFESSOR");
        b.cadastrarUsuario("V01", "Carla", "carla@email.com", "VISITANTE");

        b.cadastrarLivro("L1", "Código Limpo");
        b.cadastrarLivro("L2", "Refatoração");
        b.cadastrarLivro("L3", "Domain-Driven Design");
        b.cadastrarLivro("L4", "Java Efetivo");
        b.cadastrarLivro("L5", "Padrões de Projeto");

        LocalDate inicio = LocalDate.of(2026, 3, 2);
        b.emprestar("2026001", "L1", inicio);
        b.emprestar("2026001", "L2", inicio);
        b.emprestar("P001", "L3", inicio);
        b.emprestar("V01", "L4", inicio);
        tentar(() -> b.emprestar("V01", "L5", inicio));      // visitante: limite de 1 livro
        tentar(() -> b.emprestar("2026001", "L3", inicio));  // livro já emprestado

        b.devolver("L1", LocalDate.of(2026, 3, 9));          // aluno: exatamente no prazo
        b.devolver("L2", LocalDate.of(2026, 3, 12));         // aluno: 3 dias de atraso
        tentar(() -> b.emprestar("2026001", "L5", LocalDate.of(2026, 3, 12))); // multa pendente
        b.pagarMulta("2026001", 3.00);
        b.emprestar("2026001", "L5", LocalDate.of(2026, 3, 12));

        b.devolver("L3", LocalDate.of(2026, 3, 20));         // professor: 4 dias de atraso
        b.devolver("L4", LocalDate.of(2026, 3, 25));         // visitante: 20 dias, bate no teto

        b.relatorio(LocalDate.of(2026, 3, 25));
    }

    private static void tentar(Runnable acao) {
        try {
            acao.run();
        } catch (RuntimeException e) {
            System.out.println("Recusado: " + e.getMessage());
        }
    }
}
