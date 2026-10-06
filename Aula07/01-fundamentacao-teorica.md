# Fundamentação teórica — Aula 07: Records em Java

> Requer Java 16+ (records são recurso definitivo desde o Java 16). Os exemplos de `switch` com *pattern matching* e desconstrução de records exigem Java 21.

## 1. O que é um record

Um **record** é uma forma concisa de declarar uma classe cujo propósito é **carregar dados imutáveis**. Você declara *quais* dados a classe carrega (o **cabeçalho**), e o compilador gera o resto:

```java
public record Endereco(String rua, String numero, String cidade, String cep) { }
```

Dessa única linha o compilador gera:

| Gerado automaticamente | Equivalente escrito à mão |
|---|---|
| Atributos `private final` para cada componente | `private final String rua;` ... |
| Construtor canônico | `public Endereco(String rua, ...) { this.rua = rua; ... }` |
| Acessores com o **mesmo nome** do componente | `rua()` (não `getRua()`) |
| `equals()` baseado em **todos** os componentes | comparação campo a campo |
| `hashCode()` consistente com `equals()` | `Objects.hash(...)` |
| `toString()` | `Endereco[rua=..., numero=..., ...]` |

Além disso, a classe é implicitamente `final` (não pode ser estendida) e herda de `java.lang.Record`.

## 2. Comparação: sem record × com record

Os dois arquivos abaixo têm **exatamente o mesmo comportamento observável**
(código completo em [`02-exemplos-codigo/01-comparacao-sem-e-com-record/`](02-exemplos-codigo/01-comparacao-sem-e-com-record/)).

**Sem record — ~45 linhas, quase tudo boilerplate:**

```java
public final class Endereco {
    private final String rua;
    private final String numero;
    private final String cidade;
    private final String cep;

    public Endereco(String rua, String numero, String cidade, String cep) {
        this.rua = Objects.requireNonNull(rua, "rua");
        this.numero = Objects.requireNonNull(numero, "numero");
        this.cidade = Objects.requireNonNull(cidade, "cidade");
        this.cep = Objects.requireNonNull(cep, "cep");
    }

    public String getRua()    { return rua; }
    public String getNumero() { return numero; }
    public String getCidade() { return cidade; }
    public String getCep()    { return cep; }

    @Override public boolean equals(Object o) { /* 8 linhas */ }
    @Override public int hashCode() { return Objects.hash(rua, numero, cidade, cep); }
    @Override public String toString() { /* 3 linhas */ }
}
```

**Com record — 10 linhas, e só o que importa (a validação):**

```java
public record Endereco(String rua, String numero, String cidade, String cep) {
    public Endereco {
        Objects.requireNonNull(rua, "rua");
        Objects.requireNonNull(numero, "numero");
        Objects.requireNonNull(cidade, "cidade");
        Objects.requireNonNull(cep, "cep");
    }
}
```

| Aspecto | Classe manual | Record |
|---|---|---|
| Linhas de código | ~45 | ~10 (ou 1, sem validação) |
| Risco de esquecer um campo no `equals`/`hashCode`/`toString` ao adicionar atributo | **Alto** — três métodos para atualizar | **Nenhum** — regenerados pelo compilador |
| Imutabilidade | Por disciplina (`final` em cada campo) | **Garantida** pela linguagem |
| Intenção do código | Escondida no meio do boilerplate | Explícita: "isto é um agregado de dados" |
| Herança | Possível (se não for `final`) | Impossível (record é `final`) |
| Atributos de instância extras | Livre | **Proibido** (só os do cabeçalho) |

O ganho central não é "escrever menos" — é **eliminar uma classe inteira de bugs**: o `equals()` desatualizado depois de adicionar um campo, o `toString()` que esqueceu um atributo, o setter que alguém adicionou "só dessa vez".

## 3. Por que utilizar

1. **Menos código, mais intenção.** Quem lê `record Endereco(...)` sabe imediatamente que é um agregado de dados, sem precisar ler 45 linhas para confirmar que `equals` e `hashCode` estão corretos.
2. **Imutabilidade por construção.** Sem setters, sem atributos não-`final`. Objetos imutáveis são seguros para compartilhar entre *threads*, para usar como chave de `Map`/elemento de `Set` e fáceis de raciocinar (nenhuma outra parte do programa pode alterá-los).
3. **Igualdade por valor correta de graça.** Dois `Endereco` com os mesmos dados são iguais — o comportamento esperado de um *Value Object* (Aula sobre associação/composição).
4. **Manutenção segura.** Adicionar um componente ao cabeçalho atualiza construtor, acessor, `equals`, `hashCode` e `toString` automaticamente.
5. **Integração com recursos modernos:** *pattern matching* com desconstrução, `sealed` interfaces e `switch` exaustivo (seção 6).

## 4. Quando utilizar

Use record quando o tipo for **definido pelos dados que carrega** e não por uma identidade ou ciclo de vida:

- **Value Objects** — `Dinheiro`, `Cpf`, `Intervalo`, `Coordenada`, `Endereco`. Igualdade por valor, imutáveis, podem validar invariantes no construtor.
- **DTOs** (*Data Transfer Objects*) — dados que atravessam uma fronteira: resposta de API, linha de relatório, mensagem entre camadas.
- **Retorno de múltiplos valores** de um método — em vez de `Object[]`, `Map<String, Object>` ou uma classe criada só para isso (ex.: `ResumoDeNotas(media, maior, menor)`).
- **Tuplas nomeadas locais** em *streams* e coleções — `record Par(Aluno aluno, double media) {}` declarado dentro do método ou da classe que o usa.
- **Chaves compostas** de `Map` — `record ChaveDeCache(long clienteId, LocalDate data) {}`: `equals`/`hashCode` corretos sem esforço.
- **Variantes de um tipo algébrico** — records dentro de uma `sealed interface` (ex.: `Circulo`, `Retangulo`).

Exemplos executáveis: [`02-exemplos-codigo/02-casos-de-uso/`](02-exemplos-codigo/02-casos-de-uso/).

## 5. Quando NÃO utilizar

| Situação | Por que record não serve | Alternativa |
|---|---|---|
| **Entidade com identidade** (`ContaBancaria`, `Contrato`, `Pedido`) | Estado muda ao longo do tempo; igualdade é pela identidade, não por todos os atributos | Classe comum com `equals` por identificador |
| **Objeto com comportamento rico e estado mutável** | Records são imutáveis; seus comandos precisariam devolver novas instâncias o tempo todo | Classe com encapsulamento (Aulas 03 e 04) |
| **Precisa de herança** | Record não pode estender classe nem ser estendido | Classe + interface |
| **Atributos derivados guardados em campo** | Não se pode declarar atributos de instância além do cabeçalho | Calcular em método do record, ou usar classe |
| **Dados sensíveis em `toString()`** | `toString()` gerado expõe **todos** os componentes (senha, token) | Sobrescrever `toString()` ou usar classe |
| **Classe gerenciada por *framework* que exige construtor vazio/setters** (alguns ORMs, como JPA `@Entity`) | Records não têm construtor sem argumentos nem setters | Classe tradicional |

Contraexemplo comentado: [`02-exemplos-codigo/03-quando-nao-usar/ContaBancaria.java`](02-exemplos-codigo/03-quando-nao-usar/ContaBancaria.java).

**Regra prática:** pergunte *"dois objetos com os mesmos dados são a mesma coisa?"*. Se **sim** (dois endereços iguais, dois intervalos iguais) → record. Se **não** (duas contas com mesmo saldo continuam sendo contas diferentes) → classe.

## 6. O que você pode customizar

### 6.1 Construtor compacto (validação e normalização)

```java
public record Intervalo(int inicio, int fim) {
    public Intervalo {                         // sem parênteses, sem atribuições
        if (inicio > fim) {
            throw new IllegalArgumentException("inicio não pode ser maior que fim");
        }
    }
}
```

O construtor compacto roda **antes** das atribuições automáticas. Também pode **normalizar** os parâmetros (ex.: `cep = cep.replace("-", "");`).

### 6.2 Métodos de instância e estáticos

```java
public int tamanho() { return fim - inicio; }
public static Intervalo de(int inicio, int fim) { return new Intervalo(inicio, fim); }
```

Comportamento derivado dos dados cabe no record. Como ele é imutável, "alterar" devolve outro record (`deslocar(5)` retorna um novo `Intervalo`).

### 6.3 Implementar interfaces

```java
public record Produto(String nome, BigDecimal preco) implements Comparable<Produto> {
    @Override public int compareTo(Produto o) { return preco.compareTo(o.preco); }
}
```

### 6.4 Cuidado: imutabilidade é *rasa*

Um record garante que a **referência** não muda, não que o **objeto referenciado** seja imutável. Com componentes mutáveis (`List`, `Date`, arrays), faça cópia defensiva:

```java
public record PedidoDto(long id, List<String> itens) {
    public PedidoDto {
        itens = List.copyOf(itens);   // copia e torna imutável
    }
}
```

### 6.5 Records + `sealed` + `switch` (Java 21)

```java
public sealed interface Forma {
    record Circulo(double raio) implements Forma { }
    record Retangulo(double largura, double altura) implements Forma { }

    static double area(Forma f) {
        return switch (f) {
            case Circulo c -> Math.PI * c.raio() * c.raio();
            case Retangulo(double largura, double altura) -> largura * altura;  // desconstrução
        };
    }
}
```

Como `Forma` é `sealed`, o compilador sabe todas as variantes: se você adicionar `Triangulo` e esquecer de tratá-lo no `switch`, **o código deixa de compilar**. Isso resolve, por tipos, o mesmo problema de "condicional espalhada" discutido nas aulas sobre interfaces e polimorfismo — quando o conjunto de variantes é **fechado e conhecido**.

## 7. Records × encapsulamento (Aulas 03 e 04)

Records **não violam** encapsulamento: o estado é `private final` e só é acessível por acessores. A diferença é de **propósito**:

- Em uma classe "de comportamento", o estado é um detalhe interno escondido; a interface pública são **comandos** e **consultas** com significado de negócio.
- Em um record, os **dados são a própria interface pública**. É um contrato transparente: "este tipo *é* essas informações".

Por isso records combinam com Value Objects e DTOs, e **não** com objetos cuja graça é esconder estado e proteger invariantes por meio de comandos.

## 8. Resumo

- Record = classe `final`, imutável, com construtor, acessores, `equals`, `hashCode` e `toString` gerados.
- Use para **valores e dados em trânsito**: Value Objects, DTOs, retornos múltiplos, chaves compostas, variantes de `sealed`.
- Não use para **entidades**, objetos mutáveis com comportamento rico, ou quando precisar de herança.
- Valide no **construtor compacto** e faça **cópia defensiva** de componentes mutáveis.
- Teste de bolso: *"dois objetos com os mesmos dados são a mesma coisa?"* Sim → record.

## 9. Exercícios sugeridos

1. Converta a classe `Posicao` da [Aula02](../Aula02/Posicao.java) em record. O que muda para quem a usa (acessores, `equals`)?
2. Converta `Endereco` da [Aula02](../Aula02/Endereco.java) em record, preservando as validações do construtor.
3. Crie `record Dinheiro(BigDecimal valor, String moeda)` com método `somar(Dinheiro)` que rejeita moedas diferentes.
4. Explique por que `Contrato` (Aula 03/04) **não** deve ser um record.
5. Crie `sealed interface MeioDePagamento` com os records `Pix`, `Boleto` e `Cartao`, e um `switch` que devolve a taxa de cada um. Adicione uma quarta variante e observe o erro de compilação.
