// Gestão de contratos: uma classe e um método fazem tudo.
// [R7] violada: validar, calcular multa e formatar texto no mesmo método (e na mesma classe).
public class Antes {

    static class Contrato {
        private final String cliente;
        private final double mensalidade;
        private final int mesesRestantes;

        Contrato(String cliente, double mensalidade, int mesesRestantes) {
            this.cliente = cliente;
            this.mensalidade = mensalidade;
            this.mesesRestantes = mesesRestantes;
        }

        String resumoDeRescisao() {
            if (mesesRestantes < 0) {
                throw new IllegalStateException("Contrato invalido.");
            }
            double multa = mesesRestantes * mensalidade * 0.30;
            return "Cliente: " + cliente + "\nMulta de rescisao: R$ " + String.format("%.2f", multa);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Contrato("Padaria Sol", 500.0, 4).resumoDeRescisao());
    }
}
