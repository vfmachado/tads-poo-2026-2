package aula06.pagamentos.heranca;

import java.math.BigDecimal;

/**
 * Superclasse comum para os meios de pagamento aceitos pelo checkout.
 */
public abstract class MeioDePagamento {

    protected final BigDecimal valor;
    protected String numeroAutorizacao;
    protected boolean autorizado;

    protected MeioDePagamento(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo.");
        }
        this.valor = valor;
        this.autorizado = false;
    }

    public abstract void processar();

    /**
     * Segunda etapa de uma autorização em duas fases (pré-autorização + captura).
     * Faz sentido para cartão de crédito; não existe em boleto nem em Pix — por
     * isso a implementação padrão aqui é apenas um "melhor esforço" que lança
     * exceção quando a subclasse não sobrescreve o comportamento.
     */
    public void capturar() {
        throw new UnsupportedOperationException(
                "Este meio de pagamento não suporta captura em duas etapas.");
    }

    public String numeroAutorizacao() {
        return numeroAutorizacao;
    }

    public boolean estaAutorizado() {
        return autorizado;
    }
}
