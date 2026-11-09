package foodexpress.aula09.depois;

import java.math.BigDecimal;

// O pedido carrega as ESTRATÉGIAS (frete e pagamento), não Strings que alguém precisa interpretar.
public record Pedido(String id, String emailDoCliente, BigDecimal subtotal,
                     CalculoDeFrete frete, FormaDePagamento pagamento) {
}
