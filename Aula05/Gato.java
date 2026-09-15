public class Gato extends Animal {

    public Gato() {
        super("FELINO");
    }
    
    @Override
    void alimenta() {
        System.out.println("Gato come raçao, peixe e rato");
    }
    
}
