package exemplos.ciclodevida;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private final List<Integer> precosEmCentavos = new ArrayList<>();

    public void adicionar(int precoEmCentavos) {
        precosEmCentavos.add(precoEmCentavos);
    }

    public void esvaziar() {
        precosEmCentavos.clear();
    }

    public int quantidadeDeItens() {
        return precosEmCentavos.size();
    }

    public int totalEmCentavos() {
        int total = 0;
        for (int preco : precosEmCentavos) {
            total += preco;
        }
        return total;
    }
}
