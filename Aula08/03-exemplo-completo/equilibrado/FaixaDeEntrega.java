package foodexpress.aula08.equilibrado;

import java.util.Arrays;

// Sem a classe DistanciaEmKm: a distância é um double validado no ponto de
// entrada (para) e consumido imediatamente. Não circula pelo sistema.
public enum FaixaDeEntrega {

    ATE_3_KM(3, "5.00"),
    ATE_8_KM(8, "9.00");

    private final double limiteEmKm;
    private final Dinheiro taxa;

    FaixaDeEntrega(double limiteEmKm, String taxa) {
        this.limiteEmKm = limiteEmKm;
        this.taxa = Dinheiro.reais(taxa);
    }

    public static FaixaDeEntrega para(double distanciaEmKm) {
        if (distanciaEmKm < 0) {
            throw new IllegalArgumentException("Distancia nao pode ser negativa.");
        }
        return Arrays.stream(values())
                .filter(faixa -> distanciaEmKm <= faixa.limiteEmKm)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Endereco fora da area de entrega (mais de 8 km)."));
    }

    public Dinheiro taxa() {
        return taxa;
    }
}
