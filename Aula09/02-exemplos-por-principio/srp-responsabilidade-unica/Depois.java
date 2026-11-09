// [SRP] cumprido: cada classe tem UM motivo para mudar e um "ator" que pede a mudança:
//   - TotalDeVendas   -> o financeiro (regra de cálculo)
//   - FormatoTexto    -> quem consome o relatório (apresentação)
//   - GravadorDeArquivo -> a infraestrutura (destino)
// Atenção ao exagero: se o formato NUNCA variar e só existir um destino, separar tudo isso seria
// fragmentação sem ganho. Aqui a separação se paga porque as três coisas mudam por motivos distintos.
import java.util.List;

public class Depois {

    static class TotalDeVendas {
        private final List<Double> vendas;

        TotalDeVendas(List<Double> vendas) {
            this.vendas = List.copyOf(vendas);
        }

        double valor() {
            return vendas.stream().mapToDouble(Double::doubleValue).sum();
        }
    }

    static class FormatoTexto {
        String formatar(double total) {
            return "Total de vendas: R$ " + String.format("%.2f", total);
        }
    }

    static class GravadorDeArquivo {
        void gravar(String caminho, String conteudo) {
            System.out.println("[gravando em " + caminho + "] " + conteudo);
        }
    }

    public static void main(String[] args) {
        TotalDeVendas total = new TotalDeVendas(List.of(10.0, 20.5, 30.0));
        String texto = new FormatoTexto().formatar(total.valor());
        new GravadorDeArquivo().gravar("vendas.txt", texto);
    }
}
