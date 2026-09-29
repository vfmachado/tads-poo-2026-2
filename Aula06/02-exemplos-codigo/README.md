# Exemplos de código — Aula 06

Três cenários independentes, em domínios diferentes do ContratoPro usado em [01-fundamentacao-teorica.md](../01-fundamentacao-teorica.md), para evitar que a turma associe "interface" a um único exemplo memorizado.

- [`01-pagamentos-heranca-vs-interface/`](01-pagamentos-heranca-vs-interface/) — o mesmo problema (meios de pagamento de um e-commerce) resolvido primeiro com herança (`heranca-problematica/`) e depois com interface (`interface-adequada/`). Mostra concretamente uma violação de LSP nascendo de um método que não se aplica a todas as subclasses, e como uma interface adicional a resolve sem lançar exceção em tempo de execução.
- [`02-seguros-estrategia-premio/`](02-seguros-estrategia-premio/) — cálculo de prêmio de seguro auto como estratégia plugável (`CalculadoraDePremio`). Caso de uso típico de interface como ponto de variação de **algoritmo**, sem nenhuma hierarquia de tipos envolvida.
- [`03-controle-acesso-heranca-adequada-mais-interfaces/`](03-controle-acesso-heranca-adequada-mais-interfaces/) — o contraponto do cenário 1: uma hierarquia de `Usuario` onde herança **é** a escolha correta (especialização real, com estado e comportamento compartilhados), combinada com interfaces para papéis transversais (`Auditavel`, `PodeAprovarDespesa`) que não seguem os limites da hierarquia.

Nenhum dos três cenários depende dos outros — cada pasta compila isoladamente (assumindo o pacote declarado no topo de cada arquivo) e pode ser usada separadamente em aula.

## Como usar cada cenário em aula

| Cenário | Pergunta central que ele responde |
|---|---|
| 1 — Pagamentos | O que acontece quando forço, via herança, um método que só faz sentido para algumas subclasses? |
| 2 — Seguros | Como variar um algoritmo de cálculo sem criar nenhuma hierarquia de classes? |
| 3 — Controle de acesso | Quando herança continua sendo a escolha certa, e como combiná-la com interfaces para papéis que não respeitam a árvore de especialização? |
