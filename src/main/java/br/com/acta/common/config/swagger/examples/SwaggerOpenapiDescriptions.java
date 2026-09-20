package br.com.acta.common.config.swagger.examples;

public final class SwaggerOpenapiDescriptions {
    public static final String AUTH_CONTROLLER = """
            Endpoints responsáveis pela autenticação e ativação de usuários.

            A API permite:

            - Consultar os dados do usuário autenticado;
            - Ativar um usuário a partir de uma identidade do Firebase.
            """;

    public static final String HEALTH_CONTROLLER = """
            Endpoint responsável por verificar a disponibilidade da API e do banco de dados.

            A verificação informa se a aplicação está disponível e se a conexão com o banco de dados está funcionando.
            """;

    public static final String USUARIO_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de usuários.

            A API permite:

            - Criar e consultar usuários;
            - Filtrar usuários por empresa e tipo;
            - Consultar os ciclos de um usuário;
            - Atualizar dados e remover a foto de perfil;
            - Inativar um usuário.
            """;

    public static final String EMPRESA_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de empresas.

            A API permite:

            - Criar e consultar empresas;
            - Filtrar empresas por tamanho;
            - Atualizar parcialmente os dados de uma empresa;
            - Inativar uma empresa.
            """;

    public static final String COLABORADOR_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de colaboradores.

            A API permite:

            - Criar e consultar colaboradores;
            - Consultar colaboradores de uma empresa;
            - Atualizar parcialmente os dados de um colaborador;
            - Inativar um colaborador.
            """;

    public static final String TAREFA_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de tarefas dos planos de ação.

            A API permite:

            - Criar e consultar tarefas;
            - Filtrar tarefas por status, responsável e prioridade;
            - Atualizar dados e status;
            - Reabrir e reatribuir tarefas;
            - Excluir tarefas.
            """;

    public static final String META_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de metas do ciclo PDCA.

            A API permite:

            - Criar e consultar metas;
            - Filtrar metas por ciclo, status e prioridade;
            - Atualizar dados e status;
            - Gerenciar os responsáveis por uma meta;
            - Excluir metas.
            """;

    public static final String PLANO_ACAO_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento dos planos de ação de um ciclo PDCA.

            A API permite:

            - Criar e consultar planos de ação;
            - Filtrar planos por ciclo, status e prioridade;
            - Atualizar dados e status;
            - Excluir planos de ação.
            """;

    public static final String CICLO_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento dos ciclos PDCA.
            
            Um ciclo representa a execução completa ou parcial das fases Plan, Do, Check e Act dentro de uma empresa.
            
            A API permite:
            
            - Criar um novo ciclo;
            - Consultar ciclos;
            - Filtrar ciclos por empresa, gestor e status;
            - Atualizar parcialmente dados de um ciclo aberto;
            - Controlar a progressão de status;
            - Cancelar um ciclo.
            
            (* Algumas operações dependem do status atual do ciclo. Ciclos encerrados, concluídos ou cancelados podem ter sua alteração bloqueada pelas regras de negócio)
            """;
}
