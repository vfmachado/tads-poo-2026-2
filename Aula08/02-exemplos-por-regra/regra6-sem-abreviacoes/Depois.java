// [R6] cumprida: os nomes revelam intenção; o código dispensa comentário.
// (Dica: o esforço de nomear também expõe que o double aqui merece virar Dinheiro — R3.)
public class Depois {

    private static final double JUROS_DIARIO = 0.01;

    static double calcularJurosDeAtraso(double valorEmReais, int diasDeAtraso) {
        return valorEmReais * JUROS_DIARIO * diasDeAtraso;
    }

    public static void main(String[] args) {
        System.out.println(calcularJurosDeAtraso(200.0, 5)); // 10.0
    }
}
