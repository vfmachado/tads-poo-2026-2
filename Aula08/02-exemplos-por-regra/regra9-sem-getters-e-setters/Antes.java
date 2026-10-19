// Plataforma financeira: saque em conta.
// [R9] violada: a regra "não sacar além do saldo" está FORA da conta,
// e o setter permite pular a regra por completo.
public class Antes {

    static class Conta {
        private double saldo;
        Conta(double saldo) { this.saldo = saldo; }
        double getSaldo() { return saldo; }
        void setSaldo(double saldo) { this.saldo = saldo; }
    }

    public static void main(String[] args) {
        Conta conta = new Conta(100.0);
        double valor = 150.0;
        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
        }
        conta.setSaldo(-9999.0); // nada impede
        System.out.println(conta.getSaldo());
    }
}
