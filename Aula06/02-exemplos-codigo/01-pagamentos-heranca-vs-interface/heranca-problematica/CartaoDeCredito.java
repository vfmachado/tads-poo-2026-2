package aula06.pagamentos.heranca;

import java.math.BigDecimal;

public final class CartaoDeCredito extends MeioDePagamento {

    private final String numeroMascarado;

    public CartaoDeCredito(BigDecimal valor, String numeroMascarado) {
        super(valor);
        this.numeroMascarado = numeroMascarado;
    }

    @Override
    public void processar() {
        // Pré-autorização: reserva o valor no limite do cartão, mas ainda não
        // efetiva a cobrança — por isso cartão de crédito precisa de capturar().
        this.numeroAutorizacao = "AUTH-" + numeroMascarado.hashCode();
        this.autorizado = true;
    }

    @Override
    public void capturar() {
        if (!autorizado) {
            throw new IllegalStateException("Não é possível capturar um pagamento não autorizado.");
        }
        // Efetiva a cobrança da pré-autorização junto à operadora do cartão.
        System.out.println("Captura efetivada para " + numeroAutorizacao());
    }
}
