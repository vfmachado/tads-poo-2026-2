# Exemplo completo — Aula 09 (apoio ao docente)

Três versões do mesmo comportamento (fechamento de pedido do FoodExpress: frete, pagamento, gravação e aviso ao cliente). **As três produzem saída idêntica** — esse é o teste de caracterização que garante que a refatoração preservou o comportamento.

| Pasta | Papel | Pacote |
|---|---|---|
| [antes/](antes/) | Código funcional que viola os cinco princípios (marcados `[S]`, `[O]`, `[L]`, `[I]`, `[D]`) | `foodexpress.aula09.antes` |
| [depois/](depois/) | SOLID aplicado à risca (decisões comentadas no código) | `foodexpress.aula09.depois` |
| [equilibrado/](equilibrado/) | Mesmas regras de negócio; princípios flexibilizados onde o custo supera o benefício | `foodexpress.aula09.equilibrado` |

## Como compilar e comparar

```bash
mkdir -p out
javac -d out/antes antes/*.java       && java -cp out/antes      foodexpress.aula09.antes.Main       > antes.txt
javac -d out/depois depois/*.java     && java -cp out/depois     foodexpress.aula09.depois.Main      > depois.txt
javac -d out/eq equilibrado/*.java    && java -cp out/eq         foodexpress.aula09.equilibrado.Main > equilibrado.txt
diff antes.txt depois.txt && diff antes.txt equilibrado.txt && echo "comportamento preservado"
```

Verificado: as três saídas são idênticas. Os cinco cenários cobrem entrega com e sem frete grátis, expressa, retirada, cartão, PIX e dinheiro (aceito e recusado).

## Mapa dos princípios → onde cada um é resolvido

| Princípio | Violação em `antes/` | Solução em `depois/` | O que `equilibrado/` fez diferente |
|---|---|---|---|
| **S** | `ServicoDePedido` calcula frete, calcula pagamento, grava, avisa e formata o recibo (5 razões para mudar) | Serviço só orquestra; `ReciboDoPedido` formata; cálculo e E/S ficam em colaboradores | Idem. `ReciboDoPedido` mantém o texto da tela **e** o da notificação (mesmo ator) |
| **O** | Duas cadeias `if/else if` por tipo (7 linhas com `else`) | `CalculoDeFrete` e `FormaDePagamento` como **interfaces**; tipo novo = classe nova | `Frete` e `Pagamento` como **`enum`** com método abstrato por constante: tipo novo = constante nova (edita **um** arquivo, sem `if`) |
| **L** | `NotificadorPorEmail` lança `UnsupportedOperationException` em `enviarSms` e `enviarPush` | Contratos pequenos; nenhuma implementação recusa o que a interface promete | Idem |
| **I** | `Notificador` com três métodos; o serviço usa um | `Notificador.avisar` (um método); `RepositorioDePedidos` só com o que o serviço pede | `Notificador` mínimo mantido; `RepositorioDePedidos` é classe concreta |
| **D** | `new NotificadorPorEmail()` e lista de pedidos **dentro** do serviço | Serviço recebe `Notificador` e `RepositorioDePedidos` pelo construtor; `Main` é a raiz de composição | `Notificador` por interface (variação real); `RepositorioDePedidos` concreto injetado (uma implementação, nenhum banco previsto) |

## Gabarito docente

> Esta seção é para o docente. Não distribuir antes da atividade.

### O que ainda sobrou de problema na versão estrita (`depois/`)

Útil para a discussão: **"SOLID à risca" não significa "perfeito"**.

1. **`Pedido` carrega as estratégias** (`CalculoDeFrete`, `FormaDePagamento`). Quem monta o pedido na borda do sistema (tela, API) precisa saber **qual** estratégia instanciar a partir de um texto ("PIX"). Esse mapeamento texto → objeto foi **empurrado** para fora, não eliminado: em algum lugar existirá uma *fábrica* com um `switch`. É o OCP revelando seu limite: o `if` não some, muda de lugar.
2. **`RepositorioDePedidos` tem uma única implementação** (`RepositorioEmMemoria`) e nenhum requisito de outra. Em produção, é uma interface *especulativa* até a segunda implementação aparecer.
3. **Validação duplicada de contrato:** o limite de R$ 150,00 do dinheiro vive em `PagamentoEmDinheiro`; se a regra mudar por *canal* (app × balcão), a classe passará a depender de contexto que não tem.
4. **Acoplamento temporal em `ServicoDePedido.fechar`:** a ordem *salvar → notificar* importa. Se `notificar` falhar, o pedido já está gravado. Não há transação nem compensação. (Boa pergunta: qual princípio resolve isso? *Nenhum.* SOLID não trata de consistência.)
5. **`ReciboDoPedido` guarda o nome da forma de pagamento como `String`** (`"PIX"`), que é uma *Primitive Obsession* residual da Aula 08: o `nome()` poderia ser um tipo.

### Pontos de armadilha para os grupos

- Grupos tendem a criar `ProcessadorDePedido`, `GerenciadorDeFrete`, `HandlerDePagamento`. Pergunte: *qual é o nome que o especialista de negócio usaria?*
- Grupos tendem a extrair interface para **tudo** na Rodada 1 (`IServicoDePedido`, `IRecibo`). Pergunte: *quem é o cliente que justifica essa interface, e qual segunda implementação você consegue citar?*
- Alguns grupos criam a interface `Notificador` e mantêm `new NotificadorPorEmail()` dentro do serviço, e dizem "cumpri o DIP". Pergunte: *o que você precisa mudar na classe para trocar por SMS?*
- Alguns resolvem LSP com `instanceof NotificadorPorEmail` antes de chamar `enviarSms`. Pergunte: *e quando aparecer o terceiro notificador?*
- Alguns separam o `ServicoDePedido` em cinco classes **menores** sem mudar o motivo de mudança de nenhuma. Pergunte: *quando o financeiro mudar a regra de cartão, quantos arquivos você abre?*
- No M5, grupos tentam acrescentar a API à constante `Frete.ENTREGA` (o `enum` passa a depender de rede). Deixe tentar; em 10 minutos percebem que o `enum` não é o lugar de uma dependência injetável.

### Resultados esperados da Parte 4 (experimento de mudança)

Ver a tabela de M1–M5 na seção 8 da [fundamentação](../01-fundamentacao-teorica.md). Perguntas para o fechamento:

1. Em quais mudanças `antes/` foi **mais barato** que `depois/`? (Resposta honesta: nenhuma em linhas; mas M1 em `antes/` é a de menor esforço *imediato*.)
2. Em qual mudança `equilibrado/` **perdeu** para `depois/`? (M5.)
3. A refatoração para `depois/` custou 2,2× as linhas. Em que **número de mudanças futuras** ela se paga? A turma deve concluir que **não existe número universal**: depende da taxa de mudança da região.
4. Qual princípio **não** apareceu em nenhuma das mudanças? (Em geral, **L** — e isso também é informação: o contrato só importa quando há mais de uma implementação em uso.)
