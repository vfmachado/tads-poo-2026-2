/**
 * Gerente
 */
public class Gerente extends Funcionario {

    private float bonus;

    public Gerente(String nome, float salario) {
        super(nome, salario);
        this.bonus = 0.1f;
    }

    @Override
    public float getSalario() {
        return super.getSalario() * (1 + bonus);   // 20% bonus
    }

    public void aplicarBonus(float bonus) {
        this.bonus = bonus;
    }
    
}
