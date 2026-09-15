public class Aluno extends Pessoa {
    private String matricula;
    
    public Aluno(String nome, String matricula) {
        super(nome);
        this.matricula = "a" + matricula;
    }
    
      
    public String getMatricula() {
        return matricula;
    }

    // cursa disciplinas
    // curso do aluno
    // LIST<DISCIPLINAS>
}
