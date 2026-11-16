# Exemplo completo — Aula 10 (apoio ao docente)

O fechamento de pedido do FoodExpress, na versão `equilibrado/` da [Aula 09](../../Aula09/03-exemplo-completo/), **sem nenhuma alteração no código de produção** além do nome do pacote. O que esta aula acrescenta está todo em `src/test/java`.

| Pasta | Conteúdo |
|---|---|
| [src/main/java/foodexpress/aula10/](src/main/java/foodexpress/aula10/) | Produção: `Frete`, `Pagamento`, `Pedido`, `ReciboDoPedido`, `Notificador`, `NotificadorPorEmail`, `RepositorioDePedidos`, `ServicoDePedido` e o `Main` |
| [src/test/java/foodexpress/aula10/](src/test/java/foodexpress/aula10/) | Testes: `FreteTest`, `PagamentoTest`, `ReciboDoPedidoTest`, `ServicoDePedidoTest` e o dublê `NotificadorFalso` |

## Como executar

```bash
mvn test        # 33 testes
```

O `Main` continua funcionando (pelo IDE, ou `mvn -q compile` seguido de `java -cp target/classes foodexpress.aula10.Main`) e produz a mesma saída da Aula 09. Ele deixou de ser a verificação; é só uma demonstração.

### Como executar sem Maven

Baixar o `junit-platform-console-standalone-6.1.3.jar` (Maven Central, grupo `org.junit.platform`) para esta pasta e, a partir dela:

```bash
javac -d out/main src/main/java/foodexpress/aula10/*.java
javac -d out/test -cp "out/main;junit-platform-console-standalone-6.1.3.jar" src/test/java/foodexpress/aula10/*.java
java -jar junit-platform-console-standalone-6.1.3.jar execute --class-path "out/main;out/test" --scan-class-path
```

No Linux e no macOS, trocar `;` por `:` nos dois *classpaths*. Verificado: os dois caminhos (Maven e linha de comando) executam os mesmos 33 testes.

## Mapa: o que cada classe de teste ensina

| Classe de teste | Testes | Tipo de código testado | Técnicas |
|---|---|---|---|
| `FreteTest` | 15 | Função pura (entra valor, sai valor) | AAA; valores-limite com `@CsvSource`; arredondamento; teste de contrato com `@EnumSource` |
| `PagamentoTest` | 9 | Função pura com caminho de erro | Par de fronteira (150,00 aceita / 150,01 recusa); `assertThrows` com mensagem |
| `ReciboDoPedidoTest` | 2 | Formatação | Objeto montado à mão, sem passar pelo serviço |
| `ServicoDePedidoTest` | 7 | Orquestração com efeitos (gravar, avisar) | `@BeforeEach`; `@Nested` por situação; espião; verificar o que **não** aconteceu; teste de caracterização |

## Dos cenários do `Main` aos testes

| Cenário do `Main` (Aula 09) | Onde está nos testes | O que o teste acrescentou |
|---|---|---|
| A) entrega, R$ 59,70, PIX | `entregaAbaixoDoLimiteCobraFreteFixo`, linha `67.70` de `pixDaCincoPorCentoDeDesconto`, e os três testes de "quando o pedido é aceito" | Gravação e aviso conferidos, não só impressos |
| B) entrega grátis, R$ 120,00, cartão | linha `120.00` de `cartaoAcrescentaTresPorCento` | A fronteira: 99,99 / 100,00 / 100,01 |
| C) expressa, R$ 80,00, PIX | linha `80.00` de `expressaCobraTaxaFixaMaisPercentual` | Casos de meio centavo |
| D) retirada, R$ 200,00, dinheiro (recusado) | "quando a forma de pagamento recusa o total" | Que nada foi gravado nem avisado; a fronteira 150,00 / 150,01 |
| E) retirada, R$ 40,00, dinheiro | `dinheiroNaoAlteraOValor`, `retiradaNuncaCobraFrete` | — |
| *(não existia)* | `limiteDoDinheiroValeParaOTotalComFrete` | Limite aplicado ao total, não ao subtotal |
| *(não existia)* | `pedidoFicaGravadoMesmoSemAviso` | Comportamento quando o aviso falha |
| *(não existia)* | `todoFreteRespeitaOContrato` | Contrato válido para constantes futuras do `enum` |

## Gabarito docente

> Esta seção é para o docente. Não distribuir antes da atividade.

### Parte 1 — respostas

1. R$ 100,00, inclusive (`ENTREGA tem frete gratis a partir de R$ 100,00 (inclusive)`).
2. Ao total com frete (`o limite do dinheiro vale para o total com frete, nao para o subtotal`).
3. Fica gravado (`o pedido ja ficou gravado (comportamento atual, registrado para discussao)`).

### Parte 2 — resultados esperados

Ver a tabela da seção 9 da [fundamentação](../01-fundamentacao-teorica.md). Perguntas para o fechamento:

1. Por que o `Main` não viu os erros 1, 2 e 3? (Os valores dos cenários estão longe das fronteiras.)
2. No erro 4, por que a linha `0.00` continuou passando? (3% de zero é zero — um caso que não distingue as duas fórmulas.)
3. No erro 5, três testes falharam. Isso é bom ou ruim? (É o esperado para um erro no ponto de integração; ruim seria se falhassem trinta, sem indicar onde.)
4. Algum grupo consegue um erro que **nenhum** teste detecta? Dois que sobrevivem (verificado):
   - trocar `HALF_UP` por `HALF_EVEN` em `Pagamento.CARTAO`. Os três casos de `cartaoAcrescentaTresPorCento` não distinguem os modos: 0,50 × 1,03 = 0,515, que dá 0,52 nos dois. **A lacuna foi deixada de propósito**; o caso que a fecha é `"1.50, 1.55"` (1,545: `HALF_UP` dá 1,55; `HALF_EVEN` daria 1,54). Compare com o PIX, onde a linha `0.30` existe exatamente para isso;
   - trocar o texto impresso por `NotificadorPorEmail` (a classe nunca é executada nos testes — e não deveria ser).

   Discutir: o primeiro pede um teste novo; o segundo, não. Por quê?
5. E inverter a ordem `salvar`/`avisar` em `fechar`? Parece invisível, mas `pedidoFicaGravadoMesmoSemAviso` falha: com o aviso antes, a exceção impede a gravação. O teste de caracterização protege uma ordem que ninguém tinha decidido proteger — bom ponto para discutir o que ele significa.

### Parte 3 — o teste que falha no passo 3

Falham dois: o teste novo do grupo (se o esperado for `new BigDecimal("5.00")`: mesmo valor, escala diferente — a armadilha 2 de `CardapioTest`) e `todoFreteRespeitaOContrato`, porque `new BigDecimal("5")` tem escala 0 e o contrato exige duas casas. Este segundo já existia e pegou a constante nova **sem ter sido editado** (verificado). É o momento de mostrar que o recibo imprimiria `frete R$ 5` em vez de `R$ 5.00`.

Para M2 (`VALE_REFEICAO`), o mínimo defensável são três testes: um valor comum, R$ 120,00 (aceita) e R$ 120,01 (recusa).

### Parte 4 — pontos de armadilha

- Grupos colocam o `if` dentro do `NotificadorFalso` ("se o destino for vazio, não registra"). O teste passa e o código de produção continua errado: **a regra foi parar no dublê**. Pergunte: *o `NotificadorPorEmail` real se comporta assim?*
- Grupos verificam só `notificador.avisos().isEmpty()` e esquecem de verificar que o pedido **foi** gravado. Um `return` no começo de `fechar` passaria nesse teste.
- A pergunta de design que aparece: "e-mail vazio" como `String` vazia é *Primitive Obsession* (Aula 08). Um tipo `Contato` resolveria — vale a pena agora? (Checklist da seção 6 da Aula 09.)

### O que os testes desta pasta **não** cobrem (para discussão)

1. `NotificadorPorEmail` e o `Main` não são executados por nenhum teste. Testar o envio real seria um teste de **integração**.
2. Nenhum teste passa `null`. `Pedido` aceita `subtotal` nulo e falha depois, com `NullPointerException`, longe da causa. Falta validação no construtor do `record` — e um teste para ela.
3. Subtotal negativo é aceito. É regra de negócio que ninguém definiu.
4. Os testes de `ReciboDoPedido` comparam o texto inteiro: qualquer mudança de formato os quebra. É aceitável aqui (o formato **é** o comportamento da classe), mas seria fragilidade se esses textos fossem conferidos nos testes do serviço.
