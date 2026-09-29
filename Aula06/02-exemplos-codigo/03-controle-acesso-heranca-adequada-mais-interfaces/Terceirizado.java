package aula06.controleacesso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Terceirizado extends Usuario implements Auditavel {

    private final String empresaContratada;
    private final List<String> acessosAuditados = new ArrayList<>();

    public Terceirizado(String login, String nome, LocalDate dataDeVinculo, String empresaContratada) {
        super(login, nome, dataDeVinculo);
        this.empresaContratada = empresaContratada;
    }

    @Override
    public int nivelDeAcessoPadrao() {
        return 1;
    }

    @Override
    public void registrarAcessoParaAuditoria(String recursoAcessado) {
        exigirAtivo();
        acessosAuditados.add(recursoAcessado);
    }

    public List<String> acessosAuditados() {
        return List.copyOf(acessosAuditados);
    }

    public String empresaContratada() {
        return empresaContratada;
    }
}
