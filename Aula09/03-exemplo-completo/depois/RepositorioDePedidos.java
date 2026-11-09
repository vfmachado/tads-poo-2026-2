package foodexpress.aula09.depois;

// [D] O serviço (módulo de alto nível, regra de negócio) é dono desta abstração: ela diz o que
// o serviço PRECISA, não o que o banco OFERECE.
public interface RepositorioDePedidos {

    void salvar(Pedido pedido);

    int quantidade();
}
