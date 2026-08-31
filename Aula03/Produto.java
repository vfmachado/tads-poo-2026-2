public class Produto {
    private String nome;
    private float valor;

    public Produto(String nome, float valor) {
        setNome(nome);   
        setValor(valor);
    }

    public String getNome() {
        return nome;
    }
    
    // o metodo lança uma exceção, logo deve ter o throws Exception
    public void setNome(String nome) {
        if (nome == null || nome.isEmpty() || nome.isBlank())
            throw new IllegalArgumentException("Nome nao pode ser null ou vazio");
        
        this.nome = nome;
    }
    
    public float getValor() {
        return valor;
    }
    
    public void setValor(float valor)  {
        if (valor < 0) throw new IllegalArgumentException("Valor nao pode ser negativo"); 
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nome +  "\t R$ " + String.format("%.2f", valor); 
    }
}
