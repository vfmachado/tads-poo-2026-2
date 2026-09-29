# Cenário 2 — Seguros: cálculo de prêmio como estratégia plugável

## O problema de negócio

Uma seguradora de auto precisa calcular o prêmio anual de uma apólice a partir do perfil do condutor e do valor segurado. A regra comercial varia por perfil (condutor jovem, histórico de sinistros, padrão) e essas regras mudam com frequência — a área comercial testa e ajusta faixas de aliquota o tempo todo.

## Por que este cenário não tem hierarquia de classes nenhuma

Diferente do Cenário 1, aqui **não existem "tipos de apólice"** — existe uma única entidade `Apolice`, e o que varia é apenas o **algoritmo de cálculo** aplicado sobre os mesmos dados de entrada (`PerfilDoCondutor`, `valorSegurado`). Modelar isso com herança (`ApolicePadrao extends Apolice`, `ApoliceCondutorJovem extends Apolice`) forçaria a existência de várias classes de apólice só para variar uma fórmula — cada apólice deixaria de ser "uma apólice" e passaria a carregar, na própria identidade de classe, uma decisão de precificação que pode mudar a qualquer momento.

`CalculadoraDePremio` isola exatamente essa variação: `Apolice` recebe uma implementação (via `SeletorDeCalculadora`, na emissão) e delega o cálculo a ela, sem nunca precisar saber qual fórmula está por trás.

## Ponto de atenção: quem decide qual estratégia usar?

`Apolice` não decide qual `CalculadoraDePremio` usar — essa decisão é responsabilidade de `SeletorDeCalculadora`, uma classe separada. Isso evita reintroduzir, dentro de `Apolice`, o mesmo tipo de condicional (`if perfil.idade() < 25 ...`) que a interface deveria eliminar; a variação de regra fica concentrada em um único lugar, fácil de testar isoladamente de `Apolice`.

## Extensão proposta em aula

Peça à turma que adicione uma quarta estratégia (ex.: desconto para veículo com rastreador instalado, ou agravo para condutor com CNH há menos de 2 anos) e identifique **quantas classes existentes precisam ser alteradas** para isso funcionar. A resposta esperada — nenhuma, além de `SeletorDeCalculadora` — é uma instância concreta do Open-Closed Principle (retomado na aula sobre SOLID): o sistema é estendido por uma nova classe, não por modificação das existentes.
