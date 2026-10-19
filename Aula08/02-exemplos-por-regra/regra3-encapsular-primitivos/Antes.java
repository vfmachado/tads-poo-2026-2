// Saúde: agendamento de consulta.
// [R3] violada: a duração é um int solto; a validação teria de ser repetida
// em todo lugar que receber "minutos", e nada impede trocar os argumentos.
public class Antes {

    static void agendar(String paciente, int minutos) {
        if (minutos < 15 || minutos > 120) {
            throw new IllegalArgumentException("Duracao deve estar entre 15 e 120 minutos.");
        }
        System.out.println(paciente + " agendado por " + minutos + " min");
    }

    public static void main(String[] args) {
        agendar("Ana", 30);
        agendar("Bruno", 5); // lança exceção
    }
}
