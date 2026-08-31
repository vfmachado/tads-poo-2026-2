public class Main {
    public static void main(String[] args) {

        ControleDeFrota.cadastrarVeiculo("ABC1D23", "Fiorino 1.4", 45000, 10000);
        ControleDeFrota.cadastrarVeiculo("XYZ9K88", "Sprinter 2.2", 120000, 15000);

        ControleDeFrota.atualizarQuilometragem("ABC1D23", 48000);
        ControleDeFrota.registrarManutencao("ABC1D23", "10/01/2026", "PREVENTIVA", 850f);

        ControleDeFrota.atualizarQuilometragem("ABC1D23", 58500);
        ControleDeFrota.registrarManutencao("ABC1D23", "02/03/2026", "CORRETIVA", 1200f);

        ControleDeFrota.atualizarQuilometragem("XYZ9K88", 134000);

        ControleDeFrota.imprimirRelatorio("ABC1D23");
        System.out.println("----");
        ControleDeFrota.imprimirRelatorio("XYZ9K88");

        System.out.println("----");
        System.out.println("ABC1D23 precisa manutencao? " + ControleDeFrota.precisaManutencao("ABC1D23"));
        System.out.println("XYZ9K88 precisa manutencao? " + ControleDeFrota.precisaManutencao("XYZ9K88"));
    }
}
