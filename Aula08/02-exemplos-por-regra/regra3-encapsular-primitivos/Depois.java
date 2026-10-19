// [R3] cumprida: Duracao valida uma vez, no construtor, e tem tipo próprio.
public class Depois {

    static final class Duracao {
        private final int minutos;

        Duracao(int minutos) {
            if (minutos < 15 || minutos > 120) {
                throw new IllegalArgumentException("Duracao deve estar entre 15 e 120 minutos.");
            }
            this.minutos = minutos;
        }

        @Override
        public String toString() {
            return minutos + " min";
        }
    }

    static void agendar(String paciente, Duracao duracao) {
        System.out.println(paciente + " agendado por " + duracao);
    }

    public static void main(String[] args) {
        agendar("Ana", new Duracao(30));
        agendar("Bruno", new Duracao(5)); // lança exceção na criação, longe da regra de agendamento
    }
}
