package exemplos.assercoes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cardapio {

    private final List<Item> itens = new ArrayList<>();

    public void adicionar(String nome, String preco) {
        itens.add(new Item(nome, new BigDecimal(preco)));
    }

    public boolean contem(String nome) {
        return buscar(nome) != null;
    }

    /** Devolve o item com esse nome, ou null se não existir. */
    public Item buscar(String nome) {
        for (Item item : itens) {
            if (item.nome().equals(nome)) {
                return item;
            }
        }
        return null;
    }

    public List<String> nomes() {
        List<String> nomes = new ArrayList<>();
        for (Item item : itens) {
            nomes.add(item.nome());
        }
        return nomes;
    }

    /** Média simples dos preços, em double (suficiente para uma estatística; não para cobrar). */
    public double precoMedio() {
        double soma = 0;
        for (Item item : itens) {
            soma += item.preco().doubleValue();
        }
        return soma / itens.size();
    }
}
