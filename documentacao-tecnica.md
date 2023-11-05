# Documentação técnica 

### Modelagem do banco de dados
link para a modelagem do banco de dados: [imagem](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/Tech%20challange%20-%20Fase%203.png?alt=media&token=c9b54963-2b36-429d-862a-c1be64d8a566)

![modelagem do banco de dados](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/Tech%20challange%20-%20Fase%203.png?alt=media&token=c9b54963-2b36-429d-862a-c1be64d8a566)

#### Relacionamento entre tabelas
##### Tabela condutor
- Relacionamento de 1 para 1 com  **Endereço**.
- Relacionamento de 1 para muitos com **Automoveis**
- Relacionamento de 1 para muitos com **Registro de estacionamento**

##### Tabela Endereço do condutor
- Relacionamento de 1 para 1 com **Condutor**

##### Tabela Automóvel
- Relacionamento de muitos para 1 com **Condutor**

##### Registro de estacionamento
- Relacionamento de muitos para 1 com **Condutor**
- Relacionamento de 1 para 1 com **Extrato de pagamento**
- Relacionamento de muitos para 1 com **Endereço do estacionamento**

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
- Para que o custo fosse menor possível, utilizei a arquitetura gratuita do banco RDS com as seguintes configurações: db.t4g.micro, 2vCPU, 2 GB de RAM e 20GB de armazenamento. Mas para uma aplicação realmente escalável, poderiamos mudar os recursos. Optaria por utilizar um banco aurora serveless(Compátivel com PostgreSQL) com no mínimo 2GB de ACU e no máximo 6GB de ACU, com implantação multi-AZ e por fim utilizaria um RDS Proxy para diminuir o tempo de conexão entre aplicação e o banco de dados.

### Arquitetura da aplicação
[link para a arquitetura da aplicação](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/Tech%20challange%20-%20Fase%203.png?alt=media&token=c9b54963-2b36-429d-862a-c1be64d8a566)

![arquitetura da aplicação]()



### Segurança

#### Criptografia

#### Políticas de acesso na AWS

