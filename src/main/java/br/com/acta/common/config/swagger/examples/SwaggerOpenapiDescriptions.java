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

    public static final String PROBLEMA_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de problemas dos ciclos PDCA.

            A API permite:

            - Criar e consultar problemas;
            - Filtrar problemas por ciclo, status e problema pai;
            - Atualizar dados e status;
            - Excluir problemas.
            """;

    public static final String CAUSA_RAIZ_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento das causas-raiz dos ciclos PDCA.

            A API permite:

            - Criar e consultar causas-raiz;
            - Filtrar causas por ciclo, problema, aceite e indicação principal;
            - Atualizar e validar causas-raiz;
            - Excluir causas-raiz.
            """;

    public static final String PLANO_5W2H_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento do plano 5W2H de um plano de ação.

            A API permite:

            - Criar e consultar um plano 5W2H;
            - Atualizar parcialmente os dados do plano;
            - Excluir o plano 5W2H.
            """;

    public static final String USUARIO_CICLO_CONTROLLER = """
            Endpoints responsáveis pelo vínculo entre usuários e ciclos PDCA.

            A API permite:

            - Consultar os usuários de um ciclo;
            - Adicionar e remover usuários;
            - Alterar o papel de um usuário no ciclo;
            - Substituir o responsável pelo ciclo.
            """;

    public static final String PRIORIZACAO_PROBLEMA_CONTROLLER = """
            Endpoints responsáveis pela priorização de problemas.

            A API permite:

            - Consultar e registrar priorizações;
            - Atualizar os critérios de uma priorização;
            - Aplicar o peso calculado ao problema.
            """;

    public static final String TAREFA_DEPENDENTE_CONTROLLER = """
            Endpoints responsáveis pelas dependências entre tarefas.

            A API permite:

            - Consultar as dependências de uma tarefa;
            - Adicionar uma tarefa dependente;
            - Remover uma dependência.
            """;

    public static final String USUARIO_TREINAMENTO_CONTROLLER = """
            Endpoints responsáveis pelo vínculo entre usuários e treinamentos.

            A API permite:

            - Consultar os usuários de um treinamento;
            - Vincular usuários;
            - Atualizar o status do vínculo;
            - Remover usuários do treinamento.
            """;

    public static final String TREINAMENTO_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento de treinamentos dos ciclos PDCA.

            A API permite:

            - Criar e consultar treinamentos;
            - Consultar os treinamentos de um ciclo;
            - Atualizar parcialmente os dados;
            - Excluir treinamentos.
            """;

    public static final String VERIFICACAO_RESULTADO_CONTROLLER = """
            Endpoints responsáveis pelo registro e acompanhamento das verificações de resultado.

            A API permite:

            - Criar e consultar verificações;
            - Consultar as verificações de um ciclo;
            - Atualizar parcialmente os resultados;
            - Excluir verificações.
            """;

    public static final String EFEITO_SECUNDARIO_CONTROLLER = """
            Endpoints responsáveis pelos efeitos secundários de uma verificação de resultado.

            A API permite:

            - Criar e consultar efeitos secundários;
            - Atualizar parcialmente os efeitos;
            - Excluir efeitos secundários.
            """;

    public static final String ALERTA_PRAZO_CONTROLLER = """
            Endpoints responsáveis pelos alertas de prazo das tarefas.

            A API permite:

            - Consultar o alerta de uma tarefa;
            - Marcar um alerta como lido.
            """;

    public static final String ENDERECO_EMPRESA_CONTROLLER = """
            Endpoints responsáveis pelos endereços das empresas.

            A API permite:

            - Consultar os endereços de uma empresa;
            - Adicionar um endereço;
            - Excluir um endereço.
            """;

    public static final String EMAIL_CONTROLLER = """
            Endpoints responsáveis pelos e-mails de empresas e colaboradores.

            A API permite:

            - Consultar e-mails;
            - Adicionar e-mails;
            - Excluir e-mails de empresas e colaboradores.
            """;

    public static final String TELEFONE_CONTROLLER = """
            Endpoints responsáveis pelos telefones de empresas e colaboradores.

            A API permite:

            - Consultar telefones;
            - Adicionar telefones;
            - Excluir telefones de empresas e colaboradores.
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
