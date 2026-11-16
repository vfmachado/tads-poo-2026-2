# Aula 10 — Introdução a testes com JUnit: do `println` que alguém confere ao teste que se confere sozinho

> No [cronograma](../Cronograma.md) original, testes aparecem como item 12 ("Testes em OO: unidade, integração e cobertura"). Esta aula cobre a parte de **teste de unidade**; integração e cobertura aparecem apenas como vocabulário (seção 8 da fundamentação).

## 1. Título da aula

**Testes de unidade com JUnit: como fazer o computador conferir, a cada mudança, o que até agora só o nosso olho conferia.**

## 2. Objetivos de aprendizagem

Ao final desta aula, o estudante deve ser capaz de:

1. **Explicar** por que a verificação por `Main` + `diff` usada nas Aulas 08 e 09 não basta, apontando o que ela detecta e o que deixa passar.
2. **Escrever** um teste de unidade com JUnit no formato *Arrange–Act–Assert*, com nome que descreve o comportamento esperado.
3. **Escolher** a asserção adequada (`assertEquals`, `assertTrue`, `assertThrows`, `assertAll`) e **evitar** as armadilhas de igualdade com `double` e `BigDecimal`.
4. **Selecionar** casos de teste por partições de equivalência e valores-limite, e expressá-los com testes parametrizados.
5. **Isolar** a classe testada de dependências externas com dublês escritos à mão (stub e espião), **relacionando** essa possibilidade com a inversão de dependência da Aula 09.
6. **Avaliar** a qualidade de um conjunto de testes introduzindo erros de propósito no código e observando quais testes falham.

## 3. Índice dos materiais desta aula

| Arquivo | Conteúdo |
|---|---|
| [01-fundamentacao-teorica.md](01-fundamentacao-teorica.md) | O que é (e o que não é) teste de unidade; anatomia de um teste; o essencial do JUnit; como escolher casos; dublês; relação com SOLID; limites; evidências do exemplo |
| [02-exemplos-por-conceito/](02-exemplos-por-conceito/) | Seis conceitos, cada um com uma classe pequena e seu teste: primeiro teste, asserções, exceções, ciclo de vida, parametrizados, dublês (38 testes) |
| [03-exemplo-completo/](03-exemplo-completo/) | O fechamento de pedido da Aula 09 (`equilibrado/`), agora com 33 testes — incluindo o teste de `fechar` sem enviar e-mail (mudança **M4** da Aula 09) |
| [04-erros-frequentes.md](04-erros-frequentes.md) | Equívocos conceituais e de implementação |
| [05-leituras-recomendadas.md](05-leituras-recomendadas.md) | Leituras |

A atividade prática está na seção 5 deste arquivo.

## 4. Recursos utilizados

**Do JUnit** (pacotes `org.junit.jupiter.api` e `org.junit.jupiter.params`):

- `@Test`, `@DisplayName`, `@Nested`;
- `@BeforeEach` (e, só para demonstração, `@BeforeAll`, `@AfterEach`, `@AfterAll`);
- `assertEquals`, `assertNotEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertNotNull`, `assertNotSame`, `assertThrows`, `assertDoesNotThrow`, `assertAll`;
- `@ParameterizedTest` com `@CsvSource`, `@ValueSource` e `@EnumSource`.

**Da linguagem Java:** *lambda* (para `assertThrows`, `assertAll` e para dublês de interfaces de um método), `import static`, `record` (igualdade por valor nos testes), `BigDecimal`.

**Do ambiente:** estrutura de projeto Maven (`src/main/java` × `src/test/java`, `pom.xml`). Os dois projetos desta aula usam **JUnit 6.1.3** e **Java 21**. As anotações e asserções usadas são as mesmas do JUnit 5; quem estiver em Java 11 pode trocar a versão no `pom.xml` para `5.14.4`.

Deliberadamente **fora** do escopo: Mockito e outras bibliotecas de dublês, TDD como disciplina, testes de integração, ferramentas de cobertura (JaCoCo) e de mutação (PIT). Aparecem pelo nome, para que a turma saiba que existem.

## 5. Como executar

Pelo IDE (IntelliJ ou VS Code com *Extension Pack for Java*): abrir a pasta `02-exemplos-por-conceito` **ou** `03-exemplo-completo` como projeto (é a pasta que contém o `pom.xml`) e usar o botão de executar ao lado de cada classe ou método de teste.

Pelo terminal, com Maven instalado:

```bash
cd 03-exemplo-completo
mvn test
```

Sem Maven nem IDE: ver "Como executar sem Maven" em [03-exemplo-completo/README.md](03-exemplo-completo/README.md).

## 6. Exercícios de fixação

Todos partem de [03-exemplo-completo/](03-exemplo-completo/). Antes de começar, rode os testes e confirme: 33 passando.

### Parte 1 — Ler os testes como especificação

Sem abrir `src/main/java`, só lendo os nomes dos testes no relatório, responder:

1. A partir de qual subtotal a entrega é grátis? O valor exato entra ou não?
2. O limite do dinheiro se aplica ao subtotal ou ao total com frete?
3. O que acontece com o pedido se o envio do aviso falhar?

Depois, conferir no código. **Se um nome de teste não permitiu responder, reescreva o nome.**

### Parte 2 — Quebrar de propósito

Em duplas, introduzir **um erro por vez** no código de produção, rodar os testes, anotar quais falharam e **desfazer** antes do próximo:

| # | Erro a introduzir | Onde |
|---|---|---|
| 1 | Trocar `>= 0` por `> 0` no frete grátis | `Frete.ENTREGA` |
| 2 | Trocar `> 0` por `>= 0` no limite | `Pagamento.DINHEIRO` |
| 3 | Trocar `HALF_UP` por `HALF_EVEN` | `Pagamento.PIX` |
| 4 | Trocar `0.02` por `0.03` | `Frete.EXPRESSA` |
| 5 | Calcular o valor a pagar sobre o subtotal, não sobre o total | `ServicoDePedido.fechar` |
| 6 | Mover `repositorio.salvar(pedido)` para antes do cálculo do pagamento | `ServicoDePedido.fechar` |

Para cada erro: **o `Main` teria mostrado?** (Rodar o `Main` antes e depois e comparar as saídas.) Comparar com a tabela da seção 9 da fundamentação.

### Parte 3 — Escrever testes para uma regra nova

Implementar a mudança **M1** da Aula 09 (tipo de atendimento `AGENDADA`, R$ 5,00 fixos) nesta ordem:

1. Rodar os testes **antes** de mexer em qualquer coisa.
2. Escrever em `FreteTest` o teste de `AGENDADA`. Ele não compila: a constante não existe.
3. Criar a constante devolvendo `new BigDecimal("5")`. Rodar. **Quais testes falham, e por quê?** (Dica: um deles você não escreveu.)
4. Corrigir e chegar a todos os testes passando.

Depois, o mesmo para **M2** (`VALE_REFEICAO`: sem desconto; recusa total acima de R$ 120,00). Quantos testes essa regra precisa? Justificar pela tabela de partições e limites.

### Parte 4 — Um dublê novo

Requisito: *o aviso só deve ser enviado se o cliente tiver e-mail* (campo vazio = cliente sem e-mail; o pedido é fechado e gravado normalmente).

1. Escrever primeiro o teste em `ServicoDePedidoTest`, usando o `NotificadorFalso`.
2. Vê-lo falhar.
3. Alterar `ServicoDePedido` até passar, sem quebrar os outros 33.

### Parte 5 — Testar o próprio código

Escolher uma classe com regra de negócio do Trabalho II e escrever para ela **pelo menos cinco testes**, com: um caminho feliz, um par de valores-limite, um caminho de erro com `assertThrows` e a verificação de que o objeto continua válido depois do erro.

Registrar: **o que foi difícil de testar, e por quê?** (Se a resposta for "a classe lê do teclado", "imprime direto" ou "cria suas dependências com `new`", a dificuldade é uma informação sobre o design — ver seção 7 da fundamentação.)
