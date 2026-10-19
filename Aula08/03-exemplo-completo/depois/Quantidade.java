package foodexpress.aula08.depois;

// [R3] Encapsula o int e a regra "quantidade é positiva".
public final class Quantidade {

    private final int unidades;

    public Quantidade(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        this.unidades = unidades;
    }

    // Tell, Don't Ask: em vez de expor "unidades" por um getter, a
    // Quantidade sabe calcular o valor total de um preço unitário.
    public Dinheiro valorTotalDe(Dinheiro precoUnitario) {
        return precoUnitario.multiplicadoPor(unidades);
    }
}
