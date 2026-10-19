import java.util.List;

// [R1] cumprida: cada método tem um único nível, e cada nome descreve um passo.
public class Depois {

    static int pesoTotalValido(List<List<Integer>> pedidos) {
        return pedidos.stream().mapToInt(Depois::pesoValidoDe).sum();
    }

    static int pesoValidoDe(List<Integer> volumes) {
        return volumes.stream().filter(Depois::pesoValido).mapToInt(Integer::intValue).sum();
    }

    static boolean pesoValido(int peso) {
        return peso > 0;
    }

    public static void main(String[] args) {
        System.out.println(pesoTotalValido(List.of(List.of(5, -1, 3), List.of(0, 10)))); // 18
    }
}
