# Aula 01 – Introdução à Programação Orientada a Objetos (POO)


# 1. Por que Programação Orientada a Objetos?

Ao longo da evolução do software, tornou-se evidente que apenas organizar código em funções não era suficiente para lidar com sistemas complexos.

Sistemas reais envolvem:

* Muitas regras
* Diversas entidades
* Estados que mudam ao longo do tempo
* Interações entre diferentes partes

A POO surge como uma forma de:

* Organizar melhor o código
* Representar o mundo real de forma mais natural
* Reduzir acoplamento
* Aumentar manutenção e reuso
* Facilitar evolução do sistema

A principal mudança de mentalidade é:

> Em vez de pensar apenas em "passos de execução", passamos a pensar em **entidades que possuem responsabilidades**.

---

# 2. O Conceito Central: Abstração

## O que é Abstração?

Abstração é a capacidade de:

> Focar apenas nas características essenciais de algo e ignorar detalhes irrelevantes para o contexto atual.

Ela é o fundamento da POO.

---

## Exemplo simples

Imagine um sistema bancário.

Quando modelamos uma **Conta Bancária**, não nos importamos com:

* Cor do cartão
* Material do cartão
* Marca do caixa eletrônico
* Como o banco imprime extratos

O que realmente importa para o sistema?

* Número da conta
* Titular
* Saldo
* Regras de depósito e saque

Isso é abstração:
Selecionar apenas o que é relevante para o problema que estamos resolvendo.

---

## Abstração como ferramenta de modelagem

Abstrair significa responder:

* O que é essencial aqui?
* O que faz parte do problema?
* O que é apenas detalhe técnico?

Se você modelar demais, o sistema fica complexo.
Se modelar de menos, ele fica pobre e impreciso.

A boa abstração encontra o equilíbrio.

---

# 3. Classe e Objeto

## Classe

Uma classe é um **modelo conceitual** que define:

* Quais informações algo possui
* Quais ações ele pode realizar

É como um molde.

Exemplo conceitual:

Classe: `ContaBancaria`

* número
* titular
* saldo
* depositar()
* sacar()
* obterSaldo()

A classe define a estrutura e o comportamento possíveis.

---

## Objeto

Um objeto é uma **instância concreta de uma classe**.

Exemplo:

* Conta 001 – Titular: Maria – Saldo: 500
* Conta 002 – Titular: João – Saldo: 1200

Ambas seguem o modelo da classe `ContaBancaria`, mas possuem estados diferentes.

---

## Diferença essencial

| Classe        | Objeto                 |
| ------------- | ---------------------- |
| Modelo        | Instância concreta     |
| Define regras | Executa comportamentos |
| Abstrata      | Concreta               |

---

# 4. Estado e Comportamento

A base da POO é a união de:

* **Estado** → Dados internos
* **Comportamento** → Ações que manipulam esses dados

---

## Estado

O estado representa a condição atual do objeto.

Exemplo:
Uma conta bancária tem:

* saldo
* titular
* número

Se o saldo muda, o estado mudou.

---

## Comportamento

Comportamento são as operações que alteram ou consultam o estado.

Exemplo:

* depositar(valor)
* sacar(valor)
* obterSaldo()

Um objeto não é apenas um conjunto de dados.
Ele é uma **entidade que sabe agir sobre seus próprios dados**.

---

# 5. Atributos e Métodos

## Atributos

Representam o estado do objeto.

São as informações que caracterizam aquela entidade.

Exemplo:
Livro:

* título
* autor
* código
* disponível

---

## Métodos

São as ações que o objeto pode executar.

Exemplo:
Livro:

* emprestar()
* devolver()
* estaDisponivel()

Um método deve representar um comportamento coerente com o objeto.

Pergunta importante:

> Essa ação realmente pertence a essa classe?

Se a resposta for não, há problema de modelagem.

---

# 6. Encapsulamento (Foco Conceitual)

Encapsulamento significa:

> O objeto controla seu próprio estado.

Ou seja:

* O estado não deve ser alterado de qualquer maneira.
* Mudanças devem ocorrer por meio de comportamentos definidos.

Exemplo incorreto (conceitualmente):

* Alterar saldo diretamente sem regra.

Exemplo correto:

* Usar depositar()
* Usar sacar() com validação

Encapsulamento:

* Protege regras de negócio
* Reduz acoplamento
* Torna o sistema mais previsível

O ponto central aqui não é "getters e setters", mas sim:

> A ideia de que cada objeto é responsável por manter sua própria consistência.

---

# 7. Coesão

Coesão é a medida de quão bem uma classe representa uma única responsabilidade.

Uma classe coesa:

* Tem propósito claro
* Não mistura responsabilidades diferentes
* Não acumula comportamentos que pertencem a outras entidades

Exemplo ruim:
Classe `Sistema`

* calcula folha
* envia email
* gera relatório
* autentica usuário

Isso não é coeso.

Exemplo bom:

* Classe `Usuario`
* Classe `Relatorio`
* Classe `FolhaPagamento`

Cada uma com responsabilidade clara.

---

# 8. Comandos e Consultas

Uma distinção importante na modelagem:

## Comando

* Altera o estado
* Exemplo: depositar()

## Consulta

* Apenas retorna informação
* Não altera estado
* Exemplo: obterSaldo()

Separar claramente esses dois tipos de método:

* Torna APIs previsíveis
* Evita efeitos colaterais inesperados
* Facilita testes

---

# 9. Modelando Domínios Reais

POO não é apenas sintaxe.
É principalmente modelagem.

Exemplo: Sistema de Biblioteca

Entidades principais:

* Livro
* Usuario
* Emprestimo

Livro:

* título
* autor
* disponível
* emprestar()
* devolver()

Usuario:

* nome
* matrícula
* limiteDeEmprestimos
* podeEmprestar()

Emprestimo:

* livro
* usuario
* dataInicio
* dataDevolucao
* finalizar()

Perceba que:

* Cada classe representa algo do domínio
* Cada uma tem responsabilidade clara
* Métodos fazem sentido dentro do contexto

---

# 10. A Mentalidade Orientada a Objetos

Ao pensar em POO, pergunte:

1. Quais são as entidades do meu domínio?
2. Quais informações caracterizam cada entidade?
3. Quais ações pertencem naturalmente a cada uma?
4. Essa classe está assumindo responsabilidades demais?
5. Estou representando o problema ou apenas escrevendo código?

---

# 11. O Erro Comum do Iniciante

Confundir POO com:

* Criar classes apenas para organizar código
* Criar classes com apenas atributos e nenhum comportamento
* Usar objetos apenas como "estruturas de dados"

POO não é sobre:

* getters
* setters
* construtores

POO é sobre:

> Representar conceitos do mundo real através de entidades que possuem estado e comportamento.

---

# 12. Conclusão

Programação Orientada a Objetos é uma forma de estruturar sistemas baseada em:

* Abstração
* Entidades com responsabilidades
* Combinação de estado e comportamento
* Organização por domínio

O foco principal deve ser:

✔ Modelar corretamente
✔ Criar classes coesas
✔ Definir comportamentos coerentes
✔ Proteger regras de negócio

Se você entende abstração, você entende a base da POO.

---
