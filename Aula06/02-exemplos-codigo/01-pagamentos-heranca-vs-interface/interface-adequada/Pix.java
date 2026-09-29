package aula06.pagamentos.interfaces;

import java.math.BigDecimal;

public final class Pix implements MeioDePagamento {

    private final String chaveRecebedor;

    public Pix(String chaveRecebedor) {
        this.chaveRecebedor = chaveRecebedor;
    }

    @Override
    public ResultadoAutorizacao autorizar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            return ResultadoAutorizacao.recusado("Valor inválido.");
        }
        // Liquidação instantânea: mesma razão de Boleto para não implementar
        // CapturavelEmDuasEtapas.
        return ResultadoAutorizacao.aprovado("PIX-" + chaveRecebedor.hashCode());
    }
}
