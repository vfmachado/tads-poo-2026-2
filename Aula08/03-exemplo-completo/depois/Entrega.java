package foodexpress.aula08.depois;

// [R8] Duas variáveis de instância. Agrupa o que muda junto: distância e
// prioridade determinam, juntas, quanto a entrega custa.
public final class Entrega {

    private final DistanciaEmKm distancia;
    private final Prioridade prioridade;

    public Entrega(DistanciaEmKm distancia, Prioridade prioridade) {
        this.distancia = distancia;
        this.prioridade = prioridade;
    }

    public Dinheiro taxaBase() {
        FaixaDeEntrega faixa = FaixaDeEntrega.para(distancia); // [R5] variável local evita a 2ª chamada na mesma linha
        return faixa.taxa();
    }

    public Dinheiro taxaCobrada(Dinheiro descontoNaTaxaBase) {
        Dinheiro taxaComDesconto = taxaBase().menos(descontoNaTaxaBase);
        return prioridade.acrescentarA(taxaComDesconto);
    }
}
