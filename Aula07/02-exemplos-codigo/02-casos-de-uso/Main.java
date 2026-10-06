package aula07.usos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    // Caso 5: record aninhado como tupla nomeada em pipelines de stream.
    record Aluno(String nome, String turma, double nota) { }

    public static void main(String[] args) {
        // Caso 1
        Intervalo i = Intervalo.de(10, 20);
        System.out.println(i + " tamanho=" + i.tamanho() + " contem(15)=" + i.contem(15));
        System.out.println("deslocado: " + i.deslocar(5) + " (original intacto: " + i + ")");
        try {
            new Intervalo(5, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Invariante protegida: " + e.getMessage());
        }

        // Caso 2
        System.out.println(ResumoDeNotas.de(7.5, 9.0, 6.0, 8.5));

        // Caso 3
        System.out.printf("Área círculo: %.2f%n", Forma.area(new Forma.Circulo(2)));
        System.out.printf("Área retângulo: %.2f%n", Forma.area(new Forma.Retangulo(3, 4)));

        // Caso 4
        List<String> itens = new ArrayList<>(List.of("Teclado", "Mouse"));
        PedidoDto pedido = new PedidoDto(1L, "Ana", itens, new BigDecimal("250.00"));
        itens.add("Monitor"); // não afeta o record
        System.out.println(pedido);

        // Caso 5
        List<Aluno> alunos = List.of(
                new Aluno("Ana", "A", 9.0),
                new Aluno("Bruno", "A", 7.0),
                new Aluno("Carla", "B", 8.0));
        Map<String, Double> mediaPorTurma = alunos.stream()
                .collect(Collectors.groupingBy(Aluno::turma, Collectors.averagingDouble(Aluno::nota)));
        System.out.println(mediaPorTurma);
    }
}
