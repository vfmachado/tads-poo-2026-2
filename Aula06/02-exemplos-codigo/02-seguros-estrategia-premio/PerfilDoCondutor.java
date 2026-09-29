package aula06.seguros;

public record PerfilDoCondutor(int idade, int sinistrosUltimosDoisAnos) {

    public PerfilDoCondutor {
        if (idade < 16) {
            throw new IllegalArgumentException("Idade mínima do condutor é 16 anos.");
        }
        if (sinistrosUltimosDoisAnos < 0) {
            throw new IllegalArgumentException("Quantidade de sinistros não pode ser negativa.");
        }
    }
}
