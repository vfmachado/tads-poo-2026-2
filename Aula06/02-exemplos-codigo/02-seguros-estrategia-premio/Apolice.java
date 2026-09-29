package aula06.seguros;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Apolice {

    private final Long id;
    private final PerfilDoCondutor perfil;
    private final BigDecimal valorSegurado;
    private final CalculadoraDePremio calculadora;

    private Apolice(Long id, PerfilDoCondutor perfil, BigDecimal valorSegurado, CalculadoraDePremio calculadora) {
        if (valorSegurado == null || valorSegurado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor segurado deve ser positivo.");
        }
        this.id = id;
        this.perfil = perfil;
        this.valorSegurado = valorSegurado;
        this.calculadora = calculadora;
    }

    /**
     * Emite a apólice já com a estratégia de cálculo definida no momento da
     * contratação — Apolice delega o cálculo, mas não decide qual estratégia
     * usar (essa responsabilidade é do SeletorDeCalculadora).
     */
    public static Apolice emitir(Long id, PerfilDoCondutor perfil, BigDecimal valorSegurado,
                                  SeletorDeCalculadora seletor) {
        CalculadoraDePremio calculadora = seletor.selecionar(perfil);
        return new Apolice(id, perfil, valorSegurado, calculadora);
    }

    public BigDecimal premioAnual() {
        return calculadora.calcular(perfil, valorSegurado).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal premioMensal() {
        return premioAnual().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_EVEN);
    }

    public Long id() {
        return id;
    }
}
