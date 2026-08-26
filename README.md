# Sistema de Reservas de Voo ✈️

Projeto acadêmico desenvolvido para a disciplina de Programação de Soluções Computacionais (A3), no curso de Análise e Desenvolvimento de Sistemas. O sistema gerencia contas de clientes e reservas de viagem, aplicando conceitos de Programação Orientada a Objetos, persistência de dados em banco relacional e arquitetura em camadas.

# 📋 Sobre o projeto

O sistema nasceu como uma aplicação de console com conexão direta ao banco via JDBC, e foi migrado para uma arquitetura em camadas (Controller → Service → Repository), agora também exposta como uma **API REST** construída com Spring Boot. O modo console original continua disponível como uma segunda forma de interação com o sistema.

## ✨ Funcionalidades

- Cadastro, autenticação, consulta, atualização e remoção de clientes
- Criação, consulta, edição e remoção de reservas de viagem
- Persistência dos dados em banco de dados PostgreSQL
- Duas formas de uso: menu interativo via terminal, ou API REST via HTTP

## 🛠️ Tecnologias utilizadas

- **Java 21**
- **Spring Boot** — framework para a API REST
- **Maven** — gerenciamento de dependências e build
- **PostgreSQL** — armazenamento e persistência dos dados
- **JDBC** — conexão entre a aplicação Java e o banco de dados

## 🏗️ Arquitetura

```
Cliente (HTTP)  →  Controller (@RestController)  →  Service  →  Repository  →  PostgreSQL
```

- **Controller**: expõe os endpoints HTTP (`ContaController`, `ReservaController`)
- **Service**: regras de negócio (`GerenciadorContas`, `GerenciadorReservas`)
- **Repository**: acesso a dados via JDBC (`ContaRepository`, `ReservaRepository`)
- **Model**: entidades do domínio (`Cliente`, `Pessoa`, `Reserva`)

## ✅ Pré-requisitos

- [JDK 21+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)

## 🚀 Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/Theusith/A3_psc.git
   cd A3_psc/Projeto-A3-Psc/sistema-ReserV
   ```

2. Crie o banco de dados e as tabelas no PostgreSQL:
   ```sql
   CREATE DATABASE sistema_reserv;

   CREATE TABLE usuarios (
       id SERIAL PRIMARY KEY,
       nome VARCHAR(100) NOT NULL,
       cpf VARCHAR(20) NOT NULL,
       email VARCHAR(100) NOT NULL,
       senha VARCHAR(100) NOT NULL,
       tipo VARCHAR(20) NOT NULL,
       matricula VARCHAR(20)
   );

   CREATE TABLE reservas (
       idReservas SERIAL PRIMARY KEY,
       idCliente INTEGER NOT NULL REFERENCES usuarios(id),
       origem VARCHAR(100) NOT NULL,
       destino VARCHAR(100) NOT NULL,
       dataViagem DATE NOT NULL
   );
   ```

3. Configure as credenciais do banco:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
   Edite o arquivo `application.properties` recém-criado com seu usuário e senha reais do PostgreSQL. **Esse arquivo não deve ser commitado.**

4. Escolha como executar:

   **Modo API REST** (sobe um servidor em `http://localhost:8080`):
   ```bash
   mvn spring-boot:run
   ```

   **Modo console** (menu interativo no terminal):
   ```bash
   mvn compile exec:java -Dexec.mainClass="Service.Main"
   ```

## 📡 Endpoints da API

| Método | Rota | Descrição |
|---|---|---|
| POST | `/contas` | Cadastra um novo cliente |
| GET | `/contas` | Lista todos os clientes |
| GET | `/contas/{id}` | Busca um cliente pelo ID |
| PUT | `/contas/{id}` | Atualiza um cliente |
| DELETE | `/contas/{id}` | Remove um cliente |
| POST | `/contas/login` | Autentica um usuário |
| POST | `/reservas` | Cria uma nova reserva |
| GET | `/reservas/cliente/{idCliente}` | Lista reservas de um cliente |
| PUT | `/reservas/{idReserva}/cliente/{idCliente}` | Atualiza uma reserva |
| DELETE | `/reservas/{idReserva}` | Remove uma reserva |
| DELETE | `/reservas/cliente/{idCliente}` | Remove todas as reservas de um cliente |

## 📁 Estrutura do projeto

```
A3_psc/
├── Javadoc/                          # Documentação gerada do código
├── Projeto-A3-Psc/
│   └── sistema-ReserV/
│       ├── pom.xml
│       └── src/main/
│           ├── java/
│           │   ├── Conexao/          # Conexão JDBC + configuração via Spring
│           │   ├── Controller/       # Endpoints REST
│           │   ├── Model/            # Entidades do domínio
│           │   ├── Repository/       # Acesso a dados
│           │   └── Service/          # Regras de negócio + menu console
│           └── resources/
│               └── application.properties.example
└── README.md
```

## 👥 Colaboradores

Projeto desenvolvido em grupo para a disciplina de Programação de Soluções Computacionais.

- [Matheus Oliveira da Silva Costa](https://github.com/Theusith)

## 📚 Aprendizados

Este projeto começou como uma das primeiras aplicações práticas de Programação Orientada a Objetos e integração com banco de dados relacional. Posteriormente, evoluiu para explorar arquitetura em camadas e construção de APIs REST com Spring Boot, incluindo boas práticas como separação de responsabilidades e gerenciamento seguro de credenciais.