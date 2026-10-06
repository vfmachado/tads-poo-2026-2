package aula07.semrecord;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Endereco a = new Endereco("Rua das Flores", "10", "Bento Gonçalves", "95700-000");
        Endereco b = new Endereco("Rua das Flores", "10", "Bento Gonçalves", "95700-000");

        System.out.println(a);
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("rua: " + a.getRua());

        Set<Endereco> conjunto = new HashSet<>();
        conjunto.add(a);
        conjunto.add(b);
        System.out.println("Tamanho do conjunto: " + conjunto.size());
    }
}
