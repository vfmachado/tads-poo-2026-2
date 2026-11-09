// [LSP] cumprido: a hierarquia reflete o que cada tipo REALMENTE sabe fazer.
// Estornar é uma capacidade de alguns pagamentos, não de todos; por isso é um tipo separado.
// Quem precisa estornar pede um Estornavel; quem só precisa autorizar pede um Pagamento.
import java.util.List;

public class Depois {

    interface Pagamento {
        void autorizar(double valor);
    }

    interface Estornavel extends Pagamento {
        void estornar(double valor);
    }

    static class PagamentoPix implements Estornavel {
        public void autorizar(double valor) { System.out.println("PIX autorizado: " + valor); }

        public void estornar(double valor) { System.out.println("PIX estornado: " + valor); }
    }

    static class PagamentoEmDinheiro implements Pagamento {
        public void autorizar(double valor) { System.out.println("Dinheiro recebido: " + valor); }
        // Sem estornar(): o compilador impede o erro, em vez de deixá-lo para a execução.
    }

    static void cancelarTodos(List<Estornavel> pagamentos) {
        for (Estornavel pagamento : pagamentos) {
            pagamento.estornar(50.0); // vale para QUALQUER Estornavel, sem exceção e sem instanceof
        }
    }

    public static void main(String[] args) {
        new PagamentoEmDinheiro().autorizar(50.0);
        cancelarTodos(List.of(new PagamentoPix()));
        // cancelarTodos(List.of(new PagamentoEmDinheiro())); // não compila: exatamente o que queremos
    }
}
