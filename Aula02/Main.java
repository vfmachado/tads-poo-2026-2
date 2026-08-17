import java.math.BigDecimal;

public class Main {
    /*
        Sistema de gestao de contratos e assinaturas (externo)

        - Partes envolvidas     (cliente, empresa)
                                CPF         CNPJ
        - Valor mensal
        - Data inicio
        - Duracao
        - STATUS

        - fidelidade, multa, vinculo com algum tipo de produto

    */
    public static void main(String[] args) {
        
        Cliente cliente = new Cliente(1L, "Vinicius", "12312312312", "vinicius.machado@ifrs.edu.br");
        System.out.println(cliente);

        cliente.setEmail("vinicius.machado@riogrande.ifrs.edu.br");

        // classe Contrato
        // instancia contrato
                                // new => chamando o método construtor
        Contrato contrato = new Contrato(cliente, new BigDecimal(199));

        // contrato.setCpf("32132132132");  deveria ser impossivel
        System.out.println(contrato);

        contrato.assinarContrato();
        System.out.println(contrato);
        
    }
}
