abstract class Animal {
    
    private String especie;

    // ESPECIE DEVERIA SER UM ENUM
    public Animal(String especie) {
        this.especie = especie;
    }

    public String getEspecie() {
        return this.especie;
    }

    // O MESMO METODO EM TODAS AS INSTANCIAS, AINDA PODEMOS SOBRESCREVER NA CLASSE CACHORRO COMO EU QUERO A ALIMENTACAO "SÓ" DO DOG
    // public void alimenta() {
    //     System.out.println("COMPARTILHAM A MESMA FORMA DE SE ALIMENTAR");
    // }

    // em alguns cassos queremos     // OBRIGAR AS CLASSES FILHAS A IMPLEMENTAR ESSE METODO
    abstract void alimenta(); // agora TODA CLASSE FILHA TEM QUE implementar e elas nao compartilham um comportamento comum

}
