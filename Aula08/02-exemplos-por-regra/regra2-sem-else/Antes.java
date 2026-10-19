// Assinaturas: desconto de fidelidade por tempo de contrato.
// [R2] violada: cadeia if / else if / else só para escolher um valor.
public class Antes {

    static double descontoDeFidelidade(int mesesDeContrato) {
        double desconto;
        if (mesesDeContrato >= 12) {
            desconto = 0.10;
        } else if (mesesDeContrato >= 6) {
            desconto = 0.05;
        } else {
            desconto = 0.0;
        }
        return desconto;
    }

    public static void main(String[] args) {
        System.out.println(descontoDeFidelidade(14)); // 0.1
        System.out.println(descontoDeFidelidade(7));  // 0.05
        System.out.println(descontoDeFidelidade(2));  // 0.0
    }
}
