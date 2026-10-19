package foodexpress.aula08.depois;

// [R3] A forma de pagamento deixa de ser String: o valor "BOLETO" ou
// "pix " simplesmente não existe como estado possível do sistema.
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
