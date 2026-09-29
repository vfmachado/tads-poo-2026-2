package aula06.pagamentos.interfaces;

/**
 * Capacidade adicional, restrita a meios de pagamento que de fato operam em
 * duas etapas (pré-autorização + captura). Diferente da versão com herança,
 * aqui a ausência desta capacidade em Boleto e Pix não é um método herdado
 * que lança exceção — é a simples ausência de um "implements", verificável
 * em tempo de compilação por quem escreve código cliente com cuidado.
 */
public interface CapturavelEmDuasEtapas {
    ResultadoAutorizacao capturar(String codigoAutorizacao);
}
