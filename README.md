# Books-Service: Microserviço de Gestão de Livros com JWT, JPA e Swagger

**Sustentando seu fluxo de leitura do “quero ler” ao “já concluído” — com segurança, consistência e desempenho.**

## 1. Título do Projeto
Books-Service: Microserviço de Gestão de Livros com JWT, JPA e Swagger

## 2. Slogan/Chamada Curta
Sustentando seu fluxo de leitura do “quero ler” ao “já concluído” — com segurança, consistência e desempenho.

## 3. Introdução e Visão Geral
Se a sua aplicação precisa administrar o acervo de livros por usuário (com criação, edição, exclusão e acompanhamento do progresso), você enfrenta um desafio comum: manter dados consistentes, expor rotas com segurança e reduzir atrito na integração com outros serviços.

O **Books-Service** resolve isso oferecendo um **microserviço Spring Boot** focado em:
- **Organização do acervo por usuário** (cada usuário enxerga apenas seus livros)
- **Evolução do status** do livro em fases claras (**TO DO**, **IN PROGRESS**, **COMPLETED**)
- **APIs documentadas e consumíveis** via Swagger/OpenAPI
- **Segurança por JWT assinado em RSA** (validação centralizada no serviço)
- **Persistência confiável em MySQL** com JPA

Resultado: uma base sólida para você entregar uma experiência fluida ao usuário final e uma arquitetura preparada para crescer.

## 4. Funcionalidades Principais
1. **Cadastro de Livro**
   - Endpoint: `POST /ms/books/{idUser}/{token}`
   - Você cadastra um livro para um usuário e o serviço persiste no banco.
   - O status inicia com `TO DO`, garantindo um fluxo coerente do “início” até a conclusão.

2. **Edição de Livro**
   - Endpoint: `PUT /ms/books/{idUser}/{idBook}/{token}`
   - Atualiza título, autor, tema e imagem de capa mantendo o status atual do registro.
   - Protege contra alterações indevidas: se o livro não pertence ao usuário informado, a API bloqueia.

3. **Controle de Status em Etapas**
   - Endpoint: `PUT /ms/books/status/todo/{idUser}/{idBook}/{token}`
   - Endpoint: `PUT /ms/books/status/progress/{idUser}/{idBook}/{token}`
   - Endpoint: `PUT /ms/books/status/completed/{idUser}/{idBook}/{token}`
   - Movimenta o livro entre as fases:
     - `TO DO`
     - `IN PROGRESS`
     - `COMPLETED`
   - Valor para o negócio: facilita dashboards, filtros e experiências orientadas a progresso.

4. **Listagem de Livros por Usuário**
   - Endpoint: `GET /ms/books/{idUser}/{token}`
   - Retorna todos os livros cadastrados para o usuário.
   - Se não houver registros, a API sinaliza adequadamente (evita “respostas vazias” sem contexto).

5. **Listagem por Status**
   - Endpoint: `GET /ms/books/status/todo/{idUser}/{token}`
   - Endpoint: `GET /ms/books/status/progress/{idUser}/{token}`
   - Endpoint: `GET /ms/books/status/completed/{idUser}/{token}`
   - Retorna apenas os livros do usuário que estão em uma fase específica.
   - Valor: reduz esforço na camada front-end e acelera a entrega de filtros.

6. **Consulta Detalhada**
   - Endpoint: `GET /ms/books/{idUser}/{idBook}/{token}`
   - Recupera um livro específico, respeitando a regra de propriedade (idUser deve corresponder ao dono do registro).

7. **Exclusão de Livro**
   - Endpoint: `DELETE /ms/books/{idUser}/{idBook}/{token}`
   - Remove o livro do banco com confirmação em formato DTO.
   - A API trata erros de banco e acessos indevidos com mensagens consistentes.

8. **Segurança JWT com RSA (Validação)**
   - A API valida o token via **RSA256** usando a **chave pública** `public.pem`.
   - Regras verificadas:
     - Issuer: `AuthForMService`
     - Audience: `books-service`
   - Benefício: robustez e padronização de autenticação entre serviços.

9. **Tratamento Centralizado de Erros (Consistência)**
   - Um `RestExceptionHandler` centraliza respostas para erros como:
     - `Book not found in database` (404)
     - `Unauthorized User About the Book Reported` (403)
     - Erros de integração com banco (500)
   - Resultado: menos ambiguidade e melhor experiência para consumidores da API.

10. **Execução Assíncrona**
   - As rotas do controller retornam `CompletableFuture` com `@Async("asyncExecutor")`.
   - Benefício: melhora percepção de resposta e prepara o serviço para maior volume de requisições.

11. **Documentação Swagger/OpenAPI**
   - Swagger/OpenAPI configurado para documentar rotas e esquema de segurança.
   - Facilita onboarding de times e integrações rápidas.

## 5. Tecnologias Utilizadas
- **Java 21**
  - Base moderna e alinhada a ambientes atuais de backend.
- **Spring Boot 3.4.5**
  - Estrutura completa para APIs REST, JPA e integrações.
- **Spring Data JPA**
  - Persistência com repositórios e modelagem clara do domínio (`books`).
- **MySQL (mysql-connector-j)**
  - Banco relacional confiável para dados transacionais do acervo.
- **Spring Cloud OpenFeign**
  - Dependência preparada para consumo declarativo de APIs externas (quando aplicável na arquitetura).
- **Spring Boot Actuator**
  - Observabilidade e saúde do serviço (útil em produção).
- **Swagger/OpenAPI (springdoc-openapi-starter-webmvc-ui)**
  - Documentação automática e consumível por desenvolvedores.
- **Auth0 java-jwt**
  - Verificação de JWT com assinatura **RSA256**.
- **Lombok**
  - Reduz código repetitivo, mantendo o código mais legível.
- **JUnit 5 + Mockito**
  - Testes unitários para validar regras do serviço e cenários de falha/ sucesso.
- **Maven Wrapper (`mvnw`)**
  - Execução previsível do build em qualquer máquina.
- **Docker (Dockerfile + docker-compose)**
  - Empacotamento do serviço e subida consistente em ambientes.

## 6. Demonstração Visual

![Swagger UI](./docs/swagger-ui.png)

## 8. Contribuição
Se você quiser evoluir este microserviço:
1. Crie uma branch para sua feature: `feature/nome-da-feature`
2. Implemente mudanças com foco em:
   - clareza de domínio,
   - consistência de erros,
   - cobertura de cenários relevantes.
3. Rode os testes:
   ```powershell
   cd "C:\workspace\InEvolving\Back-End\Books-Service\books"
   .\mvnw.cmd test
   ```
4. Abra uma solicitação de pull request com descrição objetiva do “porquê” e do impacto.

## 9. Licença
Nenhuma licença foi definida explicitamente no repositório. Na prática, na ausência de um arquivo de licença (ex.: `MIT`, `Apache-2.0`, `GPL`), o código tende a ficar sob **todos os direitos reservados**. Se você quiser, posso ajudar a adicionar uma licença adequada ao seu objetivo (portfólio, código aberto, uso interno etc.).

## 10. Agradecimentos
- Estruturado a partir de boas práticas do ecossistema **Spring Boot** e de padrões de documentação com **Swagger/OpenAPI**.
- Agradecimento às equipes e referenciais internos que contribuíram para a integração entre serviços.

## 11. Contato
Para parcerias, vagas e clientes:
- LinkedIn: [https://www.linkedin.com/in/victor-teixeira-354a131a3/](https://www.linkedin.com/in/victor-teixeira-354a131a3/)
- E-mail: `victor.teixeira@inovasoft.tech`
