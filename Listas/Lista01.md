
# Lista 01 — Abstração de Regras + Coesão

## Regras gerais para todos os exercícios (você pode colocar no enunciado)

* Não usar `System.out.println()` dentro das classes (classes não fazem UI).
* Validar entradas conforme regras (o objeto mantém consistência).
* Métodos que falham devem **ignorar** a ação ou retornar `boolean` (quando indicado).
* A implementação deve permitir que o `Main` rode sem alterações.


# Exercício 1 — Cofre Digital (abstração de regras + coesão)

## O que o aluno deve implementar

Classe `CofreDigital` (coesa: responsável apenas por guardar um valor e aplicar regras de depósito/saque).

### Regras (abstração)

* O cofre tem um **saldo** (inteiro).
* `depositar(int valor)` só aceita valor > 0.
* `sacar(int valor)` só permite se valor > 0 e `valor <= saldo`.
* `saldo()` retorna o saldo atual (consulta).

## Main.java (forneça pronto)

```java
public class Main {
    public static void main(String[] args) {
        CofreDigital cofre = new CofreDigital();

        cofre.depositar(100);
        cofre.depositar(-10);  // deve ser ignorado
        System.out.println("Saldo esperado = 100 | Saldo atual = " + cofre.saldo());

        boolean ok1 = cofre.sacar(30);
        boolean ok2 = cofre.sacar(500); // deve falhar
        System.out.println("Saque 30 ok? " + ok1 + " | Saque 500 ok? " + ok2);

        System.out.println("Saldo esperado = 70 | Saldo atual = " + cofre.saldo());
    }
}
```



# Exercício 2 — Termômetro (abstração de unidade + coesão)

## O que o aluno deve implementar

Classe `Termometro`.

### Regras

* Temperatura inicial é 0°C.
* `aumentar(double delta)` só aceita delta > 0.
* `diminuir(double delta)` só aceita delta > 0.
* `emCelsius()` retorna o valor atual.
* `emFahrenheit()` retorna conversão (consulta, sem mudar estado).

## Main.java

```java
public class Main {
    public static void main(String[] args) {
        Termometro t = new Termometro();

        t.aumentar(10);
        t.diminuir(3);
        t.diminuir(-2); // ignora

        System.out.println("Celsius esperado = 7.0 | Atual = " + t.emCelsius());
        System.out.println("Fahrenheit esperado ~ 44.6 | Atual = " + t.emFahrenheit());
    }
}
```



# Exercício 3 — Controle de Estoque (coesão por responsabilidade)

## O que o aluno deve implementar

Classe `EstoqueProduto`.

### Regras

* Produto tem: `nome` e `quantidade`.
* `entrada(int qtd)` só aceita qtd > 0.
* `saida(int qtd)` só aceita qtd > 0 e `qtd <= quantidade`.
* `quantidadeAtual()` consulta.
* `nome()` consulta.

## Main.java

```java
public class Main {
    public static void main(String[] args) {
        EstoqueProduto e = new EstoqueProduto("Arroz", 10);

        e.entrada(5);
        boolean ok = e.saida(12); // deve ser true (10+5-12=3)
        boolean falha = e.saida(10); // deve ser false (só tem 3)

        System.out.println("Ok esperado = true | Atual = " + ok);
        System.out.println("Falha esperado = false | Atual = " + falha);
        System.out.println("Qtd esperada = 3 | Atual = " + e.quantidadeAtual());
        System.out.println("Nome esperado = Arroz | Atual = " + e.nome());
    }
}
```


# Exercício 4 — Sessão de Votação (abstração de regras + coesão + comandos/consultas)

## O que o aluno deve implementar

Classe `SessaoVotacao`.

### Regras

* Sessão começa **fechada**.
* `abrir()` abre (comando).
* `fechar()` fecha (comando).
* `votarSim()` e `votarNao()` só funcionam se estiver aberta.
* Consultas:

  * `sim()`, `nao()`, `total()`
  * `estaAberta()`

## Main.java

```java
public class Main {
    public static void main(String[] args) {
        SessaoVotacao s = new SessaoVotacao();

        s.votarSim(); // ignorar (fechada)
        s.abrir();

        s.votarSim();
        s.votarSim();
        s.votarNao();

        s.fechar();
        s.votarNao(); // ignorar (fechada)

        System.out.println("Sim esperado = 2 | Atual = " + s.sim());
        System.out.println("Nao esperado = 1 | Atual = " + s.nao());
        System.out.println("Total esperado = 3 | Atual = " + s.total());
        System.out.println("Aberta esperado = false | Atual = " + s.estaAberta());
    }
}
```


# Exercício 5 — Modelagem mais real: Pedido e Item (coesão entre classes)

Aqui os alunos implementam **duas classes**, mas o `Main` dá o norte e obriga separação de responsabilidades.

## O que o aluno deve implementar

* `ItemPedido` (representa item: nome, precoUnitario, quantidade)
* `Pedido` (agrega itens e calcula total)

### Regras

* `ItemPedido`:

  * quantidade >= 1
  * precoUnitario > 0
  * `subtotal()` = precoUnitario * quantidade
* `Pedido`:

  * `adicionarItem(ItemPedido item)`
  * `total()` soma subtotais
  * `quantidadeDeItens()` (quantos itens diferentes)

## Main.java

```java
public class Main {
    public static void main(String[] args) {
        Pedido p = new Pedido();

        p.adicionarItem(new ItemPedido("Café", 8.50, 2));
        p.adicionarItem(new ItemPedido("Pão", 1.50, 6));

        System.out.println("Itens esperados = 2 | Atual = " + p.quantidadeDeItens());
        System.out.println("Total esperado = 26.0 | Atual = " + p.total());
        // 8.50*2 = 17.0
        // 1.50*6 = 9.0
        // total = 26.0
    }
}
```


