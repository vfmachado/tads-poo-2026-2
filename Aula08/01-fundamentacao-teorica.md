# Fundamentação teórica — Aula 08

> Todos os trechos de código citados existem em [03-exemplo-completo/](03-exemplo-completo/) e compilam. Os marcadores `[R1]`…`[R9]` identificam a regra violada.

## 1. O que é — e o que não é — Object Calisthenics

**Object Calisthenics** é um conjunto de nove restrições de programação proposto por Jeff Bay no ensaio "Object Calisthenics" (coletânea *The ThoughtWorks Anthology*). O nome é uma analogia com a calistenia física: exercícios com o peso do próprio corpo, feitos por um tempo limitado, para desenvolver força e percepção — não uma rotina de trabalho.

A ideia central é uma **restrição artificial que força decisões de design**. Quando o `else` é proibido, você é obrigado a perguntar "que objeto deveria decidir isso?". Quando `String` e `int` soltos são proibidos, você é obrigado a perguntar "que conceito do domínio esse valor representa?".

### Classificação do que estamos estudando

A diretriz da disciplina é não misturar categorias. Object Calisthenics **não é** um princípio:

| Categoria | Definição | Exemplos nesta disciplina |
|---|---|---|
| **Princípio** | Afirmação geral sobre por que um design é bom; expressa um valor | Encapsulamento, coesão, baixo acoplamento, substituição |
| **Prática** | Atividade que se executa | Refatoração, CRC Cards, revisão de código, TDD |
| **Heurística** | Regra de bolso que *tende* a produzir bom design e tem exceções | Tell Don't Ask, Lei de Demeter, "prefira composição" |
| **Restrição de exercício** | Regra artificial e radical, aplicada deliberadamente para treinar percepção | **As nove regras de Object Calisthenics** |
| **Recurso da linguagem** | Mecanismo da linguagem que viabiliza ou dificulta o design | `record`, `enum`, `interface`, `final` |

Isso importa porque o erro mais comum com Object Calisthenics é tratá-lo como **padrão de produção**. Ele não foi desenhado para isso.

### A regra de ouro da aula

> **Cada regra é uma pergunta disfarçada de proibição.** O valor do exercício está na pergunta que a violação provoca, não no cumprimento literal da regra. Se você seguiu a regra e não aprendeu nada sobre o domínio, o exercício falhou. Se você *não* seguiu a regra mas consegue explicar por quê, o exercício funcionou.

### Três tipos de problema (e por que distingui-los)

Ao analisar qualquer violação, classifique-a:

- **Sintaxe/idioma:** o código funciona e é legível, mas foge de uma convenção (ex.: abreviação).
- **Implementação:** o código funciona, mas é mais difícil de ler/testar do que precisa (ex.: aninhamento profundo).
- **Modelagem:** falta um conceito do domínio, ou uma responsabilidade está no objeto errado (ex.: `String` para forma de pagamento).

Violações de modelagem são as que mais custam, e as que as regras R3, R4, R8 e R9 expõem melhor.

## 2. Mapa das nove regras

| Grupo | Regras | Pergunta que o grupo provoca |
|---|---|---|
| **Fluxo de controle** | R1 um nível de indentação · R2 sem `else` | *Quem deveria tomar esta decisão? Esta função faz uma coisa só?* |
| **Tipos e dados** | R3 encapsular primitivos · R4 coleções de primeira classe · R8 poucas variáveis de instância | *Que conceito do domínio este valor/lista/grupo de campos representa?* |
| **Fronteiras entre objetos** | R5 um ponto por linha · R9 sem getters/setters | *Estou conversando com meus colaboradores diretos ou com os amigos deles? Estou mandando ou perguntando?* |
| **Legibilidade e escala** | R6 sem abreviações · R7 entidades pequenas | *O nome revela intenção? Esta classe/método tem uma só razão para existir?* |

## 3. As nove regras

Cada regra segue o mesmo roteiro de dez itens. Os exemplos de violação vêm de [`antes/FechamentoPedido.java`](03-exemplo-completo/antes/FechamentoPedido.java); os refatorados, de [`depois/`](03-exemplo-completo/depois/); e a versão pragmática, de [`equilibrado/`](03-exemplo-completo/equilibrado/).

---

### 3.1 Regra 1 — Apenas um nível de indentação por método

1. **Intenção.** Obrigar que cada método faça *uma coisa* num único nível de abstração.
2. **Problema que busca evitar.** Métodos em "seta" (`>` deitada) que misturam validação, cálculo e decisão, impossíveis de testar por partes.
3. **Violação.**
   ```java
   if (p.getItens() != null && p.getItens().size() > 0) {          // nível 1
       for (ItemPedido i : p.getItens()) {                          // nível 2
           if (i.getQt() > 0) {                                     // nível 3
               if (i.getVl().compareTo(BigDecimal.ZERO) >= 0) {     // nível 4
                   sub = sub.add(i.getVl().multiply(BigDecimal.valueOf(i.getQt())));
   ```
4. **Refatorado.** O laço e as validações migram para onde pertencem; o método original vira uma sequência de passos nomeados:
   ```java
   public ReciboDoPedido fechar() {
       Dinheiro subtotal = itens.subtotal();
       Dinheiro taxaBase = entrega.taxaBase();
       Dinheiro descontoNoSubtotal = cupom.descontoNoSubtotal(subtotal);
       ...
   ```
   A validação "quantidade positiva" foi para o construtor de `Quantidade`; "não vazio", para `ItensDoPedido`; o laço, para `ItensDoPedido.subtotal()`.
5. **Benefícios.** Métodos testáveis isoladamente; nomes que documentam o fluxo; fim do "arrow code".
6. **Limitações.** O nível de indentação é um *sintoma*, não a causa. É possível cumprir a regra extraindo métodos privados com nomes ruins (`processar2()`) e piorar o código. A regra também não distingue `if` de guarda (trivial) de `if` com lógica pesada.
7. **Exageros.** Extrair um método de uma linha apenas para satisfazer a regra, quebrando o fluxo de leitura em dez saltos. Lambdas dentro de `stream()` que reintroduzem o aninhamento "escondido".
8. **Impacto.** Leitura: alto ganho em métodos de regra de negócio; neutro em código de configuração. Manutenção: o ponto exato de mudança fica óbvio.
9. **Flexibilização.** Um `if` simples ou um `for` único, quando o corpo é uma linha e extrair o método não ganha um nome melhor que o próprio código (ver `PedidoDeEntrega.fechar()` na versão [equilibrada](03-exemplo-completo/equilibrado/PedidoDeEntrega.java)). Código de infraestrutura/parsing costuma ter aninhamento legítimo.
10. **Relações.** *Coesão de método* (um método, um nível de abstração). *Tell Don't Ask*: a pressão por reduzir níveis empurra decisões para dentro dos objetos que têm os dados.

---

### 3.2 Regra 2 — Evitar o uso de `else`

1. **Intenção.** Tornar o `else` um sinal de alerta de que uma decisão está no lugar errado — ou de que um *guard clause* resolveria.
2. **Problema que busca evitar.** Cadeias `if / else if / else` que codificam variações de comportamento e se multiplicam a cada novo caso.
3. **Violação.**
   ```java
   if (c.equals("VALE10")) { ... }
   else if (c.equals("FRETEGRATIS")) { ... }       // [R2]
   else { throw new IllegalArgumentException("Cupom desconhecido: " + c); }
   ```
4. **Refatorado.** Três técnicas diferentes, conforme a causa:
   - *Guard clause / retorno antecipado:* `if (unidades <= 0) { throw ...; }` seguido do fluxo normal (`Quantidade`).
   - *Polimorfismo:* `Cupom` (interface) com `CupomPercentual`, `CupomFreteGratis`, `SemCupom`.
   - *Tabela/enum:* `FaixaDeEntrega` substitui `if (d <= 3) ... else if (d <= 8)`; `Prioridade` substitui `if (pri) tx = tx + 6`.
5. **Benefícios.** Casos novos viram classes/constantes novas, sem editar o método antigo; o *Null Object* (`SemCupom`) elimina `if (cupom != null)`.
6. **Limitações.** Polimorfismo tem custo: mais tipos, mais indireção. Para uma decisão binária estável, um `if/else` curto costuma ser a solução mais clara.
7. **Exageros.** Criar `interface Condicao` + duas classes para decidir `if (x > 0)`. Trocar `else` por operador ternário aninhado (só esconde o `else`). Usar exceções como controle de fluxo para fugir do `else`.
8. **Impacto.** Leitura: guard clauses melhoram quase sempre; polimorfismo melhora quando há ≥ 3 variantes que crescem. Manutenção: reduz *shotgun surgery* em regras que variam por tipo.
9. **Flexibilização.** `if/else` simples, binário e estável. Ternário curto. Expressão `switch` sobre `enum`/`String` quando as variantes variam **só em dados** (ver [`equilibrado/Cupom.java`](03-exemplo-completo/equilibrado/Cupom.java)).
10. **Relações.** *Polimorfismo* (Aula 06), *Open-Closed* (antecipação), *Tell Don't Ask* (o objeto decide, não quem pergunta).

---

### 3.3 Regra 3 — Encapsular todos os tipos primitivos e `String`

1. **Intenção.** Dar tipo de domínio a valores que hoje são "só um `int`/`String`", concentrando nele validação e comportamento.
2. **Problema que busca evitar.** *Primitive obsession*: validação duplicada, valores inválidos circulando, parâmetros trocados sem erro de compilação (`fechar(String cupom, String pagamento)` aceita os dois na ordem errada).
3. **Violação.**
   ```java
   String pg = p.getPgto();                         // "PIX"? "pix"? "Pix "? "BOLETO"?
   if (pg.equals("DINHEIRO")) { ... }
   else if (!pg.equals("PIX") && !pg.equals("CARTAO")) { throw ... }
   ```
4. **Refatorado.**
   ```java
   public final class Quantidade {
       private final int unidades;
       public Quantidade(int unidades) {
           if (unidades <= 0) { throw new IllegalArgumentException("Quantidade deve ser positiva."); }
           this.unidades = unidades;
       }
       public Dinheiro valorTotalDe(Dinheiro precoUnitario) { return precoUnitario.multiplicadoPor(unidades); }
   }
   ```
   Mais: `Dinheiro`, `DistanciaEmKm`, `CodigoDeCupom`, `FormaDePagamento` (enum — estados inválidos tornam-se **irrepresentáveis**).
5. **Benefícios.** Invariantes verificadas uma só vez, no construtor. O compilador passa a distinguir `Dinheiro` de `Quantidade`. O comportamento (soma, comparação, arredondamento) tem dono.
6. **Limitações.** Cada tipo novo é uma classe a mais para ler, testar, serializar e mapear. Nem todo `String` tem regra.
7. **Exageros.** `Nome`, `Rua`, `Cidade` como classes que só embrulham `String` sem nenhuma regra. `Quantidade` quando a única regra é "> 0" e a quantidade nunca sai do `ItemDoPedido`.
8. **Impacto.** Leitura: alta onde há regra (dinheiro, CPF, e-mail). Manutenção: a regra muda em um lugar.
9. **Flexibilização.** Sem comportamento nem invariante própria → não encapsule. Valor que nasce e morre no mesmo método. Campo de DTO de fronteira. **Critério prático:** *encapsule quando houver ao menos uma destas três coisas — uma invariante, uma operação própria, ou risco real de confundir com outro valor do mesmo tipo.* Na versão [equilibrada](03-exemplo-completo/equilibrado/), `Dinheiro` sobrevive e `Quantidade`/`DistanciaEmKm` são removidas.
10. **Relações.** *Encapsulamento* (proteger invariante), *coesão* (comportamento junto do dado), combate direto ao smell *Primitive Obsession*.

---

### 3.4 Regra 4 — Coleções de primeira classe

1. **Intenção.** Toda classe que contém uma coleção não contém mais nada; a coleção ganha uma classe própria com seu comportamento.
2. **Problema que busca evitar.** Regras sobre "o conjunto de itens" espalhadas por quem recebe a lista (somar, validar não-vazio, filtrar).
3. **Violação.** `PedidoEntrega.getItens()` devolve `List<ItemPedido>` crua; `FechamentoPedido` soma, valida e itera por fora ([R4], [R9]).
4. **Refatorado.**
   ```java
   public final class ItensDoPedido {
       private final List<ItemDoPedido> itens;
       public ItensDoPedido(List<ItemDoPedido> itens) {
           if (itens.isEmpty()) { throw new IllegalArgumentException("O pedido precisa ter ao menos um item."); }
           this.itens = List.copyOf(itens);               // cópia defensiva
       }
       public Dinheiro subtotal() { ... }
   }
   ```
5. **Benefícios.** Invariante da coleção ("não vazia") protegida; cópia defensiva em um lugar; ponto natural para novas regras (limite de itens, agrupamento).
6. **Limitações.** Uma classe a mais para repassar a lista; se ela só delega `add/get/size`, é um *Middle Man* sem valor.
7. **Exageros.** Embrulhar toda `List` do sistema, inclusive as locais e as transitórias. Reimplementar metade da API de `List` por delegação.
8. **Impacto.** Leitura: o domínio ganha vocabulário ("itens do pedido"). Manutenção: regras de conjunto deixam de duplicar.
9. **Flexibilização.** Coleção local a um método, ou usada só como resultado intermediário. Sem invariante nem operação própria de conjunto.
10. **Relações.** *Encapsulamento de coleções* (Aula 03), *Tell Don't Ask* (peça o subtotal, não os itens), *Lei de Demeter* (ninguém navega pelo interior da lista).

---

### 3.5 Regra 5 — Apenas um ponto por linha

1. **Intenção.** Falar só com colaboradores diretos. É a Lei de Demeter transformada em regra de contagem.
2. **Problema que busca evitar.** *Inappropriate Intimacy* e *Message Chains*: o chamador conhece a estrutura interna de objetos distantes e quebra quando ela muda.
3. **Violação.**
   ```java
   double d = p.getEnd().getBairro().getDistKm();     // PedidoEntrega -> Endereco -> Bairro -> double
   ```
   Se a distância passar a vir de um serviço de geolocalização, **todo chamador dessa cadeia** muda.
4. **Refatorado.** O pedido, que *sabe* sua entrega, responde diretamente (`entrega.taxaBase()`). Dentro de `Entrega`, quando duas chamadas seriam necessárias, usa-se variável local nomeada:
   ```java
   FaixaDeEntrega faixa = FaixaDeEntrega.para(distancia);
   return faixa.taxa();
   ```
5. **Benefícios.** Menos acoplamento estrutural; propagação de mudança mais curta.
6. **Limitações e a confusão mais comum.** **Contar pontos não é a Lei de Demeter.** A lei trata de *conhecimento da estrutura alheia*. Interfaces fluentes (`stream().map().filter()`), *builders* e `StringBuilder.append().append()` têm muitos pontos e **não violam** Demeter, porque cada chamada devolve o mesmo tipo/contexto.
7. **Exageros.** Quebrar `a.b().c()` em duas linhas para "cumprir a regra" **sem mudar nada** — é esconder o ponto, não removê-lo. Criar `pedido.distanciaDoBairroDoEnderecoDeEntrega()` (método-túnel) que só repassa a cadeia.
8. **Impacto.** Leitura: positiva quando o método de repasse tem nome de domínio; negativa quando é um túnel. Manutenção: reduz *shotgun surgery*.
9. **Flexibilização.** APIs fluentes e `stream`. Encadeamentos sobre **tipos estáveis da plataforma** (`texto.strip().toUpperCase()`). Código de teste. Na [versão equilibrada](03-exemplo-completo/equilibrado/PedidoDeEntrega.java), `FaixaDeEntrega.para(distanciaEmKm).taxa()` mantém dois pontos de propósito: o resultado é um valor imutável, estável e legível.
10. **Relações.** Equivale a *Lei de Demeter*; reforça *Tell Don't Ask* e *encapsulamento* (quem não pode navegar não depende da estrutura).

---

### 3.6 Regra 6 — Não abreviar

1. **Intenção.** Forçar nomes que revelam intenção; abreviar é sintoma de que o nome está difícil — e nome difícil costuma indicar responsabilidade mal definida.
2. **Problema que busca evitar.** Código que exige decodificação (`tx`, `sub`, `desc`, `tot`, `cp`, `pgto`, `pri`).
3. **Violação.**
   ```java
   BigDecimal tx;  BigDecimal desc;  BigDecimal tot;  String pg;  boolean pri;
   ```
   (`desc` é *descrição* ou *desconto*?)
4. **Refatorado.** `taxaBase`, `descontoNoSubtotal`, `taxaCobrada`, `formaDePagamento`, `prioritaria`.
5. **Benefícios.** Menor carga cognitiva; menos comentários; ajuda de IDE melhor.
6. **Limitações.** Nome longo **não** é nome bom. A regra trata de abreviação, não de verbosidade.
7. **Exageros.** `valorTotalDoPedidoComDescontoAplicadoEImpostosIncluidos`. Proibir siglas de domínio consagradas (`CPF`, `CNPJ`, `km`, `URL`). Nomes tão longos que a linha precisa quebrar.
8. **Impacto.** Legibilidade: ganho direto e barato. É a regra de melhor relação custo/benefício do conjunto.
9. **Flexibilização.** Siglas de domínio conhecidas; convenções universais de escopo curto (`i` em laço de 3 linhas, `e` em `catch`); lambdas curtas (`f -> f.limite`).
10. **Relações.** Expressividade e *simplicidade*. Um nome difícil de achar frequentemente denuncia **baixa coesão**: se não dá para nomear, talvez o método faça duas coisas.

---

### 3.7 Regra 7 — Manter entidades pequenas

1. **Intenção.** Combater classes e métodos que crescem por acréscimo. Valores clássicos do exercício: métodos até ~5 linhas, classes até ~50 linhas, pacotes com até ~10 arquivos.
2. **Problema que busca evitar.** *Large Class*, *Long Method*, *God Object*, pacote-gaveta.
3. **Violação.** `FechamentoPedido.fechar()` tem cerca de 70 linhas e cinco responsabilidades: validar itens, calcular taxa, aplicar cupom, aplicar prioridade, validar pagamento.
4. **Refatorado.** Cada responsabilidade foi para o objeto que tem o dado: itens → `ItensDoPedido`; faixas → `FaixaDeEntrega`; cupons → `Cupom`; pagamento → `FormaDePagamento`.
5. **Benefícios.** Cada classe é compreendida de uma vez; mudanças ficam localizadas.
6. **Limitações.** Os limites numéricos são arbitrários. Pequeno demais gera **fragmentação**: entender uma regra exige abrir doze arquivos. Ousterhout chama essa armadilha de *classes rasas* (*shallow*): interface quase tão complexa quanto a implementação.
7. **Exageros.** Dividir uma classe coesa de 80 linhas em quatro de 20 sem ganho de nomes. Métodos de 1 linha cuja única função é satisfazer o limite.
8. **Impacto.** Evidência no exemplo: 5 arquivos/168 linhas → 18 arquivos/437 linhas na versão estrita. Cada arquivo é menor, mas **o sistema total é 2,6× maior** e a navegação, mais custosa. Veja a seção 8.
9. **Flexibilização.** Tamanho é indicador, não meta. Use a pergunta: *"esta classe tem mais de uma razão significativa para mudar?"* (coesão). `Dinheiro` (63 linhas) extrapola o limite e está correta: `equals/hashCode/toString` são boilerplate.
10. **Relações.** *Coesão* (razões para mudar), *SRP* (visto adiante na disciplina), *Divergent Change*. Também testa o limite oposto: *Shotgun Surgery*.

---

### 3.8 Regra 8 — Evitar classes com muitos atributos (versão original: no máximo duas variáveis de instância)

1. **Intenção.** Pressionar para que cada classe represente **um** conceito; muitos campos sugerem que há conceitos escondidos.
2. **Problema que busca evitar.** Classes-pacote de dados com campos independentes (`Data Clumps`, `Large Class`).
3. **Violação.** `PedidoEntrega` tem cinco campos (`itens`, `end`, `cp`, `pgto`, `pri`) que variam por razões diferentes.
4. **Refatorado.** Surgem agrupamentos reais: `Entrega(distancia, prioridade)` e `ExigenciaDeSubtotal(codigo, minimo)` — esta última **eliminou duplicação** entre dois tipos de cupom. Esse é o caso em que a regra funciona como instrumento de descoberta.
5. **Benefícios.** Conceitos latentes aparecem; instâncias com menos estados possíveis são mais fáceis de manter válidas.
6. **Limitações.** "Duas" é um número de exercício. Há classes legítimas com quatro campos.
7. **Exageros.** Agrupar campos só para cumprir a contagem. **No exemplo, tentamos `Pagamento(cupom, formaDePagamento)`: ela só repassava chamadas** — era um *Middle Man* — e foi descartada. `PedidoDeEntrega` ficou com **quatro** campos, em violação consciente da regra, registrada no código.
8. **Impacto.** Leitura positiva quando o agrupamento tem nome de domínio; negativa quando é nome genérico (`Dados`, `Contexto`, `Parametros`).
9. **Flexibilização.** Agregados de domínio com vários campos coesos; *records* de saída (`ReciboDoPedido` tem quatro componentes). **Teste:** *o grupo tem um nome de domínio que um especialista de negócio reconheceria?* Se não, não agrupe.
10. **Relações.** *Coesão*; *Data Clumps*; *Primitive Obsession*. Com R3, forma o par que descobre conceitos do domínio.

---

### 3.9 Regra 9 — Evitar getters, setters e exposição indiscriminada do estado

1. **Intenção.** Obrigar a pensar em **comportamento** em vez de dados: em vez de "me dê o campo", "faça isto por mim".
2. **Problema que busca evitar.** Objetos anêmicos, *Feature Envy*, regras de negócio em classes de serviço que manipulam dados alheios.
3. **Violação.** `ItemPedido`, `Bairro`, `Endereco` e `PedidoEntrega` são puros sacos de `getX/setX`; toda a regra mora em `FechamentoPedido`. Um `setQt(-5)` depois da criação invalida o pedido sem ninguém notar.
4. **Refatorado.** Objetos imutáveis, sem setters, com operações de domínio: `Quantidade.valorTotalDe(preco)`, `ItemDoPedido.subtotal()`, `PedidoDeEntrega.fechar()`, `FormaDePagamento.validarTotal(total)`.
5. **Benefícios.** Invariantes protegidas; mudança de representação interna sem efeito colateral; lógica próxima do dado.
6. **Limitações.** Objetos precisam, em algum momento, **mostrar** informação (relatório, JSON, tela). Eliminar toda leitura é inviável.
7. **Exageros.** Substituir `getNome()` por `exibirNomeEm(Impressora)` em *toda* classe; criar métodos `ehIgualA...`, `jaPassouDe...` para cada consulta possível.
8. **Impacto.** Leitura: o código passa a falar a linguagem do domínio. Manutenção: regra centralizada no dono do dado.
9. **Flexibilização.** Fronteiras: `ReciboDoPedido` (record de saída), DTOs de entrada/saída, objetos de valor que expõem uma consulta (`FaixaDeEntrega.taxa()`). **Critério:** consultas que devolvem **valor imutável** são menos perigosas que consultas que devolvem **estado mutável ou estrutura interna**.
10. **Relações.** *Tell Don't Ask* (é a mesma ideia), *encapsulamento*, *CQS* (comando muda estado; consulta devolve valor sem efeito colateral). Se um `if` começa com `obj.getX()`, a decisão provavelmente deveria estar em `obj`.

---

## 4. Smells e notas técnicas just-in-time

Como a Aula 08 antecede a aula formal de refatoração e code smells, os termos abaixo são apresentados aqui em uma linha cada.

| Smell | Descrição curta | Onde aparece em `antes/` |
|---|---|---|
| **Primitive Obsession** | Usar tipos primitivos/`String` onde há um conceito de domínio | `String pgto`, `String cp`, `double distKm` |
| **Feature Envy** | Um método usa mais dados de outro objeto do que do próprio | `FechamentoPedido` só manipula dados de `PedidoEntrega` |
| **Message Chain** | `a.getB().getC().getD()` | `p.getEnd().getBairro().getDistKm()` |
| **Long Method / Large Class** | Método/classe que faz demais | `fechar()` com 70 linhas |
| **Data Clumps** | Dados que andam sempre juntos | `cp` + mínimo; `distKm` + prioridade |
| **Anemic Domain Model** | Objetos de domínio sem comportamento | `ItemPedido`, `Bairro`, `Endereco`, `PedidoEntrega` |
| **Middle Man** | Classe que só delega | `Pagamento(cupom, forma)` (tentada e descartada) |
| **Shotgun Surgery** | Uma mudança exige muitas edições espalhadas | Aparece quando as regras ficam fragmentadas demais |

**Notas técnicas de dois minutos (para o docente):**

- *`enum` com método abstrato:* cada constante pode implementar o método de maneira diferente. É polimorfismo sem hierarquia aberta. Uso: `Prioridade`.
- *`stream().filter(...).findFirst().orElseThrow(...)`:* "o primeiro elemento que satisfaz; se nenhum, erro". Substitui um `for` com `if` e `return` (dois níveis de indentação). Uso: `FaixaDeEntrega.para`.
- *Record compacto:* `record CodigoDeCupom(String texto)` com construtor compacto que normaliza `null`. Já visto na [Aula 04](../Aula04/).

## 5. Riscos de aplicar sem critério

| Risco | Como se manifesta | Evidência no exemplo |
|---|---|---|
| **Classes excessivamente pequenas** | Entender uma regra exige abrir muitos arquivos | 18 arquivos para um fechamento de pedido |
| **Excesso de objetos de valor** | Classes que só embrulham tipo sem regra | `DistanciaEmKm` (uma regra: ≥ 0), `Quantidade` (uma regra: > 0) |
| **Dificuldade de navegação** | Fluxo percorre vários arquivos até chegar ao cálculo | `Pedido → Entrega → FaixaDeEntrega → Dinheiro` |
| **Aumento artificial de abstrações** | Interface criada sem segunda implementação provável | `Cupom` com 3 implementações triviais e um catálogo |
| **Uso inadequado de um ponto por linha** | Quebrar a cadeia sem reduzir o acoplamento | Método-túnel `distanciaDoEnderecoDeEntrega()` |
| **Agrupamento artificial (R8)** | Classes `Dados`/`Parametros` | `Pagamento(cupom, forma)` descartada |
| **Custo de indireção** | Estado/regra espalhados dificultam depuração | Rastrear um desconto passa por 4 classes |

> **Atenção à armadilha lógica:** as regras reduzem *acoplamento local* e *tamanho local* mas aumentam o **número de partes**. Se a mudança que você enfrenta cruza todas as partes, o custo total pode subir. Só a evidência de mudança real (seção 8 e Parte 4 da atividade) responde se compensou.

## 6. Critérios para flexibilizar uma regra (checklist de revisão)

Antes de violar uma regra — ou de cumpri-la — responda por escrito:

1. **Qual pergunta de design esta regra provoca aqui?** Já a respondi?
2. **Qual é a frequência de mudança** da região? Código que muda raramente justifica menos estrutura.
3. **A abstração criada tem nome de domínio** que um especialista reconheceria?
4. **A estrutura nova isola uma variação real** (algo que já variou ou que há razão concreta para variar), ou antecipa uma hipotética?
5. **Quantas partes eu precisaria abrir** para entender uma regra completa? Aumentou ou diminuiu?
6. **O custo recai sobre quem?** (quem lê, quem testa, quem depura em produção).
7. **A violação é local e reversível?** Violar uma regra num ponto isolado é barato; espalhar o padrão violado, não.
8. **Registrei a decisão** (comentário curto no código ou ADR)?

## 7. Código didático × código de produção

| Dimensão | Código didático (calistenia) | Código de produção |
|---|---|---|
| **Objetivo** | Treinar a percepção; expor decisões ocultas | Entregar valor com custo de manutenção aceitável |
| **Rigor das regras** | Literal, temporário | Guiado por contexto |
| **Tolerância a fragmentação** | Alta (é o ponto) | Baixa (custo de navegação real) |
| **Cobertura do domínio** | Pequeno e recortado | Completo, com bordas e legado |
| **Frequência de mudança** | Assumida | Conhecida (histórico, backlog) |
| **Critério de sucesso** | "O que aprendi sobre este código?" | "Mudar este código ficou mais barato?" |
| **Entrega típica** | Um *kata*, descartável | Uma classe em produção, mantida por anos |

Uma prática profissional razoável, inspirada no exercício: **aplicar a calistenia como "lente de revisão"** — passar o código por cada regra como pergunta, corrigir o que expõe um defeito real e deixar o resto.

## 8. Evidências do exemplo (as três versões, saída idêntica)

Medidas feitas sobre os arquivos de [03-exemplo-completo/](03-exemplo-completo/), excluindo `Main.java`:

| Medida | `antes/` | `depois/` (estrita) | `equilibrado/` |
|---|---|---|---|
| Arquivos `.java` | 5 | 18 | 8 |
| Linhas totais | 168 | 437 | 242 |
| Linhas de código (sem vazias/comentários) | 140 | 304 | 175 |
| Maior nível de indentação em um método (corpo = nível 1) | 5 | 2 (apenas cláusulas de guarda) | 2 (cláusulas de guarda e um `if` em `PedidoDeEntrega.fechar`) |
| Linhas com `else` no código executável | 11 | 0 | 0 |
| Getters/setters públicos nas classes de domínio | 24 (em 4 classes) | 0 (restam consultas que devolvem valores imutáveis, ex. `FaixaDeEntrega.taxa()`) | 0 (idem; mais os acessores dos `record`) |
| Classes com mais de 2 campos/componentes | `ItemPedido` (3), `PedidoEntrega` (5) | `PedidoDeEntrega` (4) e `ReciboDoPedido` (4) — ambas justificadas no código | `Cupom` (3), `PedidoDeEntrega` (5), `ReciboDoPedido` (4) |

**Como ler:** a versão estrita elimina a lógica procedural, mas custa 2,6× as linhas e 3,6× os arquivos. A equilibrada preserva os ganhos conceituais mais caros de perder (objeto de valor monetário, coleção de primeira classe, `enum` para formas de pagamento, polimorfismo onde a variação é *dados*) com 1,4× as linhas originais (1,25× em linhas de código). **Nenhuma das duas versões é "a correta".** Qual escolher depende de como o domínio vai mudar — e é isso que a Parte 4 da atividade mede.
