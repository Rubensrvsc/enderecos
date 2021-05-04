# Construção de uma API de gestão de usuários e seus endereços

#### Uma empresa contratou uma software house para fazer um software de cadastro de usuários e seus respectivos endereços, e para começar o projeto o time de backend ficou responsável por desenvolver a parte da API, parte do software responsável por enviar os dados para clientes que desejam consumí-los.

#### O fluxo de dados básico que irá guiar esse projeto é o especificado no protocolo de comunicação REST onde os dados são enviados em um formato, como por exemplo o json, e o endpoint responsável pelo recebimento dos dados é utilizado para fazer as operações necessárias. As ações que o sistema deve fazer são a de cadastrar usuário, cadastrar um endereço e relacionar a um usuário e listar todos os endereços de um usuário específico.

#### Para fazer a API desejada foi escolhida as ferramentas contidas no universo Spring

##### As dependências utilizadas nesse projeto tiveram como objetivo o aumento de produtividade no momento da escrita de código. Um exemplo disso são os starters, bibliotecas utilizadas no spring que a partir da sua declaração é possível utilizar todas as suas funcionalidades. Também foi utilizado o driver do Mysql para fazer a conexão com o banco de dados

```
Spring starter WEB - Starter responsável por comunicar que é uma aplicação WEB
Spring starter JPA - Responsável pela comunicação com o banco de dados
Spring starter Validation - Responsável pela validação das entradas do usuário
Mysql Connector - Driver para conectar com o MYSQL
Hibernate Validator - Responsável pela validação da entrada dos dados
```

#### Conectando o banco de dados

##### De posse do driver de conexão do Mysql é necessário indicar para o spring no arquivo application.properties o local do banco de dados, nome do banco de dados, usuário, senha e o dialeto que a pessoa está usando
