import java.util.ArrayList;
import java.util.List;

// Sistema acadêmico: turma com no máximo 3 alunos (número pequeno só para a demo).
// [R4] violada: a lista é exposta e a regra "máximo" depende de cada chamador lembrar dela.
public class Antes {

    static class Turma {
        private final List<String> alunos = new ArrayList<>();

        List<String> getAlunos() {
            return alunos; // qualquer um pode adicionar sem checar o limite
        }
    }

    public static void main(String[] args) {
        Turma turma = new Turma();
        for (String nome : List.of("Ana", "Bruno", "Carla", "Davi")) {
            turma.getAlunos().add(nome);
        }
        System.out.println(turma.getAlunos().size()); // 4: limite violado sem aviso
    }
}
