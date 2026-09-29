package aula06.controleacesso;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Superclasse comum a todo vínculo com o sistema de controle de acesso.
 * Diferente do Cenário 1, aqui a herança modela uma especialização real:
 * Funcionario e Terceirizado SÃO um Usuario, compartilham identidade
 * (login, nome) e comportamento (bloquear/desbloquear), e qualquer rotina
 * escrita para Usuario continua correta para as duas subclasses — não há
 * método herdado que precise ser "desativado" em nenhuma delas.
 */
public abstract class Usuario {

    private final String login;
    private final String nome;
    private final LocalDate dataDeVinculo;
    private boolean ativo;

    protected Usuario(String login, String nome, LocalDate dataDeVinculo) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login é obrigatório.");
        }
        this.login = login;
        this.nome = Objects.requireNonNull(nome, "Nome é obrigatório.");
        this.dataDeVinculo = Objects.requireNonNull(dataDeVinculo, "Data de vínculo é obrigatória.");
        this.ativo = true;
    }

    public abstract int nivelDeAcessoPadrao();

    public String login() {
        return login;
    }

    public String nome() {
        return nome;
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public void bloquear() {
        exigirAtivo();
        this.ativo = false;
    }

    public void reativar() {
        exigirEstado(!ativo, "Usuário " + login + " já está ativo.");
        this.ativo = true;
    }

    protected void exigirAtivo() {
        exigirEstado(ativo, "Usuário " + login + " já está bloqueado.");
    }

    private void exigirEstado(boolean condicao, String mensagemSeInvalida) {
        if (!condicao) {
            throw new IllegalStateException(mensagemSeInvalida);
        }
    }
}
