package foodexpress.aula08.depois;

import java.util.Arrays;

// Substitui a cadeia "if / else if / else" sobre a distância por uma
// tabela de faixas. Acrescentar uma faixa nova é acrescentar uma constante.
public enum FaixaDeEntrega {

    ATE_3_KM(3, "5.00"),
    ATE_8_KM(8, "9.00");

    private final double limiteEmKm;
    private final Dinheiro taxa;

    FaixaDeEntrega(double limiteEmKm, String taxa) {
        this.limiteEmKm = limiteEmKm;
        this.taxa = Dinheiro.reais(taxa);
    }

    // [R1] Um único nível de indentação: a busca é feita por stream
    // (a cadeia de chamadas fluente é a exceção usual da regra R5).
    public static FaixaDeEntrega para(DistanciaEmKm distancia) {
        return Arrays.stream(values())
                .filter(faixa -> distancia.naoExcede(faixa.limiteEmKm))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Endereco fora da area de entrega (mais de 8 km)."));
    }

    public Dinheiro taxa() {
        return taxa;
    }
}
