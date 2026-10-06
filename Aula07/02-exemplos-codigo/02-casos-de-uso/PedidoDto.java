package aula07.usos;

import java.math.BigDecimal;
import java.util.List;

// Caso 4: DTO — dados que atravessam uma fronteira (API, camada de apresentação)
// sem identidade nem ciclo de vida. A cópia defensiva no construtor compacto
// garante imutabilidade real mesmo com atributo do tipo List.
public record PedidoDto(long id, String cliente, List<String> itens, BigDecimal total) {

    public PedidoDto {
        itens = List.copyOf(itens); // sem isso, quem criou a lista ainda poderia alterá-la
    }
}
