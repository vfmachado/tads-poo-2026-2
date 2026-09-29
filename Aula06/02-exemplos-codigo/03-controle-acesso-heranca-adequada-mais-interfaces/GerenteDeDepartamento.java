package aula06.controleacesso;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Especialização real de Funcionario (herança continua apropriada aqui:
 * um gerente É um funcionário, com todo o comportamento de Funcionario, e
 * pode substituir um Funcionario em qualquer lugar do sistema sem quebrar
 * expectativas). A capacidade de aprovar despesa é adicionada via
 * interface, não via mais um nível de especialização de Usuario.
 */
public final class GerenteDeDepartamento extends Funcionario implements PodeAprovarDespesa {

    private final BigDecimal limiteDeAprovacao;

    public GerenteDeDepartamento(String login, String nome, LocalDate dataDeVinculo,
                                  String departamento, BigDecimal limiteDeAprovacao) {
        super(login, nome, dataDeVinculo, departamento);
        this.limiteDeAprovacao = limiteDeAprovacao;
    }

    @Override
    public int nivelDeAcessoPadrao() {
        return 4;
    }

    @Override
    public BigDecimal limiteDeAprovacao() {
        return limiteDeAprovacao;
    }
}
