import java.util.ArrayList;
import java.util.List;

public class ControleDeFrota {

    public static List<String> placas = new ArrayList<>();
    public static List<String> modelos = new ArrayList<>();
    public static List<Double> quilometragemAtual = new ArrayList<>();
    public static List<Double> quilometragemUltimaPreventiva = new ArrayList<>();
    public static List<Double> intervaloManutencaoKm = new ArrayList<>();

    public static List<String> manutencaoPlaca = new ArrayList<>();
    public static List<String> manutencaoData = new ArrayList<>();
    public static List<String> manutencaoTipo = new ArrayList<>();
    public static List<Float> manutencaoCusto = new ArrayList<>();

    public static void cadastrarVeiculo(String placa, String modelo, double kmAtual, double intervaloManutencao) {
        placas.add(placa);
        modelos.add(modelo);
        quilometragemAtual.add(kmAtual);
        quilometragemUltimaPreventiva.add(kmAtual);
        intervaloManutencaoKm.add(intervaloManutencao);
    }

    public static int indiceDoVeiculo(String placa) {
        for (int i = 0; i < placas.size(); i++) {
            if (placas.get(i).equals(placa)) {
                return i;
            }
        }
        return -1;
    }

    public static void atualizarQuilometragem(String placa, double novaQuilometragem) {
        int indice = indiceDoVeiculo(placa);
        if (indice == -1) {
            System.out.println("Veiculo nao encontrado: " + placa);
            return;
        }
        quilometragemAtual.set(indice, novaQuilometragem);
    }

    public static void registrarManutencao(String placa, String data, String tipo, float custo) {
        int indice = indiceDoVeiculo(placa);
        if (indice == -1) {
            System.out.println("Veiculo nao encontrado: " + placa);
            return;
        }
        manutencaoPlaca.add(placa);
        manutencaoData.add(data);
        manutencaoTipo.add(tipo);
        manutencaoCusto.add(custo);
        if (tipo.equals("PREVENTIVA")) {
            quilometragemUltimaPreventiva.set(indice, quilometragemAtual.get(indice));
        }
    }

    public static boolean precisaManutencao(String placa) {
        int indice = indiceDoVeiculo(placa);
        if (indice == -1) {
            return false;
        }
        double rodadosDesdeUltimaPreventiva = quilometragemAtual.get(indice) - quilometragemUltimaPreventiva.get(indice);
        return rodadosDesdeUltimaPreventiva >= intervaloManutencaoKm.get(indice);
    }

    public static float custoTotalManutencao(String placa) {
        float total = 0;
        for (int i = 0; i < manutencaoPlaca.size(); i++) {
            if (manutencaoPlaca.get(i).equals(placa)) {
                total += manutencaoCusto.get(i);
            }
        }
        return total;
    }

    public static void imprimirRelatorio(String placa) {
        int indice = indiceDoVeiculo(placa);
        if (indice == -1) {
            System.out.println("Veiculo nao encontrado: " + placa);
            return;
        }
        System.out.println("Placa: " + placas.get(indice));
        System.out.println("Modelo: " + modelos.get(indice));
        System.out.println("KM atual: " + quilometragemAtual.get(indice));
        System.out.println("Precisa manutencao? " + (precisaManutencao(placa) ? "SIM" : "NAO"));
        System.out.println("Custo total de manutencao: R$ " + custoTotalManutencao(placa));
        System.out.println("Historico:");
        for (int i = 0; i < manutencaoPlaca.size(); i++) {
            if (manutencaoPlaca.get(i).equals(placa)) {
                System.out.println("  " + manutencaoData.get(i) + " - " + manutencaoTipo.get(i)
                        + " - R$ " + manutencaoCusto.get(i));
            }
        }
    }
}
