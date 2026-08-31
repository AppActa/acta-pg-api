package br.com.acta.common.config.swagger.examples;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SwaggerRequestExamples {
    // Compartilhados
    public static final String NOME = "Jones Prado";
    public static final String EMAIL = "jones.prado@acta.com.br";
    public static final String SENHA = "SenhaForte@123";

    public static final String PRINCIPAL = "true";
    public static final String OBRIGATORIO = "true";
    public static final String ORIGEM = "IA";
    public static final String PRIORIDADE = "ALTA";
    public static final String PESO = "0.85";

    public static final String ID_EMPRESA = "1";
    public static final String ID_USUARIO = "1";
    public static final String ID_RESPONSAVEL = "1";
    public static final String ID_PROBLEMA = "1";

    // Auth
    public static final String NOVA_SENHA = "SenhaNova@123";
    public static final String FIREBASE_UID = "HgrZsrAyeURocKW1uso0gFq01ks1";

    // Usuário
    public static final String TIPO_USUARIO = "ADMIN";

    // Colaborador
    public static final String CPF = "55652297077";
    public static final String CARGO = "Desenvolvedor";
    public static final String AREA = "TI";
    public static final String DATA_NASCIMENTO = "1990-01-01";
    public static final String DATA_CONTRATACAO = "2020-01-01";
    public static final String PERMISSAO_GESTOR = "true";

    // Contato
    public static final String TELEFONE = "11961394458";
    
    // Empresa
    public static final String CNPJ = "04816294000185";
    public static final String EMPRESA_NOME = "ACTA Ltda.";
    public static final String TAMANHO_EMPRESA = "GRANDE";
    public static final String SETOR = "Tecnologia";

    // Endereço
    public static final String CEP = "08450020";
    public static final String UF = "SP";
    public static final String CIDADE = "São Paulo";
    public static final String BAIRRO = "Lajeado";
    public static final String LOGRADOURO = "R. Getulina";
    public static final String NUMERO_ENDERECO = "170";
    public static final String COMPLEMENTO = "Vizinho do Majô Fashion";

    // Ciclo
    public static final String TITULO_CICLO = "Redução de refugo na Linha 2";
    public static final String DESCRICAO_CICLO = "Ciclo para reduzir o índice de refugo na Linha 2 de produção";
    public static final String DATA_INICIO_CICLO = "2026-05-13";
    public static final String DATA_ESTIMADA_FIM = "2026-11-06";
    public static final String ID_GESTOR = "1";
    public static final String STATUS_CICLO = "PENDENTE";

    // Usuario Ciclo
    public static final String PAPEL_CICLO = "RESPONSAVEL";

    // Priorização Problema
    public static final String POSICAO = "1";
    public static final String PESO_CALCULADO = "0.90";

    // Problema
    public static final String TITULO_PROBLEMA = "Alto índice de refugo na linha 2";
    public static final String DESCRICAO_PROBLEMA = "Peças reprovadas na inspeção final";
    public static final String PERSISTENTE = "true";
    public static final String ID_PROBLEMA_PAI = "1";

    // Causa Raiz
    public static final String DESCRICAO_CAUSA_RAIZ = "Falta de caibração periódica da máquina de corte";
    public static final String ID_5_PORQUES = "507f1f77bcf86cd799439011";

    // Plano de ação
    public static final String NOME_PLANO_ACAO = "Plano de calibração preventiva";
    public static final String OBJETIVO_PLANO_ACAO = "Eliminar refugo por descalibração";

    // 5W2H
    public static final String WHAT_ACAO = "Implementar checklist diário de calibração da máquina de corte";
    public static final String WHY_JUSTIFICATIVA = "Reduzir o refugo causado por descalibração recorrente";
    public static final String WHERE_LOCAL = "Linha 2 de produção";
    public static final String WHEN_INICIO = "2026-06-15";
    public static final String WHEN_FIM = "2026-07-15";
    public static final String HOW_MODO_EXECUCAO = "Checklist impresso preenchido a cada troca de turno";
    public static final String HOW_MUCH_CUSTO = "1500.00";

    // Tarefa
    public static final String TITULO_TAREFA = "Elaborar checklist de calibração";
    public static final String DESCRICAO_TAREFA = "Documento com passos e frequência de verificação";
    public static final String DATA_FIM_PREVISTA = "2026-06-20";
    public static final String ID_TAREFA_DEPENDENCIA = "1";
    public static final String STATUS_TAREFA = "PENDENTE";
    public static final String DATA_INICIO_REAL = "2026-06-15";
    public static final String DATA_FIM_REAL = "2026-06-20";

    // Treinamento
    public static final String TITULO_TREINAMENTO = "Treinamento de calibração de máquinas";
    public static final String DESCRICAO_TREINAMENTO = "Capacitação sobre o novo checklist de calibração da Linha 2";
    public static final String DATA_TREINAMENTO = "2026-06-25";
    public static final String ID_ANEXO_MONGO = "1";

    // Verificação do Resultado
    public static final String STATUS_VERIFICACAO = "PARCIAL";
    public static final String RESUMO_VERIFICACAO = "Refugo caiu de 18% para 9%, meta era 5%";
    public static final String OBSERVACAO_VERIFICACAO = "Necessário reforçar frequência de calibração";

    // Efeito secundário
    public static final String DESCRICAO_EFEITO_SECUNDARIO = "Redução de ruído na linha durante o corte";
    public static final String IMPACTO_ESTIMADO = "Baixo impacto positivo em conforto acústico";
    public static final String TIPO_EFEITO_SECUNDARIO = "POSITIVO";

    // Meta
    public static final String OBJETIVO_META = "Reduzir o índice de refugo na Linha 2";
    public static final String VALOR_BASE = "18.00";
    public static final String VALOR_ALVO = "5.00";
    public static final String UNIDADE_MEDIDA = "%";
    public static final String PRAZO_META = "2026-08-30";
    public static final String CATEGORIA = "Qualidade";
}