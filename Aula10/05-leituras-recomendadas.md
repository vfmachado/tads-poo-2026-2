# Leituras recomendadas — Aula 10

Indicações por **tema/seção**; não há números de capítulo ou de página porque variam entre edições. Confirme autoria e ano antes de citar.

## Para consultar na documentação oficial

- **JUnit User Guide** (site do projeto JUnit). Seções: *Writing Tests* (anotações, asserções, ciclo de vida, testes aninhados), *Parameterized Tests* (fontes de argumentos e conversão de tipos) e *Running Tests* (IDE, Maven, *Console Launcher*). É a referência para qualquer dúvida sobre o que uma anotação faz.
- Documentação do **Maven**: *Introduction to the Standard Directory Layout* (`src/main/java` × `src/test/java`) e do **Surefire** (o plugin que executa os testes em `mvn test`).
- Documentação de linguagem Java: `java.math.BigDecimal` (em especial a diferença entre `equals` e `compareTo`), expressões *lambda* e interfaces funcionais.

## Fundamentos de teste de unidade

- **Kent Beck** — *Test-Driven Development: By Example*. Tema: o ciclo vermelho–verde–refatorar e, mais importante para esta aula, como pensar em uma lista de casos antes de escrever código. Beck é também coautor do JUnit original.
- **Robert C. Martin** — *Clean Code*. Tema: o capítulo sobre testes de unidade — as propriedades **FIRST** e a ideia de que código de teste merece o mesmo cuidado que o de produção.
- **Roy Osherove** — *The Art of Unit Testing*. Tema: o que caracteriza um bom teste de unidade (legível, confiável, de fácil manutenção); nomes de testes; quando usar dublês.
- **Vladimir Khorikov** — *Unit Testing: Principles, Practices, and Patterns*. Tema: testar **comportamento observável** em vez de implementação; resistência a refatoração como critério de qualidade de um teste.
- **Maurício Aniche** — *Effective Software Testing: A Developer's Guide*. Tema: derivação sistemática de casos (partições, valores-limite) a partir da especificação; exemplos em Java com JUnit. Do mesmo autor, em português, *Testes Automatizados de Software: um guia prático* (Casa do Código).

## Dublês de teste

- **Gerard Meszaros** — *xUnit Test Patterns*. Tema: a origem da nomenclatura *dummy, stub, spy, mock, fake*; catálogo de *test smells*.
- **Martin Fowler** — textos curtos em seu site: *Mocks Aren't Stubs* (a diferença entre verificar estado e verificar interação) e *TestDouble* (resumo da nomenclatura de Meszaros).
- **Steve Freeman e Nat Pryce** — *Growing Object-Oriented Software, Guided by Tests*. Tema: dublês como instrumento de design de colaborações entre objetos — a ligação mais direta com as Aulas 05 e 09.

## Testes e design

- **Michael Feathers** — *Working Effectively with Legacy Code*. Tema: **testes de caracterização** (exatamente o que o `Main` + `diff` das Aulas 08 e 09 fazia) e *seams* — os pontos em que se consegue trocar uma dependência para testar.
- **Martin Fowler** — *Refactoring* (2ª ed.). Tema: o capítulo sobre construção de testes como pré-requisito para refatorar com segurança.
- **Martin Fowler** — textos curtos em seu site: *TestPyramid*, *TestCoverage* (por que cobertura alta não é objetivo) e *UnitTest* (as diferentes definições de "unidade").

## Para discutir o contraponto

- O debate sobre **quanto dublar** ("escola de Londres" × "escola clássica/de Detroit"): busque pelos textos de Fowler citados acima e pelas respostas a eles. Pergunta para a turma: no exemplo, por que o `RepositorioDePedidos` real foi usado e o `Notificador` não?
- O debate *"Is TDD Dead?"* (série de conversas entre Kent Beck, Martin Fowler e David Heinemeier Hansson, 2014). Tema: quando o design voltado a testes melhora o código e quando o deforma. Verifique o material original antes de citar.
- **Edsger W. Dijkstra** — a frase "testes mostram a presença de erros, nunca a ausência" vem de suas notas sobre programação estruturada (fim dos anos 1960/início dos 1970). Confirme a fonte antes de citar literalmente.

## Complementar para mestrado

- Pesquisa acadêmica sobre **teste de mutação** como medida de qualidade de conjuntos de teste (o experimento da Parte 2 da atividade, feito por ferramenta) e sobre a relação empírica entre **cobertura e detecção de defeitos**. Verifique bases como ACM Digital Library e IEEE Xplore; confirme autoria e ano antes de citar.
- Pesquisa sobre ***test smells*** e seu efeito na manutenção de testes, e sobre **testes intermitentes** (*flaky tests*) em projetos de grande porte.
