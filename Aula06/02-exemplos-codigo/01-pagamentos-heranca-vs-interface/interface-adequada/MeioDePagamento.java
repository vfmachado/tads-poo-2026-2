package aula06.pagamentos.interfaces;

import java.math.BigDecimal;

/**
 * Contrato mínimo comum a todos os meios de pagamento: autorizar um valor.
 * Nenhuma capacidade que não seja universal (como captura em duas etapas)
 * entra aqui — cada capacidade adicional é um contrato separado, ver
 * {@link CapturavelEmDuasEtapas}.
 */
public interface MeioDePagamento {
    ResultadoAutorizacao autorizar(BigDecimal valor);
}
