package foodexpress.aula08.equilibrado;

import java.math.BigDecimal;

// Um enum com dados no lugar de interface + 3 classes + catálogo + código +
// exigência. Os três cupons variam apenas em DADOS (mínimo, percentual,
// isenção de frete), não em ALGORITMO — logo, polimorfismo de classes seria
// abstração prematura. Quando um cupom exigir lógica própria (ex.: "leve 3,
// pague 2"), a migração para interface é uma refatoração pequena e localizada.
public enum Cupom {

    NENHUM("0.00", "0", false),
    VALE10("100.00", "0.10", false),
    FRETEGRATIS("60.00", "0", true);

    private final Dinheiro subtotalMinimo;
    private final BigDecimal fracaoDeDesconto;
    private final boolean isentaEntrega;

    Cupom(String subtotalMinimo, String fracaoDeDesconto, boolean isentaEntrega) {
        this.subtotalMinimo = Dinheiro.reais(subtotalMinimo);
        this.fracaoDeDesconto = new BigDecimal(fracaoDeDesconto);
        this.isentaEntrega = isentaEntrega;
    }

    public static Cupom pelo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return NENHUM;
        }
        return switch (codigo.strip()) {
            case "VALE10" -> VALE10;
            case "FRETEGRATIS" -> FRETEGRATIS;
            default -> throw new IllegalArgumentException("Cupom desconhecido: " + codigo);
        };
    }

    public Dinheiro descontoNoSubtotal(Dinheiro subtotal) {
        exigirSubtotalMinimo(subtotal);
        return subtotal.percentual(fracaoDeDesconto);
    }

    public Dinheiro descontoNaEntrega(Dinheiro subtotal, Dinheiro taxaBase) {
        exigirSubtotalMinimo(subtotal);
        return isentaEntrega ? taxaBase : Dinheiro.ZERO;
    }

    private void exigirSubtotalMinimo(Dinheiro subtotal) {
        if (subtotal.menorQue(subtotalMinimo)) {
            throw new IllegalArgumentException(
                    "Cupom " + name() + " exige subtotal minimo de " + subtotalMinimo);
        }
    }
}
