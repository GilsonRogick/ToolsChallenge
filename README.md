# ToolsChallenge - API de Pagamentos

## Visão Geral

O **ToolsChallenge** é uma API de pagamentos desenvolvida para um banco, especificamente para a área de cartões de
crédito. A API oferece funcionalidades para criação de pagamentos, estornos, e consultas de transações. Foi desenvolvida
como parte de uma demanda para o Time Elite, utilizando conceitos de REST, JSON e o protocolo HTTP.

## Como Executar o Projeto
### Pré-requisitos
- **Java 11+**
- **Maven**

## Funcionalidades Principais

1. **Pagamento**:
    - Endpoint para criação de um novo pagamento.
    - Recebe uma solicitação JSON e retorna uma resposta com o status da transação.

2. **Estorno**:
    - Endpoint para estornar uma transação existente.
    - A consulta pode ser feita por ID da transação.
    - Retorna um JSON com os detalhes do estorno.

3. **Consulta**:
    - Endpoints para consultar todas as transações ou uma transação específica por ID.
    - Retorna um JSON com os detalhes das transações.

## Requisitos das Transações

- **ID da Transação**: Deve ser único.
- **Status da Transação**: Pode ser "AUTORIZADO", "NEGADO", ou "CANCELADO".
- **Forma de Pagamento**: Pode ser "AVISTA", "PARCELADO LOJA", ou "PARCELADO EMISSOR".

## Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada.
- **Spring Boot**: Framework para criação da API REST.
- **Spring Data JPA**: Para persistência de dados.
- **PostgreSQL**: Banco de dados.
- **Maven**: Gerenciador de dependências.
- **JUnit com Mockito**: Para testes unitários.
- **Git**: Controle de versionamento.

## Endpoints da API

### Pagamento

- **Descrição**: Cria uma nova transação de pagamento.
- **Endpoint**: `POST /transactions/makePayment`

### Estorno

- **Descrição**: Estorna uma transação existente.
- **Endpoint**: `PUT /transactions/refundTransaction/{id}`

## Consultas de estorno

### Consulta por ID:

- **Descrição**: Consulta uma transação de estorno por ID.
- **Endpoint**: `GET /transactions/selectRefundedTransactionById/{id}`

### Consulta todos

- **Descrição**: Consulta todas as transações de estorno.
- **Endpoint**:  `GET /transactions/selectAllRefundedTransaction`

## Consultas de transações autorizadas

### Consulta por ID:

- **Descrição**: Consulta todas as transações autorizadas.
- **Endpoint**: `GET /transactions/selectAuthorizedTransactionById/{id}`

### Consulta todos

- **Descrição**: Consulta uma transação autorizadas por ID
- **Endpoint**:  `GET /transactions/selectAllAuthorizedTransactions`