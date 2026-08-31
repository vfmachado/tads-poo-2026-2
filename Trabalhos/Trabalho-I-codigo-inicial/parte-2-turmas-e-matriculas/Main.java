public class Main {
    public static void main(String[] args) {

        ControleAcademico.criarTurma("POO-2026A", 3);

        ControleAcademico.matricular("POO-2026A", "Ana");
        ControleAcademico.matricular("POO-2026A", "Bruno");
        ControleAcademico.matricular("POO-2026A", "Ana"); // deveria ser rejeitado: aluno ja matriculado

        ControleAcademico.lancarNota("POO-2026A", "Ana", 8.5);
        ControleAcademico.lancarNota("POO-2026A", "Bruno", 5.0);

        ControleAcademico.matricular("POO-2026A", "Carla");
        ControleAcademico.matricular("POO-2026A", "Diego"); // deveria ser rejeitado: turma cheia (capacidade 3)

        ControleAcademico.imprimirTurma("POO-2026A");
    }
}
