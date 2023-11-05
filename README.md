# Parquimetro - fiap postech fase 3

## Observaçoes gerais
- Os detalhes da entrada e saída de cada endpoint, você verá no arquivo [documentacao.json](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/documentacao.yaml) construído com a especificação openapi 3.0

## Inicio rápido
#### Para agendar rápidamente um estacionamendo, execute em sequência os seguintes endpoints abaixo
- `POST: /api/condutores`
- `POST: /api/automoveis`
- `POST: /api/credito`, `POST: /api/debito` ou `POST: /api/pix`
- `POST: /api/parquimetro/tempoVariavel`

## Regras de negócio
- Se o usuário iniciar um novo estacionamento por tempo variável e enviar o ID de um método de pagamento do tipo PIX, vamos enviar um retorno impedindo inicializar o temporizador do estacionamento.
  De acordo com um dos requisitos, só é permitido enviar o método de pagamento do tipo PIX, utilizando o estacionamento por tempo fixo
  
retorno da API:
````

[{
  "field": "MetodoDePagamentoInvalido",
  "error": "Nao e permitido selecionar metodo de pagamento do tipo PIX para estacionamentos por tempo variavel"
}]
````

- Também não será permitido a inicialização de estacionamento por tempo fixo ou variável, se o condutor não tiver veiculos cadastros, ou métodos de pagamento condizentes com o tipo de estácionamento que ele escolheu.

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

## Validações
- Todos os endpoints estão sendo validados, mas na api `/api/parquimetro/tempoFixo` existe uma validação especial para marcar um estacionamento por tempo fixo. Para evitarmos que o condutor marque um horário antes do horario atual adicionamos os seguintes validadores
`@FutureOrPresent` para a propriedade `tempoInicial` e `@Future` para a propriedade `tempoFinal`

## Segurança
- Como o nosso sistema permite cadastro de informações pessoais dos nossos condutores, estou criptografando cada informação sensível relacionada aos cartões de crédito e débito e o PIX. Também não apresentamos essas informações por completo quando
a url `GET: /api/pagamentos/{dondutor_id}` é chamada pelo consumidor  

Segue o link do vídeo mostrando todas essas e outras **validações**, **regras de negócio** e **segurança** [vídeo]()


## Como iniciar um estactionamento por tempo fixo: 
- Após o condutor passar por todas as regras de negócio e validações listadas acima, ele poderá iniciar o primeiro estácionamento. Seja ele por tempo fixo ou por tempo variável. 




