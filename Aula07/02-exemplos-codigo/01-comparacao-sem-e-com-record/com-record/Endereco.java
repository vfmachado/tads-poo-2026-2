package aula07.comrecord;

import java.util.Objects;

// Mesmo conceito, mesmo comportamento observável. O compilador gera:
// atributos private final, construtor canônico, acessores (rua(), numero()...),
// equals, hashCode e toString.
public record Endereco(String rua, String numero, String cidade, String cep) {

    // Construtor compacto: só a validação; as atribuições são automáticas.
    public Endereco {
        Objects.requireNonNull(rua, "rua");
        Objects.requireNonNull(numero, "numero");
        Objects.requireNonNull(cidade, "cidade");
        Objects.requireNonNull(cep, "cep");
    }
}
