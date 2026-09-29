package aula06.pagamentos.interfaces;

import java.math.BigDecimal;

public final class CartaoDeCredito implements MeioDePagamento, CapturavelEmDuasEtapas {

    private final String numeroMascarado;

    public CartaoDeCredito(String numeroMascarado) {
        this.numeroMascarado = numeroMascarado;
    }

    @Override
    public ResultadoAutorizacao autorizar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            return ResultadoAutorizacao.recusado("Valor inválido.");
        }
        return ResultadoAutorizacao.aprovado("AUTH-" + numeroMascarado.hashCode());
    }

    @Override
    public ResultadoAutorizacao capturar(String codigoAutorizacao) {
        // Efetiva a cobrança de uma pré-autorização junto à operadora do cartão.
        return ResultadoAutorizacao.aprovado("CAPTURA-" + codigoAutorizacao);
    }
}
