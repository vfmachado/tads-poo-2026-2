package foodexpress.aula08.depois;

// [R3] Encapsula o double e a regra "distância não é negativa".
public final class DistanciaEmKm {

    private final double km;

    public DistanciaEmKm(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("Distancia nao pode ser negativa.");
        }
        this.km = km;
    }

    public boolean naoExcede(double limiteEmKm) {
        return km <= limiteEmKm;
    }
}
