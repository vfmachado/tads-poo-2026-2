package exemplos.primeiroteste;

// O "ANTES": como vínhamos verificando o código até a Aula 09 — um main que imprime e um humano que confere.
//
// Três problemas:
//   1. Quem decide se está certo é o OLHO de quem lê (e ninguém lê 200 linhas de saída com atenção).
//   2. Se o resultado estiver errado, o programa termina normalmente: nada avisa.
//   3. O "valor esperado" mora na cabeça de quem escreveu, ou num comentário que ninguém atualiza.
//
// Compare com src/test/java/exemplos/primeiroteste/DivisorDeContaTest.java.
public class VerificacaoManual {

    public static void main(String[] args) {
        DivisorDeConta divisor = new DivisorDeConta();

        System.out.println("R$ 100,00 por 4: " + divisor.porPessoa(10000, 4));   // esperado: 2500
        System.out.println("R$ 100,00 por 3: " + divisor.porPessoa(10000, 3));   // esperado: 3334 (ou era 3333?)
    }
}
