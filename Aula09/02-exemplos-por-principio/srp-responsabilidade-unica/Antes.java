// Vendas: um relatório que calcula, formata e grava.
// [SRP] violado: três razões para mudar (regra de cálculo, formato do texto, destino da gravação).
import java.util.List;

public class Antes {

    static class RelatorioDeVendas {
        private final List<Double> vendas;

        RelatorioDeVendas(List<Double> vendas) {
            this.vendas = vendas;
        }

        double total() {                       // razão 1: regra de negócio
            double soma = 0;
            for (double venda : vendas) {
                soma += venda;
            }
            return soma;
        }

        String formatar() {                    // razão 2: apresentação
            return "Total de vendas: R$ " + String.format("%.2f", total());
        }

        void gravarEmArquivo(String caminho) { // razão 3: infraestrutura
            System.out.println("[gravando em " + caminho + "] " + formatar());
        }
    }

    public static void main(String[] args) {
        RelatorioDeVendas relatorio = new RelatorioDeVendas(List.of(10.0, 20.5, 30.0));
        relatorio.gravarEmArquivo("vendas.txt");
        // Trocar o formato para HTML, ou gravar em banco, obriga a editar a mesma classe
        // que contém a regra de cálculo.
    }
}
