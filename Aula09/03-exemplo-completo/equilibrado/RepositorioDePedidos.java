package foodexpress.aula09.equilibrado;

import java.util.ArrayList;
import java.util.List;

// DIP flexibilizado: classe concreta, sem interface. Há uma única implementação e nenhum banco real
// previsto. Ainda é INJETADA pelo construtor do serviço, então extrair a interface depois (quando surgir
// a segunda implementação ou a necessidade de um dublê) é uma refatoração mecânica e local.
public class RepositorioDePedidos {

    private final List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public int quantidade() {
        return pedidos.size();
    }
}
