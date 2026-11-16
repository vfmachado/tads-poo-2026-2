package exemplos.dubles;

import java.time.LocalTime;

// A hora atual é uma dependência EXTERNA como outra qualquer: muda sozinha e o teste não a controla.
// Escondê-la atrás de uma interface (DIP, Aula 09) é o que permite testar "às 22:59" às três da tarde.
public interface Relogio {

    LocalTime agora();
}
