package foodexpress.aula08.equilibrado;

// Igual à versão estrita: aqui o enum é a melhor ferramenta — fecha o
// conjunto de valores válidos e carrega a regra específica de DINHEIRO.
public enum FormaDePagamento {

    PIX,
    CARTAO,
    DINHEIRO {
        @Override
        public void validarTotal(Dinheiro total) {
            Dinheiro limite = Dinheiro.reais("150.00");
            if (total.maiorQue(limite)) {
                throw new IllegalArgumentException(
                        "Pagamento em dinheiro nao e aceito para pedidos acima de " + limite);
            }
        }
    };

    public void validarTotal(Dinheiro total) {
        // por padrão, nenhuma restrição
    }
}
