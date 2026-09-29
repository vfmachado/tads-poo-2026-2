package aula06.controleacesso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Usuario> usuarios = List.of(
                new Funcionario("ana.silva", "Ana Silva", LocalDate.of(2019, 3, 1), "Financeiro"),
                new GerenteDeDepartamento("bruno.costa", "Bruno Costa", LocalDate.of(2015, 6, 10),
                        "Financeiro", new BigDecimal("5000.00")),
                new Terceirizado("carla.souza", "Carla Souza", LocalDate.of(2024, 1, 15), "Consultoria XPTO")
        );

        // Herança em ação: todo Usuario responde a bloquear()/nivelDeAcessoPadrao(),
        // independentemente da subclasse concreta — é o comportamento
        // substituível que justifica extends aqui.
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.nome() + " -> nível " + usuario.nivelDeAcessoPadrao());
        }

        // Papéis transversais: verificados por capacidade (interface), não
        // por posição na hierarquia. Um Terceirizado poderia, em outra
        // configuração, também implementar PodeAprovarDespesa sem que isso
        // exigisse mexer em Usuario, Funcionario ou GerenteDeDepartamento.
        BigDecimal valorDaDespesa = new BigDecimal("1200.00");
        for (Usuario usuario : usuarios) {
            if (usuario instanceof PodeAprovarDespesa aprovador) {
                System.out.println(usuario.nome() + " pode aprovar R$ " + valorDaDespesa + "? "
                        + aprovador.podeAprovar(valorDaDespesa));
            }
            if (usuario instanceof Auditavel auditavel) {
                auditavel.registrarAcessoParaAuditoria("relatorio-financeiro-mensal");
                System.out.println(usuario.nome() + " teve acesso registrado para auditoria.");
            }
        }
    }
}
