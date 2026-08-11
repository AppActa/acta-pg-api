package br.com.acta.common.config.swagger.examples;

public final class SwaggerOpenapiDescriptions {
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
