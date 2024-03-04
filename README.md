BCB - Big Chat Brasil
O BCB (Big Chat Brasil) é uma plataforma para envio de mensagens, incluindo SMS e WhatsApp, que permite que os clientes enviem mensagens para seus usuários finais. Esta aplicação foi desenvolvida utilizando tecnologias Java, Spring Boot, Spring JPA, PostgreSQL e Docker.

Requisitos do Sistema
Java JDK 8 ou superior
Docker
Configuração do Banco de Dados PostgreSQL
O BCB utiliza um banco de dados PostgreSQL para armazenar os clientes, mensagens enviadas e informações de consumo de SMS. Para executar o PostgreSQL via Docker, siga estas etapas:

Instale o Docker em sua máquina, se ainda não estiver instalado. Consulte aqui para instruções de instalação.

Execute o seguinte comando para iniciar um contêiner PostgreSQL:

css
Copy code
docker run --name bcb-postgres -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=bcb_database -p 5432:5432 -d postgres
Este comando criará e iniciará um contêiner PostgreSQL com as configurações especificadas.

Executando a Aplicação
Clone este repositório para o seu ambiente de desenvolvimento:

bash
Copy code
git clone https://github.com/seu-usuario/bcb.git
Navegue até o diretório clonado:

bash
Copy code
cd bcb
Compile e inicie a aplicação usando o Maven:

arduino
Copy code
./mvnw spring-boot:run
Isso iniciará a aplicação Spring Boot.

A aplicação estará disponível em http://localhost:8080.

API REST
A aplicação fornece uma API REST para operações como inserir créditos, consultar saldo, alterar limite, alterar plano e visualizar dados do cliente. Aqui estão os endpoints disponíveis:

PUT /api/clientes/{id}/inserirCreditos: Adiciona créditos à conta de um cliente.
GET /api/clientes/{id}/consultaSaldo: Consulta o saldo disponível na conta de um cliente.
PUT /api/clientes/{id}/alterarLimite: Altera o limite de consumo mensal de um cliente.
PUT /api/clientes/{id}/alterarPlano: Altera o plano de pagamento de um cliente.
GET /api/clientes/{id}: Retorna os dados de um cliente específico.

JSON completo para cada endpoint:

Cadastrar Cliente:

Rota: POST /api/clientes/cadastrar
JSON de requisição:
json
Copy code
{
  "nome": "Nome do Cliente",
  "email": "cliente@example.com",
  "telefone": "(99) 99999-9999",
  "cpf": "123.456.789-00",
  "cnpj": "12.345.678/0001-90",
  "nomeEmpresa": "Nome da Empresa"
}

Inserir Créditos:
Rota: PUT /api/clientes/{id}/inserirCreditos?creditos={valor}

Consultar Saldo:
Rota: GET /api/clientes/{id}/consultaSaldo

Alterar Limite:
Rota: PUT /api/clientes/{id}/alterarLimite?novoLimite={valor}

Alterar Plano:
Rota: PUT /api/clientes/{id}/alterarPlano?novoPlano={plano}

Consultar Cliente:
Rota: GET /api/clientes/{id}
