package foodexpress.aula08.depois;

// Saída do fechamento. Quatro componentes — acima do limite da R8 — mas
// é um agregado de leitura sem comportamento próprio além de se descrever.
// Ver discussão no README: este é um caso típico de flexibilização.
public record ReciboDoPedido(Dinheiro subtotal, Dinheiro desconto, Dinheiro entrega, Dinheiro total) {

    public String texto() {
        return "Subtotal: " + subtotal + "\n"
                + "Desconto: " + desconto + "\n"
                + "Entrega: " + entrega + "\n"
                + "TOTAL: " + total;
    }
}
