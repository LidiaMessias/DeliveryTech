# Delivery Tech API

API RESTful desenvolvida com Spring Boot e Java 17 para gerenciar um sistema de delivery completo. O sistema simula as principais funcionalidades de plataformas como iFood e Uber Eats, incluindo autenticação JWT, cache, monitoramento e CI/CD .



## 🧪 Tecnologias Utilizadas
- **Java 17.0.16** 
- Maven
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- Spring Security + JWT
- Spring Validation
- Spring Boot Actuator
- H2 Database
- MySQL 8
- SpringDoc OpenAPI (Swagger)
- Docker + Docker Compose
- JUnit 5 + Mockito
## 🚀 Funcionalidades Principais

- Cadastro e login de usuários com JWT
- Controle de acesso por perfis (CLIENTE, RESTAURANTE, ADMIN, ENTREGADOR)
- Cadastro de clientes, restaurantes, produtos e pedidos
- Listagem de produtos por restaurante
- Listagem de restaurantes por categoria alimentícia (LANCHES, PIZZA etc)
- Criação de pedidos com itens e cálculo do total
- Atualização de status de pedido
- Cache com Spring Cache
- Testes automatizados com JUnit e Mockito
- Documentação com Swagger/OpenAPI
- Banco de dados em memória com H2 (Perfil Dev)
- Banco de dados MySQL 8.0 (Perfil Prod)
- Containerização com Docker e orquestração com Docker Compose
- Pipeline CI/CD com GitHub Actions


## 🏃‍♂️ Como Executar o Projeto

Para configurar e rodar a aplicação em seu ambiente local, siga os passos abaixo:

- **Pré-requisitos:** JDK 17 instalado, Maven, Docker e Docker-Compose (Opcional)

- **Clonar o Repositório:**
    ```bash
    git clone https://github.com/LidiaMessias/DeliveryTech.git
    ```

- **Compilar e Rodar:**  
    - **Via Maven:**   
    Use o Maven Wrapper (`./mvnw`) para iniciar a aplicação sem a necessidade de instalação global do Maven. Este comando também executa as fases de `clean` e `install` antes de iniciar.
    ```bash
    ./mvnw spring-boot:run
    ```
    (Se estiver no Windows PowerShell, use: `.\mvnw spring-boot:run`)

    - **Via Docker:**    
    ```bash
    docker-compose up --build
    ```

- **Acesso:** O servidor estará ativo na porta padrão.
    `http://localhost:8080`
## 📖 Documentação da API

- #### Documentação Interativa (Swagger UI)

Todos os endpoints da API estão documentados interativamente. Acesse esta URL no seu navegador:  
```bash
    http://localhost:8080/swagger-ui.html
```  



## 📋 Endpoints  

* Health Check Completo: `http://localhost:8080/actuator/health`  
* Métricas Prometheus: `http://localhost:8080/actuator/prometheus`
* Console do banco H2: `http://localhost:8080/h2-console`
## ✅ Testes e Cobertura JaCoCo

| Objetivo | Comando | Observação |
| :--- | :--- | :--- |
| **Executar TODOS os Testes** | `./mvnw clean test` | Verifica testes unitários e de integração (Serviço/Controller). |
| **Gerar Relatório de Cobertura** | `./mvnw clean test jacoco:report` | O relatório HTML é gerado em `target/site/jacoco/index.html`. |
| **Executar Aplicação Principal** | `./mvnw spring-boot:run` | (Não executa testes) |

---

## 💻 Desenvolvedor  

Lidia Messias - Arquitetura de Sistemas - Turma 04  
Desenvolvido com JDK 17 e Spring Boot 3.5.6
## 📐 Arquitetura do Sistema

O projeto adota uma arquitetura de camadas (Layered Architecture), com ênfase na separação de responsabilidades (Controller, Service, Repository) e um pipeline de observabilidade desacoplado (Actuator/Zipkin).

![Diagrama da Arquitetura do Sistema Delivery Tech API](delivery-api/images/arquitetura-sistema.png)