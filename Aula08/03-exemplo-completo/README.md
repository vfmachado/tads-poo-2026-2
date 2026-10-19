# Exemplo completo — Aula 08 (apoio ao docente)

Três versões do mesmo comportamento (fechamento de pedido do FoodExpress). **As três produzem saída idêntica** — esse é o teste de caracterização que garante que a refatoração preservou o comportamento.

| Pasta | Papel | Pacote |
|---|---|---|
| [antes/](antes/) | Código funcional que viola as nove regras (marcadas `[R1]`…`[R9]`) | `foodexpress.aula08.antes` |
| [depois/](depois/) | As nove regras aplicadas à risca (exceções conscientes comentadas no código) | `foodexpress.aula08.depois` |
| [equilibrado/](equilibrado/) | Mesmas regras de negócio; regras flexibilizadas onde o custo supera o benefício | `foodexpress.aula08.equilibrado` |

## Como compilar e comparar

```bash
mkdir -p out
javac -d out/antes antes/*.java       && java -cp out/antes       foodexpress.aula08.antes.Main       > antes.txt
javac -d out/depois depois/*.java     && java -cp out/depois      foodexpress.aula08.depois.Main      > depois.txt
javac -d out/eq equilibrado/*.java    && java -cp out/eq          foodexpress.aula08.equilibrado.Main > equilibrado.txt
diff antes.txt depois.txt && diff antes.txt equilibrado.txt && echo "comportamento preservado"
```

Verificado com OpenJDK 21.0.2: as três saídas são idênticas.

## Mapa das regras → onde cada uma é resolvida

| Regra | Violação em `antes/` | Solução em `depois/` | O que `equilibrado/` fez diferente |
|---|---|---|---|
| R1 um nível | `fechar()`: 5 níveis | `PedidoDeEntrega.fechar()` sem estruturas de controle | Mantém um `if` em `fechar()` (prioridade) |
| R2 sem `else` | 11 linhas com `else` | `Cupom` (interface), `Prioridade` (enum), `FaixaDeEntrega` (tabela), guard clauses | `Cupom` vira `enum` com dados; `switch` em `Cupom.pelo`; `if` simples para prioridade |
| R3 primitivos | `String`, `double`, `BigDecimal`, `int` soltos | `Dinheiro`, `Quantidade`, `DistanciaEmKm`, `CodigoDeCupom`, `FormaDePagamento` | Só `Dinheiro` e `FormaDePagamento`; `int` e `double` validados no ponto de entrada |
| R4 coleção | `List<ItemPedido>` crua | `ItensDoPedido` | Idem (a regra se paga) |
| R5 um ponto | `p.getEnd().getBairro().getDistKm()` | Variável local; `entrega.taxaBase()` | Aceita `FaixaDeEntrega.para(x).taxa()` |
| R6 sem abreviar | `tx`, `sub`, `desc`, `tot`… | Nomes de domínio | Idem |
| R7 pequenas | `fechar()` ≈ 70 linhas | Todas as classes pequenas (exceto `Dinheiro`, 63 linhas) | Idem, com 8 classes |
| R8 poucos campos | `PedidoEntrega` com 5 | `Entrega`, `ExigenciaDeSubtotal`; `PedidoDeEntrega` com 4 (justificado) | `PedidoDeEntrega` com 5 componentes; `Cupom` com 3 campos |
| R9 sem getters | 24 getters/setters | Operações de domínio; objetos imutáveis | Idem, mais acessores de `record` |

## Gabarito docente

> Esta seção é para o docente. Não distribuir antes da atividade.

### O que ainda sobrou de problema na versão estrita (`depois/`)

Útil para a discussão: **"estrita" não significa "perfeita"**.

1. **`BigDecimal fracao` em `CupomPercentual`** é um primitivo residual (R3 incompleta). Um `Percentual` resolveria — e seria mais uma classe de uma regra só.
2. **Acoplamento temporal em `Cupom`:** as duas operações (`descontoNoSubtotal`, `descontoNaEntrega`) verificam a exigência de forma independente. Funciona, mas se alguém chamar só uma delas e esquecer da outra, o cupom é parcialmente validado. Alternativa: um único método que devolve um objeto `Abatimento`.
3. **A mensagem `"mais de 8 km"`** está duplicada em `FaixaDeEntrega` e acopla o texto ao limite da última constante: adicionar uma faixa `ATE_12_KM` exige lembrar de atualizar a mensagem. (O mesmo problema existe, de outra forma, em `antes/` e em `equilibrado/`.)
4. **`CatalogoDeCupons.CUPONS`** é um mapa estático: sem injeção, o teste de `PedidoDeEntrega` com cupons de teste exige passar o `Cupom` direto — o que o construtor já permite.
5. **`ReciboDoPedido`** devolve quatro `Dinheiro` por acessor: exposição de estado (R9) numa fronteira de saída — aceitável, e é uma boa pergunta de discussão.
6. **Regra de negócio sensível ao `null`:** `Optional.ofNullable(CUPONS.get(codigo))` só existe para poder usar `orElseThrow` em uma expressão. Em produção, `Map.get` + `if` seria igualmente legível — exemplo de R2 respeitada ao custo de uma indireção.

### Pontos de armadilha para os grupos

- Grupos tendem a criar `Pagamento(cupom, forma)` para cumprir R8. Deixe criar; peça para tentar escrever os métodos. Em 10 minutos percebem que é um *Middle Man*.
- Grupos tendem a criar `Taxa`, `Desconto`, `Subtotal` e `Total` como classes distintas "para ficar seguro". Pergunte: *qual operação cada uma tem que `Dinheiro` não tem?*
- Alguns grupos quebram `a.b().c()` em duas linhas e dizem "cumpri R5". Pergunte: *o que mudou se `b()` mudar de tipo?*
- Alguns trocam `else` por `?:` aninhado. Peça para ler em voz alta.

### Resultados esperados da Parte 4 (experimento de mudança)

| Mudança | `antes/` | `depois/` | `equilibrado/` |
|---|---|---|---|
| **M1 — Cupom `VALE5`** (R$ 5,00 fixos, mínimo R$ 30,00) | 1 arquivo editado (`FechamentoPedido`), um novo ramo `else if` com 3 níveis de indentação | 1 arquivo **novo** (`CupomValorFixo`) + 1 editado (`CatalogoDeCupons`); nada existente é reescrito | 1 arquivo editado (`Cupom`), **mas** exige um novo campo (`descontoFixo`) e tocar o construtor de **todas** as constantes — o `enum` de dados chegou ao limite |
| **M2 — Nova faixa `ATE_12_KM` (R$ 14,00)** | 1 arquivo editado (`else if` + mensagem) | 1 constante nova + ajuste da mensagem | 1 constante nova + ajuste da mensagem |
| **M3 — Cupom que só vale de segunda a quinta** | Novo ramo e nova dependência de data dentro de `fechar()` | Nova classe `CupomDiaUtil` (e o relógio vira dependência: ponto de discussão sobre dependência e acoplamento temporal) | Muda de **dado** para **algoritmo**: o `enum` precisa migrar para `interface` |

Conclusão pedagógica a ser extraída pela turma (não imposta): **`enum` de dados cobre variação de *valor*; mudança de *comportamento* pede polimorfismo.** A versão equilibrada está certa enquanto o negócio só variar valores — e a migração para a versão polimórfica é uma refatoração pequena e conhecida quando (e se) M3 chegar. Este é um caso concreto de *"esperar o requisito antes de abstrair"*, sem ainda mencionar o Open-Closed Principle pelo nome.

> **Importante:** os números da tabela acima são a *expectativa* do docente com base no código. Peça aos grupos que meçam e **registrem as próprias contagens**; se divergirem, a divergência é material de discussão, não erro.
