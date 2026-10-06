package aula07.usos;

// Caso 1: Value Object com invariante validada no construtor compacto
// e métodos de negócio. Records podem ter métodos, métodos estáticos
// e implementar interfaces — só não podem declarar atributos de instância extras.
public record Intervalo(int inicio, int fim) {

    public Intervalo {
        if (inicio > fim) {
            throw new IllegalArgumentException(
                    "inicio (" + inicio + ") não pode ser maior que fim (" + fim + ")");
        }
    }

    public static Intervalo de(int inicio, int fim) {
        return new Intervalo(inicio, fim);
    }

    public int tamanho() {
        return fim - inicio;
    }

    public boolean contem(int valor) {
        return valor >= inicio && valor <= fim;
    }

    // Records são imutáveis: "alterar" significa criar outro.
    public Intervalo deslocar(int delta) {
        return new Intervalo(inicio + delta, fim + delta);
    }
}
