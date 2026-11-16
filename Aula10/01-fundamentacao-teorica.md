# Fundamentação teórica — Aula 10

> Todos os trechos de código citados existem em [02-exemplos-por-conceito/](02-exemplos-por-conceito/) e [03-exemplo-completo/](03-exemplo-completo/), compilam e passam. Os números da seção 9 foram medidos nesses arquivos.

## 1. De onde viemos: o `Main` que alguém confere

Nas Aulas 08 e 09, a prova de que uma refatoração preservou o comportamento era esta:

```bash
java ... antes.Main  > antes.txt
java ... depois.Main > depois.txt
diff antes.txt depois.txt && echo "comportamento preservado"
```

Isso **já é um teste automatizado** — um *teste de caracterização*: compara o que o programa faz agora com o que fazia antes. Funcionou porque a pergunta era "mudou alguma coisa?". Ele tem quatro limites:

| Limite | Consequência |
|---|---|
| Compara com a versão **anterior**, não com a regra **correta** | Se a versão anterior estava errada, o `diff` aprova o erro |
| Só exercita os cenários que alguém lembrou de pôr no `Main` | Cinco cenários; nenhum na fronteira de uma regra |
| Quando falha, diz **que** mudou, não **o que** quebrou | Uma linha diferente no meio de quinze; achar a causa é com você |
| Depende de tudo que é impresso | Mudar um texto de tela "quebra" a verificação de uma regra de cálculo |

Um **teste de unidade** responde a outra pergunta: *este comportamento específico está de acordo com a regra?* E responde sozinho: passa ou falha, sem ninguém ler saída.

### O que é — e o que não é — um teste de unidade

Um teste de unidade é um trecho de código que **executa uma pequena parte do sistema** (um método, uma classe, um punhado de classes que colaboram), **com dados escolhidos**, e **compara o resultado com um valor esperado declarado no próprio teste**. Três propriedades o definem: é **automático** (decide sozinho), **rápido** (milissegundos) e **isolado** (não depende de rede, disco, relógio, nem de outro teste).

| Categoria | O que é | Nesta aula |
|---|---|---|
| **Prática** | Atividade que se executa | Escrever testes de unidade; refatorar com os testes rodando |
| **Técnica** | Um modo de fazer a prática bem | Partições de equivalência, valores-limite, dublês |
| **Ferramenta** | O que executa e relata | **JUnit** (escrever e rodar), Maven/IDE (disparar) |
| **Disciplina de trabalho** | Ordem em que se trabalha | TDD (escrever o teste antes) — fora do escopo; a Parte 3 da atividade dá uma amostra |
| **Métrica** | Número que resume algo | Cobertura — vocabulário, seção 8 |

Duas confusões que a tabela evita: **JUnit não é "teste de unidade"** (é a ferramenta; dá para escrever testes ruins com ela e testes de integração com ela) e **ter testes não é fazer TDD**.

### A regra de ouro da aula

> **Um teste é uma afirmação sobre o comportamento, escrita de um jeito que a máquina consegue conferir.** Se ao ler o nome do teste você não sabe que afirmação é essa, ou se o teste continuaria passando com o código errado, ele não está afirmando nada.

## 2. Anatomia de um teste

De [`FreteTest`](03-exemplo-completo/src/test/java/foodexpress/aula10/FreteTest.java):

```java
@Test
@DisplayName("ENTREGA abaixo de R$ 100,00 cobra frete fixo de R$ 8,00")
void entregaAbaixoDoLimiteCobraFreteFixo() {
    // Arrange (preparar)
    BigDecimal subtotal = new BigDecimal("59.70");

    // Act (agir)
    BigDecimal frete = Frete.ENTREGA.para(subtotal);

    // Assert (verificar)
    assertEquals(new BigDecimal("8.00"), frete);
}
```

| Parte | Pergunta | Regra prática |
|---|---|---|
| **Nome** | Que comportamento está sendo afirmado? | Descreve a **regra**, não o método: `entregaAbaixoDoLimiteCobraFreteFixo`, e não `testPara1` |
| **Arrange** | Em que situação? | Só o necessário para **este** cenário |
| **Act** | O que é feito? | **Uma** chamada. Duas ações = dois testes |
| **Assert** | O que deve ser verdade depois? | Valor esperado **escrito à mão** no teste, nunca recalculado com a mesma fórmula da produção |

A última linha da tabela merece atenção. Este teste não verifica nada:

```java
assertEquals(total.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP),
             Pagamento.PIX.valorAPagar(total));     // repete a fórmula: se ela estiver errada, o teste erra junto
```

O esperado tem de vir **da regra de negócio** ("5% de desconto sobre R$ 100,00 dá R$ 95,00"), calculado por fora.

## 3. O essencial do JUnit

O JUnit é o *framework* de testes padrão do ecossistema Java. A parte que se usa para **escrever** testes chama-se **JUnit Jupiter** (pacote `org.junit.jupiter`); ela é a mesma no JUnit 5 e no JUnit 6. O que o JUnit faz: encontra os métodos anotados, cria os objetos, executa cada teste, captura falhas e produz o relatório.

### 3.1 Onde ficam os testes

```
03-exemplo-completo/
├── pom.xml                      <- declara a dependência do JUnit (escopo "test")
└── src/
    ├── main/java/foodexpress/aula10/    <- código de produção
    │       Frete.java
    └── test/java/foodexpress/aula10/    <- testes: MESMO pacote, OUTRA árvore
            FreteTest.java
```

O mesmo pacote dá ao teste acesso ao que é *package-private*; a árvore separada garante que testes e JUnit **não vão para produção**. Convenção de nome: `Frete` → `FreteTest`.

### 3.2 Anotações usadas nesta aula

| Anotação | Papel | Exemplo |
|---|---|---|
| `@Test` | Marca um método como teste | todos |
| `@DisplayName("...")` | Nome legível no relatório | [`FreteTest`](03-exemplo-completo/src/test/java/foodexpress/aula10/FreteTest.java) |
| `@BeforeEach` | Roda antes de **cada** teste: monta o cenário comum | [`ServicoDePedidoTest`](03-exemplo-completo/src/test/java/foodexpress/aula10/ServicoDePedidoTest.java) |
| `@Nested` | Agrupa testes que partem da mesma situação | "quando o pedido é aceito" / "quando o pagamento recusa" |
| `@ParameterizedTest` + `@CsvSource` / `@ValueSource` / `@EnumSource` | O mesmo teste com vários dados | [`TaxaDeEntregaTest`](02-exemplos-por-conceito/src/test/java/exemplos/parametrizados/TaxaDeEntregaTest.java) |
| `@BeforeAll`, `@AfterEach`, `@AfterAll` | Demais pontos do ciclo de vida (uso raro em teste de unidade) | [`OrdemDasChamadasTest`](02-exemplos-por-conceito/src/test/java/exemplos/ciclodevida/OrdemDasChamadasTest.java) |
| `@Disabled("motivo")` | Desliga um teste temporariamente — sempre com o motivo | não usado; ver [erros frequentes](04-erros-frequentes.md) |

### 3.3 Asserções

Todas são métodos estáticos de `org.junit.jupiter.api.Assertions`. A ordem é sempre **(esperado, obtido)**.

| Asserção | Quando usar |
|---|---|
| `assertEquals(esperado, obtido)` | O caso geral. Usa `equals()` |
| `assertEquals(esperado, obtido, delta)` | `double`/`float`: **sempre** com tolerância |
| `assertTrue(cond)` / `assertFalse(cond)` | Perguntas de sim ou não. Não use para comparar valores: `assertTrue(a == b)` falha sem dizer quanto valia `a` |
| `assertNull` / `assertNotNull` | Ausência e presença |
| `assertThrows(Tipo.class, () -> ...)` | O trecho **deve** lançar a exceção; devolve-a para inspecionar a mensagem |
| `assertDoesNotThrow(() -> ...)` | Deixa explícito que "não lançar" é o que se está afirmando |
| `assertAll(() -> ..., () -> ...)` | Várias verificações do **mesmo** resultado; relata todas as falhas |

Três armadilhas de igualdade, todas demonstradas em [`CardapioTest`](02-exemplos-por-conceito/src/test/java/exemplos/assercoes/CardapioTest.java):

1. **Objetos:** `assertEquals` chama `equals()`. Com `record` ([Aula 07](../Aula07/)) funciona por valor; com classe comum sem `equals()`, compara identidade e falha.
2. **`double`:** `0.1 + 0.2` não é `0.3`. Use o terceiro argumento.
3. **`BigDecimal`:** `equals()` considera a escala — `10.0` e `10.00` **não** são `equals`. Ou o código garante a escala (`setScale(2)`, como em `Frete`), ou o teste compara com `compareTo`.

### 3.4 Ciclo de vida e isolamento

O JUnit cria **um objeto novo da classe de teste para cada método `@Test`** (rode [`OrdemDasChamadasTest`](02-exemplos-por-conceito/src/test/java/exemplos/ciclodevida/OrdemDasChamadasTest.java) para ver). Somado ao `@BeforeEach`, isso garante que cada teste parte do zero:

```java
@BeforeEach
void montarServico() {
    repositorio = new RepositorioDePedidos();
    notificador = new NotificadorFalso();
    servico = new ServicoDePedido(repositorio, notificador);
}
```

Consequências: a **ordem** de execução não importa (e não é garantida); um teste que falha não derruba os seguintes; pode-se rodar um teste sozinho. O que quebra o isolamento é estado **compartilhado**: campos `static`, arquivos, banco, singletons.

### 3.5 Exceções

```java
IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
        () -> Pagamento.DINHEIRO.valorAPagar(total));

assertEquals("Dinheiro nao aceito acima de R$ 150.00", erro.getMessage());
```

Depois do erro, verifique também **o que não aconteceu** — em [`ServicoDePedidoTest`](03-exemplo-completo/src/test/java/foodexpress/aula10/ServicoDePedidoTest.java), que o pedido recusado não foi gravado nem avisado. O caminho de erro é comportamento como outro qualquer, e costuma ser o menos testado.

### 3.6 Testes parametrizados

```java
@ParameterizedTest(name = "subtotal {0} -> frete {1}")
@CsvSource({
        "0.00,   8.00",
        "99.99,  8.00",
        "100.00, 0.00",
        "100.01, 0.00"
})
void entregaTemFreteGratisAPartirDeCemReais(BigDecimal subtotal, BigDecimal freteEsperado) {
    assertEquals(freteEsperado, Frete.ENTREGA.para(subtotal));
}
```

Cada linha é um teste independente no relatório. O JUnit converte os textos para o tipo do parâmetro (`int`, `BigDecimal`, `LocalTime`, constantes de `enum`...). Use quando a **estrutura** do teste é idêntica e só os **dados** mudam; se cada linha precisa de um `if` dentro do teste, são testes diferentes.

## 4. Como escolher os casos de teste

Testar todas as entradas é impossível; a questão é escolher poucas que valham por muitas.

**Partições de equivalência.** Divida as entradas em grupos nos quais o código *deveria* se comportar do mesmo jeito; um representante por grupo. Para `Frete.ENTREGA`: "abaixo de 100" e "100 ou mais".

**Valores-limite.** Os erros se concentram nas fronteiras entre partições (`>` no lugar de `>=`). Para cada fronteira, dois casos: o último de um lado e o primeiro do outro.

| Regra | Partições | Valores-limite testados |
|---|---|---|
| Entrega grátis a partir de R$ 100,00 | paga · grátis | 99,99 · **100,00** · 100,01 |
| Dinheiro até R$ 150,00 | aceita · recusa | **150,00** · 150,01 |
| Taxa por distância (3 km, 8 km, 15 km) | 3 faixas válidas · negativa · fora da área | 0 · 3000/3001 · 8000/8001 · 15000/15001 · −1 |
| Aberto das 11h às 23h | fechado · aberto · fechado | 10:59/11:00 · 22:59/23:00 |

**Lista de verificação por comportamento:**

1. O caminho feliz mais comum.
2. Cada fronteira (dois casos).
3. Cada caminho de erro — e o estado do objeto depois dele.
4. Os valores "esquisitos" do tipo: zero, vazio, um elemento, negativo, `null` (se for aceito).
5. Arredondamento, quando há dinheiro: um caso que termine exatamente em meio centavo.

**O que não vale um teste:** *accessors* gerados de um `record`, código do JDK ("o `ArrayList` adiciona?"), e a mesma partição cinco vezes com números diferentes.

## 5. Dublês de teste

Algumas classes dependem de coisas que o teste não pode ou não deve usar de verdade: e-mail, banco, rede, relógio. Um **dublê** (*test double*, termo de Gerard Meszaros) é um objeto que ocupa o lugar da dependência real durante o teste.

| Tipo | O que faz | Quando usar | Exemplo |
|---|---|---|---|
| **Stub** | Devolve **respostas prontas** | O objeto testado faz uma **consulta** à dependência | `Relogio relogioParado = () -> hora;` em [`RestauranteTest`](02-exemplos-por-conceito/src/test/java/exemplos/dubles/RestauranteTest.java) |
| **Espião** (*spy*) | **Registra** o que recebeu | O objeto testado dá um **comando** à dependência | [`NotificadorFalso`](03-exemplo-completo/src/test/java/foodexpress/aula10/NotificadorFalso.java) |
| **Fake** | Implementação simplificada que funciona de verdade | Substituir infraestrutura pesada | Um repositório em memória no lugar de um banco |
| **Mock** | Espião que já sabe o que **espera** receber e falha sozinho | Geralmente via biblioteca (Mockito) | fora do escopo |

A distinção stub × espião retoma **comando × consulta** (Aula 04): se o método da dependência devolve valor, controla-se a resposta; se não devolve, registra-se a chamada.

Nesta aula os dublês são **escritos à mão** — uma classe de dez linhas ou um *lambda*. Bibliotecas como Mockito automatizam isso; só vale adotá-las depois de entender o que elas geram.

**Dublê só é possível com a dependência injetada.** `ServicoDePedido` recebe `Notificador` pelo construtor (DIP, [Aula 09](../Aula09/01-fundamentacao-teorica.md)); por isso o teste pode entregar um `NotificadorFalso`. Na versão `antes/` da Aula 09, com `new NotificadorPorEmail()` dentro do serviço, não havia por onde entrar. É a mudança **M4** daquela aula, agora executada.

**Não dublar o que não precisa.** `RepositorioDePedidos` é rápido, em memória e determinístico: os testes usam o **real**. Dublar tudo produz testes que só verificam que o código chama o que chama.

## 6. O que faz um teste ser bom

O acrônimo **FIRST** (popularizado em *Clean Code*) resume:

| Letra | Propriedade | Sintoma de violação |
|---|---|---|
| **F** — *Fast* | Roda em milissegundos | "Só rodo os testes antes de entregar" |
| **I** — *Independent* | Não depende de outro teste nem da ordem | Passa sozinho, falha em conjunto (ou o contrário) |
| **R** — *Repeatable* | Mesmo resultado em qualquer máquina e horário | Falha às vezes; depende de data, rede, idioma do sistema |
| **S** — *Self-validating* | Passa ou falha sem interpretação humana | Teste com `System.out.println` e sem `assert` |
| **T** — *Timely* | Escrito junto com o código | "Depois eu escrevo os testes" |

Mais dois critérios que o acrônimo não cobre:

- **Uma razão para falhar.** Quando o teste quebra, o nome dele já deve dizer qual regra foi violada.
- **Testa comportamento, não implementação.** O teste conhece a interface pública e a regra de negócio. Se uma refatoração que não muda o comportamento quebra o teste, o teste estava acoplado ao *como*.

## 7. Testabilidade é informação sobre o design

Código difícil de testar quase sempre tem um problema de design que as aulas anteriores já nomearam. O teste é o **primeiro cliente** da classe: se ele sofre para usá-la, os outros clientes também sofrerão.

| Dificuldade ao testar | O que ela revela | Onde foi discutido |
|---|---|---|
| "Preciso montar dez objetos para chamar um método" | Acoplamento alto; classe conhece demais | [Aula 05](../Aula05/); R5 e R8 da [Aula 08](../Aula08/) |
| "Não consigo trocar o e-mail/banco/relógio" | `new` do detalhe dentro da regra | **D** ([Aula 09](../Aula09/)) |
| "O método calcula **e** imprime; não tenho o que comparar" | Duas responsabilidades; comando misturado com consulta | **S**; Aula 04 |
| "Para testar uma regra tenho de passar por todas as outras" | Método que faz tudo | **S**; R1 e R7 |
| "O dublê precisa implementar cinco métodos que não uso" | Interface gorda | **I** |
| "O teste passa com uma implementação e falha com outra" | Contrato não respeitado | **L** |
| "Tenho de ler um campo privado para verificar" | Falta comportamento público observável — ou estou testando implementação | Encapsulamento ([Aula 03](../Aula03/)) |

Compare com o exemplo: `Frete` e `Pagamento` são funções puras e se testam em uma linha; `ReciboDoPedido` se testa sem o serviço; `ServicoDePedido` se testa sem e-mail. Essas três facilidades são consequência direta das refatorações das Aulas 08 e 09.

Relação com as aulas anteriores, em uma frase: **Calistenia e SOLID tornaram o código testável; os testes tornam a próxima refatoração segura.**

## 8. Limites e riscos

| Risco | Como se manifesta |
|---|---|
| **Teste que não falha nunca** | Sem asserção, ou com esperado recalculado pela fórmula de produção. Passa com o código certo e com o errado |
| **Falsa segurança da cobertura** | *Cobertura* mede quais linhas foram **executadas** por algum teste, não quais foram **verificadas**. Um teste sem `assert` dá 100% de cobertura |
| **Testes frágeis** | Acoplados a texto de tela, ordem de chamadas internas ou estrutura privada; quebram a cada refatoração e passam a ser ignorados |
| **Testes lentos ou intermitentes** | Deixam de ser rodados; um teste que "às vezes falha" ensina a equipe a desconfiar de todos |
| **Excesso de dublês** | O teste descreve as chamadas internas, não o resultado |
| **Testar só o caminho feliz** | As regras de recusa, limite e erro ficam sem dono |

> **Atenção à armadilha lógica:** testes mostram a **presença** de erros, nunca a ausência (Dijkstra). Trinta e três testes passando significam "nenhuma das trinta e três afirmações foi violada" — não "o código está correto". O que se ganha não é certeza: é **detectar cedo** a violação de algo que alguém se deu ao trabalho de afirmar.

**Vocabulário para as próximas aulas:**

- **Teste de integração:** verifica várias partes juntas *com* infraestrutura real (banco, arquivo, HTTP). Mais lento, pega erros que o de unidade não vê (configuração, SQL, formato).
- **Pirâmide de testes:** muitos testes de unidade, menos de integração, poucos de ponta a ponta.
- **Cobertura:** percentual de linhas/ramos executados pelos testes (ferramenta: JaCoCo). Útil para achar o que **não** foi testado; inútil como meta.
- **Teste de mutação:** ferramenta que introduz erros no código e verifica se algum teste falha (PIT). A Parte 2 da atividade faz isso à mão.
- **TDD:** escrever o teste antes do código, em ciclos curtos (vermelho → verde → refatorar).

## 9. Evidências do exemplo

Medidas feitas em [03-exemplo-completo/](03-exemplo-completo/):

| Medida | Valor |
|---|---|
| Classes de produção (sem `Main`) | 8 arquivos, 158 linhas |
| Classes de teste | 4 + 1 dublê, 338 linhas |
| Métodos de teste | 19 |
| Testes executados (parametrizados expandidos) | 33 |
| Dublês | 2 (`NotificadorFalso`; um *lambda* que lança exceção) |
| Tempo de execução | menos de 1 segundo |
| Linhas impressas ou e-mails enviados durante os testes | 0 |

O código de teste tem **o dobro** das linhas do código de produção. Isso é comum e não é desperdício: é o que custa ter as regras escritas de forma conferível.

### O experimento: seis erros introduzidos de propósito

Cada erro foi aplicado sozinho ao código de produção. Coluna "`Main`": a saída do `Main` (os cinco cenários A–E da Aula 09) mudou? Coluna "Testes": quais falharam.

| # | Erro introduzido | `Main` percebe? | Testes que falham |
|---|---|---|---|
| 1 | `Frete.ENTREGA`: `>= 100` vira `> 100` | **Não** | 1 — `entregaTemFreteGratisAPartirDeCemReais` (linha `100.00`) |
| 2 | `Pagamento.DINHEIRO`: `> limite` vira `>= limite` | **Não** | 1 — `dinheiroAceitaExatamenteOLimite` |
| 3 | `Pagamento.PIX`: `HALF_UP` vira `HALF_EVEN` | **Não** | 1 — `pixDaCincoPorCentoDeDesconto` (linha `0.30`) |
| 4 | `Frete.EXPRESSA`: 2% vira 3% | Sim | 3 — `expressaCobraTaxaFixaMaisPercentual` (três linhas) |
| 5 | `ServicoDePedido`: pagamento calculado sobre o subtotal | Sim | 3 — `reciboTemFreteTotalEValorAPagar`, `clienteRecebeUmAviso`, `limiteDoDinheiroValeParaOTotalComFrete` |
| 6 | `ServicoDePedido`: grava o pedido antes de calcular o pagamento | Sim | 1 — `nadaEGravadoNemAvisado` |

**Como ler:**

- O `Main` percebeu 3 de 6; os testes, 6 de 6. Os três que o `Main` deixou passar são **erros de fronteira e de arredondamento**: os cenários A–E usam 59,70, 120,00, 80,00, 200,00 e 40,00, todos longe dos limites.
- Nos erros 1, 2 e 3, **um único teste** falhou, e o nome dele aponta a regra violada. No erro 5, falharam três: o serviço é o ponto onde as regras se encontram, e um erro ali se espalha.
- No erro 4, três das quatro linhas da tabela falharam; a de subtotal `0.00` continuou passando (3% de zero é zero). Um conjunto só com esse caso não teria visto nada.
- O erro 6 o `Main` percebe **por acaso**: só porque imprime "Pedidos gravados: 4" no fim. Sem essa linha, passaria.

> **Importante:** esta tabela foi obtida com os testes desta pasta. Na Parte 2 da atividade a turma repete o experimento; se algum grupo encontrar um erro que **nenhum** teste detecta, encontrou um teste que falta escrever — e isso vale mais que a tabela. (Existe pelo menos um, deixado de propósito; está no gabarito de [03-exemplo-completo](03-exemplo-completo/README.md).)

### O que os testes revelaram sobre o código da Aula 09

Escrever os testes levou a duas perguntas que o `Main` nunca fez:

1. **O limite de R$ 150,00 do dinheiro vale para o subtotal ou para o total com frete?** O código aplica ao total. Um pedido expresso de R$ 135,00 em dinheiro é **recusado** (total R$ 152,70). É a regra desejada? O teste `limiteDoDinheiroValeParaOTotalComFrete` registra a resposta atual; a decisão é do negócio.
2. **Se o e-mail falhar, o pedido fica gravado?** Fica (`pedidoFicaGravadoMesmoSemAviso`). É o acoplamento temporal apontado no gabarito da Aula 09, agora documentado por um teste de caracterização.

Nenhuma das duas é um *bug* encontrado. São **decisões que estavam implícitas** e passaram a estar escritas — o segundo benefício dos testes, depois de detectar regressões.
