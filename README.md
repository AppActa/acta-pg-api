# 🧭 ACTA API

API REST do ACTA para apoiar o ciclo **PDCA** (*Plan, Do, Check, Act*): organiza problemas, ações, execução, verificações e aprendizado contínuo.

## 📌 Visão geral

A API é a camada de persistência, regras de negócio e autorização do ecossistema ACTA. Gerencia empresas, pessoas, ciclos PDCA, problemas, causas-raiz, planos de ação, 5W2H, tarefas, metas, treinamentos, verificações de resultado, alertas e efeitos secundários.

Clientes autenticam no Firebase e enviam o **Firebase ID Token** para consumir as rotas protegidas.

## ✨ Funcionalidades

- Gestão de empresas, usuários, colaboradores e contatos.
- Ciclos PDCA, participantes, problemas, priorização e causas-raiz.
- Planos de ação, 5W2H, tarefas, dependências, metas e treinamentos.
- Verificação de resultados, efeitos secundários e alertas de prazo.
- Health check da API e do banco em `GET /health`.

## 🛠️ Tecnologias

| Tecnologia | Versão |
| --- | --- |
| Java | 17 |
| Spring Boot | 4.1.0 |
| PostgreSQL | Driver runtime; versão do servidor não definida |
| Firebase Admin SDK | 9.10.0 |
| Springdoc OpenAPI | 3.0.0 |
| MapStruct | 1.6.3 |
| Docker | Dockerfile presente |

Também utiliza Maven Wrapper, Lombok, Argon2, Caelum Stella e libphonenumber.

## ✅ Pré-requisitos e configuração

- JDK 17 e PostgreSQL acessível.
- Esquema de banco compatível com as entidades: a aplicação usa `spring.jpa.hibernate.ddl-auto=validate` e não cria tabelas.
- Projeto Firebase e credenciais de servidor disponíveis via *Application Default Credentials*.
- Docker, caso a execução seja por imagem.

Defina as variáveis abaixo no ambiente ou em um `.env` local. Nunca versione senhas, tokens ou arquivos de credencial.

```env
DB_URL=jdbc:postgresql://localhost:5432/acta
DB_USER=seu_usuario_local
DB_PASSWORD=sua_senha_local
FIREBASE_PROJECT_ID=seu-projeto-firebase
```

| Variável | Uso |
| --- | --- |
| `DB_URL`, `DB_USER`, `DB_PASSWORD` | Conexão PostgreSQL |
| `FIREBASE_PROJECT_ID` | Propriedade `firebase.project-id` da aplicação |
| `GOOGLE_APPLICATION_CREDENTIALS` | Caminho da credencial de servidor, quando aplicável ao ambiente local |

O [`.env.example`](.env.example) é somente uma referência; `PEPPER` não é lida pelo `application.properties` atual.

## 🚀 Execução local

```powershell
.\mvnw.cmd spring-boot:run
```

```bash
./mvnw spring-boot:run
```

A API usa a porta `8080`. Com banco e Firebase configurados, consulte `http://localhost:8080/health`.

### Docker

Para criar e executar a imagem:

```powershell
docker build -t acta-pg-api .
docker run --rm -p 8080:8080 --env-file .env acta-pg-api
```

Ao executar em contêiner, também disponibilize as credenciais de servidor do Firebase de forma segura; não as copie para a imagem.

## 📚 Documentação e autenticação

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`
- Público: `GET /health`, Swagger UI e OpenAPI.
- Demais rotas: `Authorization: Bearer <FIREBASE_ID_TOKEN>`.

Após o login Firebase, use `POST /auth/ativar` para vincular uma identidade a um usuário ACTA existente, ativo e com e-mail verificado. Consulte `GET /me` para obter o contexto autenticado.

> Custom token, refresh token e senha **não** são Firebase ID Tokens e não devem ser usados no cabeçalho `Authorization`.

## 🔌 Endpoints principais

Todas as rotas abaixo exigem Firebase ID Token, exceto `GET /health`.

| Domínio | Rotas |
| --- | --- |
| Sessão | `POST /auth/ativar`, `GET /me` |
| Empresa | `GET/POST /empresa`, `GET/PATCH/DELETE /empresa/{id}`; endereços, e-mails e telefones em `/empresa/{idEmpresa}/...` |
| Pessoas | `GET/POST /colaborador`, `GET/PATCH/DELETE /colaborador/{id}`; `POST /usuario`, `GET/PATCH/DELETE /usuario/{id}` |
| Ciclo | `GET/POST /ciclo`, `GET/PATCH/DELETE /ciclo/{id}`, `PATCH /ciclo/{id}/status`, participantes em `/ciclo/{idCiclo}/usuario` |
| Problemas | `/ciclo/{idCiclo}/problema`, `/ciclos/{idCiclo}/problema`, `/problema/{id}`, `/problema/{idProblema}/priorizacao`, `/ciclos/{idCiclo}/causas-raiz`, `/causas-raiz/{id}` |
| Planos e execução | `/ciclo/{idCiclo}/plano-acao`, `/plano-acao/{id}`, `/plano-acao/{idPlanoAcao}/5w2h`, `/plano-acao/{idPlanoAcao}/tarefa`, `/tarefa/{id}` |
| Acompanhamento | `/ciclos/{idCiclo}/meta`, `/meta/{id}`, `/ciclo/{idCiclo}/treinamento`, `/treinamento/{id}`, `/tarefa/{idTarefa}/alerta` |
| Verificação | `/ciclos/{idCiclo}/verificacoes`, `/verificacoes/{id}`, `/verificacao/{idResultado}/efeito-secundario` |

O Swagger contém métodos, parâmetros, enumerações, esquemas e todas as rotas derivadas dos controllers.

## 💻 Exemplos

```bash
curl http://localhost:8080/health
```

```bash
curl -X POST http://localhost:8080/ciclo \
  -H "Authorization: Bearer <FIREBASE_ID_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Redução de retrabalho",
    "descricao": "Ciclo para reduzir retrabalho na conferência.",
    "dataInicio": "2026-09-10",
    "dataEstimadaFim": "2026-11-30",
    "idEmpresa": 4,
    "idGestor": 12
  }'
```

Resposta de saúde, com dados fictícios:

```json
{
  "status": "UP",
  "banco": "UP",
  "mensagem": "O CATO verificou: a API e o banco estão funcionando!",
  "verificadoEm": "2026-09-01T10:30:00-03:00"
}
```

## ⚠️ Erros

O formato confirmado é:

```json
{
  "mensagens": ["campo: mensagem de validação"],
  "httpStatus": 400,
  "timestamp": "2026-09-01T10:35:00"
}
```

| Código | Uso |
| --- | --- |
| 400 | Requisição, parâmetros ou validação inválidos |
| 401 / 403 | Token inválido/ausente ou acesso negado |
| 404 / 405 | Recurso ou método inexistente |
| 409 / 422 | Conflito de dados ou regra de negócio |
| 415 / 500 / 503 | Conteúdo não suportado, erro interno ou health check indisponível |

## 🏗️ Arquitetura

- `controller/`: rotas HTTP e DTOs.
- `service/`: regras, transações e autorização; `service/base/BaseService.java` concentra CRUD comum.
- `repository/`: acesso JPA.
- `entity/`: modelos `core`, `pdca`, `join` e `base`.
- `dto/` e `dto/mapper/`: contratos e mapeamento MapStruct.
- `common/`: segurança, validação, patch, Swagger e erros.

A API é stateless. `common/config/security/SecurityConfig.java` registra `FirebaseAuthFilter`, enquanto `FirebaseUtils` valida token, revogação e status de usuário, empresa e colaborador. `AuthService` e `@PreAuthorize` nos serviços aplicam regras de proprietário, papel e escopo da empresa.

## 📁 Estrutura

```text
src/main/java/br/com/acta/
├── common/       # segurança, erros e configurações
├── controller/   # endpoints
├── dto/          # contratos e mapeadores
├── entity/       # entidades e enums
├── repository/   # dados
└── service/      # domínio
```

## 🤝 Links e autoria

- [Repositório](https://github.com/AppActa/acta-pg-api) · [Licença MIT](LICENSE) · `acta.institutojef@gmail.com`
- Contribuições: use *issues* e *pull requests*; há um [`PULL_REQUEST_TEMPLATE.md`](PULL_REQUEST_TEMPLATE.md).
- Autoria: Equipe ACTA.