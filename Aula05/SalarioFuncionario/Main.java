public class Main {
    public static void main(String[] args) {
        
        Funcionario b1 = new Funcionario("Banana 1", 4000); // getSalario
        Funcionario b2 = new Funcionario("Banana 2", 4500);
        Gerente g1 = new Gerente("Gerente", 8000); // 10%  bonus na hora de calcular/retornar salario é padrao para gerente
        g1.aplicarBonus(0.15f); 

        Empresa empresa = new Empresa();
        empresa.addFunc(b1); // passo uma referencia do objeto para empresa
        empresa.addFunc(b2);
        empresa.addFunc(g1);

        
        b1 = null;

        empresa.mostrarFolha();
        
    }
}




