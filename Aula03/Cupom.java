public class Cupom {
    
    private String nome;
    private float percentual;
    private float valorMinimo;
    private boolean utilizado;
    
    public Cupom(String nome, float percentual, float valorMinimo) {
        this.nome = nome;
        this.percentual = percentual;
        this.valorMinimo = valorMinimo;
        this.utilizado = false;
    }

    public String getNome() {
        return nome;
    }

    public float getPercentual() {
        return percentual;
    }

    public float getValorMinimo() {
        return valorMinimo;
    }

    public boolean estaDisponivel() {
        return !utilizado;
    }

    public void marcarComoUtilizado() {
        utilizado = true;
    }

    @Override
    public String toString() {
        return "Cupom [nome=" + nome + ", percentual=" + percentual + ", valorMinimo=" + valorMinimo + ", utilizado="
                + utilizado + "]";
    }

    

}
