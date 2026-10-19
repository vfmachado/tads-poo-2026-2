package foodexpress.aula08.depois;

// [R2] O "if (pri) tx = tx + 6.00" vira polimorfismo: cada constante sabe
// como alterar a taxa. Nenhum if, nenhum else.
public enum Prioridade {

    NORMAL {
        @Override
        public Dinheiro acrescentarA(Dinheiro taxa) {
            return taxa;
        }
    },

    PRIORITARIA {
        @Override
        public Dinheiro acrescentarA(Dinheiro taxa) {
            return taxa.mais(Dinheiro.reais("6.00"));
        }
    };

    public abstract Dinheiro acrescentarA(Dinheiro taxa);
}
