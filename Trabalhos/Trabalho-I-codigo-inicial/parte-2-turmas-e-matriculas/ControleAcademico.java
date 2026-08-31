import java.util.ArrayList;
import java.util.List;

public class ControleAcademico {

    public static List<String> turmaCodigo = new ArrayList<>();
    public static List<Integer> turmaCapacidade = new ArrayList<>();

    public static List<String> matriculaTurma = new ArrayList<>();
    public static List<String> matriculaAluno = new ArrayList<>();
    public static List<Double> matriculaNota = new ArrayList<>();

    public static void criarTurma(String codigo, int capacidade) {
        turmaCodigo.add(codigo);
        turmaCapacidade.add(capacidade);
    }

    public static int quantidadeDeMatriculas(String codigoTurma) {
        int quantidade = 0;
        for (String turma : matriculaTurma) {
            if (turma.equals(codigoTurma)) {
                quantidade++;
            }
        }
        return quantidade;
    }

    public static void matricular(String codigoTurma, String nomeAluno) {
        int indiceTurma = turmaCodigo.indexOf(codigoTurma);
        if (indiceTurma == -1) {
            System.out.println("Turma nao encontrada: " + codigoTurma);
            return;
        }
        for (int i = 0; i < matriculaTurma.size(); i++) {
            if (matriculaTurma.get(i).equals(codigoTurma) && matriculaAluno.get(i).equals(nomeAluno)) {
                System.out.println("Aluno ja matriculado nesta turma.");
                return;
            }
        }
        if (quantidadeDeMatriculas(codigoTurma) >= turmaCapacidade.get(indiceTurma)) {
            System.out.println("Turma cheia: " + codigoTurma);
            return;
        }
        matriculaTurma.add(codigoTurma);
        matriculaAluno.add(nomeAluno);
        matriculaNota.add(null);
    }

    public static void lancarNota(String codigoTurma, String nomeAluno, double nota) {
        for (int i = 0; i < matriculaTurma.size(); i++) {
            if (matriculaTurma.get(i).equals(codigoTurma) && matriculaAluno.get(i).equals(nomeAluno)) {
                matriculaNota.set(i, nota);
                return;
            }
        }
        System.out.println("Matricula nao encontrada.");
    }

    public static int quantidadeDeAprovados(String codigoTurma) {
        int aprovados = 0;
        for (int i = 0; i < matriculaTurma.size(); i++) {
            if (matriculaTurma.get(i).equals(codigoTurma)
                    && matriculaNota.get(i) != null
                    && matriculaNota.get(i) >= 6) {
                aprovados++;
            }
        }
        return aprovados;
    }

    public static void imprimirTurma(String codigoTurma) {
        System.out.println("Turma: " + codigoTurma);
        for (int i = 0; i < matriculaTurma.size(); i++) {
            if (matriculaTurma.get(i).equals(codigoTurma)) {
                String situacao = matriculaNota.get(i) == null ? "sem nota" : String.valueOf(matriculaNota.get(i));
                System.out.println("  " + matriculaAluno.get(i) + " - nota: " + situacao);
            }
        }
        System.out.println("Aprovados: " + quantidadeDeAprovados(codigoTurma));
    }
}
