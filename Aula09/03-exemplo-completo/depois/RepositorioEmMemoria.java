package foodexpress.aula09.depois;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEmMemoria implements RepositorioDePedidos {

    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public int quantidade() {
        return pedidos.size();
    }
}
