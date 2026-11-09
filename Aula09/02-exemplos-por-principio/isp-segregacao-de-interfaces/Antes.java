// Cardápio: uma interface única para ler e editar.
// [ISP] violado: o cardápio do site só LÊ, mas é obrigado a implementar operações de edição.
import java.util.ArrayList;
import java.util.List;

public class Antes {

    interface FonteDeCardapio {
        List<String> listar();

        void adicionar(String prato);

        void remover(String prato);
    }

    static class CardapioDoSite implements FonteDeCardapio {
        private final List<String> pratos = List.of("Feijoada", "Risoto");

        public List<String> listar() { return pratos; }

        public void adicionar(String prato) { throw new UnsupportedOperationException("site e somente leitura"); }

        public void remover(String prato) { throw new UnsupportedOperationException("site e somente leitura"); }
    }

    static class CardapioDaCozinha implements FonteDeCardapio {
        private final List<String> pratos = new ArrayList<>(List.of("Feijoada"));

        public List<String> listar() { return pratos; }

        public void adicionar(String prato) { pratos.add(prato); }

        public void remover(String prato) { pratos.remove(prato); }
    }

    // Só precisa listar, mas depende de uma interface com 3 métodos: qualquer mudança em
    // adicionar/remover recompila e re-testa quem só lê.
    static void imprimir(FonteDeCardapio fonte) {
        fonte.listar().forEach(System.out::println);
    }

    public static void main(String[] args) {
        imprimir(new CardapioDoSite());
        try {
            new CardapioDoSite().adicionar("Pizza");
        } catch (UnsupportedOperationException erro) {
            System.out.println("FALHA: " + erro.getMessage());
        }
    }
}
