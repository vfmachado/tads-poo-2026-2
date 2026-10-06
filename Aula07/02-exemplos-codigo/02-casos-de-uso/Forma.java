package aula07.usos;

// Caso 3: records + sealed interface + pattern matching no switch (Java 21).
// O compilador garante que todos os casos foram tratados — sem default
// e sem cadeia de instanceof.
public sealed interface Forma {

    record Circulo(double raio) implements Forma { }

    record Retangulo(double largura, double altura) implements Forma { }

    static double area(Forma forma) {
        return switch (forma) {
            case Circulo c -> Math.PI * c.raio() * c.raio();
            case Retangulo(double largura, double altura) -> largura * altura; // desconstrução
        };
    }
}
