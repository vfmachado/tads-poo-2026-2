package foodexpress.aula08.equilibrado;

public record ReciboDoPedido(Dinheiro subtotal, Dinheiro desconto, Dinheiro entrega, Dinheiro total) {

    public String texto() {
        return "Subtotal: " + subtotal + "\n"
                + "Desconto: " + desconto + "\n"
                + "Entrega: " + entrega + "\n"
                + "TOTAL: " + total;
    }
}
