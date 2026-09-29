# Cenário 3 — Controle de acesso: onde herança continua sendo a escolha certa

## O problema de negócio

Um sistema de controle de acesso corporativo precisa distinguir funcionários próprios de terceirizados (regras de vínculo diferentes), reconhecer gerentes com autonomia para aprovar despesas, e garantir que todo acesso de terceirizados seja auditável por política de compliance.

## Por que herança é apropriada aqui — ao contrário do Cenário 1

`Usuario` → `Funcionario` → `GerenteDeDepartamento` é uma hierarquia de **especialização real**:

- todo `Funcionario` **é** um `Usuario` e pode ser tratado como tal em qualquer lugar do sistema, sem exceção nem comportamento inesperado (Liskov satisfeito);
- as três classes compartilham **estado** genuíno (`login`, `nome`, `ativo`) e **comportamento** genuíno (`bloquear()`, `reativar()`) — não há aqui um método herdado que precise ser "desativado" como no Cenário 1;
- a hierarquia tem exatamente os níveis que o domínio realmente distingue (identidade → vínculo empregatício → cargo de gestão), sem inventar uma subclasse para cada variação possível.

## Onde a interface entra: papéis que não respeitam a árvore de herança

`Auditavel` e `PodeAprovarDespesa` não são propriedades de "ser um certo tipo de `Usuario`" — são papéis que a organização atribui de forma independente da posição na hierarquia:

- `Terceirizado implements Auditavel`, mas nem `Funcionario` nem `GerenteDeDepartamento` implementam — a política de compliance da empresa exige rastro de auditoria apenas para acesso externo, não para funcionários próprios;
- `GerenteDeDepartamento implements PodeAprovarDespesa`, mas isso não veio de mais um nível de `extends` — veio de uma interface adicionada à classe que já existia na hierarquia por outro motivo.

Se amanhã a empresa decidir que um `Terceirizado` em função de consultoria sênior também pode aprovar despesas, isso se resolve adicionando `implements PodeAprovarDespesa` a `Terceirizado` — sem tocar em `Usuario`, `Funcionario` ou `GerenteDeDepartamento`, e sem inventar uma superclasse comum artificial só para hospedar essa capacidade.

## Comparação direta com o Cenário 1

| | Cenário 1 (pagamentos) | Cenário 3 (controle de acesso) |
|---|---|---|
| A hierarquia modela | Uma capacidade opcional (captura em duas etapas) disfarçada de especialização | Uma especialização real, com estado e comportamento genuinamente compartilhados |
| Sintoma de alerta | Método herdado que lança exceção em algumas subclasses | Ausência de qualquer método "desativado" — todas as subclasses usam tudo o que herdam |
| Papel da interface | Substitui inteiramente a hierarquia problemática | Complementa uma hierarquia que já está correta, para capacidades transversais |

A pergunta que resume os dois cenários, para discussão em aula: **antes de escrever `extends`, todo objeto da subclasse consegue substituir um objeto da superclasse em qualquer contexto, sem exceções, sem métodos que "não se aplicam"? Se a resposta for não em algum caso, a modelagem correta provavelmente envolve uma interface, não mais um nível de herança.**
