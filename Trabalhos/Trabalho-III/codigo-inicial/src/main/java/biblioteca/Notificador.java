package biblioteca;

public interface Notificador {

    void enviarEmail(String destino, String mensagem);

    void enviarSms(String destino, String mensagem);

    void enviarWhatsapp(String destino, String mensagem);
}
