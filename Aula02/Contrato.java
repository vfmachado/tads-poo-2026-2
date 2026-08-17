import java.math.BigDecimal;
import java.time.LocalDate;

public class Contrato {
    
    // atributos estaticos da classe PODEM ser publicos (normalmente constantes)

    // CPF privado - apenas a propria classe pode alterar
    private Cliente cliente;
    private String cnpj;
    private BigDecimal valor;
    private StatusContrato status;
    private int duracaoMeses;
    private LocalDate dataInicial;

    // no construtor é passado uma instancia do Cliente
    // é uma associação do tipo  Composição, pois o contrato depende do cliente para existir
    public Contrato(Cliente cliente, BigDecimal valor) {
        status = StatusContrato.PENDENTE;
        this.duracaoMeses = 12;

        this.cliente = cliente;     // cliente para existir ja foi validado
        this.valor = valor;
    }

    // MULTIPLOS CONSTRUTORES NA MESMA CLASSE
    // OBRIGATORIAMENTE AS ASSINATURAS DEVEM SER DIFERENTES
    public Contrato(Cliente cliente, BigDecimal valor, int duracaoMeses) {
        this(cliente, valor);   // chamando outro construtor da mesma classe
        this.duracaoMeses = duracaoMeses;
    }

    public Contrato(Cliente cliente, int duracaoMeses) {
        // contrato sem valor fixo
        this(cliente, BigDecimal.ZERO, duracaoMeses);
    }



    // eu permito consultar um cpf 
    public Cliente getCliente() {
        return this.cliente;
    }

    // inicio de vigencia do contrato se da com a assinatura
    public void assinarContrato() {
        assinarContrato(LocalDate.now());
    }

    // sobrecarga - mesmo nome do metodo, mas assinatura diferente (parametros diferentes)
    // um metodo pode chamar o outro - melhora a reusabilidade do codigo, legibilidade, manutencao e outros.
    public void assinarContrato(LocalDate data) {
        if (this.status != StatusContrato.PENDENTE) {
            throw new IllegalStateException("Contrato não pode ser assinado, status atual: " + this.status);
        }
        this.status = StatusContrato.ATIVO;
        this.dataInicial = data;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "cliente=" + cliente +
                ", cnpj='" + cnpj + '\'' +
                ", valor=" + valor +
                ", status=" + status +
                ", duracaoMeses=" + duracaoMeses +
                ", dataInicial=" + dataInicial +
                '}';
    }
}
