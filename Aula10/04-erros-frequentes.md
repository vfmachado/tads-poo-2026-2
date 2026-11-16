# Erros e equívocos frequentes — Aula 10

## Conceituais

- **Achar que teste serve para provar que o código está certo.** Teste detecta a violação de uma afirmação específica. Todos passando significa "nada do que foi afirmado quebrou", não "não há erros".
- **Confundir JUnit com teste de unidade.** JUnit é a ferramenta. O que torna um teste "de unidade" é ser pequeno, rápido e isolado.
- **Tratar cobertura como medida de qualidade.** Cobertura diz quais linhas foram *executadas*. Um teste sem asserção executa tudo e não verifica nada.
- **"Depois eu escrevo os testes."** O teste escrito depois confirma o que o código já faz, inclusive os erros. E o código escrito sem pensar em teste costuma ser difícil de testar.
- **Considerar o teste código de segunda classe.** Teste com nome ruim, duplicação e números mágicos também vira dívida. Ele será lido quando falhar — em geral por outra pessoa, com pressa.
- **Achar que a dificuldade de testar é culpa do JUnit.** Quase sempre é informação sobre o design (seção 7 da fundamentação).
- **Testar métodos em vez de comportamentos.** "Um teste por método" deixa regras sem teste (um método com três regras) e gera testes vazios (um *accessor*).
- **Concluir que o `Main` das aulas anteriores era inútil.** Era um teste de caracterização legítimo, adequado à pergunta "a refatoração mudou algo?". O que muda é a pergunta.

## De escrita do teste

- **Teste sem asserção.** Chama o método, imprime o resultado e termina. Passa sempre.
- **Esperado e obtido invertidos:** `assertEquals(frete, new BigDecimal("8.00"))`. O teste funciona, mas a mensagem de falha diz o contrário do que aconteceu.
- **Recalcular o esperado com a fórmula da produção.** Se a fórmula estiver errada, teste e código erram juntos.
- **`assertTrue(a.equals(b))` ou `assertTrue(a == b)`.** Ao falhar, informa apenas `expected: <true> but was: <false>`. Use `assertEquals`.
- **`assertEquals` com `double` sem tolerância.** Falha por diferenças na décima sexta casa.
- **`assertEquals` com `BigDecimal` de escalas diferentes.** `8.0` e `8.00` não são `equals`. Não "conserte" trocando o esperado sem entender: decida se a escala faz parte do contrato.
- **Testar exceção com `try/catch` e esquecer o `fail()`.** Se nada for lançado, o teste passa. Use `assertThrows`.
- **Nome que não diz nada:** `test1`, `testFrete`, `deveFuncionar`. O nome é a primeira linha do relatório de falha.
- **Várias ações e várias regras em um teste só.** Falha na primeira e esconde o resto; e ninguém sabe qual regra quebrou.
- **Lógica dentro do teste** (`if`, `for`, cálculo). Teste com lógica precisa de teste. Dados diferentes pedem teste parametrizado.
- **Só o caminho feliz.** Nenhum teste de limite, de recusa, de entrada inválida.
- **Só valores "redondos"** (10, 100, 1000), longe de qualquer fronteira e de qualquer arredondamento.
- **Depois de um erro, não verificar o estado.** A exceção foi lançada — mas o estoque já tinha sido descontado?

## De isolamento e ciclo de vida

- **Um teste depende do que outro deixou** (campo `static`, arquivo, ordem de execução). Passam juntos, falham sozinhos — ou o contrário.
- **Usar `@BeforeAll` para montar objetos mutáveis.** O estado é compartilhado entre todos os testes; quase sempre o que se queria era `@BeforeEach`.
- **`@BeforeEach` gigante** que monta o cenário de todos os testes. Para entender um teste, é preciso ler outro método inteiro. Monte no `@BeforeEach` só o que é comum a **todos**.
- **Teste que depende da hora, da data ou do idioma da máquina.** Passa hoje e falha no fim do mês. Injete o relógio.
- **Teste que imprime no console.** Polui o relatório e sugere que alguém deveria ler a saída.
- **`@Disabled` sem motivo e sem prazo.** Teste desligado "temporariamente" é teste apagado, com o inconveniente de parecer que existe.
- **Apagar ou afrouxar o teste que falhou** para "passar". Primeiro decida quem está errado: o código ou a afirmação.

## De dublês

- **Colocar regra de negócio no dublê.** O teste passa por causa do dublê, e a produção continua errada.
- **Dublar o que não precisa** (um `record`, uma coleção em memória). O teste passa a verificar o dublê.
- **Verificar todas as chamadas internas** em vez do resultado. Qualquer refatoração quebra o teste, mesmo sem mudar comportamento.
- **Não conseguir criar o dublê e desistir.** Se a dependência é criada com `new` dentro da classe, o problema é anterior ao teste (DIP, Aula 09).
- **Deixar dublês em `src/main/java`.** Dublê é código de teste; em produção, vira uma implementação que alguém um dia vai ligar por engano.

## De ambiente

- **Classe de teste em `src/main/java`** (ou o contrário). O JUnit tem escopo `test`: fora de `src/test/java`, os `import` não compilam.
- **Abrir no IDE a pasta errada.** O projeto é a pasta que contém o `pom.xml`, não a raiz do repositório.
- **Importar `org.junit.Test` (JUnit 4) em vez de `org.junit.jupiter.api.Test`.** Compila se as duas versões estiverem no *classpath*, e o teste simplesmente não é encontrado.
- **Método de teste `private` ou `static`, ou com valor de retorno.** O JUnit não o executa como teste.
- **Concluir "passou" sem olhar quantos testes rodaram.** Zero testes executados também termina sem falhas.
