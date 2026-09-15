import java.util.ArrayList;
import java.util.List;

public class Projeto {
    
    private String nome;

    // LISTA PARA alunos e outra para professor
    // List<Aluno> e outra List<Professor>
    List<Pessoa> membros = new ArrayList<>();

    public Projeto(String nome) {
        this.nome = nome;
    }

    
    // professor, aluno, publicoExt
    public void addPessoa(Pessoa p) {
        membros.add(p);
    }


    public void detalhar() {
        // nome
        // professores e alunos
        System.out.println("PROJETO: " + nome);
        for (Pessoa pessoa : membros) {
            System.out.println("MEMBRO: " + pessoa);
        }
    }
}
