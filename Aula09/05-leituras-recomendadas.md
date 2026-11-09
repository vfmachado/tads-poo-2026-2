# Leituras recomendadas — Aula 09

Indicações por **tema/seção**; não há números de capítulo ou de página porque variam entre edições. Confirme autoria e ano antes de citar.

## Fonte original dos princípios

- **Robert C. Martin** — *Agile Software Development: Principles, Patterns, and Practices* (2003). Reúne os princípios do SOLID com exemplos em Java/C++. Os artigos originais dos anos 1990 (*The Open-Closed Principle*, *The Liskov Substitution Principle*, *The Dependency Inversion Principle*, *The Interface Segregation Principle*) estão disponíveis em versões digitalizadas; busque pelo nome do autor e pelo título.
- **Robert C. Martin** — *Clean Architecture* (2017). Tema: a formulação do **SRP em termos de atores** ("uma razão para mudar" reescrita como "responde a um único ator") e o **DIP como regra de dependência** entre camadas.
- **Bertrand Meyer** — *Object-Oriented Software Construction* (2ª ed.). Tema: origem do **princípio aberto/fechado** (via herança) e do **projeto por contrato** (pré-condições, pós-condições, invariantes), que é a base técnica do LSP.
- **Barbara Liskov e Jeannette Wing** — *A Behavioral Notion of Subtyping* (ACM TOPLAS, 1994). Tema: a definição formal de substituibilidade comportamental.

## Para sustentar a análise crítica

- **Martin Fowler** — *Refactoring* (2ª ed.). Tema: *code smells* que se relacionam diretamente com SOLID — *Divergent Change* e *Shotgun Surgery* (S, O), *Refused Bequest* (L), *Speculative Generality* (O, D), *Switch Statements* (O).
- **Martin Fowler** — texto curto em seu site sobre **Inversion of Control** e **Dependency Injection** (*Inversion of Control Containers and the Dependency Injection pattern*). Tema: separar princípio, mecanismo e ferramenta.
- **Joshua Bloch** — *Effective Java* (3ª ed.). Temas: "prefira interfaces a classes abstratas"; "prefira composição à herança"; "projete e documente para herança ou a proíba". Útil para discutir LSP com herança de implementação.
- **Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides** — *Design Patterns* (1994). Tema: *Strategy*, *Factory Method* e *Adapter* como formas de cumprir OCP e DIP; a frase "program to an interface, not an implementation".
- **John Ousterhout** — *A Philosophy of Software Design*. Tema: *módulos profundos versus rasos*; crítica a interfaces e classes demais — a contrapartida necessária ao entusiasmo por ISP e SRP.
- **Rebecca Wirfs-Brock e Alan McKean** — *Object Design: Roles, Responsibilities, and Collaborations*. Tema: papéis e colaboradores; fundamento para pensar a interface a partir do cliente (ISP).
- **Sandi Metz** — *Practical Object-Oriented Design* (POODR). Tema: dependências, "pergunte-me o que você faz, não o que você é", e quando esperar antes de abstrair — com exemplos fáceis de traduzir para Java.

## Para discutir o contraponto

- Textos e palestras que criticam SOLID como dogma (por exemplo, a crítica de **Dan North** em *CUPID — for joyful coding*, e artigos sobre "SOLID considerado danoso"). Servem para a discussão da turma: qual princípio resiste melhor à crítica e qual é mais contextual? Verifique o texto original antes de citar.
- A discussão sobre `UnsupportedOperationException` nas coleções do JDK (`List.of`, `Collections.unmodifiableList`) e o LSP: busque pela documentação de `java.util.Collection` ("optional operations") e por debates sobre *Liskov violations in the Java Collections Framework*.

## Para consultar na documentação oficial

- Documentação de linguagem Java: *interfaces* (métodos `default`, interfaces funcionais), *enum types* (métodos específicos por constante), *record classes*, `java.math.BigDecimal` e `RoundingMode`, `java.util.List.of` / `List.copyOf`.

## Complementar para mestrado

- Pesquisa acadêmica sobre **métricas de acoplamento e coesão** (por exemplo, CBO e LCOM da família de métricas de Chidamber e Kemerer) e sobre a relação empírica entre **aplicação de princípios de design e propensão a defeitos / esforço de mudança**. Verifique bases como ACM Digital Library e IEEE Xplore; confirme autoria e ano antes de citar.
- Textos sobre **ADR (Architecture Decision Records)** — formato curto de registro de decisão, aplicável ao item (b) da extensão de mestrado.
- Textos curtos de Martin Fowler sobre **Yagni** e **Speculative Generality**, para articular a diferença entre *estar preparado para extrair* um ponto de extensão e *criá-lo antecipadamente*.
