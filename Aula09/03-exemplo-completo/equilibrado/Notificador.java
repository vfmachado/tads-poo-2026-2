package foodexpress.aula09.equilibrado;

// DIP/ISP mantidos AQUI porque a variação é real (e-mail hoje, SMS e push já pedidos pelo negócio)
// e porque é a fronteira com o mundo externo (o teste precisa de um dublê).
public interface Notificador {

    void avisar(String destino, String mensagem);
}
