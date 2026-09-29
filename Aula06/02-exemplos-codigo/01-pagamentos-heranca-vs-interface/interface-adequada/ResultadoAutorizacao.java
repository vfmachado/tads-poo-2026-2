package aula06.pagamentos.interfaces;

public record ResultadoAutorizacao(boolean aprovado, String codigoAutorizacao, String motivoRecusa) {

    public ResultadoAutorizacao {
        if (aprovado && (codigoAutorizacao == null || codigoAutorizacao.isBlank())) {
            throw new IllegalArgumentException("Autorização aprovada precisa de um código.");
        }
        if (!aprovado && (motivoRecusa == null || motivoRecusa.isBlank())) {
            throw new IllegalArgumentException("Autorização recusada precisa de um motivo.");
        }
    }

    public static ResultadoAutorizacao aprovado(String codigoAutorizacao) {
        return new ResultadoAutorizacao(true, codigoAutorizacao, null);
    }

    public static ResultadoAutorizacao recusado(String motivo) {
        return new ResultadoAutorizacao(false, null, motivo);
    }
}
