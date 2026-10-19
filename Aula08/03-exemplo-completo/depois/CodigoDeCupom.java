package foodexpress.aula08.depois;

// [R3] Encapsula a String do cupom. Como record, ganha equals/hashCode e
// pode ser chave de Map. Código ausente (null ou em branco) vira o código
// vazio, evitando que null circule pelo sistema.
public record CodigoDeCupom(String texto) {

    public static final CodigoDeCupom NENHUM = new CodigoDeCupom(null);

    public CodigoDeCupom {
        texto = (texto == null) ? "" : texto.strip();
    }

    @Override
    public String toString() {
        return texto;
    }
}
