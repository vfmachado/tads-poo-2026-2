# Fundamentação teórica — Aula 06

## 1. O que uma interface realmente declara

Uma **interface** em Java declara um **contrato comportamental**: um conjunto de mensagens que um objeto promete responder, sem dizer nada sobre *como* ele vai responder, nem sobre *que estado* ele vai manter para isso.

```java
public interface PoliticaDeInadimplencia {
    boolean deveSuspender(int faturasEmAberto);
    boolean deveCancelar(int avisosEmitidos);
}
```

Note o que **não** existe aqui: nenhum atributo, nenhum construtor, nenhuma implementação (até a Aula 06, ignorando por ora os métodos `default`, tratados na seção 4). Uma interface não tem estado próprio — cada classe que a implementa é livre para guardar o estado que precisar, do jeito que precisar, desde que cumpra o contrato. Isso é a diferença mais fundamental entre interface e herança de implementação (retomada em detalhe na seção 6): **herança compartilha estado e código; interface compartilha apenas o formato do contrato.**

## 2. O problema motivador: variar uma regra sem tocar em `Contrato`

Releia `Contrato.registrarFaturaEmAberto()` e `Contrato.registrarAvisoInadimplencia()` (Aula 03/04, [`Aula03/03-exemplo-completo/depois/Contrato.java`](../Aula03/03-exemplo-completo/depois/Contrato.java)). A regra de inadimplência hoje está **fixa** dentro de `Contrato`, em duas constantes:

```java
private static final int LIMITE_FATURAS_EM_ABERTO = 2;
private static final int LIMITE_AVISOS_INADIMPLENCIA = 3;
```

Cenário real do ContratoPro: a operação comercial quer que contratos **corporativos** tenham uma tolerância maior (5 faturas em aberto, 5 avisos) e que contratos de **plano de teste** sejam bem mais rígidos (suspende já na primeira fatura em aberto). `Contrato`, porém, é a mesma classe para os três casos — ela representa a identidade e o ciclo de vida do vínculo contratual, não uma política comercial de cobrança.

Duas saídas ruins, já descartadas nas aulas anteriores:

- **Condicionais dentro de `Contrato`** (`if (tipoContrato == CORPORATIVO) ...`): a classe cresce uma responsabilidade que não é dela, e cada novo tipo de política exige editar `Contrato` de novo — o mesmo problema de coesão da Aula 03.
- **Uma subclasse de `Contrato` para cada política** (`ContratoCorporativo extends Contrato`, `ContratoDeTeste extends Contrato`): a política de inadimplência não é uma *especialização do que é um contrato* — é uma *regra de negócio plugável*. Usar herança aqui herdaria também identidade, ciclo de vida e todo o resto de `Contrato`, só para variar duas regras. É o mesmo risco de herança inadequada discutido na aula anterior sobre herança.

A saída adequada é **extrair a variação para uma interface** e **delegar** a ela a decisão — o mesmo princípio de delegação já praticado desde as aulas anteriores, agora aplicado a um colaborador cuja implementação concreta pode variar por polimorfismo:

```java
public interface PoliticaDeInadimplencia {
    boolean deveSuspender(int faturasEmAberto);
    boolean deveCancelar(int avisosEmitidos);
}

public final class PoliticaPadrao implements PoliticaDeInadimplencia {
    public boolean deveSuspender(int faturasEmAberto) { return faturasEmAberto > 2; }
    public boolean deveCancelar(int avisosEmitidos)   { return avisosEmitidos >= 3; }
}

public final class PoliticaCorporativa implements PoliticaDeInadimplencia {
    public boolean deveSuspender(int faturasEmAberto) { return faturasEmAberto > 5; }
    public boolean deveCancelar(int avisosEmitidos)   { return avisosEmitidos >= 5; }
}

public final class PoliticaPlanoDeTeste implements PoliticaDeInadimplencia {
    public boolean deveSuspender(int faturasEmAberto) { return faturasEmAberto >= 1; }
    public boolean deveCancelar(int avisosEmitidos)   { return avisosEmitidos >= 1; }
}
```

`Contrato` passa a receber a política por composição (construtor) e delega a decisão, sem nunca saber qual implementação concreta está do outro lado:

```java
public class Contrato {
    private final PoliticaDeInadimplencia politicaDeInadimplencia;
    private int faturasEmAberto;
    private int avisosInadimplencia;

    public Contrato(/* ... */ PoliticaDeInadimplencia politicaDeInadimplencia) {
        // ...
        this.politicaDeInadimplencia = politicaDeInadimplencia;
    }

    public void registrarFaturaEmAberto() {
        exigirEstado(status != StatusContrato.CANCELADO && status != StatusContrato.ENCERRADO,
                "Contrato em status " + status + " não gera novas faturas.");
        this.faturasEmAberto++;
        if (politicaDeInadimplencia.deveSuspender(faturasEmAberto)) {
            this.status = StatusContrato.SUSPENSO;
        }
    }
}
```

`Contrato` não muda mais quando uma nova política surge — apenas uma nova classe é criada e injetada. É exatamente o **despacho dinâmico** (a decisão de *qual* código executar é tomada em tempo de execução, com base no tipo real do objeto recebido) que sustenta a programação orientada a interfaces, aprofundada com foco em eliminação de condicionais na aula sobre polimorfismo.

## 3. Múltiplas interfaces: um limite que a herança não tem

Uma classe Java só pode estender **uma** superclasse (`extends`), mas pode implementar **quantas interfaces forem necessárias** (`implements`). Isso importa porque um objeto de domínio frequentemente precisa cumprir vários contratos independentes, que não têm relação de generalização entre si:

```java
public interface Renovavel {
    boolean podeRenovar(LocalDate dataReferencia);
}

public final class Contrato implements Comparable<Contrato>, Renovavel {

    @Override
    public int compareTo(Contrato outro) {
        return this.dataInicio.compareTo(outro.dataInicio);
    }

    @Override
    public boolean podeRenovar(LocalDate dataReferencia) {
        return status == StatusContrato.ATIVO && !estaInadimplente();
    }
}
```

`Contrato` não é um tipo especializado de `Comparable`, nem de `Renovavel` — não faria sentido modelar nenhum dos dois com `extends`. Cada interface é apenas mais um papel que `Contrato` assume, ortogonal a tudo o que ele já é e ortogonal entre si (um `Contrato` pode ser comparável e renovável ao mesmo tempo, sem que um papel dependa do outro). Tentar obter esse mesmo efeito com herança exigiria uma única superclasse artificial que reunisse todos os papéis possíveis — uma hierarquia inventada apenas para contornar a limitação de herança simples, sinal de abstração prematura.

## 4. Métodos `default`: comportamento compartilhado sem estado compartilhado

Uma interface pode fornecer uma implementação padrão para um método, através de `default`:

```java
public interface PoliticaDeInadimplencia {
    boolean deveSuspender(int faturasEmAberto);
    boolean deveCancelar(int avisosEmitidos);

    default String descricaoDoLimite(int faturasEmAberto, int avisosEmitidos) {
        if (deveCancelar(avisosEmitidos)) return "Contrato deve ser cancelado.";
        if (deveSuspender(faturasEmAberto)) return "Contrato deve ser suspenso.";
        return "Contrato dentro dos limites de tolerância.";
    }
}
```

`descricaoDoLimite(...)` é reaproveitado por `PoliticaPadrao`, `PoliticaCorporativa` e `PoliticaPlanoDeTeste` sem que nenhuma delas precise implementá-lo — mas ele só pode ser escrito em termos dos **outros métodos do próprio contrato** (`deveSuspender`, `deveCancelar`), nunca em termos de um atributo, porque a interface não tem nenhum para acessar. Essa é a diferença que separa `default` de herança de implementação: um método `default` reaproveita **lógica**, não **estado** — se as três políticas precisassem compartilhar um contador interno, isso exigiria composição (uma classe abstrata auxiliar ou um objeto colaborador), não uma interface.

## 5. Interfaces funcionais: um caso particular, não a regra geral

Quando uma interface tem **exatamente um método abstrato**, ela pode ser implementada por uma expressão lambda, e é chamada de **interface funcional** (`@FunctionalInterface`, opcional mas recomendada como documentação de intenção):

```java
List<Contrato> contratosElegiveisParaRenovacao(List<Contrato> contratos, Predicate<Contrato> criterio) {
    return contratos.stream().filter(criterio).toList();
}

// uso:
contratosElegiveisParaRenovacao(contratos, c -> !c.estaInadimplente());
```

`PoliticaDeInadimplencia`, com dois métodos abstratos, **não** é uma interface funcional — e não deveria ser forçada a ser uma só para "parecer moderna". Interfaces funcionais são adequadas quando o contrato é, de fato, uma única operação (um critério, uma transformação, uma ação); forçar duas responsabilidades relacionadas em uma única interface funcional para caber em uma lambda é sacrificar clareza do contrato por estética de código.

## 6. Quadro comparativo: interface × herança

A tabela a seguir retoma o que foi visto na aula anterior sobre herança e contrasta diretamente com interface, ponto a ponto.

| Aspecto | Herança (`extends`) | Interface (`implements`) |
|---|---|---|
| Relação modelada | Especialização ("é-um", substituição de tipo — Liskov) | Contrato comportamental ("pode fazer", papel assumido pelo objeto) |
| Estado compartilhado | Sim — atributos da superclasse são herdados e ocupam a instância da subclasse | Não — uma interface não declara atributos de instância |
| Código compartilhado | Sim — métodos concretos da superclasse são reaproveitados diretamente | Parcial — apenas via métodos `default`, e só em termos de outros métodos do contrato |
| Multiplicidade | Uma única superclasse por classe (herança simples em Java) | Uma classe pode implementar várias interfaces sem restrição |
| Acoplamento gerado | Forte — a subclasse depende da implementação interna da superclasse (problema da classe-base frágil) | Fraco — o cliente depende apenas da assinatura do contrato, não de nenhuma implementação |
| Construtores | A subclasse participa da cadeia de construtores da superclasse (`super(...)`) | Não existem construtores de interface; cada classe implementadora se constrói de forma independente |
| Impacto de evolução | Alterar um método concreto da superclasse pode alterar o comportamento de todas as subclasses, mesmo sem querer | Adicionar um método abstrato novo à interface obriga todas as implementações a tratá-lo (ou exige um `default` de transição) |
| Quando é a escolha adequada | Quando existe uma hierarquia real de especialização, e toda subclasse pode substituir a superclasse sem violar o comportamento esperado (LSP) | Quando o que varia é *o que um objeto faz*, não *o que ele é* — especialmente entre tipos sem parentesco natural, ou quando múltiplos papéis independentes precisam coexistir na mesma classe |
| Uso combinado | — | Uma classe pode estender uma superclasse **e** implementar várias interfaces ao mesmo tempo; os dois mecanismos não são mutuamente exclusivos |

O critério prático para decidir não é "interface é sempre melhor que herança" (isso seria trocar um dogma por outro) — é perguntar: **as implementações alternativas são, de fato, tipos substituíveis de uma mesma coisa, ou são políticas/estratégias diferentes para o mesmo papel?** No exemplo da seção 2, `PoliticaPadrao`, `PoliticaCorporativa` e `PoliticaPlanoDeTeste` não são "tipos de política que um cliente possa confundir uns com os outros esperando o mesmo comportamento numérico" — são estratégias deliberadamente diferentes atrás do mesmo contrato, o que é exatamente o caso de uso que motiva uma interface em vez de uma hierarquia de classes.

## 7. Programação orientada a interfaces

Um efeito prático de modelar com interface: código cliente deveria depender do **tipo da interface**, não do tipo concreto:

```java
// Acoplado a uma implementação concreta — cada nova política exige mudar esta assinatura
void processar(PoliticaPadrao politica) { /* ... */ }

// Depende apenas do contrato — qualquer implementação presente ou futura funciona
void processar(PoliticaDeInadimplencia politica) { /* ... */ }
```

Essa prática — declarar variáveis, parâmetros e atributos pelo tipo da interface, reservando o tipo concreto apenas para o ponto de construção (`new PoliticaCorporativa()`) — é a base do princípio da Inversão de Dependência, retomado com profundidade na aula sobre SOLID: módulos de alto nível (`Contrato`) dependem de uma abstração (`PoliticaDeInadimplencia`), não de detalhes concretos (`PoliticaCorporativa`).
