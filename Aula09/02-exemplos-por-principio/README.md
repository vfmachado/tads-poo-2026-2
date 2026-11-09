# Exemplos mínimos por princípio — Aula 09

Um exemplo **deliberadamente pequeno** para cada princípio do SOLID. Cada subpasta tem `Antes.java` (violação) e `Depois.java` (princípio aplicado), ambos executáveis de forma independente. Para o exemplo integrado, com os cinco princípios juntos, veja [../03-exemplo-completo/](../03-exemplo-completo/).

| Pasta | Princípio | Domínio | O que observar |
|---|---|---|---|
| [srp-responsabilidade-unica](srp-responsabilidade-unica/) | **S** | Vendas | Um relatório que calcula, formata e grava vira três classes, cada uma com **um** motivo para mudar |
| [ocp-aberto-fechado](ocp-aberto-fechado/) | **O** | Fidelidade | A cadeia `if/else if` por categoria vira `RegraDeDesconto`; a categoria `ESTUDANTE` entra **sem editar nada** |
| [lsp-substituicao-de-liskov](lsp-substituicao-de-liskov/) | **L** | Pagamentos | `PagamentoEmDinheiro.estornar` lançava exceção; "estornar" vira um tipo próprio (`Estornavel`) e o erro vai da execução para a compilação |
| [isp-segregacao-de-interfaces](isp-segregacao-de-interfaces/) | **I** | Cardápio | A interface de leitura **e** edição se divide em `LeitorDeCardapio` e `EditorDeCardapio`; o site nunca promete editar |
| [dip-inversao-de-dependencia](dip-inversao-de-dependencia/) | **D** | Cadastro | O `new EnviadorDeEmail()` sai da regra de negócio; o teste usa um `NotificadorFalso` de 5 linhas |

## Como executar

```bash
cd srp-responsabilidade-unica
javac Antes.java Depois.java
java Antes
java Depois
```

Para rodar todos de uma vez (a partir desta pasta):

```bash
for d in */; do (cd "$d" && mkdir -p out && javac -d out Antes.java Depois.java && echo "--- $d" && java -cp out Antes && echo ".. depois" && java -cp out Depois); done
```

## Uso sugerido em aula

1. Projetar `Antes.java` e perguntar: **qual é a dor? Que mudança de requisito faria isto quebrar?** (Se ninguém consegue descrever a dor, a violação é só estética.)
2. Pedir que os estudantes proponham o `Depois` antes de abrir o arquivo.
3. Fechar cada princípio com: **em que situação eu NÃO aplicaria isto?** (Respostas resumidas em [../01-fundamentacao-teorica.md](../01-fundamentacao-teorica.md), seção 3, itens 6, 7 e 9 de cada princípio.)

> Os exemplos são simplificados de propósito: usam `double` para dinheiro e `System.out` no lugar de e-mail, arquivo ou SMS de verdade. Isso é uma limitação didática, não uma recomendação — o tratamento mais cuidadoso (com `BigDecimal` e fronteiras injetadas) está no exemplo integrado.
