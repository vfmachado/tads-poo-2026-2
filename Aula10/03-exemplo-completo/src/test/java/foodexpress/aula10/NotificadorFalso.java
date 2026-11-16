package foodexpress.aula10;

import java.util.ArrayList;
import java.util.List;

// DUBLÊ DE TESTE (do tipo "espião"): implementa a mesma interface que o NotificadorPorEmail, mas em vez
// de enviar, GUARDA o que recebeu para o teste conferir depois.
//
// Só é possível porque o ServicoDePedido recebe um Notificador pelo construtor (DIP, Aula 09).
// Mora em src/test/java: existe para os testes e nunca vai para produção.
class NotificadorFalso implements Notificador {

    record Aviso(String destino, String mensagem) {
    }

    private final List<Aviso> avisos = new ArrayList<>();

    @Override
    public void avisar(String destino, String mensagem) {
        avisos.add(new Aviso(destino, mensagem));
    }

    List<Aviso> avisos() {
        return List.copyOf(avisos);
    }
}
