package aula06.controleacesso;

/**
 * Papel transversal: por política de compliance, todo acesso de usuários
 * externos à empresa precisa ser registrado para auditoria. Isso não
 * depende de onde o usuário está na hierarquia de especialização — por
 * isso é uma interface, não um método em Usuario.
 */
public interface Auditavel {
    void registrarAcessoParaAuditoria(String recursoAcessado);
}
