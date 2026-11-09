package foodexpress.aula09.depois;

// [I] Interface mínima, do ponto de vista de quem USA (o serviço só precisa avisar alguém).
// SMS e push seriam OUTRAS implementações desta mesma interface, não métodos extras.
public interface Notificador {

    void avisar(String destino, String mensagem);
}
