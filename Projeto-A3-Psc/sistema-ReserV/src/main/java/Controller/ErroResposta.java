package Controller;

/**
 * Representa o corpo padrão de uma resposta de erro da API,
 * garantindo que toda falha (400, 404, 401, etc.) devolva um JSON
 * consistente, em vez de texto puro ou corpo vazio.
 */
public class ErroResposta {
    private String mensagem;

    public ErroResposta(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}