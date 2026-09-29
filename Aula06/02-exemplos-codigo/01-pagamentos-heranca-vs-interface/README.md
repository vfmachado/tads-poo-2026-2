# Cenário 1 — Meios de pagamento: herança problemática × interface adequada

## O problema de negócio

Um checkout de e-commerce aceita cartão de crédito, boleto e Pix. Cartão de crédito opera em **duas etapas** (pré-autorização e captura, para lidar com estorno de itens fora de estoque antes da cobrança final); boleto e Pix são **liquidados de uma só vez**, sem segunda etapa.

## Versão com herança ([`heranca-problematica/`](heranca-problematica/))

`MeioDePagamento` é uma classe abstrata com um método `capturar()` que, por padrão, lança `UnsupportedOperationException`. `CartaoDeCredito` sobrescreve esse método com a implementação real; `Boleto` e `Pix` simplesmente **herdam** a versão que lança exceção, porque não têm nada sensato para colocar ali.

Isso é uma violação do Princípio da Substituição de Liskov (retomado com profundidade na aula sobre LSP): um `MeioDePagamento` deveria poder ser usado no lugar de outro sem quebrar expectativas — mas qualquer rotina genérica que trate a lista de forma polimórfica e chame `capturar()` (ver [`Main.java`](heranca-problematica/Main.java), rotina financeira fictícia) quebra em tempo de execução para 2 dos 3 tipos, mesmo todos sendo, formalmente, `MeioDePagamento`.

## Versão com interface ([`interface-adequada/`](interface-adequada/))

`MeioDePagamento` passa a declarar **apenas** o que é universal: `autorizar(valor)`. A capacidade de captura em duas etapas vira uma interface separada, `CapturavelEmDuasEtapas`, implementada **somente** por `CartaoDeCredito`.

Código cliente que precisa capturar verifica a capacidade explicitamente (`pagamento instanceof CapturavelEmDuasEtapas capturavel`) antes de chamar o método — sem exceção em tempo de execução, sem método herdado sem sentido, sem necessidade de "adivinhar" quais subtipos suportam a operação.

## O que essa comparação demonstra

| | Herança | Interface |
|---|---|---|
| Onde o erro aparece | Em tempo de execução, ao chamar `capturar()` em `Boleto`/`Pix` | Em tempo de compilação/leitura — `Boleto`/`Pix` nunca implementam `CapturavelEmDuasEtapas` |
| Causa raiz | Um método definido para "todos" na base, mas que só se aplica a alguns | Cada capacidade é seu próprio contrato, implementado só onde faz sentido |
| Sintoma típico | Métodos com corpo `throw new UnsupportedOperationException(...)` em subclasses | Ausência natural de um `implements` — nada a "desativar" |

Fica evidente aqui um sinal prático de que uma hierarquia está sendo usada para modelar uma **capacidade opcional**, não uma **especialização real**: sempre que uma subclasse precisa sobrescrever um método herdado só para lançar uma exceção informando que "isso não se aplica a mim", é sinal de que esse método não deveria estar na superclasse — deveria ser uma interface separada, implementada apenas por quem de fato oferece a capacidade.
