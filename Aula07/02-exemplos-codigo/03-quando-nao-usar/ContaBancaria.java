package aula07.naousar;

import java.math.BigDecimal;

// Contraexemplo: ContaBancaria tem identidade e estado que muda ao longo do tempo,
// e protege invariantes (saldo nunca negativo) através de comandos.
// Modelar isso como record seria um erro:
//   - todos os atributos de um record são final, então o saldo não poderia mudar;
//   - "mudar" o saldo geraria outro objeto, e o equals() gerado trataria duas
//     contas de mesmo número e mesmo saldo como a MESMA conta.
// Entidades continuam sendo classes.
public class ContaBancaria {

    private final String numero;
    private BigDecimal saldo;

    public ContaBancaria(String numero, BigDecimal saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public void sacar(BigDecimal valor) {
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }
        saldo = saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        saldo = saldo.add(valor);
    }

    public BigDecimal saldo() {
        return saldo;
    }

    // A identidade é o número da conta, não o conjunto de todos os atributos.
    @Override
    public boolean equals(Object o) {
        return o instanceof ContaBancaria outra && numero.equals(outra.numero);
    }

    @Override
    public int hashCode() {
        return numero.hashCode();
    }
}
