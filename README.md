# Parquimetro - fiap postech fase 3

## Observações gerais
- Os detalhes da entrada e saída de cada endpoint, você verá no arquivo [documentacao.json](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/documentacao.yaml) construído com a especificação openapi 3.0
- Todos os vídeos gravados, foram utilizado a aplicação enviada ao ECS e se conectando ao RDS PostgreSQL, Event Bridge e AWS SQS
- Todos os vídeos gravados foram adicionados os links no PDF que foi enviado na plataforma da FIAP

## Inicio rápido
#### Para agendar rapidamente um estacionamento, execute em sequência os seguintes endpoints abaixo
- `POST: /api/condutores`
- `POST: /api/automoveis`
- `POST: /api/credito`, `POST: /api/debito` ou `POST: /api/pix`
- `POST: /api/parquimetro/tempoVariavel/iniciar` ou `/api/parquimetro/tempoFixo` 

## Regras de negócio

#### Método de pagamento
Se o usuário iniciar um novo estacionamento por **tempo variável** e enviar o ID do método de pagamento do tipo PIX, vamos enviar um retorno impedindo inicializar o temporizador do estacionamento. De acordo com um dos requisitos, só é permitido enviar o método de pagamento do tipo PIX, utilizando o estacionamento por **tempo fixo**
  
retorno da API:
````
[{
  "field": "MetodoDePagamentoInvalido",
  "error": "Nao e permitido selecionar metodo de pagamento do tipo PIX para estacionamentos por tempo variavel"
}]
````
#### Veículos
Também não será permitido a inicialização de estacionamento por tempo fixo ou variável, se o condutor não tiver veiculos cadastros

Retorno da API:
````
[{
  "field": "VeiculosNaoEncontradoException",
  "error": "Nao foi possivel encontrar os veiculos desse condutor"
}]
````

#### Métodos de pagamento

Não será permitido a inicialização de estacionamento por tempo fixo ou variável, se o condutor não tiver os métodos de pagamento cadastrados

Retorno da API:
````
[{
  "field": "NaoExistePagamentosRegistradosExcetion",
  "error": "Nao foram encontrados pagamentos para esse condutor"
}]
````

#### Cálculo do preço do estacionamento
De acordo com o zona azul, o preço por 60 minutos estacionado, é de R$ 6,08. Mas se o condutor permanecer estacionado por 30 minutos, ou até mesmo 2 horas quanto ele pagaria? Para isso utilizamos a regra de três. Veja o exemplo abaixo

Se o condutor ficar estacionado por 60 minutos, ele pagará R$ 6,08. Porém se ele ficar estacionado por 16 minutos, quanto ele pagará?
R: 
| Minutos | Dinheiro |
| ------- | -------- |
| 60 min  |   6.08   |
| 16 min  |     X    |

60x = 6.08 * 16

60x = 97.28

x = 97.28 / 60

x = R$ 1,62

Então se o condutor ficar 16 minutos estacionado, ele pagará R$ 1,62. 

Toda o calculo dessa regra está contida dentro da classe [CalcularValorDoPagamento.java](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/dominio/CalcularValorDoPagamento.java)

Referências: 

[regra de três](https://brasilescola.uol.com.br/matematica/regra-tres-simples.htm)


#### Envio de e-mails quando o estacionamento está próximo de expirar (estacionamento por tempo fixo)
Se o condutor escolher o tipo de estacionamento por **tempo fixo**, quando faltarem **10 minutos** para o tempo se encerrar, vamos enviar um e-mail com a seguinte informação **"Falta pouco para se encerrar o tempo do seu estacionamento"**
Quando o tempo do estácionamento expirar, vamos enviar um e-mail de confirmação de pagamento.  

#### Envio de e-mails quando o estacionamento está próximo de expirar (estacionamento por tempo variável)
Se o condutor escolher o tipo de estacionamento por **tempo variavel**, a cada **60 minutos** enviaremos um e-mail com a seguinte informação **"Acrescimo de 60 minutos no tempo do seu estacionamento"** 
Quando o condutor encerrar o estácionamento por **tempo variável** enviaremos um e-mail confirmando o pagamento do estacionamento.

## Validações uteis
- Todos os endpoints estão sendo validados, mas na API `/api/parquimetro/tempoFixo` existe uma validação especial para marcar um estacionamento por **tempo fixo**. Para evitarmos que o condutor marque um horário antes do horário atual adicionamos os seguintes validadores
`@FutureOrPresent` para a propriedade `tempoInicial` e `@Future` para a propriedade `tempoFinal`. Com isso nós impedimos que o condutor inicie um estacionamento marcando um horário anterior ao seu horário atual.

## Segurança
- Como o nosso sistema permite cadastro de informações pessoais dos nossos condutores, estou criptografando cada informação sensível relacionada aos cartões de crédito e débito. Também não apresentamos essas informações por completo quando
a url `GET: /api/pagamentos/{dondutor_id}` é chamada pelo consumidor. Por exemplo não apresentamos a chave do PIX, CPF, inoformações sensíveis dos cartões de crédito e debito. Você verá como nós apresentamos cada informação nos vídeos listados no documento PDF postado na plataforma FIAP.  

## Padronização de horários UTC e UTC-3

- Na aplicação estou utilizando o padrão UTC-3 pra agendamento dos estacionamento por tempo fixo e por tempo variável. Mas para enviar um evento (cronjob ou agendamento) ao `event bridge`tenho que converter de UTC-3 para UTC. Para isso tiver que fazer o seguinte:

  1° Primeiro específiquei o padrão de horário em que o consumidor nos envia `ZonedDateTime agendamentoUTC = tempoFinal.atZone(ZoneOffset.ofHours(-3));`, em seguida fiz a conversão para UTC 
`LocalDateTime agendamentoParaAlerta = agendamentoUTC.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()` e então enviamos o evento ao `event bridge`. Veja como fizemos essa tratamento na classe [CriarCronJob.java](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/dominio/CriarCronJob.java). Nessa classe está todas as conversões para enviarmos os horários corretos de cada evento.

## Desativar cliente
- Apesar do endpoint `api/condutores/{id}` está especificado com o verbo `DELETE`. Ao invés de remover o cliente e todos os dados deles relacionados, estamos apenas desativando o cliente e mantendo os dados do mesmo.


## Vídeos
1° vídeo: validações e regras de negócio e iniciando um estacionamento por tempo fixo

2° vídeo: iniciando o estacionamento por tempo variável, visualizando regra de negócio e confirmando estacionamento por tempo fixo

3° vídeo: confirmando o estacionamento por tempo variável

4° vídeo: criptografia do banco de dados

*todos os links estão no PDF enviado a plataforma da FIAP*












