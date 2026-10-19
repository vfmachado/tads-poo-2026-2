package foodexpress.aula08.depois;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public final class CatalogoDeCupons {

    private static final CodigoDeCupom VALE10 = new CodigoDeCupom("VALE10");
    private static final CodigoDeCupom FRETEGRATIS = new CodigoDeCupom("FRETEGRATIS");

    private static final Map<CodigoDeCupom, Cupom> CUPONS = Map.of(
            CodigoDeCupom.NENHUM, new SemCupom(),
            VALE10, new CupomPercentual(
                    new ExigenciaDeSubtotal(VALE10, Dinheiro.reais("100.00")), new BigDecimal("0.10")),
            FRETEGRATIS, new CupomFreteGratis(
                    new ExigenciaDeSubtotal(FRETEGRATIS, Dinheiro.reais("60.00"))));

    private CatalogoDeCupons() {
    }

    public static Cupom para(CodigoDeCupom codigo) {
        return Optional.ofNullable(CUPONS.get(codigo))
                .orElseThrow(() -> new IllegalArgumentException("Cupom desconhecido: " + codigo));
    }
}
