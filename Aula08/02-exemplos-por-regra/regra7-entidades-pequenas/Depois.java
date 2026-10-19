// [R7] cumprida: cada método faz uma coisa; a formatação saiu para outra classe
// (outra razão para mudar: o texto do relatório muda por motivos diferentes da multa).
// Cuidado com o exagero: não vale dividir ainda mais se isso piorar os nomes.
public class Depois {

    static class Contrato {
        private final String cliente;
        private final double mensalidade;
        private final int mesesRestantes;

        Contrato(String cliente, double mensalidade, int mesesRestantes) {
            if (mesesRestantes < 0) {
                throw new IllegalArgumentException("Contrato invalido.");
            }
            this.cliente = cliente;
            this.mensalidade = mensalidade;
            this.mesesRestantes = mesesRestantes;
        }

        double multaDeRescisao() {
            return mesesRestantes * mensalidade * 0.30;
        }

        String cliente() {
            return cliente;
        }
    }

    static class ResumoDeRescisao {
        static String texto(Contrato contrato) {
            return "Cliente: " + contrato.cliente()
                    + "\nMulta de rescisao: R$ " + String.format("%.2f", contrato.multaDeRescisao());
        }
    }

    public static void main(String[] args) {
        System.out.println(ResumoDeRescisao.texto(new Contrato("Padaria Sol", 500.0, 4)));
    }
}
