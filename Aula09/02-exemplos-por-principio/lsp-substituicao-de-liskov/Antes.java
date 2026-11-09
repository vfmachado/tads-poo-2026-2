// Pagamentos: toda forma de pagamento "é um" Pagamento... mas dinheiro vivo não estorna.
// [LSP] violado: o subtipo quebra o contrato do supertipo; quem usa Pagamento precisa saber o subtipo.
import java.util.List;

public class Antes {

    static abstract class Pagamento {
        abstract void autorizar(double valor);

        abstract void estornar(double valor); // promessa: "qualquer pagamento pode ser estornado"
    }

    static class PagamentoPix extends Pagamento {
        void autorizar(double valor) { System.out.println("PIX autorizado: " + valor); }

        void estornar(double valor) { System.out.println("PIX estornado: " + valor); }
    }

    static class PagamentoEmDinheiro extends Pagamento {
        void autorizar(double valor) { System.out.println("Dinheiro recebido: " + valor); }

        void estornar(double valor) {
            throw new UnsupportedOperationException("dinheiro vivo nao estorna"); // [LSP] promessa quebrada
        }
    }

    // Código cliente escrito contra o supertipo: parece correto, mas explode.
    static void cancelarTodos(List<Pagamento> pagamentos) {
        for (Pagamento pagamento : pagamentos) {
            pagamento.estornar(50.0);
        }
    }

    public static void main(String[] args) {
        try {
            cancelarTodos(List.of(new PagamentoPix(), new PagamentoEmDinheiro()));
        } catch (UnsupportedOperationException erro) {
            System.out.println("FALHA: " + erro.getMessage());
        }
        // A "correção" típica é um instanceof dentro de cancelarTodos: o cliente passa a conhecer o
        // subtipo, e o polimorfismo foi embora.
    }
}
