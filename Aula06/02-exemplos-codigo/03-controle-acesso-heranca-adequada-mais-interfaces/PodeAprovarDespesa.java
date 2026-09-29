package aula06.controleacesso;

import java.math.BigDecimal;

/**
 * Outro papel transversal: aprovar despesas é uma capacidade ligada ao
 * cargo, não à posição na hierarquia Usuario/Funcionario/Terceirizado —
 * um Terceirizado em função de gestão também poderia, em tese, implementar
 * esta interface, sem que isso exija repensar a árvore de herança.
 */
public interface PodeAprovarDespesa {

    BigDecimal limiteDeAprovacao();

    default boolean podeAprovar(BigDecimal valorDespesa) {
        return valorDespesa.compareTo(limiteDeAprovacao()) <= 0;
    }
}
