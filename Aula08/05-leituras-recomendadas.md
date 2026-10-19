# Leituras recomendadas — Aula 08

Indicações por **tema/seção**; não há números de capítulo ou de página porque variam entre edições.

## Fonte original das regras

- **Jeff Bay** — ensaio *Object Calisthenics*, na coletânea *The ThoughtWorks Anthology* (ThoughtWorks, 2008). É a origem das nove regras e a leitura obrigatória para entender que foram propostas como **exercício**, para uma duração limitada.
- **William Durand** — artigos e apresentações sobre *Object Calisthenics* em seu blog (busque pelo nome do autor e pelo título). Boa leitura complementar, com exemplos em PHP facilmente traduzíveis para Java.

## Para sustentar a análise crítica

- **Martin Fowler** — *Refactoring* (2ª ed.). Seção sobre *code smells*: *Primitive Obsession*, *Feature Envy*, *Message Chains*, *Middle Man*, *Large Class*, *Data Clumps*, *Shotgun Surgery*, *Divergent Change*. É o vocabulário que a Aula 08 usa sem ter ainda formalizado.
- **John Ousterhout** — *A Philosophy of Software Design*. Tema: *módulos profundos versus rasos* — a crítica direta ao exagero de R7 e R8 ("classes demais, cada uma sem muita função").
- **Joshua Bloch** — *Effective Java* (3ª ed.). Temas: imutabilidade; cópias defensivas; `enum` no lugar de constantes `int`/`String`; `equals`/`hashCode` em objetos de valor; `Optional` com cautela.
- **Rebecca Wirfs-Brock e Alan McKean** — *Object Design: Roles, Responsibilities, and Collaborations*. Tema: distribuição de responsabilidades; objetos que "sabem" e "fazem" — base para R9.
- **Eric Evans** — *Domain-Driven Design*. Tema: *Value Objects* e *Entities*; modelo de domínio rico versus anêmico — fundamento conceitual de R3 e R9.
- **Bertrand Meyer** — *Object-Oriented Software Construction* (2ª ed.). Tema: separação comando × consulta (retomado para R9).
- **Robert C. Martin** — *Clean Code*. Temas: nomes (R6), funções pequenas e que fazem uma coisa (R1, R7). Leitura crítica: o livro defende limites de tamanho mais rígidos do que a evidência empírica sustenta, o que rende boa discussão.

## Para consultar na documentação oficial

- Documentação de linguagem Java: *enum types* (inclusive métodos específicos por constante), *record classes*, `java.util.List.copyOf`, `java.util.stream.Stream` (`filter`, `findFirst`), `java.util.Optional`, expressões `switch`.

## Complementar para mestrado

- **Martin Fowler** — textos curtos em seu site sobre *Tell Don't Ask*, *Anemic Domain Model*, *Value Object* e *Yagni*: ajudam a articular a diferença entre eliminar um smell e antecipar um requisito.
- Pesquisa acadêmica sobre **métricas de acoplamento e coesão** (por exemplo, CBO e LCOM da família de métricas de Chidamber e Kemerer) para fundamentar o item da extensão de mestrado que pede análise de métricas. Verifique bases como ACM Digital Library e IEEE Xplore; confirme autoria e ano antes de citar.
