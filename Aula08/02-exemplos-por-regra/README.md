# Exemplos mínimos por regra — Aula 08

Um exemplo **deliberadamente pequeno** para cada regra de Object Calisthenics. Cada subpasta tem `Antes.java` (violação) e `Depois.java` (regra aplicada), ambos executáveis de forma independente. Para o exemplo integrado, com as nove regras juntas, veja [../03-exemplo-completo/](../03-exemplo-completo/).

| Pasta | Regra | Domínio | O que observar |
|---|---|---|---|
| [regra1-um-nivel-de-indentacao](regra1-um-nivel-de-indentacao/) | R1 | Logística | Laço + laço + `if` viram três métodos nomeados |
| [regra2-sem-else](regra2-sem-else/) | R2 | Assinaturas | Cadeia `else if` vira cláusulas de guarda |
| [regra3-encapsular-primitivos](regra3-encapsular-primitivos/) | R3 | Saúde | `int` solto vira `Duracao` que valida no construtor |
| [regra4-colecao-de-primeira-classe](regra4-colecao-de-primeira-classe/) | R4 | Acadêmico | Lista exposta vira `Matriculas` com a regra do limite |
| [regra5-um-ponto-por-linha](regra5-um-ponto-por-linha/) | R5 | E-commerce | A cadeia sai do chamador; cada objeto fala só com o vizinho |
| [regra6-sem-abreviacoes](regra6-sem-abreviacoes/) | R6 | Financeiro | `calcTx(vl, dt)` vira `calcularJurosDeAtraso(valorEmReais, diasDeAtraso)` |
| [regra7-entidades-pequenas](regra7-entidades-pequenas/) | R7 | Contratos | A formatação sai de `Contrato` (outra razão para mudar) |
| [regra8-poucas-variaveis-de-instancia](regra8-poucas-variaveis-de-instancia/) | R8 | Reservas | Dois *data clumps* viram `Periodo` e `Quarto` |
| [regra9-sem-getters-e-setters](regra9-sem-getters-e-setters/) | R9 | Financeiro | `getSaldo/setSaldo` viram `sacar(valor)` que protege a invariante |

## Como executar

```bash
cd regra1-um-nivel-de-indentacao
javac Antes.java Depois.java
java Antes
java Depois
```

## Uso sugerido em aula

1. Projetar `Antes.java` e perguntar: **qual problema isto causa, e de que tipo é (sintaxe, implementação, modelagem)?**
2. Pedir que os estudantes proponham o `Depois` antes de abrir o arquivo.
3. Fechar cada regra com a pergunta: **em que situação eu NÃO aplicaria isto?** (A resposta está resumida em [../01-fundamentacao-teorica.md](../01-fundamentacao-teorica.md), seção 3.)

> Os exemplos são simplificados de propósito: usam `double` para dinheiro e omitem tipos de domínio em vários pontos (comentados nos arquivos). Isso é uma limitação didática, não uma recomendação — o tratamento completo está no exemplo integrado.
