package br.com.acta.common.config.swagger.examples;

public final class SwaggerResponseExamples {
    public static final String ERRO_400 = """
            {
              "mensagens": [
                "Um parâmetro obrigatório não foi informado"
              ],
              "httpStatus": 400,
              "timestamp": "2026-08-06T12:07:00"
            }
            """;

    public static final String ERRO_404 = """
            {
              "mensagens": [
                "O recurso solicitado não foi encontrado"
              ],
              "httpStatus": 404,
              "timestamp": "2026-08-06T12:07:00"
            }
            """;

    public static final String ERRO_409 = """
            {
              "mensagens": [
                "Não foi possível realizar a operação por conflito com os dados existentes"
              ],
              "httpStatus": 409,
              "timestamp": "2026-08-06T12:07:00"
            }
            """;
}