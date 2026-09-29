package aula06.controleacesso;

import java.time.LocalDate;

public class Funcionario extends Usuario {

    private final String departamento;

    public Funcionario(String login, String nome, LocalDate dataDeVinculo, String departamento) {
        super(login, nome, dataDeVinculo);
        this.departamento = departamento;
    }

    @Override
    public int nivelDeAcessoPadrao() {
        return 2;
    }

    public String departamento() {
        return departamento;
    }
}
