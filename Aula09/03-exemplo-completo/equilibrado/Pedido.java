package foodexpress.aula09.equilibrado;

import java.math.BigDecimal;

public record Pedido(String id, String emailDoCliente, BigDecimal subtotal, Frete frete, Pagamento pagamento) {
}
