package foodexpress.aula08.equilibrado;

// Record como "comando" imutável de fechamento: cinco componentes (R8
// flexibilizada), todos fornecidos de uma vez e nunca alterados. Dois níveis
// de indentação aparecem em fechar() só onde uma regra realmente o exige
// (R1 flexibilizada); o "if" sem "else" é preferível a criar Prioridade só
// para esconder um acréscimo de R$ 6,00.
public record PedidoDeEntrega(
        ItensDoPedido itens,
        double distanciaEmKm,
        boolean prioritaria,
        Cupom cupom,
        FormaDePagamento formaDePagamento) {

    private static final Dinheiro ADICIONAL_DE_PRIORIDADE = Dinheiro.reais("6.00");

    public ReciboDoPedido fechar() {
        Dinheiro subtotal = itens.subtotal();
        Dinheiro taxaBase = FaixaDeEntrega.para(distanciaEmKm).taxa(); // um ponto a mais: fluente e legível
        Dinheiro descontoNoSubtotal = cupom.descontoNoSubtotal(subtotal);
        Dinheiro taxaCobrada = taxaBase.menos(cupom.descontoNaEntrega(subtotal, taxaBase));
        if (prioritaria) {
            taxaCobrada = taxaCobrada.mais(ADICIONAL_DE_PRIORIDADE);
        }
        Dinheiro total = subtotal.menos(descontoNoSubtotal).mais(taxaCobrada);
        formaDePagamento.validarTotal(total);
        return new ReciboDoPedido(subtotal, descontoNoSubtotal, taxaCobrada, total);
    }
}
