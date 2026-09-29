package aula06.seguros;

/**
 * Decide qual estratégia de cálculo se aplica a um perfil — a única classe
 * do cenário que conhece as implementações concretas de CalculadoraDePremio.
 * Apolice e o restante do sistema dependem apenas da interface.
 */
public final class SeletorDeCalculadora {

    private static final int IDADE_LIMITE_JOVEM = 25;

    public CalculadoraDePremio selecionar(PerfilDoCondutor perfil) {
        if (perfil.idade() < IDADE_LIMITE_JOVEM) {
            return new CalculoCondutorJovem();
        }
        if (perfil.sinistrosUltimosDoisAnos() > 0) {
            return new CalculoComHistoricoDeSinistros();
        }
        return new CalculoPadrao();
    }
}
