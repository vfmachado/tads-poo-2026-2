package foodexpress.aula08.antes;

public class Bairro {

    private String nome;
    private double distKm;

    public Bairro(String nome, double distKm) {
        this.nome = nome;
        this.distKm = distKm;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getDistKm() { return distKm; }
    public void setDistKm(double distKm) { this.distKm = distKm; }
}
