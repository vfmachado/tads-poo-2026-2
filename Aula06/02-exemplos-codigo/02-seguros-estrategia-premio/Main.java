package aula06.seguros;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        SeletorDeCalculadora seletor = new SeletorDeCalculadora();
        BigDecimal valorSegurado = new BigDecimal("60000.00");

        Apolice apoliceCondutorExperiente = Apolice.emitir(
                1L, new PerfilDoCondutor(40, 0), valorSegurado, seletor);

        Apolice apoliceCondutorJovem = Apolice.emitir(
                2L, new PerfilDoCondutor(21, 0), valorSegurado, seletor);

        Apolice apoliceComSinistros = Apolice.emitir(
                3L, new PerfilDoCondutor(45, 2), valorSegurado, seletor);

        System.out.println("Condutor experiente, sem sinistros: " + apoliceCondutorExperiente.premioAnual());
        System.out.println("Condutor jovem: " + apoliceCondutorJovem.premioAnual());
        System.out.println("Condutor com 2 sinistros recentes: " + apoliceComSinistros.premioAnual());

        // Introduzir uma quarta política (ex.: desconto para veículo com
        // rastreador) exige apenas uma nova classe implementando
        // CalculadoraDePremio e um ajuste no SeletorDeCalculadora — nem
        // Apolice, nem o restante do sistema que já depende da interface
        // precisam ser tocados.
    }
}
