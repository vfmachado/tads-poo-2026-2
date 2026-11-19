package biblioteca;

public class Usuario {

    private String matricula;
    private String nome;
    private String email;
    private String tipo; // "ALUNO", "PROFESSOR" ou "VISITANTE"
    private double debito;

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getDebito() { return debito; }
    public void setDebito(double debito) { this.debito = debito; }
}
