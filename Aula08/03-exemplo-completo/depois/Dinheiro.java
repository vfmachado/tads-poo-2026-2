package foodexpress.aula08.depois;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// [R3] Objeto de valor: dinheiro deixa de ser um BigDecimal "solto".
// Imutável; toda operação devolve um novo Dinheiro. O comportamento que
// antes estava espalhado em FechamentoPedido (somar, descontar, comparar)
// passa a morar aqui.
public final class Dinheiro {

    public static final Dinheiro ZERO = new Dinheiro(BigDecimal.ZERO);

    private final BigDecimal valor;

    private Dinheiro(BigDecimal valor) {
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static Dinheiro reais(String valor) {
        return new Dinheiro(new BigDecimal(valor));
    }

    public Dinheiro mais(Dinheiro outro) {
        return new Dinheiro(valor.add(outro.valor));
    }

    public Dinheiro menos(Dinheiro outro) {
        return new Dinheiro(valor.subtract(outro.valor));
    }

    public Dinheiro multiplicadoPor(int fator) {
        return new Dinheiro(valor.multiply(BigDecimal.valueOf(fator)));
    }

    public Dinheiro percentual(BigDecimal fracao) {
        return new Dinheiro(valor.multiply(fracao));
    }

    public boolean menorQue(Dinheiro outro) {
        return valor.compareTo(outro.valor) < 0;
    }

    public boolean maiorQue(Dinheiro outro) {
        return valor.compareTo(outro.valor) > 0;
    }

    @Override
    public boolean equals(Object outro) {
        return outro instanceof Dinheiro dinheiro && valor.equals(dinheiro.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return "R$ " + valor;
    }
}
