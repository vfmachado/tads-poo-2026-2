package foodexpress.aula08.equilibrado;

// Sem a classe Quantidade: a única regra ("positiva") é validada aqui, no
// único lugar onde uma quantidade existe. Um int validado no construtor de
// quem o possui protege a invariante com um décimo do custo.
public record ItemDoPedido(Dinheiro precoUnitario, int unidades) {

    public ItemDoPedido {
        if (precoUnitario.menorQue(Dinheiro.ZERO)) {
            throw new IllegalArgumentException("Valor unitario nao pode ser negativo.");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
    }

    public Dinheiro subtotal() {
        return precoUnitario.multiplicadoPor(unidades);
    }
}
