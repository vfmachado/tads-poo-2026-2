package foodexpress.aula09.depois;

import java.math.BigDecimal;

// [S] Única razão para mudar: a ORDEM do fluxo de fechamento (frete, pagamento, gravar, avisar).
// [D] Recebe as dependências pelo construtor: depende só de abstrações.
// [O] Não tem if por tipo de atendimento nem por forma de pagamento.
public class ServicoDePedido {

    private final RepositorioDePedidos repositorio;
    private final Notificador notificador;

    public ServicoDePedido(RepositorioDePedidos repositorio, Notificador notificador) {
        this.repositorio = repositorio;
        this.notificador = notificador;
    }

    public ReciboDoPedido fechar(Pedido pedido) {
        BigDecimal frete = pedido.frete().para(pedido.subtotal());
        BigDecimal total = pedido.subtotal().add(frete);
        BigDecimal aPagar = pedido.pagamento().valorAPagar(total);

        ReciboDoPedido recibo = new ReciboDoPedido(pedido.id(), pedido.subtotal(), frete,
                total, pedido.pagamento().nome(), aPagar);

        repositorio.salvar(pedido);
        notificador.avisar(pedido.emailDoCliente(), recibo.mensagemDeConfirmacao());
        return recibo;
    }
}
