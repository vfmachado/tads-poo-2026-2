import java.util.ArrayList;
import java.util.List;

/**
 * Empresa
 */
public class Empresa {
    List<Funcionario> funcionarios = new ArrayList<>();


    public void addFunc(Funcionario f) {
        funcionarios.add(f);
    }


    public void mostrarFolha() {
        
        System.out.println("=== FOLHA ===");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("NOME: " + funcionario.getNome() + "\tR$ " + funcionario.getSalario());
        }

    }


}
