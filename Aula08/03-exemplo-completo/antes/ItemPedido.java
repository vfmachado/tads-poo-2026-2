package foodexpress.aula08.antes;

import java.math.BigDecimal;

public class ItemPedido {

    private String nm;
    private BigDecimal vl;
    private int qt;

    public ItemPedido(String nm, BigDecimal vl, int qt) {
        this.nm = nm;
        this.vl = vl;
        this.qt = qt;
    }

    public String getNm() { return nm; }
    public void setNm(String nm) { this.nm = nm; }
    public BigDecimal getVl() { return vl; }
    public void setVl(BigDecimal vl) { this.vl = vl; }
    public int getQt() { return qt; }
    public void setQt(int qt) { this.qt = qt; }
}
