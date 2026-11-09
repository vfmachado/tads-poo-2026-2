// [OCP] cumprido: a regra "calcular desconto" está FECHADA para modificação e ABERTA para extensão.
// A categoria ESTUDANTE (abaixo) foi acrescentada SEM tocar em nada que já existia.
//
// Cuidado (ver 04-erros-frequentes): só vale criar o ponto de extensão onde a variação é REAL.
// Para uma decisão binária estável, um if simples continua sendo o design correto.
public class Depois {

    interface RegraDeDesconto {
        double aplicar(double valor);
    }

    static class SemDesconto implements RegraDeDesconto {
        public double aplicar(double valor) { return valor; }
    }

    static class DescontoPercentual implements RegraDeDesconto {
        private final double fracao;

        DescontoPercentual(double fracao) {
            this.fracao = fracao;
        }

        public double aplicar(double valor) { return valor * (1 - fracao); }
    }

    // --- Extensão: novo requisito = novo arquivo/classe; nenhuma linha acima foi alterada. ---
    static class DescontoEstudante implements RegraDeDesconto {
        public double aplicar(double valor) { return Math.max(valor - 15.0, 0); }
    }

    static double cobrar(RegraDeDesconto regra, double valor) {
        return regra.aplicar(valor); // este código nunca mais muda por causa de uma categoria nova
    }

    public static void main(String[] args) {
        System.out.println(cobrar(new SemDesconto(), 100));            // 100.0
        System.out.println(cobrar(new DescontoPercentual(0.10), 100)); // 90.0
        System.out.println(cobrar(new DescontoPercentual(0.20), 100)); // 80.0
        System.out.println(cobrar(new DescontoEstudante(), 100));      // 85.0
    }
}
