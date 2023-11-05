# Parquimetro - fiap postech fase 3

## Observações gerais
- Os detalhes da entrada e saída de cada endpoint, você verá no arquivo [documentacao.json](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/documentacao.yaml) construído com a especificação openapi 3.0
- Todos os vídeos foram gravados, foi utilizado a aplicação que enviada ao ECS e se conenctando ao RDS PostgreSQL, Event Bridge e AWS SQS
- Todos os vídeos com os links adicionados no PDF que foi enviado na plataforma da FIAP

## Inicio rápido
#### Para agendar rápidamente um estacionamendo, execute em sequência os seguintes endpoints abaixo
- `POST: /api/condutores`
- `POST: /api/automoveis`
- `POST: /api/credito`, `POST: /api/debito` ou `POST: /api/pix`
- `POST: /api/parquimetro/tempoVariavel` ou `/api/parquimetro/tempoFixo` 

## Regras de negócio

#### Método de pagamento
Se o usuário iniciar um novo estacionamento por tempo variável e enviar o ID de um método de pagamento do tipo PIX, vamos enviar um retorno impedindo inicializar o temporizador do estacionamento. De acordo com um dos requisitos, só é permitido enviar o método de pagamento do tipo PIX, utilizando o estacionamento por tempo fixo
  
retorno da API:
````

[{
  "field": "MetodoDePagamentoInvalido",
  "error": "Nao e permitido selecionar metodo de pagamento do tipo PIX para estacionamentos por tempo variavel"
}]
````
#### Veículos
Também não será permitido a inicialização de estacionamento por tempo fixo ou variável, se o condutor não tiver veiculos cadastros, ou métodos de pagamento condizentes com o tipo de estácionamento que ele escolheu.

Retorno da API, caso o usuário não cadastrou nenhum veículo
````
[{
  "field": "VeiculosNaoEncontradoException",
  "error": "Nao foi possivel encontrar os veiculos desse condutor"
}]
````

Retorno da API, caso o usuário não cadastrou nenhum método de pagamento
````
[{
  "field": "NaoExistePagamentosRegistradosExcetion",
  "error": "Nao foram encontrados pagamentos para esse condutor"
}]
````

#### Calculo do preço do estacionamento
De acordo com o zona azul, o preço por 60 minutos estacionado, é de R$ 6,08. Mas se o condutor permanecer estácionado por 30 minutos, ou 2 horas quanto ele pagaria? Para isso utilizamos a regra de três. Veja o exemplo abaixo

Se o condutor ficar estacionado por 60 minutos, ele pagará R$ 6,08, porém se ele ficar estacionado por 16 minutos, quanto ele pagará?
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

#### Envio de e-mails quando o estacionamento está próximo de expirar
Se o condutor escolher o tipo de estácionamento por tempo fixo, quando faltarem **10 minutos** para o tempo se encerrar, vamos enviar um e-mail com a seguinte informação **"Falta pouco para se encerrar o tempo do seu estacionamento"**
Se o condutor escolhler o tipo de estacionamento por tempo variavel, a cada **60 minutos** enviaremos um e-mail com a seguinte informação **"Acrescimo de 60 minutos no tempo do seu estacionamento"** 

## Validações
- Todos os endpoints estão sendo validados, mas na api `/api/parquimetro/tempoFixo` existe uma validação especial para marcar um estacionamento por tempo fixo. Para evitarmos que o condutor marque um horário antes do horario atual adicionamos os seguintes validadores
`@FutureOrPresent` para a propriedade `tempoInicial` e `@Future` para a propriedade `tempoFinal`

## Segurança
- Como o nosso sistema permite cadastro de informações pessoais dos nossos condutores, estou criptografando cada informação sensível relacionada aos cartões de crédito e débito e o PIX. Também não apresentamos essas informações por completo quando
a url `GET: /api/pagamentos/{dondutor_id}` é chamada pelo consumidor  

## Como iniciar um estactionamento por tempo fixo ou por tempo variável
- Após o condutor passar por todas as regras de negócio e validações listadas acima, ele poderá iniciar o primeiro estacionamento. Seja ele por tempo fixo ou por tempo variável.
- Estacionamento por tempo variável (vídeo: Iniciando um estacionamento por tempo variável e validações)
- Estacionamento por tempo fixo  (vídeo: Iniciando um estacionamento por tempo fixo e validações)

## Padronização de horários UTC e UTC-3

- Por padrão na aplicação, estou utilizando o padrão UTC-3 pra agendamento dos estacionamento por tempo fixo e por tempo variável. Mas para enviar ao `event bridge` tenho que criar o evento utilizando UTC ao invés de UTC-3. Portanto tive que fazer
uma conversão conversão.

Primeiro específiquei o padrão de horário em que o consumidor nos envia `ZonedDateTime agendamentoUTC = tempoFinal.atZone(ZoneOffset.ofHours(-3));`, em seguida fiz a conversão para UTC 
`LocalDateTime agendamentoParaAlerta = agendamentoUTC.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()` e então enviamos o evento ao `event bridge`. Veja como fizemos essa tratamento na classe [CriarCronJob.java](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/dominio/CriarCronJob.java)

## Desativar cliente
- Apesar do endpoint está do verbo `DELETE` Ao invés de remover o cliente e todos os dados deles relacionados, estamos apenas desativando o cliente e mantendo os dados do mesmo.


## Vídeo
1° vídeo: mostrando as validações e regras de negócio e iniciando um estacionamento por tempo fixo e por tempo variável
2° vídeo: iniciando o estacionamento por tempo variavel e confirmando estacionamento por tempo fixo
3° vídeo: confirmando o estacionamento por tempo variavel  
3° vídeo: mostrando a criptografia do banco de dados
*todos os links estão no PDF enviado a plataforma da FIAP*












