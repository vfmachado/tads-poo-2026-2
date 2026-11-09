# Aula 09 — SOLID: cinco perguntas sobre o que acontece quando o código precisa mudar

## 1. Título da aula

**SOLID: cinco princípios, cinco "dores" e uma pergunta — quando abstrair se paga, e quando é só cerimônia?**

## 2. Objetivos de aprendizagem

Ao final desta aula, o estudante deve ser capaz de:

1. **Identificar** violações de cada um dos cinco princípios em um trecho de código Java e **descrever a dor concreta** que cada uma causa (mudança que quebra, extensão que exige edição, `instanceof` no cliente, método que lança exceção, teste impossível).
2. **Distinguir** princípio, heurística, padrão de projeto e mecanismo (por exemplo: DIP × injeção de dependência × container de DI; OCP × *Strategy*).
3. **Refatorar**, preservando o comportamento (comprovado por saída idêntica), uma classe que concentra várias responsabilidades, aplicando um princípio por vez e verificando o resultado a cada rodada.
4. **Comparar** a versão estrita (todos os princípios) com uma versão pragmática, usando evidências objetivas (arquivos, linhas, razões para mudar, arquivos tocados por mudança de requisito).
5. **Avaliar** o impacto de mudanças de requisito nas três versões e **identificar** quando uma abstração é prematura e quando a falta dela custa mais que a abstração.
6. **Relacionar** SOLID e Object Calisthenics: o que cada regra da calistenia "provoca" e qual princípio explica por quê.

## 3. Índice dos materiais desta aula

| Arquivo | Conteúdo |
|---|---|
| [01-fundamentacao-teorica.md](01-fundamentacao-teorica.md) | O que é (e o que não é) SOLID; os cinco princípios com os dez itens exigidos cada; riscos; checklist de flexibilização; relação com Object Calisthenics; evidências do exemplo |
| [02-exemplos-por-principio/](02-exemplos-por-principio/) | Um exemplo mínimo e executável por princípio, cada um com `Antes.java` e `Depois.java` |
| [03-exemplo-completo/](03-exemplo-completo/) | `antes/` (violações marcadas), `depois/` (princípios à risca), `equilibrado/` (flexibilizado com critério) — **os três produzem saída idêntica** |
| [04-erros-frequentes.md](04-erros-frequentes.md) | Equívocos conceituais e de implementação |
| [05-leituras-recomendadas.md](05-leituras-recomendadas.md) | Leituras |

A atividade prática (diagnóstico → refatoração → comparação → experimento de mudança) está descrita na seção 7 deste arquivo.

## 4. Recursos da linguagem Java utilizados

- `interface` como **ponto de extensão** (`CalculoDeFrete`, `FormaDePagamento`) e como **fronteira** (`Notificador`, `RepositorioDePedidos`): base de OCP e DIP;
- **injeção pelo construtor** com campos `final` (`ServicoDePedido`) e **raiz de composição** (`Main`): o mecanismo mais simples de DIP, sem framework;
- `enum` com **método abstrato por constante** (`Frete`, `Pagamento`): a alternativa pragmática ao polimorfismo aberto, retomada da Aula 08;
- `record` para dados imutáveis (`Pedido`, `ReciboDoPedido`);
- `BigDecimal` com `setScale` e `RoundingMode.HALF_UP` para dinheiro;
- classes internas estáticas nos exemplos por princípio, para que cada `Antes.java`/`Depois.java` seja um arquivo executável sozinho;
- `UnsupportedOperationException` como **sintoma** de violação de LSP/ISP.

Deliberadamente **fora** do escopo: container de injeção de dependência (Spring, Guice), `sealed`, generics avançados, frameworks de teste. Os padrões de projeto aparecem apenas como "o nome do que já foi feito".

## 5. Exercícios de Fixação

### Parte 1 — Diagnóstico

Em duplas, anotar em `antes/ServicoDePedido.java` e `antes/NotificadorPorEmail.java`:

1. Cada trecho que viola um princípio, com a letra (`S`, `O`, `L`, `I`, `D`).
2. Para cada um: **qual é a dor?** (a mudança que quebraria, o teste que não dá para escrever). Sem dor descrita, a violação não conta.
3. Classificar: é problema de **implementação** ou de **modelagem**? (Retoma a distinção da Aula 08.)

### Parte 2 — Refatoração em rodadas

Um princípio por rodada, **rodando a saída e comparando com `diff` ao fim de cada uma**:

| Rodada | Princípio | Mudança esperada |
|---|---|---|
| 1 | **D** + **I** | Extrair `Notificador` mínimo e `RepositorioDePedidos`; injetar no construtor; tirar os `new` do serviço |
| 2 | **O** | Substituir as duas cadeias `if/else if` por colaboradores (interface ou `enum`) |
| 3 | **S** | Tirar do serviço a formatação do recibo e qualquer outra responsabilidade que sobrar |
| 4 | **L** | Conferir os contratos: nenhuma implementação lança exceção que a interface não declare |

Regra de ouro da rodada: **se não souber dizer qual dor o passo remove, não dê o passo.**

### Parte 3 — Comparação

Preencher, para a versão do grupo, a tabela da seção 8 da fundamentação (arquivos, linhas, `else`, razões para mudar, testabilidade). Comparar com `depois/` e `equilibrado/` e **listar três diferenças de decisão** (não de sintaxe).

### Parte 4 — Experimento de mudança

Cada grupo recebe **uma** mudança (a outra fica de surpresa) e a aplica nas três versões, registrando: quantos arquivos **editados**, quantos **criados**, e qual o risco de regressão.

| Mudança | Natureza |
|---|---|
| **M1** — tipo de atendimento `AGENDADA` (R$ 5,00 fixos) | Valor/comportamento simples |
| **M2** — forma `VALE_REFEICAO` (sem desconto; recusa total acima de R$ 120,00) | Comportamento com regra própria |
| **M3** — avisar também por SMS | Nova implementação de uma fronteira |
| **M4** — testar `fechar` sem enviar e-mail | Testabilidade |
| **M5** — frete que consulta uma API externa de distância | Dependência externa nova (o `enum` chega ao limite) |

