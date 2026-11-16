# Exemplos mínimos por conceito — Aula 10

Um exemplo **deliberadamente pequeno** para cada conceito. Cada pacote tem uma classe de produção em `src/main/java/exemplos/<pacote>/` e o teste correspondente em `src/test/java/exemplos/<pacote>/`. Para o exemplo integrado, veja [../03-exemplo-completo/](../03-exemplo-completo/).

Ordem sugerida:

| # | Pacote | Conceito | Classe testada | O que observar |
|---|---|---|---|---|
| 1 | [primeiroteste](src/test/java/exemplos/primeiroteste/DivisorDeContaTest.java) | `@Test`, `assertEquals`, Arrange–Act–Assert | `DivisorDeConta` | Comparar com [`VerificacaoManual`](src/main/java/exemplos/primeiroteste/VerificacaoManual.java): o mesmo cenário com `println` e um humano conferindo |
| 2 | [assercoes](src/test/java/exemplos/assercoes/CardapioTest.java) | As asserções mais usadas | `Cardapio`, `Item` | As três armadilhas de igualdade: objeto sem `equals`, `double`, `BigDecimal` |
| 3 | [excecoes](src/test/java/exemplos/excecoes/EstoqueTest.java) | `assertThrows` | `Estoque` | Depois do erro, o estoque **não mudou**; o anti-exemplo com `try/catch` no fim do arquivo |
| 4 | [ciclodevida](src/test/java/exemplos/ciclodevida/CarrinhoTest.java) | `@BeforeEach`, isolamento, `@Nested`, `@DisplayName` | `Carrinho` | Dois testes adicionam um item e ambos encontram **um** item; rodar [`OrdemDasChamadasTest`](src/test/java/exemplos/ciclodevida/OrdemDasChamadasTest.java) e ler a saída |
| 5 | [parametrizados](src/test/java/exemplos/parametrizados/TaxaDeEntregaTest.java) | `@ParameterizedTest`, valores-limite | `TaxaDeEntrega` | Cada fronteira tem dois casos; cada linha da tabela vira um teste no relatório |
| 6 | [dubles](src/test/java/exemplos/dubles/) | Stub e espião escritos à mão | `Restaurante`, `CadastroDeClientes` | `RestauranteTest` controla a **resposta** do relógio; `CadastroDeClientesTest` confere o **comando** dado ao notificador |

## Como executar

Pelo IDE: abrir **esta pasta** (a que contém o `pom.xml`) como projeto e executar a classe ou o método de teste.

Pelo terminal:

```bash
mvn test                              # todos (38 testes)
mvn test -Dtest=EstoqueTest           # uma classe
mvn test -Dtest=EstoqueTest#retirarTudoEPermitido    # um método
```

## Uso sugerido em aula

1. Projetar a classe de produção e perguntar: **que casos vocês testariam?** Anotar no quadro antes de abrir o teste.
2. Abrir o teste e comparar com a lista da turma: que caso ninguém lembrou? (Quase sempre: a fronteira exata e o estado depois do erro.)
3. **Fazer um teste falhar de propósito** — trocar um valor esperado — e ler a mensagem juntos. Ler mensagens de falha é uma habilidade, e é melhor treiná-la com uma falha conhecida.
4. Fechar cada conceito com: **que erro no código de produção este teste pegaria? E qual ele não pegaria?**

> Os exemplos são simplificados de propósito: usam `int` em centavos no lugar de `BigDecimal` e não tratam todos os casos (o que `DivisorDeConta` faz com zero pessoas? — bom exercício: escrever o teste antes de decidir). O tratamento mais cuidadoso está no exemplo integrado.
