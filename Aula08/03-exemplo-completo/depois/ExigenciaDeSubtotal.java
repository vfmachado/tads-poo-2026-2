package foodexpress.aula08.depois;

// Nasceu da regra R8 (no máximo duas variáveis de instância): CupomPercentual
// teria código + mínimo + percentual. Agrupar "código + mínimo" cria um
// conceito coeso — a exigência que o cupom faz sobre o subtotal — e ainda
// elimina a duplicação da mesma verificação nos dois tipos de cupom.
public final class ExigenciaDeSubtotal {

    private final CodigoDeCupom codigo;
    private final Dinheiro minimo;

    public ExigenciaDeSubtotal(CodigoDeCupom codigo, Dinheiro minimo) {
        this.codigo = codigo;
        this.minimo = minimo;
    }

    public void verificar(Dinheiro subtotal) {
        if (subtotal.menorQue(minimo)) {
            throw new IllegalArgumentException(
                    "Cupom " + codigo + " exige subtotal minimo de " + minimo);
        }
    }
}
