package foodexpress.aula10;

import java.math.BigDecimal;

public record Pedido(String id, String emailDoCliente, BigDecimal subtotal, Frete frete, Pagamento pagamento) {
}
