# Lista 02 — construtores, getters e setters

**Instrução geral, válida para todos os exercícios:** para cada classe, decida — e justifique em um comentário — quais atributos realmente precisam de um método que altere seu valor depois da criação, e quais deveriam ser fixos (`final`) para sempre. Não crie um setter só porque "toda classe tem". Se um valor puder mudar, prefira um método com nome de intenção (ex.: `renomear(...)`) a um `setNome(...)` genérico, e valide a entrada do mesmo jeito que valida no construtor.

---

## Exercício 1 — `Produto` de um catálogo

Crie a classe `Produto` com `nome` (String), `preco` (BigDecimal) e `quantidadeEmEstoque` (int).

- O construtor deve rejeitar `nome` vazio/nulo, `preco` menor ou igual a zero, e `quantidadeEmEstoque` negativo.
- Forneça consultas para os três atributos.
- **Não** crie um `setQuantidadeEmEstoque(int)` genérico. Em vez disso, crie dois métodos de intenção: `registrarEntrada(int quantidade)` e `registrarSaida(int quantidade)`, cada um validando que a quantidade informada é positiva e que `registrarSaida` não deixe o estoque negativo.

## Exercício 2 — `Paciente` de uma clínica

Crie a classe `Paciente` com `nome` (String), `dataNascimento` (LocalDate) e `email` (String).

- O construtor deve rejeitar `nome` e `email` vazios/nulos, e `dataNascimento` no futuro.
- Forneça consultas para os três atributos.
- `nome` e `dataNascimento` nunca mudam depois do cadastro (torne-os `final`). `email` pode ser atualizado pelo próprio paciente: crie `atualizarEmail(String novoEmail)`, validando que não é vazio/nulo.

## Exercício 3 — `Agendamento` de consulta

Crie a classe `Agendamento` com `paciente` (referência a `Paciente`), `dataHora` (LocalDateTime) e um status interno (`enum StatusAgendamento { AGENDADO, CONFIRMADO, CANCELADO }`).

- O construtor deve rejeitar `paciente` nulo e `dataHora` no passado, e sempre iniciar como `AGENDADO`.
- Forneça uma consulta `status()`.
- **Não** crie `setStatus(...)`. Crie os comandos `confirmar()` (só permitido a partir de `AGENDADO`) e `cancelar()` (permitido a partir de `AGENDADO` ou `CONFIRMADO`, mas não de um agendamento já `CANCELADO`). Cada comando deve lançar `IllegalStateException` se chamado em um status que não permite a transição.

## Exercício 4 — `Avaliacao` de um aluno

Crie a classe `Avaliacao` com `aluno` (String), `disciplina` (String) e `nota` (double, de 0 a 10).

- O construtor deve rejeitar `aluno`/`disciplina` vazios e `nota` fora do intervalo `[0, 10]`.
- Forneça consultas para os três atributos.
- Discuta e decida: essa classe precisa de **algum** método que altere o estado depois de criada, ou uma avaliação já lançada deveria ser sempre imutável (e uma correção de nota gerar uma *nova* `Avaliacao`, mantendo a anterior como histórico)? Implemente a opção que você escolher e justifique em um comentário de uma linha.

## Exercício 5 — `Assinatura` de um plano de streaming

Crie a classe `Assinatura` com `planoContratado` (String), `dataInicio` (LocalDate) e um `boolean ativa`.

- O construtor deve rejeitar `planoContratado` vazio/nulo e `dataInicio` nula, e sempre iniciar `ativa = true`.
- Forneça uma consulta `estaAtiva()` (sem expor o `boolean` bruto por um getter genérico `isAtiva()` que qualquer um possa usar para inferir lógica externa — a diferença é sutil, discuta em grupo se `estaAtiva()` deveria ser a única forma de consultar o estado).
- **Não** crie `setAtiva(boolean)`. Crie os comandos `suspender()` (só se estiver ativa) e `reativar()` (só se estiver suspensa), cada um validando a transição.
