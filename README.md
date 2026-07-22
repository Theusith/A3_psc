# Sistema de Reservas de Voo ✈️

Projeto acadêmico desenvolvido para a disciplina de Programação de Soluções Computacionais (A3), no curso de Análise e Desenvolvimento de Sistemas. O sistema simula o gerenciamento de reservas de voos, aplicando conceitos de Programação Orientada a Objetos e persistência de dados em banco relacional.

## 📋 Sobre o projeto

O sistema permite gerenciar voos e reservas através de uma interface via terminal/console, com todas as informações armazenadas em um banco de dados PostgreSQL. Foi desenvolvido em grupo, com foco na aplicação prática de lógica de programação, modelagem de dados e integração entre Java e banco de dados.

## ✨ Funcionalidades

- Cadastro e consulta de voos
- Realização de reservas
- Persistência dos dados em banco de dados PostgreSQL
- Operações via menu de terminal (CRUD de reservas/voos)

> Ajuste esta lista conforme as funcionalidades exatas implementadas no código.

## 🛠️ Tecnologias utilizadas

- **Java** — lógica da aplicação e orientação a objetos
- **PostgreSQL** — armazenamento e persistência dos dados
- **JDBC** — conexão entre a aplicação Java e o banco de dados

## ✅ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)
- Driver JDBC do PostgreSQL (`postgresql.jar`)

## 🚀 Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/Theusith/A3_psc.git
   cd A3_psc
   ```

2. Crie o banco de dados no PostgreSQL:
   ```sql
   CREATE DATABASE reservas_voo;
   ```

3. Execute o script de criação das tabelas (se houver um arquivo `.sql` no repositório, referencie-o aqui, por exemplo):
   ```bash
   psql -U seu_usuario -d reservas_voo -f script.sql
   ```

4. Configure a conexão com o banco no arquivo de configuração/classe de conexão do projeto (usuário, senha, host e porta do PostgreSQL).

5. Compile e execute a aplicação:
   ```bash
   javac Main.java
   java Main
   ```

> Ajuste os comandos acima (nome da classe principal, caminho dos arquivos, etc.) conforme a estrutura real do projeto.

## 📁 Estrutura do projeto

```
A3_psc/
├── Javadoc/            # Documentação gerada do código
├── Projeto-A3-Psc/      # Código-fonte da aplicação
└── README.md
```

## 👥 Colaboradores

Projeto desenvolvido em grupo para a disciplina de Programação de Soluções Computacionais.

- [Matheus Oliveira da Silva Costa](https://github.com/Theusith)
- *(adicione aqui os nomes/links dos demais integrantes do grupo)*

## 📚 Aprendizados

Este projeto foi uma das primeiras aplicações práticas de Programação Orientada a Objetos e integração com banco de dados relacional, unindo lógica de programação, modelagem de dados e trabalho em equipe.
