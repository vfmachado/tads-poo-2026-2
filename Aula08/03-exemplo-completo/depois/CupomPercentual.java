package foodexpress.aula08.depois;

import java.math.BigDecimal;

public final class CupomPercentual implements Cupom {

    private final ExigenciaDeSubtotal exigencia;
    private final BigDecimal fracao; // ex.: 0.10 = 10% (primitivo residual: ver README, "o que ainda sobrou")

    public CupomPercentual(ExigenciaDeSubtotal exigencia, BigDecimal fracao) {
        this.exigencia = exigencia;
        this.fracao = fracao;
    }

    @Override
    public Dinheiro descontoNoSubtotal(Dinheiro subtotal) {
        exigencia.verificar(subtotal);
        return subtotal.percentual(fracao);
    }

    @Override
    public Dinheiro descontoNaEntrega(Dinheiro subtotal, Dinheiro taxaBase) {
        exigencia.verificar(subtotal);
        return Dinheiro.ZERO;
    }
}
