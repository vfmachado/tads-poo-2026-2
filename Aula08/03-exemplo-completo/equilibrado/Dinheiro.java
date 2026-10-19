package foodexpress.aula08.equilibrado;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Mantido como objeto de valor: dinheiro é o caso em que "primitive
// obsession" mais dói (arredondamento, escala, comparação). Aqui a regra R3
// se paga.
public record Dinheiro(BigDecimal valor) {

    public static final Dinheiro ZERO = new Dinheiro(BigDecimal.ZERO);

    public Dinheiro {
        valor = valor.setScale(2, RoundingMode.HALF_UP);
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
    public String toString() {
        return "R$ " + valor;
    }
}
