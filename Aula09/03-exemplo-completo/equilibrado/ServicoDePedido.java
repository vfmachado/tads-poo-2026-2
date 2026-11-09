package foodexpress.aula09.equilibrado;

import java.math.BigDecimal;

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
                total, pedido.pagamento(), aPagar);

        repositorio.salvar(pedido);
        notificador.avisar(pedido.emailDoCliente(), recibo.mensagemDeConfirmacao());
        return recibo;
    }
}
