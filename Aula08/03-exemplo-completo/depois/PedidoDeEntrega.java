package foodexpress.aula08.depois;

// Substitui FechamentoPedido + PedidoEntrega: agora o pedido sabe se fechar.
//
// [R8] Quatro variáveis de instância — acima do limite "duas" do exercício.
// Tentamos agrupar cupom + forma de pagamento em uma classe Pagamento, mas
// ela só repassava chamadas (Middle Man) e foi descartada. Decisão
// registrada: aqui a regra é deliberadamente flexibilizada.
public final class PedidoDeEntrega {

    private final ItensDoPedido itens;
    private final Entrega entrega;
    private final Cupom cupom;
    private final FormaDePagamento formaDePagamento;

    public PedidoDeEntrega(ItensDoPedido itens, Entrega entrega, Cupom cupom, FormaDePagamento formaDePagamento) {
        this.itens = itens;
        this.entrega = entrega;
        this.cupom = cupom;
        this.formaDePagamento = formaDePagamento;
    }

    // [R1] Zero estruturas de controle: um único nível, só passos nomeados.
    public ReciboDoPedido fechar() {
        Dinheiro subtotal = itens.subtotal();
        Dinheiro taxaBase = entrega.taxaBase();
        Dinheiro descontoNoSubtotal = cupom.descontoNoSubtotal(subtotal);
        Dinheiro descontoNaEntrega = cupom.descontoNaEntrega(subtotal, taxaBase);
        Dinheiro taxaCobrada = entrega.taxaCobrada(descontoNaEntrega);
        Dinheiro total = subtotal.menos(descontoNoSubtotal).mais(taxaCobrada);
        formaDePagamento.validarTotal(total);
        return new ReciboDoPedido(subtotal, descontoNoSubtotal, taxaCobrada, total);
    }
}
