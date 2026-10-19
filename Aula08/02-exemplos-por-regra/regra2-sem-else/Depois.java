// [R2] cumprida com cláusulas de guarda (retorno antecipado).
// Quando a variação é de ALGORITMO e cresce (ex.: um tipo de plano por regra),
// o próximo passo seria polimorfismo; aqui, para valores, guardas bastam.
public class Depois {

    static double descontoDeFidelidade(int mesesDeContrato) {
        if (mesesDeContrato >= 12) {
            return 0.10;
        }
        if (mesesDeContrato >= 6) {
            return 0.05;
        }
        return 0.0;
    }

    public static void main(String[] args) {
        System.out.println(descontoDeFidelidade(14)); // 0.1
        System.out.println(descontoDeFidelidade(7));  // 0.05
        System.out.println(descontoDeFidelidade(2));  // 0.0
    }
}
