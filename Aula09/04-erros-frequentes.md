# Erros e equívocos frequentes — Aula 09

## Conceituais

- **Tratar SOLID como checklist de conformidade.** São cinco *perguntas* sobre mudança. Código que "passa" nos cinco e ninguém consegue mudar com facilidade falhou; código com um `if` por tipo e nenhuma mudança à vista pode estar perfeitamente adequado.
- **Confundir DIP com injeção de dependência.** Injeção é o mecanismo (passar a dependência de fora). Inversão é a **direção** do código-fonte: o detalhe aponta para a abstração, que pertence à regra de negócio. Criar `interface Foo` ao lado de `class FooImpl` e continuar fazendo `new FooImpl()` na regra de negócio não inverte nada.
- **Confundir DIP com container de DI.** Spring, Guice e similares são ferramentas que *automatizam* a montagem. Dá para cumprir o DIP sem nenhuma delas (raiz de composição manual), e dá para usar a ferramenta violando o DIP.
- **Equiparar OCP a "nunca editar uma classe".** O OCP trata de escolher **pontos de extensão** para as variações que se espera; ninguém fecha um sistema para todas as mudanças. Corrigir um *bug* sempre edita código.
- **Achar que OCP = herança.** Em Java moderno, o OCP é cumprido com interfaces e composição; a herança de implementação é a forma mais frágil.
- **Interpretar SRP como "uma classe, um método"** ou "uma classe, uma coisa". A formulação é sobre **razões para mudar / atores**. Uma classe com dez métodos que mudam sempre pelo mesmo motivo cumpre o SRP.
- **Usar `instanceof` e achar que isso resolve LSP.** É o oposto: o `instanceof` no cliente é o **sintoma** de que a substituição falhou.
- **Considerar LSP só uma questão de assinaturas.** Compila ≠ substituível. Pré-condições, pós-condições e invariantes fazem parte do contrato.
- **Medir ISP pelo número de métodos.** O critério é a **coesão do ponto de vista do cliente**: se todo cliente sempre usa os três métodos juntos, a interface de três métodos está certa.
- **Dizer que SOLID é "o jeito certo" e Object Calisthenics "o exercício".** São de naturezas diferentes e se complementam (ver seção 7 da fundamentação).
- **Esquecer que os princípios redistribuem complexidade.** O sistema total costuma crescer; quem ganha e quem perde depende de qual mudança vem a seguir.
- **Achar que SOLID resolve consistência, concorrência ou desempenho.** Não trata de nenhum dos três (ver item 4 do gabarito de `03-exemplo-completo`).

## De implementação

- **SRP por extração mecânica** (`ServicoDePedidoParte1`, `ServicoDePedidoParte2`): dividiu o arquivo sem dividir o motivo de mudança.
- **SRP com classes que só repassam chamadas** (*Middle Man*): `ProcessadorDeFrete` que apenas chama `CalculoDeFrete`.
- **OCP com interface para uma decisão binária estável** (`interface AceitaDinheiro`): abstração prematura.
- **OCP com ponto de extensão no lugar errado:** a variação real era *outro parâmetro* e a interface foi criada para o que nunca mudou.
- **OCP com `switch` sobre `enum` espalhado por vários lugares:** o `enum` ganhou uma constante e cinco `switch` ficaram incompletos. Se há `switch` sobre a mesma constante em mais de um lugar, o comportamento deveria estar **dentro** do `enum`.
- **LSP com `UnsupportedOperationException` "temporário"** que vira permanente.
- **LSP com subclasse que restringe a entrada** (`if (valor > 100) throw ...` que o supertipo não previa): pré-condição reforçada.
- **ISP dividindo por método** (`IGetNome`, `IGetId`) em vez de por papel de cliente.
- **ISP no servidor, não no cliente:** a interface foi desenhada olhando as capacidades da implementação, não as necessidades de quem a chama.
- **DIP com a abstração no módulo errado:** `RepositorioDePedidos` declarado junto do banco, forçando a regra de negócio a depender do pacote de infraestrutura.
- **DIP sem raiz de composição:** `new` espalhado pelas classes "porque agora tem interface". O `new` das implementações deve concentrar-se em um único ponto.
- **Injeção por campo/estático/*service locator*:** esconde a dependência. A injeção pelo construtor deixa-a visível e impede objeto parcialmente montado.
- **Alterar mensagens de erro ou formato de saída durante a refatoração**, perdendo a capacidade de comparar com `diff`.
- **Refatorar sem rodar a saída entre rodadas:** descobrir tarde que uma etapa anterior quebrou o comportamento.
- **Abandonar um princípio sem registrar o motivo:** vira decisão invisível, que o próximo leitor refaz.

## Do experimento de mudança

- **Julgar a versão pela contagem de arquivos tocados** sem olhar o *tipo* de mudança. Editar um arquivo existente e criar um arquivo novo têm riscos diferentes (o segundo não pode quebrar o que já existe).
- **Conclusões gerais a partir de uma mudança só.** M1–M5 variam em *natureza* (valor, regra própria, fronteira, testabilidade, dependência externa); as conclusões precisam respeitar isso.
- **Concluir que "`depois/` sempre vence"** sem considerar que a abstração teve custo antes de a mudança chegar.
- **Concluir que "`antes/` é mais simples"** sem considerar o custo cumulativo da quinta, da sexta, da décima mudança.
- **Contar M4 (testar sem e-mail) como "só mais um requisito":** é uma mudança de *ambiente*, e é o teste mais direto do DIP.
