# Documentação técnica 

### Modelagem do banco de dados
link para visualizar a modelagem do banco de dados: [imagem](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/Tech%20challange%20-%20Fase%203.png?alt=media&token=c9b54963-2b36-429d-862a-c1be64d8a566)

![modelagem do banco de dados](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/Tech%20challange%20-%20Fase%203.png?alt=media&token=c9b54963-2b36-429d-862a-c1be64d8a566)

#### Relacionamento entre tabelas
##### Tabela condutor
- Relacionamento de 1 para 1 com  **Endereco do condutor**.
- Relacionamento de 1 para muitos com **Automoveis**
- Relacionamento de 1 para muitos com **Registro de estacionamento**

##### Tabela Endereço do condutor
- Relacionamento de 1 para 1 com **Condutor**

##### Tabela Automóvel
- Relacionamento de muitos para 1 com **Condutor**

##### Tabela Registro de estacionamento
- Relacionamento de muitos para 1 com **Condutor**
- Relacionamento de 1 para 1 com **Extrato de pagamento**
- Relacionamento de muitos para 1 com **Endereco do estacionamento**

##### Tabela Metodo de pagamento
- Relacionamento de muitos para 1 com **Condutor**

##### Tabela Credito
- Relacionamento de 1 para 1 com **Metodo de pagamento**

##### Tabela Debito
- Relacionamento de 1 para 1 com **Metodo de pagamento**

##### Tabela Pix
- Relacionamento de 1 para 1 com **Metodo de pagamento**

#### Tablea extrato de pagamento
- Relacionamento de muitos para 1 com **Método de pagamento**

#### Tabela Endereço do estacionamento
- Relacionamento de 1 para muitos com **Registro de estacionamento**

### Arquitetura do banco de dados
- O banco de dados foi criado dentro de uma VPC ou seja sem acesso ao mundo externo
- Para que o custo fosse o menor possível, utilizei a arquitetura gratuita do banco RDS com as seguintes configurações: db.t4g.micro, 2vCPU, 2 GB de RAM e 20GB de armazenamento.

  Mas para uma aplicação realmente escalável, poderíamos mudar os recursos. **Optaria por utilizar um banco aurora serveless(Compatível com PostgreSQL) com no mínimo 2GB de ACU e no máximo 6GB de ACU, com implantação multi-AZ e por fim utilizaria um RDS Proxy para diminuir o tempo de conexão entre aplicação e o banco de dados.**

### Arquitetura da aplicação
![arquitetura da aplicação](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/arquitetura.png?alt=media&token=c11e0039-78d9-4eff-b37d-25349ffd3424)

[link da arquitetura](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/arquitetura.png?alt=media&token=c11e0039-78d9-4eff-b37d-25349ffd3424)

- Utilizamos os seguintes recursos da AWS: ECS, Fargate, SQS para envio de alertas e finalização do agendamento e o Event Bridge.
- Utilizaria exatamente essa arquitetura para uma aplicação escalável, porém com algumas ressalvas. Aumentaria o número de containers de 1 para pelo menos 6 containers, incluiria também um API Gateway, 
  Load balance e por fim criaria uma VPC para incluir todos esses recursos. Por último, configuraria apenas um endereço público a partir do API Gateway para os consumidores da API poderem acessá-la corretamente.
  
- **event bridge**: configurei três tipos de roles (regras).
  
  1° role: para envio de e-mail, que será ativada sempre 10 minutos antes do tempo do estacionamento expirar que é utilizada apenas para o estacionamento por tempo fixo.

  2° role: Focada para finalização do agendamento

  3° role: Focado para criar um intervalo de tempo de 60 minutos para estacionamento por **tempo variável**

 - **AWS SQS** criei duas filas que envia para a aplicação diferentes tipos de informações.
   
  1° enviar e-mail (postech_enviar_email): Apenas para alerta, utlizada tanto para estacionamento por tempo fixo, quanto para estacionamento por tempo variável [envio de alertas ao consumidor](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/view/controller/EnviarEmailSqsListener.java)
  
  2° finalização (postech_remover_evento): Utilizado apenas para o estacionamento por tempo fixo, que irá enviar a informação para a aplicação quando o tempo do estacionamento já expirou. [finalizar agendamentos](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/view/controller/FinalizarAgendamentosListener.java)  

- Para envio de e-mail, estamos utilizando o Amazon simple e-mail service, como como ficou a implamtação [envio de e-mail](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/infra/email/Email.java)

### Detalhando os passos que a aplicação faz quando marcamos um estacionamento por tempo fixo:

  1° Executamos o endpoint `/api/parquimetro/tempoFixo`, com isso criamos dois eventos do tipo cronjob no event bridge (alertaParaEnviarEmail_ e agendamentoPorTempoFixo_). O evento `alertaParaEnviarEmail_` é focado para enviar um alerta 10 minutos antes do tempo expirar e o evento `agendamentoPorTempoFixo_` envia uma mensagem a fila quando o tempo do estacionamento expirar.

  2° Quando chegar o horário de 10 minutos antes de encerrar o estacionamento o evento enviará uma mensagem a fila de alertas chamada de `postech_enviar_email`.

  3° A aplicação vai ler a mensagem da fila, e enviará um e-email ao usuário alertando que o tempo está se encerrando, e por fim a aplicação apagará esse evento.

  4° Quando o tempo do estácionamento for encerrado, o evento `agendamentoPorTempoFixo_` enviará uma mensagem a fila de encerramento chamada de `postech_remover_evento`, a aplicação vai ler essa mensagem e então enviará um e-mail de confirmação e com a informação do pagamento, então apagaremos o evento criado no event bridge e por fim adicionaremos as informações necessárias no banco de dados.

  5° Se ocorrer até 3 erros durante o processamento da mensagem, enviaremos a mensagem em uma fila DLQ que posteriormente iremos fazer o reprocessamento dessas mensagens.

  6° Veja todo esse processo em funcionamento aqui: [iniciar estacionamento por tempo fixo](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/aplicacao/parquimetro/EstacionamentoPorTempoFixo.java) e [finalizar estacionamento por tempo fixo](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/aplicacao/parquimetro/FinalizarAgendamentoPorTempoFixo.java)

### Detalhando os passos que a aplicação faz quando marcamos um estacionamento por tempo variável:

  1° Executamos o endpoint `/api/parquimetro/tempoVariavel/iniciar`, com isso criamos 1 evento do tipo intervalo no event bridge nomeado como (alertaParaEnviarEmail_)

  2° A cada 60 minutos o evento envia uma mensagem a fila de alertas chamada de `postech_enviar_email` que a aplicação irá ler essa mensagem coletar as informações necessárias e então enviará um e-mail dizendo que o tempo do estacionamento foi renovado. Isso irá ocorrer até que o condutor encerre o tempo do estacionamento

  3° Ao executar a url `/api/parquimetro/tempoVariavel/finalizar` a aplicação enviará um e-mail ao usuário com o valor do pagamento, armazenará as informações necessaŕias no banco de dados e por fim apagará o evento no event bridge.
  
  4° Se ocorrer até 3 erros durante o processamento da mensagem, enviaremos a mensagem em uma fila DLQ que posteriormente iremos fazer o reprocessamento dessas mensagens.

  5° Veja todo esse processo em funcionamento aqui: [iniciar estacionamento por tempo variavel](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/aplicacao/parquimetro/EstacionamentoPorTempoVariavel.java) e [finalizar estacionamento por tempo variavel](https://github.com/JonasBarros1998/techchallange-fase-3/blob/main/src/main/java/com/postech/parquimetro/aplicacao/parquimetro/FinalizarAgendamentoPorTempoVariavel.java)

- Criamos duas fila DLQ para caso haja algum erro de processamento
  
  1°: Fila de origem: postech_remover_evento, Fila DLQ: postech_remover_evento_dlq
  
  2°: Fila de origem: postech_enviar_email, Fila DLQ: postech_enviar_email_dlq
  
![SQS AWS](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/AWS-SQS.png?alt=media&token=76e0b05d-c13c-4755-8bdf-32fbad2373dd)

![SQS AWS](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/remover_eventoAWS-SQS.png?alt=media&token=542bc19a-7076-44cc-9f2a-e632e2d94e65)

![SQS AWS](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/SQS-AWS-Detalhes.png?alt=media&token=dc8b4c54-2846-49ab-bf13-f9a6ba674940)
   
### Segurança
- Para criptografia e descriptografia dos dados, estou utilizando o algoritmo AES/CFB/PKCS5Padding, um dos mais seguros atualmente. Também poderia utilizar o padrão de chave pública e chave privada, mas para isso o consumidor teria que enviar a chave pública para nós fazermos a validação e descriptografar dados. Mas dessa forma não é muito habitual comparando com o cenário que estamos desenvolvendo. Não estou utilizando nenhuma lib externa, apenas os recursos do próprio Java. Para escolher esse padrão, tive auxilio das seguintes referências: [Public/Private Keys Vs. Certificates](https://www.baeldung.com/cs/public-private-keys-vs-certificates), [Encryption and decryption of data at blazing speed using Spring Data JPA](https://medium.com/@deependra.chourasia/encryption-and-decryption-of-data-at-blazing-speed-using-spring-data-jpa-a4c36b84588e), [Guide to the Cipher Class](https://www.baeldung.com/java-cipher-class)
  
- Ao subir o nosso ambiente, criamos políticas de acesso utilizando apenas os recursos que a nossa aplicação irá utilizar. Veja abaixo cada acesso que utilizei na aplicação
  
- Acesso leitura ao elastic container registry
```json
[
  "ecr:GetAuthorizationToken",
  "ecr:BatchCheckLayerAvailability",
  "ecr:GetDownloadUrlForLayer",
  "ecr:GetRepositoryPolicy",
  "ecr:DescribeRepositories",
  "ecr:ListImages",
  "ecr:DescribeImages",
  "ecr:BatchGetImage",
  "ecr:GetLifecyclePolicy",
  "ecr:GetLifecyclePolicyPreview",
  "ecr:ListTagsForResource",
  "ecr:DescribeImageScanFindings"
]

```

- Acesso a criação e remoção dos eventos do Event Bridge
```json
[
  "events:PutEvents",
  "events:DeleteRule",
  "events:PutTargets",
  "events:EnableRule",
  "events:CreateEventBus",
  "events:PutRule",
  "events:DeleteEventBus",
  "events:RemoveTargets"
]
```

- Acesso ao AWS SES, foi utilizado o `AmazonSESFullAccess`

- Acesso ao SQS. Para os resources tomamos um cuidado para específicar apenas as filas que estamos utilizando e não todas as filas da conta da AWS. Por questão de segurança ocultei a região e o ID da conta da AWS.
```json
{
  "Actions": [
    "sqs:StartMessageMoveTask",
    "sqs:DeleteMessage",
    "sqs:GetQueueUrl",
    "sqs:CancelMessageMoveTask",
    "sqs:ChangeMessageVisibility",
    "sqs:ListMessageMoveTasks",
    "sqs:ReceiveMessage",
    "sqs:GetQueueAttributes",
    "sqs:PurgeQueue"
  ],
  "Resources": [
    "arn:aws:sqs:ab-abcd-0:00000000:postech_remover_evento",
    "arn:aws:sqs:ab-abcd-0:00000000:postech_enviar_email"
  ]
}
```

### SDK 2.x vs SDK 1.x
- Ao dercorrer do tempo houveram algumas atualizações nas SDKs que a AWS disponibiliza para o Java. Optei por utlizar a SDK 2.x porque evita de ficar trabalhando com usuários e chaves na AWS. Assim posso trabalhar criando as políticas com apenas os recursos em que eu específicar e anexar cada uma dessas políticas dentro dos serviços que a minha aplicação estará utilizando.
 
