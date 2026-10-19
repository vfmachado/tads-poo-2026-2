# Aula 08 — Object Calisthenics: um exercício deliberado para educar a percepção de design

> Parte da disciplina de Programação Orientada a Objetos — ver [cronograma geral](../cronograma.md) e diretrizes em [CLAUDE.md](../CLAUDE.md).

## Nota sobre a posição desta aula no semestre

No [cronograma](../cronograma.md) original, Object Calisthenics aparece como "Aula 13". Esta aula foi **antecipada para a posição 8** por decisão do docente. Consequências para o planejamento:

| Pré-requisito | Situação ao chegar na Aula 08 | Como esta aula lida com isso |
|---|---|---|
| Encapsulamento, Tell Don't Ask, Demeter, `record`, `BigDecimal` | Vistos nas [Aula 03](../Aula03/) e [Aula 04](../Aula04/) | Retomados como critério de análise de cada regra |
| Herança, interfaces, polimorfismo | Vistos na [Aula 06](../Aula06/) | Usados na versão refatorada (`Cupom` como interface, `enum` com método abstrato) |
| Code smells (Fowler), refatoração formal | **Ainda não vistos** como unidade | Os smells aparecem pelo nome, mas são introduzidos *just-in-time* (ver seção 4 da fundamentação) |
| SOLID | **Ainda não visto** | Não é pré-requisito. A aula **prepara** o terreno: ao final, a turma já terá sentido na prática o que SRP e OCP tentam descrever |
| Liskov / contratos | Ainda não visto | Não é usado. Nenhuma hierarquia de herança nesta aula |
| Streams e `Optional` | Pré-requisito *técnico* leve | Aparecem em 3 pontos do código; basta o docente comentar em 2 minutos (ver roteiro) |

> **Ajuste sugerido ao cronograma:** quando a Aula 13 chegar, ela deixa de ser introdução e passa a ser **revisão crítica à luz do SOLID** (reaplicar a calistenia no módulo escolhido do projeto e comparar com a decisão tomada aqui). Não alterei o [cronograma.md](../cronograma.md) automaticamente.

## 1. Título da aula

**Object Calisthenics: nove restrições, um exercício deliberado e uma pergunta — o que muda quando o código não pode ser ruim do jeito de sempre?**

## 2. Objetivos de aprendizagem

Ao final desta aula, o estudante deve ser capaz de:

1. **Identificar** violações das nove regras de Object Calisthenics em um trecho de código Java e **classificá-las** como problema de sintaxe, de implementação ou de modelagem.
2. **Refatorar**, preservando o comportamento (comprovado por saída idêntica), um método procedural aplicando as regras de forma incremental e verificável.
3. **Comparar** a versão estrita (todas as regras) com uma versão flexibilizada, usando evidências objetivas (número de classes, linhas, níveis de indentação, arquivos alterados por mudança de requisito).
4. **Justificar**, por escrito, quais regras foram flexibilizadas, em qual ponto do código e por qual razão de contexto.
5. **Avaliar** o impacto de duas mudanças de requisito nas três versões do código e **identificar** quando uma abstração criada pela regra é prematura.

## 3. Índice dos materiais desta aula

| Arquivo | Conteúdo |
|---|---|
| [01-fundamentacao-teorica.md](01-fundamentacao-teorica.md) | O que é (e o que não é) Object Calisthenics; as nove regras com os dez itens exigidos cada; riscos; código didático × código de produção |
| [02-problema-motivador.md](02-problema-motivador.md) | O fechamento de pedido do FoodExpress: uma regra de negócio real escrita num método de 70 linhas |
| [03-exemplo-completo/](03-exemplo-completo/) | `antes/` (violações marcadas), `depois/` (regras aplicadas à risca), `equilibrado/` (regras flexibilizadas com critério) — **os três produzem saída idêntica** |
| [04-atividade-pratica.md](04-atividade-pratica.md) | Diagnóstico → refatoração em rodadas → comparação → experimento de mudança |
| [05-perguntas-discussao.md](05-perguntas-discussao.md) | Questões abertas |
| [06-entregavel-e-avaliacao.md](06-entregavel-e-avaliacao.md) | Entregável, critérios e rubrica (graduação e mestrado) |
| [07-erros-frequentes.md](07-erros-frequentes.md) | Equívocos conceituais e de implementação |
| [08-leituras-recomendadas.md](08-leituras-recomendadas.md) | Leituras |

## 4. Recursos da linguagem Java utilizados

- `enum` com **métodos abstratos por constante** (`Prioridade`) e com **dados** (`FaixaDeEntrega`, `Cupom`): o principal substituto de `if/else` nesta aula;
- `interface` + implementações (`Cupom`, `CupomPercentual`, `CupomFreteGratis`, `SemCupom`) — polimorfismo no lugar de cadeia condicional, e o *Null Object*;
- `record` em dois papéis opostos: como **chave/valor** (`CodigoDeCupom`, `Dinheiro` na versão equilibrada) e como **comando imutável** (`PedidoDeEntrega` na versão equilibrada), com discussão de seus limites;
- classes imutáveis `final` com construtor validante (`Quantidade`, `DistanciaEmKm`, `Dinheiro`);
- cópia defensiva (`List.copyOf`) e **coleção de primeira classe** (`ItensDoPedido`);
- `BigDecimal` encapsulado em `Dinheiro` (continuidade da [Aula 04](../Aula04/));
- `switch` com expressão (versão equilibrada, `Cupom.pelo`);
- `Stream` e `Optional` em pontos localizados — **usados como ferramentas para manter um nível de indentação**, não como tema da aula.

Deliberadamente **fora** do escopo: `sealed`, generics avançados, exceções verificadas próprias, injeção de dependência.

## 5. Roteiro cronometrado (aula de 2 h = 120 min)

| Tempo | Atividade | Material |
|---|---|---|
| 0–10 | Abertura: executar `antes/Main.java`, mostrar a saída, mostrar `FechamentoPedido.fechar()` na tela (70 linhas, até 5 níveis). Pergunta: *"se a diretoria pedir um terceiro tipo de cupom amanhã, onde você mexe?"* | [02-problema-motivador.md](02-problema-motivador.md) |
| 10–35 | Exposição: o que é calistenia (analogia com exercício físico, não com rotina de trabalho); mapa das nove regras em 3 grupos (**fluxo** R1–R2, **tipos** R3–R4, **fronteiras** R5–R9); a regra de ouro da aula (seção 1 da fundamentação) | [01-fundamentacao-teorica.md](01-fundamentacao-teorica.md) |
| 35–40 | *Micro-nota técnica (2 min cada):* `enum` com método abstrato; `stream().filter().findFirst()`. Sem isso, a turma não consegue ler `depois/` | seção 4 da fundamentação |
| 40–95 | Atividade prática em grupos (Partes 1–3) | [04-atividade-pratica.md](04-atividade-pratica.md) |
| 95–110 | Experimento de mudança (Parte 4): cada grupo aplica uma mudança nas três versões e reporta o que precisou tocar | idem |
| 110–120 | Fechamento: cada grupo diz **uma regra que manteria e uma que abandonaria** neste código, com a razão. Docente fecha com a tabela de métricas | [05-perguntas-discussao.md](05-perguntas-discussao.md) |

### Adaptação de carga horária

- **1 h:** apenas abertura + exposição enxuta (regras R1, R2, R3, R4, R9) + Parte 1 (diagnóstico). Refatoração e experimento viram tarefa assíncrona. Corte R6 e R7 da exposição (a turma as entende sozinha).
- **2 h:** roteiro acima.
- **4 h:** inserir, após a Parte 3, uma **revisão de código em pares** (cada dupla critica a versão estrita da outra dupla *somente* pelos critérios da seção 6 da fundamentação) e estender a Parte 4 com a segunda mudança (cupom dependente de dia da semana). Mestrado: iniciar o ADR em aula.

## 6. Diferenciação graduação × mestrado nesta aula

- **Graduação:** produzir a versão estrita funcionando (saída idêntica), identificar as violações por regra e justificar **três** flexibilizações na versão equilibrada.
- **Mestrado:** além do exigido, (a) comparar as três versões com métricas objetivas e discutir a validade (e os limites) dessas métricas; (b) escrever um **ADR** para a decisão mais controversa (ex.: `Cupom` como interface *versus* `enum`); (c) conduzir o experimento de mudança com duas alterações de natureza diferente (dado × algoritmo) e relacionar o resultado ao Open-Closed Principle, **sem** ainda ter visto SOLID formalmente — a hipótese é parte do exercício.

## 7. Preparação do docente (30 min)

1. Compilar e rodar as três versões: `javac -d out antes/*.java` (idem `depois`, `equilibrado`) e comparar as saídas com `diff` (devem ser idênticas — esse é o *teste de caracterização* usado na aula).
2. Ler o gabarito em [03-exemplo-completo/README.md](03-exemplo-completo/README.md) (inclui o que **ainda sobrou** de problema na versão estrita e pontos de armadilha para a discussão).
3. Decidir qual mudança do experimento (Parte 4) será imposta à turma e qual ficará como surpresa.
