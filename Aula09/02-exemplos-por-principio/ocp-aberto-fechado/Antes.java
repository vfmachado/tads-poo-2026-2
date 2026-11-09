// Fidelidade: desconto por categoria de cliente.
// [OCP] violado: cada nova categoria obriga a EDITAR o método (e reabrir tudo que já funcionava).
public class Antes {

    static double aplicarDesconto(String categoria, double valor) {
        if (categoria.equals("COMUM")) {
            return valor;
        } else if (categoria.equals("OURO")) {
            return valor * 0.90;
        } else if (categoria.equals("PLATINA")) {
            return valor * 0.80;
        }
        throw new IllegalArgumentException("Categoria desconhecida: " + categoria);
    }

    public static void main(String[] args) {
        System.out.println(aplicarDesconto("COMUM", 100));   // 100.0
        System.out.println(aplicarDesconto("OURO", 100));    // 90.0
        System.out.println(aplicarDesconto("PLATINA", 100)); // 80.0
        // Pedido novo: categoria "ESTUDANTE". Onde você mexe? No método acima, de novo.
    }
}
