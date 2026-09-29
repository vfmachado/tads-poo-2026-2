package aula06.pagamentos.heranca;

import java.math.BigDecimal;

public final class Pix extends MeioDePagamento {

    private final String chaveRecebedor;

    public Pix(BigDecimal valor, String chaveRecebedor) {
        super(valor);
        this.chaveRecebedor = chaveRecebedor;
    }

    @Override
    public void processar() {
        // Pix é liquidado instantaneamente: também não há duas etapas.
        this.numeroAutorizacao = "PIX-" + chaveRecebedor.hashCode();
        this.autorizado = true;
    }

    // Mesma situação de Boleto: capturar() herda o comportamento que lança
    // UnsupportedOperationException.
}
