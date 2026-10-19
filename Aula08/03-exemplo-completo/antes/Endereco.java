package foodexpress.aula08.antes;

public class Endereco {

    private String rua;
    private Bairro bairro;

    public Endereco(String rua, Bairro bairro) {
        this.rua = rua;
        this.bairro = bairro;
    }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public Bairro getBairro() { return bairro; }
    public void setBairro(Bairro bairro) { this.bairro = bairro; }
}
