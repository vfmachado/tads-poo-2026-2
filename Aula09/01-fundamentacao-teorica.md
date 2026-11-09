# Fundamentação teórica — Aula 09

> Todos os trechos de código citados existem em [03-exemplo-completo/](03-exemplo-completo/) e [02-exemplos-por-principio/](02-exemplos-por-principio/) e compilam. Os marcadores `[S]`, `[O]`, `[L]`, `[I]`, `[D]` identificam o princípio violado (ou, em `depois/`, o princípio que justifica a estrutura).

## 1. O que é — e o que não é — SOLID

**SOLID** é a sigla para cinco princípios de design orientado a objetos popularizados por Robert C. Martin ("Uncle Bob") a partir de artigos do fim dos anos 1990 e início dos anos 2000; a sigla é atribuída a Michael Feathers. Cada princípio tem origem própria: o *Open-Closed* vem de Bertrand Meyer, a *Substituição* de Barbara Liskov, e os demais foram formulados por Martin. SOLID não é uma teoria única: é uma **coleção** de cinco respostas a uma mesma pergunta — *como o código se comporta quando os requisitos mudam?*

| Letra | Princípio | Pergunta que provoca |
|---|---|---|
| **S** | *Single Responsibility* — responsabilidade única | **Quantas razões (e quantos "donos") tem esta classe para mudar?** |
| **O** | *Open-Closed* — aberto/fechado | **Esta mudança exige editar código que já funciona, ou só acrescentar?** |
| **L** | *Liskov Substitution* — substituição | **Posso trocar o objeto por um subtipo sem o cliente perceber?** |
| **I** | *Interface Segregation* — segregação de interfaces | **O cliente depende só do que realmente usa?** |
| **D** | *Dependency Inversion* — inversão de dependência | **A regra de negócio depende do detalhe, ou o detalhe da regra?** |

### Classificação do que estamos estudando

Retomando a classificação da [Aula 08](../Aula08/01-fundamentacao-teorica.md):

| Categoria | Definição | Onde SOLID se encaixa |
|---|---|---|
| **Princípio** | Afirmação geral sobre por que um design é bom; expressa um valor | Os cinco, **no enunciado**: "uma razão para mudar", "substituível", "depender de abstrações" |
| **Heurística** | Regra de bolso que *tende* a produzir bom design e tem exceções | A **aplicação** de cada um: "se há `if` por tipo, talvez falte polimorfismo"; "interface com um método só costuma ser boa" |
| **Prática** | Atividade que se executa | Refatoração para chegar lá; injeção de dependência; TDD |
| **Padrão de projeto** | Solução nomeada para um problema recorrente | *Strategy*, *Factory*, *Adapter* — são **meios** para cumprir SOLID, não são SOLID |
| **Recurso da linguagem** | Mecanismo que viabiliza o design | `interface`, `enum`, `record`, `final`, construtor |
| **Restrição de exercício** | Regra artificial para treinar percepção | Object Calisthenics ([Aula 08](../Aula08/)) — **não** é SOLID |

Duas confusões comuns que esta tabela evita: tratar *Strategy* como "o OCP" (é uma das formas de cumpri-lo) e tratar *injeção de dependência* como "o DIP" (é o mecanismo mais comum; o princípio é a **direção** da dependência).

### A regra de ouro da aula

> **Cada princípio descreve um tipo de dor, não um tipo de classe.** SRP dói quando uma mudança do financeiro quebra a tela. OCP dói quando cada requisito novo reabre código testado. LSP dói quando aparece `instanceof` no cliente. ISP dói quando uma classe implementa métodos que lança exceção. DIP dói quando não se consegue testar sem o mundo externo. **Sem a dor, o princípio não tem o que resolver** — e aplicá-lo vira cerimônia.

### O que SOLID protege: as quatro "mudanças"

Para decidir qual princípio olhar, pergunte qual mudança está sendo temida:

- **Mudança de regra** (o financeiro alterou o desconto) → **S**: a regra mora em um só lugar.
- **Mudança por acréscimo** (mais um tipo de cupom, mais uma forma de pagar) → **O**: acrescenta-se, não se reescreve.
- **Mudança de implementação** (trocar e-mail por SMS, memória por banco) → **L, I, D**: a troca não vaza para quem usa.
- **Mudança no ambiente de teste** (rodar sem rede, sem banco) → **D**: o detalhe é plugável.

## 2. Mapa dos cinco princípios

| Grupo | Princípios | Pergunta do grupo |
|---|---|---|
| **Organização das responsabilidades** | S · O | *Onde mora cada decisão, e o que acontece quando ela muda?* |
| **Contratos entre tipos** | L · I | *O que cada tipo promete, e o cliente pode confiar nessa promessa?* |
| **Direção das dependências** | D | *Quem conhece quem, e em que sentido?* |

Os três grupos se reforçam: **L e I** garantem que as abstrações criadas para cumprir **O e D** sejam confiáveis e enxutas; **S** diz *quais* partes separar; **D** diz *em que direção* amarrá-las.

## 3. Os cinco princípios

Cada princípio segue o mesmo roteiro de dez itens da Aula 08. Os exemplos de violação vêm de [`antes/ServicoDePedido.java`](03-exemplo-completo/antes/ServicoDePedido.java); os refatorados, de [`depois/`](03-exemplo-completo/depois/); e a versão pragmática, de [`equilibrado/`](03-exemplo-completo/equilibrado/).

---

### 3.1 S — Single Responsibility Principle (responsabilidade única)

1. **Intenção.** Cada módulo deve ter **uma única razão para mudar** — formulação posterior de Martin: *responder a um único ator* (uma pessoa ou grupo que pede mudanças naquele código).
2. **Problema que busca evitar.** Mudança pedida por um ator quebrando funcionalidade usada por outro; classes que ninguém ousa tocar porque "faz tudo".
3. **Violação.**
   ```java
   public class ServicoDePedido {
       private final Notificador notificador = new NotificadorPorEmail();   // infraestrutura
       private final List<Pedido> pedidosGravados = new ArrayList<>();      // persistência

       public String fechar(Pedido pedido) {
           // regra de frete ...  regra de pagamento ...  gravar ...  notificar ...  formatar recibo
       }
   }
   ```
   Cinco motivos para mudar: frete (logística), pagamento (financeiro), persistência (infraestrutura), aviso (marketing/atendimento), texto do recibo (produto).
4. **Refatorado.**
   ```java
   public ReciboDoPedido fechar(Pedido pedido) {
       BigDecimal frete = pedido.frete().para(pedido.subtotal());
       BigDecimal total = pedido.subtotal().add(frete);
       BigDecimal aPagar = pedido.pagamento().valorAPagar(total);
       ...
       repositorio.salvar(pedido);
       notificador.avisar(pedido.emailDoCliente(), recibo.mensagemDeConfirmacao());
       return recibo;
   }
   ```
   O serviço só conhece a **ordem** do fluxo. Frete, pagamento, persistência, aviso e formato moram em colaboradores.
5. **Benefícios.** Mudanças localizadas; testes menores; menos conflito de merge entre pessoas que trabalham em assuntos diferentes.
6. **Limitações.** "Uma razão para mudar" depende de **quem** pede mudanças. Em um sistema de uma pessoa só, os "atores" se confundem. E o princípio não diz o tamanho certo: dá para separar demais.
7. **Exageros.** Uma classe por verbo (`CalculadorDeTotal`, `SomadorDeItens`, `ArredondadorDeTotal`) sem que nenhuma varie independentemente das outras. Resultado: fragmentação e *Shotgun Surgery* (uma mudança de negócio toca dez arquivos).
8. **Impacto.** Leitura: o nome da classe passa a dizer o que ela decide. Manutenção: o custo de mudar a regra de frete deixa de depender de entender a de pagamento.
9. **Flexibilização.** Código que muda sempre junto pode ficar junto (*Common Closure*). Em `equilibrado/`, `ReciboDoPedido` mantém o texto da tela **e** o da notificação (mesmo ator, mesmo ritmo de mudança) em vez de duas classes.
10. **Relações.** *Coesão* (Aula 03). Regras R7 e R8 da calistenia ([Aula 08](../Aula08/)) — classes pequenas e poucos campos são **sintomas**, não garantia, de SRP. *Divergent Change* (smell): uma classe que muda por motivos diferentes.

---

### 3.2 O — Open-Closed Principle (aberto/fechado)

1. **Intenção.** Entidades de software devem estar **abertas para extensão e fechadas para modificação** (Meyer; reinterpretado por Martin via polimorfismo): acrescentar um comportamento novo sem reescrever o código já testado.
2. **Problema que busca evitar.** *Shotgun Surgery* e regressões: cada requisito novo reabre um método central com `if/else if` por tipo.
3. **Violação.**
   ```java
   if (pedido.tipo().equals("ENTREGA")) {
       ...
   } else if (pedido.tipo().equals("EXPRESSA")) {
       ...
   } else if (pedido.tipo().equals("RETIRADA")) {
       ...
   } else {
       throw new IllegalArgumentException("Tipo de atendimento desconhecido: " + pedido.tipo());
   }
   ```
   Idem para a forma de pagamento. Um quarto tipo de atendimento obriga a **editar** este método.
4. **Refatorado.**
   ```java
   public interface CalculoDeFrete { BigDecimal para(BigDecimal subtotal); }
   public class FreteExpresso implements CalculoDeFrete { ... }
   ```
   Tipo novo = classe nova (`FreteAgendado implements CalculoDeFrete`); `ServicoDePedido` não muda. O ponto de extensão foi **escolhido** (a interface), e é nele que o princípio se paga.
5. **Benefícios.** Código antigo permanece testado e intocado; a mudança vira um acréscimo revisável.
6. **Limitações.** **Nenhum sistema é fechado para todas as mudanças.** O ponto de extensão precisa ser escolhido *antes* da mudança, e quem escolhe errado fica com abstração inútil e com uma mudança real que a abstração não cobre (por exemplo, um novo parâmetro que todas as implementações precisam receber).
7. **Exageros.** Interface criada para cada `if`; hierarquia aberta para uma decisão binária estável ("aceita dinheiro? sim/não"); *Speculative Generality*. Regra prática: **espere a primeira mudança real** (ou a segunda — "regra dos três"), então abstraia.
8. **Impacto.** Leitura: o fluxo deixa de ter ramos por tipo. Navegação: para entender "como o frete expresso é calculado" é preciso abrir um arquivo a mais.
9. **Flexibilização.** Variação **de valor** cabe num `enum` com dados; variação **de comportamento** cabe num `enum` com método abstrato por constante — que *edita* o `enum` ao acrescentar um caso, mas sem `if` encadeado e num só arquivo (ver [`equilibrado/Frete.java`](03-exemplo-completo/equilibrado/Frete.java)). Migrar para `interface` só quando os casos precisarem vir de fora do módulo.
10. **Relações.** **Direto resultado do experimento M3 da [Aula 08](../Aula08/03-exemplo-completo/README.md):** *"`enum` de dados cobre variação de valor; mudança de comportamento pede polimorfismo"* — esta aula dá nome a essa observação. Regra R2 da calistenia (sem `else`). *Strategy* (padrão).

---

### 3.3 L — Liskov Substitution Principle (substituição de Liskov)

1. **Intenção.** Onde se espera um objeto do tipo `T`, deve ser possível usar um objeto de qualquer subtipo de `T` **sem que o programa deixe de se comportar corretamente** (Barbara Liskov; formalização de Liskov e Wing como *behavioral subtyping*).
2. **Problema que busca evitar.** Subtipos que "herdam" a assinatura mas quebram a promessa; clientes forçados a testar o tipo concreto (`instanceof`) e perder o polimorfismo.
3. **Violação.** Em [`antes/NotificadorPorEmail.java`](03-exemplo-completo/antes/NotificadorPorEmail.java) e em [`lsp-substituicao-de-liskov/Antes.java`](02-exemplos-por-principio/lsp-substituicao-de-liskov/Antes.java):
   ```java
   static class PagamentoEmDinheiro extends Pagamento {
       void estornar(double valor) {
           throw new UnsupportedOperationException("dinheiro vivo nao estorna");
       }
   }
   // cliente: for (Pagamento p : pagamentos) p.estornar(50.0);   // explode no segundo
   ```
4. **Refatorado.** A capacidade "estornar" vira um **tipo separado**, e só quem de fato estorna o implementa:
   ```java
   interface Pagamento { void autorizar(double valor); }
   interface Estornavel extends Pagamento { void estornar(double valor); }
   ```
   O erro passa de **execução** para **compilação**.
5. **Benefícios.** O cliente confia no contrato do supertipo; o polimorfismo continua valendo.
6. **Limitações.** O "contrato" costuma não estar escrito. Em Java, a assinatura não captura **pré-condições, pós-condições e invariantes**; só a documentação e os testes capturam. Por isso LSP é difícil de verificar mecanicamente.
7. **Exageros.** Proibir toda herança por medo de violar LSP; ou, no outro extremo, criar uma hierarquia de interfaces só para modelar cada diferença mínima.
8. **Regras de contrato (o que "não quebrar" significa).** Um subtipo, para ser substituível: **não pode exigir mais** que o supertipo (pré-condição não se fortalece); **não pode prometer menos** (pós-condição não se enfraquece); **preserva as invariantes**; e não lança exceções que o contrato não prevê. Em `depois/`, `PagamentoEmDinheiro` recusa totais acima de R$ 150,00 — isso é **parte do contrato de `FormaDePagamento`** (documentado: "ou lança `IllegalArgumentException`"), portanto não viola LSP.
9. **Flexibilização.** O próprio JDK convive com violações pragmáticas: `List.of(...).add(x)` lança `UnsupportedOperationException`. É um **debate real** (discussão em [05-leituras](05-leituras-recomendadas.md)); em código de aplicação, prefira o tipo que não promete o que não entrega.
10. **Relações.** Herança e polimorfismo ([Aula 06](../Aula06/)); *composição sobre herança*; ISP (quase sempre que há `UnsupportedOperationException` há também interface gorda); smell *Refused Bequest*.

---

### 3.4 I — Interface Segregation Principle (segregação de interfaces)

1. **Intenção.** Nenhum cliente deve ser forçado a depender de métodos que não usa. Interfaces **pequenas e coesas**, desenhadas a partir de **quem as usa**.
2. **Problema que busca evitar.** Implementações obrigadas a fingir que sabem fazer o que não sabem; recompilação e retestes de clientes por mudanças em métodos que eles nem chamam.
3. **Violação.**
   ```java
   public interface Notificador {
       void enviarEmail(String destino, String mensagem);
       void enviarSms(String telefone, String mensagem);
       void enviarPush(String dispositivo, String mensagem);
   }
   // NotificadorPorEmail: sms e push lançam UnsupportedOperationException.
   ```
   O serviço só chama `enviarEmail`, mas depende de três métodos.
4. **Refatorado.**
   ```java
   public interface Notificador { void avisar(String destino, String mensagem); }
   ```
   SMS e push são **outras implementações** da mesma interface mínima, não métodos extras. Para o cardápio ([`isp-segregacao-de-interfaces/Depois.java`](02-exemplos-por-principio/isp-segregacao-de-interfaces/Depois.java)): `LeitorDeCardapio` e `EditorDeCardapio`, que uma classe pode implementar juntas.
5. **Benefícios.** Dublês de teste de uma linha; acoplamento mínimo; cada cliente declara exatamente do que precisa.
6. **Limitações.** Dividir demais gera dezenas de interfaces de um método; o critério é o **cliente**, não o número de métodos.
7. **Exageros.** Uma interface por método "por precaução"; `IReadable`, `IWritable`, `IFlushable`... mesmo quando todos os clientes sempre usam os três juntos.
8. **Impacto.** Testabilidade alta; leitura clara de "o que este objeto exige do colaborador".
9. **Flexibilização.** Se *todos* os clientes sempre usam todos os métodos juntos (e a coesão é real), uma interface de 3 métodos é a certa. Em Java, interfaces *funcionais* (um método) cumprem ISP "de graça" — `Supplier`, `Comparator`.
10. **Relações.** LSP (a interface gorda é a causa mais comum de violação de LSP por `UnsupportedOperationException`); DIP (o cliente é dono da interface que consome); *Tell Don't Ask* ([Aula 03](../Aula03/)).

---

### 3.5 D — Dependency Inversion Principle (inversão de dependência)

1. **Intenção.** (a) Módulos de alto nível (regra de negócio) **não devem depender** de módulos de baixo nível (e-mail, banco, arquivo); ambos devem depender de **abstrações**. (b) Abstrações não devem depender de detalhes; **detalhes dependem de abstrações**.
2. **Problema que busca evitar.** A regra de negócio, a parte mais valiosa e estável, refém da parte mais volátil (frameworks, bancos, serviços externos). Código impossível de testar sem o mundo externo.
3. **Violação.**
   ```java
   public class ServicoDePedido {
       private final Notificador notificador = new NotificadorPorEmail();            // new no detalhe
       private final List<Pedido> pedidosGravados = new ArrayList<>();               // persistência embutida
   ```
4. **Refatorado.**
   ```java
   public ServicoDePedido(RepositorioDePedidos repositorio, Notificador notificador) { ... }
   ```
   O serviço conhece **interfaces**. Quem escolhe as classes concretas é a **raiz de composição** ([`depois/Main.java`](03-exemplo-completo/depois/Main.java)): o único lugar com `new NotificadorPorEmail()`.
5. **Benefícios.** Troca de detalhe sem tocar na regra; testes com dublês (ver [`dip-inversao-de-dependencia/Depois.java`](02-exemplos-por-principio/dip-inversao-de-dependencia/Depois.java), com `NotificadorFalso`).
6. **Limitações.** "Inversão" significa que o **código-fonte** do detalhe aponta para a abstração, que pertence ao módulo de alto nível — não basta criar interface ao lado da classe concreta e declarar vitória. Sem essa direção, é só indireção.
7. **Exageros.** Interface para tudo, até para `String` e `Math`; container de DI para uma aplicação de dez classes; `IRepositorio` com uma única implementação *e* sem expectativa de outra.
8. **Impacto.** Navegação: um salto a mais ("quem implementa isto?"). Em compensação, o teste de unidade deixa de precisar de infraestrutura.
9. **Flexibilização.** Em `equilibrado/`, `RepositorioDePedidos` é **classe concreta** injetada no construtor (uma implementação, nenhum banco previsto), e `Notificador` permanece interface (variação real e fronteira externa). Extrair a interface depois é uma refatoração mecânica.
10. **Relações.** **Injeção de dependência** é o *mecanismo* (passar a dependência de fora); **inversão de dependência** é o *princípio* (direção); **IoC container** (Spring, Guice) é uma *ferramenta*. Os três são coisas diferentes. Retoma associação e dependência ([Aula 05](../Aula05/)) e a regra R5 da calistenia.

---

## 4. Conceitos e notas técnicas just-in-time

| Conceito | Descrição curta | Onde aparece |
|---|---|---|
| **Ator** | Pessoa/grupo que pede mudanças em um trecho de código | SRP: financeiro × logística × marketing |
| **Ponto de extensão** | Lugar escolhido para que variações entrem sem editar o resto | `CalculoDeFrete`, `FormaDePagamento` |
| **Módulo de alto/baixo nível** | Alto = regra de negócio; baixo = detalhe de E/S, banco, rede | `ServicoDePedido` × `NotificadorPorEmail` |
| **Contrato** | Pré-condições, pós-condições e invariantes de um tipo | LSP; comentário em `FormaDePagamento` |
| **Raiz de composição** | Único ponto onde as classes concretas são criadas e ligadas | `depois/Main.java` |
| **Dublê de teste** | Implementação falsa de uma abstração, usada em teste | `NotificadorFalso` |
| **Speculative Generality** | Abstração criada para um uso que nunca veio | Risco de OCP e DIP aplicados cedo |
| **Refused Bequest** | Subtipo que recusa parte do que herda | `PagamentoEmDinheiro.estornar` em `Antes` |
| **Divergent Change** | Uma classe muda por motivos diferentes | Sintoma de violação do SRP |
| **Shotgun Surgery** | Uma mudança exige editar muitos lugares | Sintoma de SRP aplicado em excesso, ou de OCP não aplicado |

**Notas técnicas de dois minutos (para o docente):**

- *Interface como fronteira:* declarar `private final Notificador notificador;` e atribuir no construtor. Quem escreve `new` fica fora da classe.
- *Dublê de teste:* uma classe de 5 linhas que implementa a interface e guarda o que recebeu (`List<String> enviados`). É o que torna o DIP **demonstrável** em sala.
- *`enum` com método abstrato:* retomado da [Aula 08](../Aula08/01-fundamentacao-teorica.md); aqui é a alternativa pragmática ao polimorfismo aberto.

## 5. Riscos de aplicar sem critério

| Risco | Como se manifesta | Evidência no exemplo |
|---|---|---|
| **Abstração prematura** (OCP, DIP) | Interface com uma única implementação e nenhuma variação à vista | `RepositorioDePedidos` + `RepositorioEmMemoria` em `depois/` |
| **Fragmentação** (SRP) | Entender uma regra exige abrir muitos arquivos | 15 arquivos em `depois/` contra 4 em `antes/` |
| **Classes que só embrulham** (SRP) | *Middle Man* | Seria o caso de um `ProcessadorDeFrete` que só chama `CalculoDeFrete` |
| **Interfaces anêmicas** (ISP) | Dezenas de interfaces de um método sem coesão | `IGetNome`, `IGetId` |
| **Herança por reaproveitamento** (LSP) | Subclasse criada para reusar código, não por "é um" | `PagamentoEmDinheiro extends Pagamento` em `Antes` |
| **Indireção que atrapalha depuração** (DIP) | A pilha de chamadas passa por interfaces e é preciso procurar a implementação real | Raiz de composição distante do ponto de falha |
| **Dogmatismo** | Rejeitar um `if` simples por "violar OCP" | Decisão binária estável |

> **Atenção à armadilha lógica:** SOLID reduz o custo de **tipos específicos de mudança** ao preço de **mais partes e mais indireção**. Só a mudança real (seção 8 e a Parte 4 da atividade) responde se compensou. Princípios sem contexto produzem cerimônia; contexto sem princípios produz *big ball of mud*.

## 6. Critérios para flexibilizar um princípio (checklist de revisão)

Antes de aplicar — ou de recusar — um princípio, responda por escrito:

1. **Qual dor este princípio resolve aqui?** Ela já aconteceu, ou estou imaginando?
2. **Quantas vezes essa região já mudou?** (Uma vez: observe. Duas: considere abstrair. Três: abstraia.)
3. **Existe mais de uma implementação** hoje ou concretamente prevista? Se não, a interface é só indireção.
4. **O ponto de extensão tem nome do domínio** que o especialista reconheceria (`FormaDePagamento`) ou nome de técnica (`Handler`, `Manager`, `Processor`)?
5. **Quem é o ator** que pede mudança neste trecho? Há mais de um?
6. **Qual o custo de reverter?** Extrair uma interface depois é barato; desfazer uma hierarquia espalhada, não.
7. **O contrato está escrito?** (LSP) Se os testes não o descrevem, a substituição é uma esperança.
8. **Registrei a decisão** (comentário curto, ADR ou mensagem de commit)?

## 7. Calistenia × SOLID: o que cada um ensina

A [Aula 08](../Aula08/) deu um **exercício** (restrições radicais para treinar a percepção); esta aula dá **critérios** (princípios para julgar o resultado). Eles se encontram:

| Calistenia (Aula 08) | Princípio SOLID que dá o "porquê" | Observação |
|---|---|---|
| R2 — sem `else` | **O** | A cadeia `if/else` por tipo é o sintoma; polimorfismo ou `enum` é o remédio. O exercício **provoca**, o princípio **explica** |
| R7 — entidades pequenas · R8 — poucas variáveis de instância | **S** | Tamanho é sintoma: classe pequena **não garante** uma só responsabilidade |
| R9 — sem getters/setters | **L** (invariantes) · **D** (encapsulamento do detalhe) | Quem protege seus dados pode prometer contratos |
| R3, R4 — encapsular primitivos e coleções | **S** · **I** | Cada objeto de valor tem uma responsabilidade pequena e uma interface mínima |
| R5 — um ponto por linha | **D** | Falar só com colaboradores diretos reduz o conhecimento de estrutura alheia |
| R1 — um nível de indentação | — | É ortogonal: trata de legibilidade do método, não do desenho entre classes |

Duas diferenças de natureza que a turma deve conseguir explicar:

1. **Calistenia é verificável mecanicamente** (conte `else`, conte indentação, conte campos). **SOLID não é**: exige juízo sobre *mudança* e *contrato*.
2. **Calistenia pode ser cumprida sem aprender nada** (extrair `processar2()`). **SOLID pode ser "cumprido" sem benefício** (interface para tudo). Em ambos, o que se avalia é a **justificativa**.

## 8. Evidências do exemplo (as três versões, saída idêntica)

Medidas feitas sobre os arquivos de [03-exemplo-completo/](03-exemplo-completo/), excluindo `Main.java`:

| Medida | `antes/` | `depois/` (SOLID à risca) | `equilibrado/` |
|---|---|---|---|
| Arquivos `.java` (tipos) | 4 | 15 | 8 |
| Linhas totais | 105 | 227 | 158 |
| Cadeias `if/else if` por tipo (OCP) | 2 (frete, pagamento) | 0 | 0 |
| Linhas com `else` no código executável | 7 | 0 | 0 |
| Razões para mudar em `ServicoDePedido` | 5 | 1 | 1 |
| Métodos que lançam `UnsupportedOperationException` | 2 | 0 | 0 |
| `new` de classe concreta dentro de regra de negócio | 2 | 0 | 0 |
| Interfaces | 1 (gorda, 3 métodos) | 4 (1 método cada, exceto `RepositorioDePedidos` com 2) | 1 |
| Serviço testável sem e-mail de verdade? | Não | Sim | Sim |

**Como ler:** a versão `depois/` custa 2,2× as linhas e 3,8× os arquivos de `antes/`; `equilibrado/` custa 1,5× e 2×, e preserva os ganhos de **L**, **I** e **D** na fronteira onde há variação real (`Notificador`), usando `enum` onde a variação é pequena e fechada. **Nenhuma delas é "a correta"**; qual escolher depende de como o domínio vai mudar — e é isso que a Parte 4 da atividade mede.

| Mudança | `antes/` | `depois/` | `equilibrado/` |
|---|---|---|---|
| **M1 — Novo tipo de atendimento `AGENDADA`** (R$ 5,00 fixos) | 1 arquivo editado (`ServicoDePedido`), novo `else if` com aninhamento | 1 arquivo **novo** (`FreteAgendado`); nada existente é reescrito | 1 arquivo editado (`Frete`): nova constante; nenhum `if` novo |
| **M2 — Nova forma `VALE_REFEICAO`** (sem desconto; recusa total acima de R$ 120,00) | 1 arquivo editado, mais um `else if` aninhado | 1 arquivo **novo** (`PagamentoComVale`) | 1 arquivo editado (`Pagamento`): nova constante |
| **M3 — Avisar também por SMS** | Editar o serviço **e** implementar 3 métodos em cada notificador (dois deles com `throw`) | 1 arquivo **novo** (`NotificadorPorSms`) + ligar na raiz de composição | Idem a `depois/` |
| **M4 — Testar `fechar` sem enviar e-mail** | Impossível sem editar o serviço (o `new` está dentro) | Passar um `NotificadorFalso` pelo construtor | Idem a `depois/` |
| **M5 — Frete que depende de **fonte externa** (API de distância)** | Mais um ramo e uma dependência de rede dentro do método | Nova classe `FreteComDistancia` que recebe o cliente da API pelo construtor | **Migrar** `Frete` de `enum` para `interface`: refatoração pequena e conhecida, mas inevitável |

Conclusão pedagógica a ser extraída pela turma (não imposta): **o `enum` é suficiente enquanto os casos são fechados e conhecidos; quando os casos passam a vir de fora (M5), o ponto de extensão precisa ser uma interface**. Isso fecha o raciocínio aberto no M3 da Aula 08 — e é um exemplo concreto de **"não antecipe o ponto de extensão; esteja preparado para extraí-lo quando o requisito chegar".**

> **Importante:** os números acima são a *expectativa* do docente com base no código (contagens conferidas nos arquivos desta pasta). Peça aos grupos que meçam e **registrem as próprias contagens**; se divergirem, a divergência é material de discussão, não erro.
