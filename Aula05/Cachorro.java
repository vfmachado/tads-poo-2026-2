public class Cachorro extends Animal {

    public Cachorro() {
        super("CANINO");
    }

    @Override
    void alimenta() {
        System.out.println("Cachorro come ração / outros animais  / comida");
    }    

    public void latir() {
        System.out.println("AU AU");
    }
   
}
