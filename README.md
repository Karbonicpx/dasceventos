# DASCEventos

Sistema de divulgação e inscrição em eventos de extensão do **DASC** (Diretório Acadêmico de
Engenharia de Computação e Sistemas de Informação da UTFPR, Campus Curitiba).

Projeto desenvolvido para a disciplina de Projeto e Análise de Algoritmos, com o professor
Laudelino Cordeiro Bastos, pelos alunos Nicolas Almeida de Paula, Jean Marco Bucciarelli Thesing
e Felipe Naoto Takiguchi.

## Stack

- **Backend:** Java 17 + Spring Boot (Web, Data JPA, Validation) + MySQL
- **Frontend:** Angular 19 (standalone components) + TypeScript

## Estrutura

```
backend/   API REST em Spring Boot
frontend/  Aplicação Angular
```

## Como executar

### Backend

1. Tenha um MySQL rodando localmente (usuário `root`, senha `root`, ou ajuste
   `backend/src/main/resources/application.properties`). O banco `dasceventos` é criado
   automaticamente na primeira execução.
2. Na pasta `backend/`, execute:

   ```bash
   ./mvnw spring-boot:run
   ```

   A API sobe em `http://localhost:8080`.

   Um administrador padrão é criado automaticamente na primeira execução:
   - e-mail: `dasc@utfpr.edu.br`
   - senha: `dasc123`

### Frontend

1. Na pasta `frontend/`, instale as dependências e inicie o servidor de desenvolvimento:

   ```bash
   npm install
   npm start
   ```

2. Acesse `http://localhost:4200`.

## Principais funcionalidades

- Cadastro e login de alunos
- Login da diretoria do DASC (administrador)
- Listagem e detalhamento de eventos e suas atividades
- Inscrição e cancelamento de inscrição em eventos (com controle de vagas)
- Cadastro, edição e exclusão de eventos pela diretoria
- Listagem de inscritos por evento
