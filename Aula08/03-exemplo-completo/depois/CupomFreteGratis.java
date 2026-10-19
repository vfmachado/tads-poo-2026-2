package foodexpress.aula08.depois;

public final class CupomFreteGratis implements Cupom {

    private final ExigenciaDeSubtotal exigencia;

    public CupomFreteGratis(ExigenciaDeSubtotal exigencia) {
        this.exigencia = exigencia;
    }

    @Override
    public Dinheiro descontoNoSubtotal(Dinheiro subtotal) {
        exigencia.verificar(subtotal);
        return Dinheiro.ZERO;
    }

    @Override
    public Dinheiro descontoNaEntrega(Dinheiro subtotal, Dinheiro taxaBase) {
        exigencia.verificar(subtotal);
        return taxaBase;
    }
}
