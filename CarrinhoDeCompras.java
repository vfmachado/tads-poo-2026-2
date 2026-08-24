package foodexpress.exercicio.antes;

import java.util.ArrayList;
import java.util.List;

// Classe deus responsável pelos dois incidentes descritos no README.
// Note as quatro listas paralelas: nada as mantém sincronizadas entre si,
// e todas são públicas — qualquer código externo pode alterar uma sem
// alterar as outras (foi exatamente isso que causou o Incidente 1).
public class CarrinhoDeCompras {

    public String nomeRestaurante;

    // Data clump + primitive obsession: quatro listas que deveriam ser
    // uma única coleção de um objeto ItemDoCarrinho.
    public List<String> nomesProdutos = new ArrayList<>();
    public List<Double> precosUnitarios = new ArrayList<>();
    public List<Integer> quantidades = new ArrayList<>();
    public List<String> observacoes = new ArrayList<>();

    public String codigoCupom;
    public double percentualDescontoCupom;
    public double valorMinimoParaCupom;
    public boolean cupomAplicado;

    public CarrinhoDeCompras(String nomeRestaurante) {
        // Nenhuma validação: nomeRestaurante pode ser nulo ou vazio.
        this.nomeRestaurante = nomeRestaurante;
    }

    public void adicionarItem(String nomeProduto, double precoUnitario, int quantidade, String observacao) {
        // Nenhuma validação: preço negativo, quantidade zero/negativa e
        // nome vazio são todos aceitos. E nada garante que as quatro
        // listas terminem este método com o mesmo tamanho, se algum
        // código externo tiver mexido em alguma delas diretamente.
        nomesProdutos.add(nomeProduto);
        precosUnitarios.add(precoUnitario);
        quantidades.add(quantidade);
        observacoes.add(observacao);
    }

    public void aplicarCupom(String codigo, double percentual, double valorMinimo) {
        // Nenhuma validação de data de validade, nenhum controle de uso
        // único, e — a causa do Incidente 2 — nenhuma verificação de que
        // o carrinho realmente atinge o valor mínimo antes de aceitar.
        this.codigoCupom = codigo;
        this.percentualDescontoCupom = percentual;
        this.valorMinimoParaCupom = valorMinimo;
        this.cupomAplicado = true;
    }

    public double calcularValorSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < nomesProdutos.size(); i++) {
            subtotal += precosUnitarios.get(i) * quantidades.get(i);
        }
        return subtotal;
    }

    public double calcularValorTotal() {
        double total = calcularValorSubtotal();
        if (cupomAplicado) {
            if (total >= valorMinimoParaCupom) {
                total = total - (total * percentualDescontoCupom);
            }
            // Se o total for menor que valorMinimoParaCupom, o cupom
            // continua marcado como "aplicado" (cupomAplicado == true),
            // mas simplesmente não tem efeito nenhum aqui — nada no
            // carrinho registra ou comunica essa rejeição silenciosa.
        }
        if (total < 0) {
            total = 0;
        }
        return total;
    }

    public String detalheCompleto() {
        StringBuilder detalhe = new StringBuilder();
        detalhe.append("Restaurante: ").append(nomeRestaurante).append("\n");
        for (int i = 0; i < nomesProdutos.size(); i++) {
            detalhe.append(quantidades.get(i)).append("x ").append(nomesProdutos.get(i));
            detalhe.append(" - R$ ").append(precosUnitarios.get(i));
            String observacao = observacoes.get(i);
            if (observacao != null && !observacao.isEmpty()) {
                detalhe.append(" (obs: ").append(observacao).append(")");
            }
            detalhe.append("\n");
        }
        detalhe.append("Subtotal: R$ ").append(calcularValorSubtotal()).append("\n");
        if (cupomAplicado) {
            detalhe.append("Cupom aplicado: ").append(codigoCupom).append("\n");
        }
        detalhe.append("Total: R$ ").append(calcularValorTotal());
        return detalhe.toString();
    }
}
