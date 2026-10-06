# Exemplos de código — Aula 07 (Records)

Requer **Java 21** (os records em si exigem apenas Java 16; o `switch` com desconstrução em `Forma.java` exige 21).

- [`01-comparacao-sem-e-com-record/`](01-comparacao-sem-e-com-record/) — a mesma classe `Endereco` escrita sem record (`sem-record/`, pacote `aula07.semrecord`) e com record (`com-record/`, pacote `aula07.comrecord`). Os dois `Main` imprimem o mesmo resultado de igualdade e de uso em `HashSet`.
- [`02-casos-de-uso/`](02-casos-de-uso/) — cinco usos típicos: Value Object com invariante (`Intervalo`), retorno de múltiplos valores (`ResumoDeNotas`), records + `sealed` + `switch` (`Forma`), DTO com cópia defensiva (`PedidoDto`) e record aninhado em *stream* (`Main`).
- [`03-quando-nao-usar/`](03-quando-nao-usar/) — `ContaBancaria`, uma entidade com identidade e estado mutável: contraexemplo comentado de por que **não** usar record.

## Como compilar e executar

A partir desta pasta:

```bash
javac -d out 01-comparacao-sem-e-com-record/*/*.java 02-casos-de-uso/*.java 03-quando-nao-usar/*.java

java -cp out aula07.semrecord.Main
java -cp out aula07.comrecord.Main
java -cp out aula07.usos.Main
```

## Como usar em aula

| Cenário | Pergunta central |
|---|---|
| 1 — Comparação | Quanto código some, e quais bugs deixam de ser possíveis, ao trocar a classe manual por record? |
| 2 — Casos de uso | Em que situações do dia a dia um record é a ferramenta natural? |
| 3 — Quando não usar | Por que uma entidade com identidade e estado mutável não pode ser record? |
