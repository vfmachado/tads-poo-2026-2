# Fundamentação teórica — Aula 01

## 1. Abstração

Abstração não é "esconder código" — é **selecionar, dentre todos os detalhes possíveis de um conceito do mundo real, apenas aqueles relevantes para o problema que está sendo resolvido**. Um `Contrato` em um sistema de cobrança não precisa saber a cor da caneta usada para assiná-lo, mas precisa saber sua data de início, seu status e as regras que determinam se ele pode ser renovado ou cancelado.

A abstração é o primeiro e mais importante ato de modelagem: decidir **o que existe** no modelo e **o que fica de fora**. Erros de abstração (incluir detalhes irrelevantes ou omitir detalhes essenciais) se propagam para todo o restante do design.

## 2. Classes e instâncias

- Uma **classe** é a abstração — o molde que descreve quais atributos e comportamentos os objetos daquele tipo terão.
- Uma **instância** (objeto) é uma ocorrência concreta dessa abstração, existente em tempo de execução, com seu próprio estado.

`Contrato` é uma classe; o contrato específico do cliente "Empresa XYZ", criado em 03/2026, é uma instância.

## 3. Identidade dos objetos

Cada objeto possui uma **identidade** própria, independente do seu estado. Dois contratos podem ter exatamente os mesmos valores de atributos (mesmo cliente, mesmo plano, mesmo valor) e ainda assim serem *objetos diferentes* — por exemplo, dois contratos distintos assinados em datas diferentes que coincidentemente têm os mesmos dados.

Em Java, por padrão, a identidade é comparada com `==` (mesma referência de memória) e a igualdade de estado é comparada com `equals` (aprofundado na Aula 12). Nesta aula, o importante é a **distinção conceitual**: identidade ≠ igualdade de estado.

## 4. Estado e comportamento

- **Estado**: o conjunto de valores dos atributos de um objeto em um dado instante (ex.: status do contrato, número de faturas em aberto).
- **Comportamento**: o conjunto de operações que o objeto é capaz de realizar, e que tipicamente leem e/ou modificam esse estado (ex.: `cancelar()`, `registrarFaturaEmAberto()`).

Um objeto orientado a objetos **não é apenas estado com métodos anexados** — é uma unidade coesa em que o comportamento existe para proteger e dar sentido ao estado.

## 5. Atributos e métodos

Sintaticamente, atributos armazenam dados e métodos executam ações. Mas o papel semântico importa mais que a sintaxe: um atributo público exposto sem controle é, na prática, um dado global disfarçado de objeto. O valor de um bom design não está em "ter atributos e métodos", mas em **quem tem permissão de alterar o quê, e sob quais condições**.

## 6. Invariantes

**Invariante** é uma condição que deve permanecer verdadeira durante toda a vida útil de um objeto (ou entre chamadas de método). Exemplos no domínio de contratos:

- um contrato cancelado nunca pode voltar a ficar `ATIVO`;
- o número de faturas em aberto nunca pode ser negativo;
- um contrato só pode ser renovado se estiver `ATIVO`.

Proteger invariantes é uma das razões centrais para a existência de classes — se qualquer código externo puder alterar o estado livremente, a classe não garante nada, e a invariante deixa de existir na prática, mesmo que esteja documentada em um comentário.

## 7. Comandos e consultas (introdução)

- **Consulta** (*query*): retorna informação, não altera o estado observável do objeto (ex.: `valorMensal()`).
- **Comando** (*command*): altera o estado do objeto, tipicamente não retornando dado de negócio (ex.: `cancelar()`).

O **princípio Command-Query Separation (CQS)**, proposto por Bertrand Meyer, recomenda não misturar as duas coisas no mesmo método — um método que altera estado *e* retorna um dado de negócio esconde efeitos colaterais e dificulta o raciocínio sobre o código. Este tema será aprofundado na Aula 03; aqui, o objetivo é reconhecer o padrão.

## 8. Objetos anêmicos versus objetos ricos

- **Objeto anêmico**: expõe seu estado quase integralmente via getters/setters, sem comportamento próprio relevante. Toda a lógica de negócio fica em classes externas (`*Service`, `*Manager`, `*Helper`), que manipulam o objeto como uma estrutura de dados.
- **Objeto rico**: encapsula estado e comportamento juntos; oferece uma API baseada em operações de domínio (`cancelar()`, `renovar()`) em vez de manipulação direta de atributos (`setStatus(...)`).

O **Modelo Anêmico de Domínio** (*Anemic Domain Model*) é amplamente reconhecido como um antipadrão (Fowler): ele *parece* orientado a objetos (existem classes, existem objetos), mas **funciona como um modelo procedural com sintaxe de classes**. As consequências práticas são: lógica de negócio duplicada, invariantes não garantidas, e alto acoplamento entre a classe de serviço e a estrutura interna de cada entidade.

## 9. Contratos e responsabilidades dos objetos

Sob a ótica de responsabilidades (Wirfs-Brock & McKean), cada objeto deve ser entendido como um **prestador de serviços** para os demais objetos do sistema, com um contrato implícito: "eu garanto X, desde que você me peça através da minha interface pública". Perguntar "de quem é essa responsabilidade?" é mais produtivo do que perguntar "onde eu guardo esse dado?".

## 10. Mensagens e colaboração entre objetos

Um sistema orientado a objetos é uma rede de objetos que colaboram trocando **mensagens** (chamadas de método), não uma hierarquia de registros de dados sendo processados por funções externas. Pensar em termos de colaboração ("quem pede o quê para quem") é uma mudança de perspectiva central desta disciplina.

## 11. Objetos mutáveis e imutáveis (introdução)

Um objeto **mutável** permite que seu estado mude após a criação (ex.: um `Contrato`, cujo status muda ao longo do tempo). Um objeto **imutável** tem seu estado fixado na criação e nunca muda (ex.: um valor monetário, uma data). Tipos imutáveis eliminam uma classe inteira de bugs relacionados a mudanças inesperadas de estado compartilhado. Este tema será aprofundado na Aula 04.

## 12. Diferença entre modelo de dados e modelo orientado a objetos

Um **modelo de dados** (típico de bancos de dados relacionais) descreve **o que existe**: tabelas, colunas, chaves. Um **modelo orientado a objetos** descreve **o que existe, o que se comporta e o que se garante**. É perfeitamente possível ter um esquema de dados bem normalizado e, ainda assim, um péssimo modelo orientado a objetos por cima dele — por exemplo, quando as classes Java são apenas espelhos das tabelas (uma classe por tabela, um atributo por coluna, getters/setters para tudo), sem nenhuma responsabilidade de comportamento. Reconhecer essa diferença é o objetivo central desta primeira aula.
