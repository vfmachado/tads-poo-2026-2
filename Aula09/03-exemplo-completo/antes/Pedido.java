package foodexpress.aula09.antes;

import java.math.BigDecimal;

// Dado puro. Tipo de atendimento e forma de pagamento são Strings: quem decide o que elas significam é o serviço.
public record Pedido(String id, String emailDoCliente, BigDecimal subtotal, String tipo, String forma) {
}
