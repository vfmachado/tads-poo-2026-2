// Financeiro: juros por atraso de pagamento.
// [R6] violada: o que são "vl", "dt" e "tx"? É preciso decifrar.
public class Antes {

    static double calcTx(double vl, int dt) {
        double tx = 0.01;
        return vl * tx * dt;
    }

    public static void main(String[] args) {
        System.out.println(calcTx(200.0, 5)); // 10.0
    }
}
