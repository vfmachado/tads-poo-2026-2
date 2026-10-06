package aula07.usos;

// Caso 2: retornar mais de um valor de um método, sem criar uma classe completa
// nem recorrer a double[] / Map<String, Object>.
public record ResumoDeNotas(double media, double maior, double menor) {

    public static ResumoDeNotas de(double... notas) {
        if (notas.length == 0) {
            throw new IllegalArgumentException("Informe ao menos uma nota");
        }
        double soma = 0;
        double maior = notas[0];
        double menor = notas[0];
        for (double n : notas) {
            soma += n;
            maior = Math.max(maior, n);
            menor = Math.min(menor, n);
        }
        return new ResumoDeNotas(soma / notas.length, maior, menor);
    }
}
