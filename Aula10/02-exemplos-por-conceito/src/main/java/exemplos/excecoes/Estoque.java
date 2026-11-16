package exemplos.excecoes;

public class Estoque {

    private int unidades;

    public Estoque(int unidadesIniciais) {
        this.unidades = unidadesIniciais;
    }

    public void retirar(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva: " + quantidade);
        }
        if (quantidade > unidades) {
            throw new IllegalStateException("Estoque insuficiente: pedido " + quantidade + ", disponivel " + unidades);
        }
        unidades -= quantidade;
    }

    public int unidades() {
        return unidades;
    }
}
