package foodexpress.aula08.antes;

import java.util.List;

// Objeto anêmico: só carrega dados. Toda a regra está em FechamentoPedido.
public class PedidoEntrega {

    private List<ItemPedido> itens;
    private Endereco end;
    private String cp;     // código do cupom (pode ser null ou vazio)
    private String pgto;   // "PIX", "CARTAO" ou "DINHEIRO"
    private boolean pri;   // entrega prioritária

    public PedidoEntrega(List<ItemPedido> itens, Endereco end, String cp, String pgto, boolean pri) {
        this.itens = itens;
        this.end = end;
        this.cp = cp;
        this.pgto = pgto;
        this.pri = pri;
    }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
    public Endereco getEnd() { return end; }
    public void setEnd(Endereco end) { this.end = end; }
    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }
    public String getPgto() { return pgto; }
    public void setPgto(String pgto) { this.pgto = pgto; }
    public boolean isPri() { return pri; }
    public void setPri(boolean pri) { this.pri = pri; }
}
