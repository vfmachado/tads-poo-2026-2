import java.math.BigDecimal;
import java.time.LocalDate;

public class ContratoErrado {
    
    // CPF privado - apenas a propria classe pode alterar
    private String cpf;
    private String cnpj;
    private BigDecimal valor;
    private String status;
    private String duracaoMeses;
    private LocalDate dataInicial;

    // no construtor é passado um valor inicial de cpf
    public ContratoErrado(String cpf) {
        this.cpf = cpf;
    }

    // eu permito consultar um cpf 
    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuracaoMeses() {
        return duracaoMeses;
    }

    public void setDuracaoMeses(String duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    // MAS NUNCA MODIFICAR
    // METODO SET - COMANDO
    // public void setCpf(String cpf) {
    //     this.cpf = cpf;
    // }

    
}
