package aula06.pagamentos.interfaces;

import java.math.BigDecimal;

public final class Boleto implements MeioDePagamento {

    private final String linhaDigitavel;

    public Boleto(String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
    }

    @Override
    public ResultadoAutorizacao autorizar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            return ResultadoAutorizacao.recusado("Valor inválido.");
        }
        // Compensação bancária: já é a liquidação definitiva, não existe
        // uma segunda etapa a capturar — por isso Boleto simplesmente não
        // implementa CapturavelEmDuasEtapas.
        return ResultadoAutorizacao.aprovado("BOL-" + linhaDigitavel.hashCode());
    }
}
