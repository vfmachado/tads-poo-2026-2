# Trabalho I — Refatoração orientada a objetos

> Disciplina de Programação Orientada a Objetos — ver [CLAUDE.md](../CLAUDE.md) e o [cronograma geral](../cronograma.md). Trabalho individual (o professor pode adaptar para duplas), cobrindo os conceitos das [Aula 01](../Aula01/), [Aula 02](../Aula02/), [Aula 03](../Aula03/) e [Aula 04](../Aula04/). Todo o código de partida está pronto para compilar em [`Trabalho-I-codigo-inicial/`](Trabalho-I-codigo-inicial/) — este documento não reproduz o código-fonte, apenas descreve o que cada parte faz e o que precisa mudar.

## 1. Objetivo do trabalho

O trabalho tem duas partes independentes. Em ambas, você recebe um sistema **funcional**, mas escrito **sem nenhum conceito de orientação a objetos**, e precisa produzir uma nova versão que:

- produza **a mesma saída observável** para os mesmos dados de entrada do `Main` original de cada parte;
- aplique os conceitos de orientação a objetos trabalhados nas Aulas 01 a 04 desta disciplina;
- proteja, através do próprio modelo de classes, as regras de negócio listadas para cada parte — inclusive as que o código de partida **não** verifica hoje.

Este não é um exercício de "deixar o código mais bonito". É um exercício de **modelagem**: decidir quais conceitos do domínio merecem virar classes, quem é responsável por cada regra, e como impedir que o sistema chegue a um estado inválido.

## 2. Parte 1 — Controle de manutenção de frota (TransportaJá)

### Contexto

A **TransportaJá** é uma transportadora que opera uma frota de veículos de entrega. Um estagiário escreveu, às pressas, um pequeno sistema de controle de manutenção da frota — e funciona: cadastra veículos, registra manutenções, calcula custo total e avisa quando um veículo precisa de manutenção. O problema é que ele foi escrito como uma única classe com métodos e listas estáticas, funcionando como um conjunto de funções e variáveis globais — o que Java permite, mas que não é, de fato, um programa orientado a objetos.

### Código de partida

[`Trabalho-I-codigo-inicial/parte-1-controle-de-frota/`](Trabalho-I-codigo-inicial/parte-1-controle-de-frota/) — `ControleDeFrota.java` (cadastro de veículos, registro de manutenções, cálculo de custo total e verificação de necessidade de manutenção, tudo com listas estáticas paralelas) e `Main.java` (cadastra dois veículos, atualiza quilometragem, registra manutenções preventivas e corretivas, e imprime relatórios).

### Regras de negócio que o sistema deve respeitar

O código de partida **não verifica nenhuma** destas regras hoje — parte do trabalho é fazer com que a nova versão as garanta:

1. Cada veículo tem placa (identificador único), modelo, quilometragem atual e um intervalo de manutenção preventiva (em km).
2. A quilometragem de um veículo **nunca pode retroceder** — atualizar para um valor menor que o atual deve ser rejeitado.
3. Toda manutenção registrada tem: data, tipo (`PREVENTIVA` ou `CORRETIVA`), custo e a quilometragem do veículo no momento do registro.
4. Uma manutenção **preventiva** reinicia a contagem: a "quilometragem da última preventiva" passa a ser a quilometragem atual do veículo naquele momento. Uma manutenção **corretiva** não reinicia essa contagem.
5. Um veículo **precisa de manutenção** quando a diferença entre sua quilometragem atual e a quilometragem da última preventiva for maior ou igual ao intervalo de manutenção definido para ele.
6. O **custo total de manutenção** de um veículo é a soma de todos os custos já registrados para ele.
7. Não deve ser possível registrar uma manutenção com **custo negativo**.
8. Não deve ser possível registrar uma manutenção com **data futura**.
9. Não deve ser possível cadastrar dois veículos com a **mesma placa**.

### O que observar antes de começar

Antes de escrever qualquer classe nova, identifique por escrito (isso faz parte da entrega — seção 6):

- onde estão os "conceitos de domínio escondidos" dentro das listas estáticas (quantos conceitos diferentes você consegue enxergar dentro de `ControleDeFrota`?);
- por que a busca por índice (`indiceDoVeiculo`) é necessária nesta versão, e por que ela deixaria de fazer sentido em uma versão orientada a objetos;
- qual das nove regras de negócio já é acidentalmente respeitada pelo código de partida, e quais são completamente ignoradas.

## 3. Parte 2 — Turmas e matrículas (relacionamento entre classes)

### Contexto

Uma escola técnica quer controlar suas turmas e as matrículas de alunos nelas. Um mesmo aluno pode se matricular em várias turmas (mas não duas vezes na mesma turma), e cada matrícula registra a nota final daquele aluno naquela turma especificamente. Diferente da Parte 1 — que gira em torno de **um** conceito central — esta parte exige modelar corretamente a **relação entre conceitos diferentes**: uma turma, os alunos matriculados nela, e a matrícula que liga os dois.

### Código de partida

[`Trabalho-I-codigo-inicial/parte-2-turmas-e-matriculas/`](Trabalho-I-codigo-inicial/parte-2-turmas-e-matriculas/) — `ControleAcademico.java` (criação de turmas, matrícula de alunos, lançamento de notas e contagem de aprovados, novamente com listas estáticas paralelas — note que aqui as listas cruzam **dois conceitos diferentes**, turma e matrícula, sem nenhuma relação real de objeto entre eles) e `Main.java` (cria uma turma, matricula alunos — incluindo duas tentativas que deveriam ser rejeitadas — lança notas e imprime o resultado).

### Regras de negócio que o sistema deve respeitar

1. Uma turma tem código (identificador único), capacidade máxima de vagas e um conjunto de matrículas.
2. Um aluno é identificado por nome (para este exercício, não é necessário mais do que isso) — mas deve ser modelado como uma classe própria, não como `String` solta espalhada pelo sistema.
3. Uma matrícula associa um aluno a uma turma específica, e guarda a nota final do aluno naquela turma — nota essa que pode ainda não existir (aluno matriculado, mas sem nota lançada).
4. Um aluno **não pode se matricular duas vezes na mesma turma**.
5. Uma turma **não pode aceitar matrícula além da capacidade máxima**.
6. A nota, quando lançada, deve estar **entre 0 e 10**.
7. Um aluno está **aprovado** na turma se a nota for maior ou igual a 6.
8. Deve ser possível consultar **quantos alunos aprovados** uma turma tem, sem expor a lista de matrículas diretamente.

### O que observar antes de começar

- Quantas classes diferentes você consegue identificar neste domínio? (a resposta não é "uma": há pelo menos um conceito para a turma, um para o aluno, e um para a relação entre os dois — pense se a matrícula é, ela mesma, um conceito com dados próprios, ou apenas uma referência).
- A relação entre `Turma` e `Matricula` é diferente da relação entre `Matricula` e `Aluno`: uma matrícula só existe no contexto de uma turma específica (se a turma deixar de existir, a matrícula não faz sentido sozinha), enquanto um aluno existe independentemente de qualquer turma e pode estar relacionado a várias matrículas ao mesmo tempo. Você não precisa nomear essas diferenças com vocabulário formal (isso será aprofundado em aula futura) — mas a modelagem da sua solução deve refletir essa diferença na prática.
- Onde, no código de partida, a ausência de uma classe `Turma` de verdade obriga a repetir o código de busca (`indexOf`, laços `for` comparando strings) que uma coleção bem encapsulada dentro de `Turma` eliminaria?

## 4. O que o trabalho exige (mapeado às aulas)

Válido para as duas partes:

| Da aula | O que deve aparecer na solução |
|---|---|
| Aula 01 | Nenhuma classe "saco de dados": todo conceito de domínio tem construtor validado e comportamento próprio, não apenas getters/setters |
| Aula 02 | Responsabilidades decididas antes de codificar (inclua as CRC Cards que usou, mesmo que informalmente); construtores que impedem estados inválidos |
| Aula 03 | Nenhuma lista pública nem estática guardando o estado do sistema; nenhuma coleção interna devolvida diretamente; decisão justificada sobre qual conceito deveria (ou não) ser `record`; nenhuma decisão de negócio tomada "por fora" de quem deveria decidi-la (Tell, Don't Ask) |
| Aula 04 | Nenhum valor representado por tipo primitivo genérico quando um tipo mais específico é apropriado (dinheiro, notas); nenhuma classe utilitária estática acumulando regra de negócio |
| Específico da Parte 2 | A relação entre `Turma`, `Matricula` e `Aluno` modelada com objetos de verdade (referências entre classes), nunca reconstruída "na mão" com comparação de identificadores em listas paralelas |

Não é necessário (nem esperado) usar herança, interfaces ou polimorfismo — esses conceitos ainda não foram trabalhados na disciplina no momento deste trabalho.

## 5. Restrições

- **Comportamento observável preservado**: rodando sua versão com os mesmos dados dos `Main` originais, os relatórios impressos devem informar os mesmos fatos (ajustados apenas pela precisão numérica correta, ver Aula 04, e pelas rejeições explícitas que os itens de "regras de negócio" passam a exigir, onde antes havia silêncio).
- **Proibido usar herança, interfaces, polimorfismo ou sealed classes** — ainda não estudados nesta disciplina no momento deste trabalho. Soluções que usem esses recursos serão aceitas na forma, mas não pontuarão além do que pontuaria uma solução sem eles.
- **Proibido qualquer estado `static` mutável** representando dados de negócio. `static` só é aceitável para constantes (`static final`) ou métodos utilitários sem estado (ver Aula 04, item 6 da fundamentação teórica).
- Cada parte ainda deve rodar por um `main` (não é necessário criar interface de usuário, arquivo ou banco de dados).

## 6. Como o trabalho é avaliado

| Critério | O que se observa |
|---|---|
| Modelagem | Conceitos de domínio corretamente identificados como classes; nenhuma classe reduzida a getters/setters sem comportamento |
| Relacionamento entre classes (Parte 2) | `Turma`, `Matricula` e `Aluno` conectados por referências reais de objeto, não por comparação de identificadores em listas soltas |
| Proteção de invariantes | As regras de negócio de cada parte são impossíveis de violar através da API pública das classes — não apenas "checadas na tela" |
| Encapsulamento | Nenhum campo público, nenhuma coleção interna vazada, nenhum estado global estático |
| Coesão | Cada classe tem uma responsabilidade central identificável; nenhuma "classe deus" reproduzindo os controles estáticos originais com nomes diferentes |
| Uso apropriado de Java | Tipos adequados para cada conceito (datas, dinheiro, identificadores, notas), sem primitive obsession nem data clumps remanescentes |
| Comportamento preservado | Saída dos dois programas consistente com o original, para os mesmos dados válidos |
| Clareza da justificativa | As respostas da seção 7 demonstram entendimento das decisões tomadas, não apenas descrição do código |

**Não são critérios de avaliação:** número de classes criadas, número de linhas de código, uso de recursos avançados de Java sem necessidade, ou velocidade de execução.

## 7. Perguntas de reflexão (entregar junto com o código)

Responda, em até um parágrafo cada:

1. Quais classes você identificou em cada parte, e como decidiu as responsabilidades de cada uma? (Se usou CRC Cards, ainda que informalmente, inclua-as.)
2. Na Parte 2, como você decidiu representar a relação entre `Turma` e `Matricula`, e entre `Matricula` e `Aluno`? As duas relações acabaram modeladas da mesma forma, ou de formas diferentes? Por quê?
3. Qual regra de negócio (de qualquer uma das partes) foi mais difícil de proteger estruturalmente (isto é, através do próprio modelo, não de um `if` em algum lugar)? Por quê?
4. Existe alguma decisão de design da sua solução que você sabe que é discutível — outra pessoa razoavelmente poderia ter modelado diferente? Qual, e por que você escolheu o caminho que escolheu?

## 8. Formato e entrega

- **Modalidade:** individual 
- **Entrega:** explicação em vídeo + pasta ou repositório contendo o código-fonte completo das duas partes, as respostas da seção 7, e a saída de execução de cada `Main` (print ou arquivo de texto).
- **Prazo:** 2 semanas.
