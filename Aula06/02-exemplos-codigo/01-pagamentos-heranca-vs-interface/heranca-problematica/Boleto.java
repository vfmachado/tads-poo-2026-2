package aula06.pagamentos.heranca;

import java.math.BigDecimal;

public final class Boleto extends MeioDePagamento {

    private final String linhaDigitavel;

    public Boleto(BigDecimal valor, String linhaDigitavel) {
        super(valor);
        this.linhaDigitavel = linhaDigitavel;
    }

    @Override
    public void processar() {
        // Boleto é liquidado quando o banco compensa o pagamento — não existe
        // uma "pré-autorização" a capturar depois; processar() já é definitivo.
        this.numeroAutorizacao = "BOL-" + linhaDigitavel.hashCode();
        this.autorizado = true;
    }

    // capturar() NÃO é sobrescrito aqui: qualquer código que chame
    // boleto.capturar() cai direto na exceção herdada de MeioDePagamento,
    // mesmo sem nenhuma intenção de captura em duas etapas fazer sentido
    // para este meio de pagamento.
}
