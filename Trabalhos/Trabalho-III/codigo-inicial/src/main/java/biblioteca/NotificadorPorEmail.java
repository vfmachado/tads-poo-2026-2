package biblioteca;

public class NotificadorPorEmail implements Notificador {

    @Override
    public void enviarEmail(String destino, String mensagem) {
        // Simula o envio: num sistema real, aqui haveria um servidor SMTP.
        System.out.println("  [e-mail para " + destino + "] " + mensagem);
    }

    @Override
    public void enviarSms(String destino, String mensagem) {
        throw new UnsupportedOperationException("Este notificador não envia SMS");
    }

    @Override
    public void enviarWhatsapp(String destino, String mensagem) {
        throw new UnsupportedOperationException("Este notificador não envia WhatsApp");
    }
}
