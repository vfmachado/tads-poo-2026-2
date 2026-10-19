# Erros e equívocos frequentes — Aula 08

## Conceituais

- **Tratar Object Calisthenics como padrão obrigatório de produção.** É um exercício deliberado, de duração limitada, para educar a percepção. A pergunta que a regra provoca é mais valiosa que o seu cumprimento literal.
- **Equiparar "um ponto por linha" à Lei de Demeter.** Demeter trata de *conhecimento da estrutura de objetos distantes*. Interfaces fluentes (`stream`, `StringBuilder`, builders) têm muitos pontos e não a violam. O inverso também vale: `a.b` seguido de `.c` em duas linhas continua violando.
- **Achar que a versão estrita "é a correta" e a flexibilizada, "a desistência".** As duas respondem a contextos diferentes. O que se avalia é a justificativa.
- **Confundir tamanho com qualidade.** Classes pequenas não são, por si, coesas. Uma classe de 15 linhas que só repassa chamadas é um *Middle Man*.
- **Esquecer que as regras redistribuem complexidade.** O sistema total costuma crescer. Quem ganha e quem perde depende de qual mudança vem a seguir.
- **Considerar que as regras substituem a modelagem.** Nenhuma regra diz *quais* conceitos do domínio existem. Elas só provocam a pergunta.
- **Misturar categorias (princípio × heurística × regra de exercício).** Dizer "Object Calisthenics é um princípio de design" mostra confusão que reaparecerá em SOLID.

## De implementação

- **R1 cumprida por extração mecânica** (`processarParte1()`, `processarParte2()`): a indentação baixou e o entendimento piorou. O nome do método extraído precisa descrever um passo do *domínio*.
- **R2 cumprida por ternário aninhado ou por `switch` com `default` que lança exceção silenciosa:** o `else` foi escondido, não removido.
- **R2 resolvida criando interface + classes para uma decisão binária estável:** abstração prematura.
- **R3 com classes que só embrulham tipo** (`Nome`, `Rua`, `Cidade`), sem invariante, sem operação, sem risco de confusão.
- **R3 incompleta:** encapsular `String` mas deixar `BigDecimal fracao`, `double km` circulando por vários métodos.
- **R4 com classe que reimplementa a API de `List`** (`adicionar`, `obter`, `tamanho`): vira *Middle Man*. A coleção de primeira classe deve oferecer **operações de domínio** (`subtotal()`), não de contêiner.
- **R4 sem cópia defensiva:** guardar a lista recebida sem `List.copyOf` permite que o chamador a altere por fora.
- **R5 com método-túnel** (`pedido.distanciaDoBairroDoEnderecoDeEntrega()`): o acoplamento estrutural continua, agora escondido num nome grande.
- **R6 confundida com nomes imensos.** O objetivo é revelar intenção, não aumentar o número de caracteres.
- **R8 cumprida por agrupamento artificial** (`Dados`, `Contexto`, `Parametros`). Se o especialista de negócio não reconhece o nome, o agrupamento não é do domínio.
- **R9 levada ao pé da letra:** esconder toda consulta e criar `exibirEm(Impressora)` em cada classe. Consultas que devolvem valores imutáveis são aceitáveis; o que deve sumir são os setters e o vazamento de estruturas mutáveis.
- **Trocar getters por métodos públicos com outro nome** (`obterValor()` → `valor()`). Mudar a sintaxe sem mudar a responsabilidade não cumpre R9.
- **Alterar mensagens de erro ou formato de saída durante a refatoração**, perdendo a capacidade de comparar com `diff`.
- **Refatorar sem rodar a saída entre rodadas:** descobrir tarde que uma rodada anterior quebrou o comportamento.
- **Abandonar regras sem registrar o motivo:** vira decisão invisível, que o próximo leitor refaz.

## Do experimento de mudança

- **Julgar a versão pela contagem de arquivos tocados** sem olhar o *tipo* de mudança em cada um. Editar um arquivo existente e criar um novo têm riscos diferentes.
- **Conclusões gerais a partir de uma mudança só.** M1, M2 e M3 variam em *natureza* (dado × algoritmo); as conclusões precisam respeitar isso.
- **Concluir que "estrito sempre ganha em M3"** sem considerar que a antecipação teve custo antes de o requisito existir.
