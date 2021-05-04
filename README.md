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


```
spring.datasource.url=jdbc:mysql://localhost:3306/springapi?useSSL=false
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto = update
```

#### Criando os models

##### A camada de modelo é a responsável por ser a parte de representação no software dos atributos do que foi pedido. Para que essa representação seja bem sucedida é necessário a criação do que é chamado classes de modelo, que vão ser transformadas em tabelas no banco de dados pelo Spring JPA.

##### O primeiro modelo a ser visto é o do Usuário que tem os atributos nome_usuário,email, cpf, data_nascimento e endereco

~~~java

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank
    private String nome_usuario;

    @Column(nullable = false,unique = true)
    @Email(message = "Coloque um email válido")
    @NotBlank
    private String email;

    @Column(nullable = false,unique = true)
    @NotBlank
    private String cpf;

    @Column(nullable = false )
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_nascimento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
    
    @JsonManagedReference
    public List<Endereco> getEnderecos() {
        return this.enderecos;
    }

~~~

##### Você já deve ter percebido vários nomes com o símbolo @ antes da palavra. Isso se annotations recurso utilizado pelo Spring para indicar que aquele componente Java vai ter algo a mais sobre ele, como por exemplo um atributo não poder receber valores nulos. Abaixo é explicado cada annotation utilizado na classe Usuário

* **@Entity** - Annotation responsável por dizer ao JPA que a classe deve ser transformada em uma tabela no banco de dados em que os atributos vão ser transformadas em colunas
* **@Id** - Esta Annotation indica ao JPA que esse atributo será a chave primária da tabela
* **@GeneratedValue** - Annotation responsável por indicar ao JPA a estratégia que o banco de dados irá gerar as chaves primária, nesse caso foi escolhida a estratégia de gerar a chave primária quando a tabela é salva no banco de dados
* **@Column** - Não seria necessário essa annotation se apenas quisessemos salvar o atributo no banco de dados, mas ela foi necessária para indicar ao JPA que o model não aceita valoves nulos
* **@NotBlank** - Indica ao JPA que não é possível salvar valores em branco
* **@Email** - Responsvel por dizer ao JPA que apenas serão aceitos email em um formato válido (ex: Joao@mail.com)
* **@Past** - Indica ao atributo de data_nascimento que somente datas passadas serão aceitas
* **@JsonFormat** - Indicar ao atributo data_nascimento o formato de data que será salva no banco de dados, no caso foi escolhida o formato ano-mês-dia
* **@OneToMany** - Indica a relação da classe Usuario com a classe Endereco, no caso a relação de um-para-muitos
* **@JsonManagedReference** - Utilizada para gerenciar o atributo enderecos na classe Usuario

##### Agora é mostrado a classe Endereco que tem os atributos logradouro, numero, complemento, bairro, cidade, estado, usuario

~~~java

@Entity
public class Endereco {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank
    private String logradouro;

    @Column(nullable = false)
    @NotBlank
    private String numero;

    @Column(nullable = false)
    @NotBlank
    private String complemento;

    @Column(nullable = false)
    @NotBlank
    private String bairro;

    @Column(nullable = false)
    @NotBlank
    private String cidade;

    @Column(nullable = false)
    @NotBlank
    private String estado;

    @Column(nullable = false)
    @NotBlank
    private String cep;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @JsonBackReference
    public Usuario getUsuario() {
        return this.usuario;
    }

~~~

##### As annotations mais relevantes nessa classe são:

* **@ManyToOne** - Annotation de relacionamento da classe Endereco com a classe Usuario indicando que um endereço pertence a um único usuario
* **@JoinColumn** - Indica ao JPA o id da classe usuario que a classe endereco terá que ser relacionada
* **@JsonBackReference** - Utilizada para gerenciar a referência do atributo usuario na classe Endereco
