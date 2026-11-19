# Trabalho III — Biblioteca do campus: refatorar e provar com testes

> Disciplina de Programação Orientada a Objetos. Trabalho **individual**, cobrindo **Object Calisthenics** ([Aula 08](../../Aula08/)), **SOLID** ([Aula 09](../../Aula09/)) e **testes de unidade com JUnit** ([Aula 10](../../Aula10/)). É um trabalho **enxuto**: o código de partida tem ~300 linhas e o que se avalia é a qualidade das decisões, não o volume.

## 1. Objetivo

Você recebe, em [`codigo-inicial/`](codigo-inicial/), um sistema de empréstimos de uma biblioteca que **funciona**, mas que acumula praticamente todos os problemas discutidos nas Aulas 08 e 09. Seu trabalho é:

1. **Refatorar** o código aplicando Object Calisthenics e SOLID **com critério** (como a versão `equilibrado/` das aulas, não "à risca a qualquer custo");
2. **Preservar o comportamento**: o `Main` deve produzir **exatamente** a saída de [`saida-esperada.txt`](codigo-inicial/saida-esperada.txt);
3. **Provar** que as regras de negócio valem com **testes JUnit**, incluindo um teste que só é possível depois da inversão de dependência.

## 2. Código de partida

Projeto Maven (Java 21, JUnit 6.1.3 — mesmo `pom.xml` da Aula 10). Abra a pasta `codigo-inicial` no IDE ou rode `mvn test`.

| Arquivo | O que faz |
|---|---|
| `Biblioteca.java` | Cadastra usuários e livros, empresta, devolve, calcula multa, recebe pagamento, notifica e imprime relatório. Tudo. |
| `Usuario`, `Livro`, `Emprestimo` | Sacos de dados com getters e setters |
| `Notificador`, `NotificadorPorEmail` | Interface com e-mail, SMS e WhatsApp; a implementação só sabe enviar e-mail |
| `Main.java` | Roteiro fixo que exercita todas as regras; sua saída é o teste de caracterização |

## 3. Regras de negócio

| Tipo de usuário | Limite de livros simultâneos | Prazo (dias) | Multa por dia de atraso |
|---|---|---|---|
| Aluno | 3 | 7 | R$ 1,00 |
| Professor | 5 | 14 | R$ 0,50 |
| Visitante | 1 | 3 | R$ 2,00 |

1. Um empréstimo é **recusado** se: o usuário ou o livro não existe; o livro já está emprestado; o usuário tem **qualquer** débito; o usuário já está no limite do seu tipo.
2. A data prevista de devolução é a data do empréstimo + prazo do tipo.
3. Devolver **na data prevista** não gera multa; cada dia depois dela gera a multa diária do tipo.
4. A multa de uma devolução tem **teto de R$ 20,00**.
5. Toda devolução **com multa** gera um aviso por e-mail ao usuário; devolução sem multa **não** gera aviso.
6. O débito acumula entre devoluções e só diminui por pagamento.

**Regras que o código de partida NÃO garante** (sua versão deve garantir, e os testes devem provar):

7. Não é permitido pagar valor **zero, negativo ou maior que o débito** atual.
8. Não é permitido devolver em data **anterior** à do empréstimo.

## 4. O que fazer

### Parte A — Refatoração (Aulas 08 e 09)

Reescreva o código em `src/main/java`. Exigências mínimas:

- **Dinheiro não é `double`.** Use `BigDecimal` encapsulado em um tipo do domínio.
- **Nenhuma cadeia `if/else` sobre o tipo de usuário.** As três políticas (limite, prazo, multa) devem morar juntas, de forma que um tipo novo não exija editar `emprestar` nem `devolver`.
- **`Biblioteca` deixa de criar seu notificador** e passa a recebê-lo pelo construtor. O `Main` é a raiz de composição.
- **A interface de notificação não pode obrigar ninguém a implementar o que não sabe fazer.**
- **Sem getters/setters de dados** onde uma operação de domínio resolve (`usuario.podeEmprestar()`, `emprestimo.multaEm(data)`...). Acessores para *exibir* no relatório são aceitáveis se justificados.
- **Nada de abreviações** (`usrs`, `lvs`, `mlt`, `tx`, `atr`...) e no máximo um nível de indentação por método, salvo exceção justificada.

Você **pode** flexibilizar qualquer regra das Aulas 08 e 09, desde que registre a decisão no `DECISOES.md` (Parte C).

**Critério de aceite:** a saída do `Main` é idêntica a `saida-esperada.txt`:

```bash
java -cp target/classes biblioteca.Main > minha-saida.txt
diff saida-esperada.txt minha-saida.txt && echo "comportamento preservado"
```

(No Windows, `fc saida-esperada.txt minha-saida.txt`.)

### Parte B — Testes (Aula 10)

Em `src/test/java`, **no mínimo 12 testes**, com nomes que descrevem o comportamento (`@DisplayName` ou nome de método). Devem estar presentes:

| # | O que testar | Recurso da Aula 10 |
|---|---|---|
| 1 | Multa nos **valores-limite**: devolver na data prevista, 1 dia depois, e no ponto em que bate o teto (para cada tipo) | `@ParameterizedTest` + `@CsvSource` |
| 2 | Cada motivo de recusa de empréstimo (regra 1) | `assertThrows` |
| 3 | Regras 7 e 8, **e** que o objeto continua válido depois da recusa (o débito não mudou) | `assertThrows` + `assertEquals` |
| 4 | Devolução com multa **envia** exatamente um aviso, ao e-mail certo; sem multa **não envia nada** | Dublê espião escrito à mão (sem Mockito) |
| 5 | Igualdade e aritmética do seu tipo de dinheiro (ex.: `0,50 × 3 = 1,50`; `1,5` igual a `1,50`?) | `assertEquals` com o tipo próprio |

Os testes **não podem** imprimir nada no console nem depender da data de hoje.

### Parte C — `DECISOES.md` (máximo 1 página)

Na raiz do seu projeto, responda:

1. **Tabela de decisões** com 5 linhas: *problema no código original* → *regra/princípio* → *o que você fez*. Pelo menos uma linha de cada aula (08 e 09).
2. **Uma regra que você flexibilizou** de propósito, e por quê.
3. **Experimento de mudança:** implemente o tipo **Servidor** (limite 4, prazo 10 dias, multa R$ 0,75/dia), sem alterar o `Main`. Quantos arquivos você **criou** e quantos **editou**? Quantos testes novos foram necessários e quais já existentes continuaram passando sem alteração?
4. **Quebrar de propósito:** introduza, um por vez, os quatro erros abaixo; para cada um, anote **quais testes falharam** e **se o `Main` teria percebido**. Desfaça antes do próximo.
   - trocar "depois da data prevista" por "a partir da data prevista" (multa no dia exato);
   - remover o teto de R$ 20,00;
   - enviar o aviso também quando a multa é zero;
   - aceitar pagamento maior que o débito.

## 5. Entrega

- Repositório (ou `.zip`) com o projeto Maven completo: `src/main`, `src/test`, `pom.xml`, `DECISOES.md`.
- `mvn test` deve passar sem nenhum teste ignorado (`@Disabled`) ou comentado.
- Prazo: **02/12/2026**.

## 6. Avaliação (10 pontos)

| Critério | Peso | O que se espera |
|---|---|---|
| Comportamento preservado | **eliminatório** | Saída do `Main` idêntica; sem ela, o trabalho é devolvido para correção |
| Refatoração — Object Calisthenics | 2,5 | Tipos do domínio no lugar de primitivos, coleções encapsuladas, métodos curtos, nomes claros, sem saco de getters/setters |
| Refatoração — SOLID | 2,5 | Política por tipo extensível sem `if`; notificador injetado e com contrato honesto; `Biblioteca` sem formatar texto e calcular multa ao mesmo tempo |
| Testes | 3,0 | Os 5 itens da tabela da Parte B; casos escolhidos por partição/limite; dublê correto; testes independentes entre si |
| `DECISOES.md` | 2,0 | Justificativas concretas (não "porque a regra manda"); experimento de mudança e de quebra com números reais |

**Descontos comuns:** interface criada só para "cumprir DIP" sem segunda implementação nem uso em teste; classe `Taxa`/`Multa`/`Debito` que não acrescenta nenhuma operação a `Dinheiro`; teste sem asserção; teste que passa mesmo com o código quebrado.
