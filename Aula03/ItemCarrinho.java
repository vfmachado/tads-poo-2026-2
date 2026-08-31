// TODO - falta validacao e possiveis SETs
public class ItemCarrinho {
    
    private Produto p;
    private int quantidade;
    private String observacao;
    
    public ItemCarrinho(Produto p, int quantidade, String observacao) {
        this.p = p;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    public Produto getProduto() {
        return p;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    @Override
    public String toString() {
        return "ItemCarrinho [p=" + p + ", quantidade=" + quantidade + ", observacao=" + observacao + "]";
    }
}
