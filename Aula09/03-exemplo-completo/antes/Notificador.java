package foodexpress.aula09.antes;

// [I] Interface "gorda": quem só sabe enviar e-mail é obrigado a assinar também SMS e push.
public interface Notificador {

    void enviarEmail(String destino, String mensagem);

    void enviarSms(String telefone, String mensagem);

    void enviarPush(String dispositivo, String mensagem);
}
