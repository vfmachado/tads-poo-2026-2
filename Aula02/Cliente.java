public class Cliente {
    private Long id;    // pode vir do banco
    private String nome;
    private String cpf;
    private String email;

    public Cliente(Long id, String nome, String cpf, String email) {

        validarCampo(nome);
        validarCampo(cpf);
        validarCampo(email);

        if (id != null && id < 0) {
            throw new IllegalArgumentException("ID não pode ser negativo");
        }

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    // EU PERMITO CONSULTAR QUALQUER DADO DO MEU CLIENTE
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getEmail() {
        return email;
    }

    // SET NAO ACEITA SIMPLEMENTE QUALQUER VALOR, TEM QUE VALIDAR
    public void setEmail(String email) {
        validarCampo(email);
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private void validarCampo(String campo) {
        if (campo == null || campo.isEmpty() || campo.isBlank()) {
            throw new IllegalArgumentException("Campo obrigatório não pode ser nulo ou vazio");
        }
    }
}
