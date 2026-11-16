package exemplos.primeiroteste;

public class DivisorDeConta {

    /**
     * Valor que cada pessoa paga, em centavos, arredondado PARA CIMA:
     * a soma das partes nunca fica abaixo do total da conta.
     */
    public int porPessoa(int totalEmCentavos, int pessoas) {
        return (totalEmCentavos + pessoas - 1) / pessoas;
    }
}
