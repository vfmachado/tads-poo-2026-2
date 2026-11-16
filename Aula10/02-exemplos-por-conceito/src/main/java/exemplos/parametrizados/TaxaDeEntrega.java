package exemplos.parametrizados;

public class TaxaDeEntrega {

    /**
     * Taxa em centavos, por faixa de distância:
     * até 3 km: R$ 5,00 · até 8 km: R$ 9,00 · até 15 km: R$ 14,00 · acima de 15 km: não entrega.
     */
    public int paraDistancia(int metros) {
        if (metros < 0) {
            throw new IllegalArgumentException("Distancia negativa: " + metros);
        }
        if (metros <= 3000) {
            return 500;
        }
        if (metros <= 8000) {
            return 900;
        }
        if (metros <= 15000) {
            return 1400;
        }
        throw new IllegalArgumentException("Fora da area de entrega: " + metros + " m");
    }
}
