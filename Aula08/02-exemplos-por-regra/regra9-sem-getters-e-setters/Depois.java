// [R9] cumprida (Tell, Don't Ask): manda-se a conta sacar; ela decide e protege a invariante.
// Ainda há um "saldo()" de consulta: devolver um valor imutável numa fronteira é a flexibilização
// usual. O que desapareceu foi o setter e a decisão feita pelo chamador.
public class Depois {

    static final class Conta {
        private double saldo;

        Conta(double saldo) {
            this.saldo = saldo;
        }

        void sacar(double valor) {
            if (valor > saldo) {
                throw new IllegalStateException("Saldo insuficiente.");
            }
            saldo -= valor;
        }

        double saldo() {
            return saldo;
        }
    }

    public static void main(String[] args) {
        Conta conta = new Conta(100.0);
        conta.sacar(30.0);
        System.out.println(conta.saldo()); // 70.0
        conta.sacar(150.0);                // lança IllegalStateException
    }
}
