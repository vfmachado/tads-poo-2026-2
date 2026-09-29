package aula06.pagamentos.heranca;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<MeioDePagamento> pagamentosDoDia = List.of(
                new CartaoDeCredito(new BigDecimal("150.00"), "4111********1111"),
                new Boleto(new BigDecimal("89.90"), "34191.79001 01043.510047..."),
                new Pix(new BigDecimal("42.00"), "financeiro@loja.com")
        );

        for (MeioDePagamento pagamento : pagamentosDoDia) {
            pagamento.processar();
        }

        // Uma rotina financeira genérica, escrita para "qualquer meio de
        // pagamento", tenta capturar todos os pagamentos autorizados —
        // comportamento correto para CartaoDeCredito, mas que quebra em
        // tempo de execução para os outros dois, mesmo eles sendo,
        // formalmente, do mesmo tipo MeioDePagamento.
        for (MeioDePagamento pagamento : pagamentosDoDia) {
            try {
                pagamento.capturar();
            } catch (UnsupportedOperationException e) {
                System.out.println("Falha ao capturar " + pagamento.getClass().getSimpleName()
                        + ": " + e.getMessage());
            }
        }
    }
}
