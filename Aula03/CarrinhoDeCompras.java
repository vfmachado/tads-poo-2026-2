import java.util.ArrayList;
import java.util.List;

// Note as quatro listas paralelas: nada as mantém sincronizadas entre si,
// e todas são públicas — qualquer código externo pode alterar uma sem
// alterar as outras (foi exatamente isso que causou o Incidente 1).
public class CarrinhoDeCompras {

    public String nomeRestaurante;

    // Data clump + primitive obsession: quatro listas que deveriam ser
    // uma única coleção de um objeto ItemDoCarrinho.
    
    // 1  public List<Produto> produtos;
    // 2  public List<ItemCarrinho> itens;
    // 3  HashMap<Produto,Integer>  produtoQuantidade;
    // public List<String> nomesProdutos = new ArrayList<>();
    // public List<Double> precosUnitarios = new ArrayList<>();
    // public List<Integer> quantidades = new ArrayList<>();
    // public List<String> observacoes = new ArrayList<>();

    private List<ItemCarrinho> itens;
    private Cupom cupom;

    public CarrinhoDeCompras(String nomeRestaurante) {
        // Nenhuma validação: nomeRestaurante pode ser nulo ou vazio.
        this.nomeRestaurante = nomeRestaurante;
        this.itens = new ArrayList<ItemCarrinho>();
    }

    // SOBRECARGA DE METODOS
    /**
     *  Chama o mesmo metodo com observacao null
     */
    public void adicionarItem(Produto produto, int quantidade) {
        adicionarItem(produto, quantidade, null);
    }

    public void adicionarItem(Produto produto, int quantidade, String observacao) {
        ItemCarrinho item = new ItemCarrinho(produto, quantidade, observacao);
        itens.add(item);
    }

    public void aplicarCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public double calcularValorSubtotal() {
        // double subtotal = 0;
        // // passa por todos os ItemCarrinho
        // for (int i = 0; i < itens.size(); i++) {

        //     // pega o produto associado ao ItemCarrinho na posicao i
        //     Produto p = itens.get(i).getProduto();
        //                                 // quantidade do produto
        //     subtotal += p.getValor() * itens.get(i).getQuantidade();
        // }
        // return subtotal;

        double subtotal = 0;
        for (ItemCarrinho item : itens) {
            Produto p = item.getProduto();
            subtotal += p.getValor() * item.getQuantidade();
        }

        return subtotal;
    }

    public double calcularValorTotal() {
        
        double total = calcularValorSubtotal();
        if (this.cupom != null && this.cupom.estaDisponivel()) {
            if (total >= this.cupom.getValorMinimo()) {
                total = total - (total * this.cupom.getPercentual());
                this.cupom.marcarComoUtilizado();
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

    /*
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
    */
}
