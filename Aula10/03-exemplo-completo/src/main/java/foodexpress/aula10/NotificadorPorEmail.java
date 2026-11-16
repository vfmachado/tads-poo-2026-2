package foodexpress.aula10;

public class NotificadorPorEmail implements Notificador {

    @Override
    public void avisar(String destino, String mensagem) {
        System.out.println("[EMAIL] para " + destino + ": " + mensagem);
    }
}
