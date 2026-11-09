package foodexpress.aula09.antes;

public class NotificadorPorEmail implements Notificador {

    @Override
    public void enviarEmail(String destino, String mensagem) {
        System.out.println("[EMAIL] para " + destino + ": " + mensagem);
    }

    // [I] + [L] Métodos que esta classe não sabe cumprir. Quem receber um Notificador e chamar
    // enviarSms vai levar uma exceção: o subtipo NÃO é substituível pelo tipo que declara o contrato.
    @Override
    public void enviarSms(String telefone, String mensagem) {
        throw new UnsupportedOperationException("este notificador nao envia SMS");
    }

    @Override
    public void enviarPush(String dispositivo, String mensagem) {
        throw new UnsupportedOperationException("este notificador nao envia push");
    }
}
