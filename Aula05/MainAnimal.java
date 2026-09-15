import java.util.ArrayList;
import java.util.List;

public class MainAnimal {
    public static void main(String[] args) {
        
        // Animal a = new Animal(); // impossivel pois animal é abstrato
        
        List<Animal> fazenda = new ArrayList<>();
        fazenda.add(new Cachorro());
        fazenda.add(new Gato());

        System.out.println("ANIMAIS DA FAZENDA");
        for (Animal animal : fazenda) {
            System.out.println(animal.getEspecie() + " -- ");
            animal.alimenta();
            
            if (animal.getClass().getName().equals(Cachorro.class.getName())) {
                Cachorro cachorro = (Cachorro) animal;
                cachorro.latir();
            }

            System.out.println("\n");
        }


    }
}
