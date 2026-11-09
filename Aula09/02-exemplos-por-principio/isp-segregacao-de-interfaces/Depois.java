// [ISP] cumprido: interfaces pequenas, desenhadas a partir de quem USA (o cliente), não de quem implementa.
// Cada cliente depende apenas do que realmente chama.
import java.util.ArrayList;
import java.util.List;

public class Depois {

    interface LeitorDeCardapio {
        List<String> listar();
    }

    interface EditorDeCardapio {
        void adicionar(String prato);

        void remover(String prato);
    }

    static class CardapioDoSite implements LeitorDeCardapio {
        private final List<String> pratos = List.of("Feijoada", "Risoto");

        public List<String> listar() { return pratos; }
    }

    // Uma classe pode implementar várias interfaces pequenas quando de fato faz tudo isso.
    static class CardapioDaCozinha implements LeitorDeCardapio, EditorDeCardapio {
        private final List<String> pratos = new ArrayList<>(List.of("Feijoada"));

        public List<String> listar() { return List.copyOf(pratos); }

        public void adicionar(String prato) { pratos.add(prato); }

        public void remover(String prato) { pratos.remove(prato); }
    }

    static void imprimir(LeitorDeCardapio leitor) {
        leitor.listar().forEach(System.out::println);
    }

    static void incluirPromocao(EditorDeCardapio editor) {
        editor.adicionar("Prato do dia");
    }

    public static void main(String[] args) {
        imprimir(new CardapioDoSite());
        CardapioDaCozinha cozinha = new CardapioDaCozinha();
        incluirPromocao(cozinha);
        imprimir(cozinha);
        // incluirPromocao(new CardapioDoSite()); // não compila: o site nunca promete edição
    }
}
