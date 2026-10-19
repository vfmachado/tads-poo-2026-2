import java.util.List;

// Logística: soma o peso válido (> 0) dos volumes de vários pedidos.
// [R1] violada: três níveis de indentação dentro de um único método.
public class Antes {

    static int pesoTotalValido(List<List<Integer>> pedidos) {
        int total = 0;
        for (List<Integer> volumes : pedidos) {
            for (int peso : volumes) {
                if (peso > 0) {
                    total += peso;
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(pesoTotalValido(List.of(List.of(5, -1, 3), List.of(0, 10)))); // 18
    }
}
