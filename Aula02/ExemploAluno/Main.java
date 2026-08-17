package ExemploAluno;

public class Main {
    
    public static void main(String[] args) {
        Aluno aluno = new Aluno("Fulano");
        Avaliacao avaliacao = new Avaliacao(aluno);
        avaliacao.addNota(6);
        avaliacao.addNota(4);
        System.out.println(avaliacao.calcularMedia() == 9.0);
        System.out.println(avaliacao);
    }

}
