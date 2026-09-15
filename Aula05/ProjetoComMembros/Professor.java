public class Professor extends Pessoa {

    // lIST<DISCIPLINAS>

    private String matricula;
    
    public Professor(String nome, String matricula) {
         super(nome);
        this.matricula = "p" + matricula;
    }
  
    
    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return "PROF. " + super.toString();
    }
}
