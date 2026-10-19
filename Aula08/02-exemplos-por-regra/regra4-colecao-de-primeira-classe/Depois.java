import java.util.ArrayList;
import java.util.List;

// [R4] cumprida: a coleção e a regra do limite vivem juntas; a lista nunca escapa.
public class Depois {

    static final class Matriculas {
        private static final int LIMITE = 3;
        private final List<String> alunos = new ArrayList<>();

        void matricular(String aluno) {
            if (alunos.size() >= LIMITE) {
                throw new IllegalStateException("Turma lotada.");
            }
            alunos.add(aluno);
        }

        int quantidade() {
            return alunos.size();
        }
    }

    public static void main(String[] args) {
        Matriculas matriculas = new Matriculas();
        for (String nome : List.of("Ana", "Bruno", "Carla", "Davi")) {
            matriculas.matricular(nome); // o 4º lança IllegalStateException
        }
    }
}
