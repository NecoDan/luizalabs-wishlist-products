 # API RESTFul WebService - Wishlist e/ou Lista de Desejos
 ###### Copyright © 2021 LUIZALABS MAGALU.
  Projeto em Spring Boot WebFlux de uma construção API RESTFul voltado a atender o desafio da [LUIZALABS MAGALU](https://medium.com/luizalabs).
   
  Uma solução criada, utilizando a tecnologia Java em formato de API REST. Voltada para atender as demandas para criação de uma lista de desejos dos produtos por determinado cliente, desde o processo de criação da lista e consulta de produtos. Onde todos os serviços devem trabalhar com JSON em suas chamadas e retornos.

 #### Visão Geral
  
  A aplicaçao tem como objetivo disponibilizar endpoints para consulta de informações e operações à respeito de:
  - Lista de desejos dos produtos, onde ```o cliente possa selecionar os produtos de sua preferência e arnazená-los nesta. A qualquer momento o cliente pode visualizar sua lista de desejos completa, com todos os produtos que ele selecionou, realizada pela busca de produtos e/ou acessar os detalhes de cada produto contido na lista```. De acordo com os respectivos requisitos: 
    
    - ```Adicionar um produto na Wishlist (lista de desejos) do cliente;```
    - ```Remover um produto na Wishlist (lista de desejos) do cliente;```
    - ```Consultar todos o(s) produto(s) na Wishlist (lista de desejos) do cliente;``` 
    - ```Consultar se um determinado produto encontra-se na Wishlist (lista de desejos) do cliente.``` 
  
 #### Stack do projeto e decisões
  - Escrito em Java 11;
     - ```Uma das linguagens mais utilizadas no mundo, conhecida por ser robusta e facil de escalar;```
     
  - Utilizando as facilidades e recursos framework Spring / WebFlux;
     - ```Framework com uma comunidade muito grande, facilita a integração com diversos outros serviços, tornando o desenvolvimento muito mais rápido;```
     - ```A programação reativa permite usufruir melhor do poder de processamento disponivel, alem de ter por principios ser Resiliente, Elástica, Responsiva e Orientada a Mensagens```
     
  - Lombok e MapStruct na classes para evitar o boilerplate do Java;
  - Framework WebFlux integrado com MongoDB  para garantir a persistência dos dados (collections). Facilitando as operações CRUD (aumentando o nivel de desempenho e escalabilidade);
  - Boas práticas de programação, utilizando Design Patterns (Builder);
  - Testes unitários (junit, mockito, webclient test);
  - Maven como gerenciador de dependências
  
  - Banco de dados MongoDB;
     - ```Banco de dados orientado a documentos facil de escalar e com driver que suporta programação reativa.```
     
  - Docker utilizando o compose;<br><br>
  
  #### Instruções inicialização e execução - (aplicação e database)
  ###### Utilizando docker-compose com MongoDB:
   Executar os comandos: <br><br>
   ```docker-compose build```<br> 
   ```docker-compose up```<br><br> 
   Logo após, inicializará a aplicação ```wishlist-products```, junto com uma instância do MongoDB dockerizada (nesse momento será criado apenas uma collection denominado ```wishlist``` no banco de dados).
   Com a finalidade de gerenciar, registrar e efetuar as operações relacionadas a lista de desejos.
  <br> 
  ###### Utilizando diretamente o jar:
  ```mvn clean install```<br>
  ```java -jar target/wishlist-products-1.0.jar```<br><br>
   
  #### Endpoints: 
  
  Logo após inicialização da aplicação. Alguns endpoints para conhecimento com reecursos disponíveis via endpoints a serem acessados.
 <br><br>
  
  - Retornar toda a coleção de wishlist com os seus respectivos produtos:
    - `http://localhost:8095/v1/wishlist`
    
  - Retornar uma unica coleção de wishlist com os seus respectivos produtos:
    - `http://localhost:8095/v1/wishlist/{id_wishlist}`
    
  - Retornar um único produto que esteja adicionado à lista de desjos, a partir de um identificador único:
    - `http://localhost:8095/v1/wishlist/products/{id}`
      
 Entre outros, aos quais podem ser identificados no endereço fornecido pelo [Swagger](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config).
 <br><br>

  
 #### Autor e mantenedor do projeto
   Daniel Santos Gonçalves. <br>
   ```Software Developer Back-end | Java | Kotlin```<br>
   ```Bacharel em Sistemas de Informação, Instituto Federal do Maranhão - IFMA```<br>
   ```Graduado em Análise e Desenvolvimento Sistemas, Faculdade Estácio de Sá```
 - [GitHub](https://github.com/NecoDan)
 
 - [Linkedin](https://www.linkedin.com/in/daniel-santos-bb072321) 
 
 - [Twitter](https://twitter.com/necodaniel)
