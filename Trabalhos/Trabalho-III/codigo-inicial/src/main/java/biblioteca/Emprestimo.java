package biblioteca;

import java.time.LocalDate;

public class Emprestimo {

    private Usuario usuario;
    private Livro livro;
    private LocalDate retirada;
    private LocalDate prevista;
    private LocalDate devolucao; // null enquanto o livro não volta

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public LocalDate getRetirada() { return retirada; }
    public void setRetirada(LocalDate retirada) { this.retirada = retirada; }
    public LocalDate getPrevista() { return prevista; }
    public void setPrevista(LocalDate prevista) { this.prevista = prevista; }
    public LocalDate getDevolucao() { return devolucao; }
    public void setDevolucao(LocalDate devolucao) { this.devolucao = devolucao; }
}
