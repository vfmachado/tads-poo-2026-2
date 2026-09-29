package aula06.pagamentos.interfaces;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<MeioDePagamento> pagamentosDoDia = List.of(
                new CartaoDeCredito("4111********1111"),
                new Boleto("34191.79001 01043.510047..."),
                new Pix("financeiro@loja.com")
        );

        for (MeioDePagamento pagamento : pagamentosDoDia) {
            ResultadoAutorizacao resultado = pagamento.autorizar(new BigDecimal("150.00"));
            System.out.println(pagamento.getClass().getSimpleName() + " -> " + resultado);

            // A captura só é oferecida, e só é chamada, para quem de fato
            // implementa a capacidade — sem exceção em tempo de execução,
            // sem método herdado que não se aplica.
            if (resultado.aprovado() && pagamento instanceof CapturavelEmDuasEtapas capturavel) {
                ResultadoAutorizacao captura = capturavel.capturar(resultado.codigoAutorizacao());
                System.out.println("  captura -> " + captura);
            }
        }
    }
}
