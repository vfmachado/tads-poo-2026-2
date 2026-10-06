package aula07.semrecord;

import java.util.Objects;

// Classe "portadora de dados" escrita à mão: 4 atributos, ~45 linhas,
// e nenhuma regra de negócio. Quase tudo aqui é boilerplate.
public final class Endereco {

    private final String rua;
    private final String numero;
    private final String cidade;
    private final String cep;

    public Endereco(String rua, String numero, String cidade, String cep) {
        this.rua = Objects.requireNonNull(rua, "rua");
        this.numero = Objects.requireNonNull(numero, "numero");
        this.cidade = Objects.requireNonNull(cidade, "cidade");
        this.cep = Objects.requireNonNull(cep, "cep");
    }

    public String getRua()    { return rua; }
    public String getNumero() { return numero; }
    public String getCidade() { return cidade; }
    public String getCep()    { return cep; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco outro = (Endereco) o;
        return rua.equals(outro.rua)
                && numero.equals(outro.numero)
                && cidade.equals(outro.cidade)
                && cep.equals(outro.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, numero, cidade, cep);
    }

    @Override
    public String toString() {
        return "Endereco{rua='" + rua + "', numero='" + numero
                + "', cidade='" + cidade + "', cep='" + cep + "'}";
    }
}
