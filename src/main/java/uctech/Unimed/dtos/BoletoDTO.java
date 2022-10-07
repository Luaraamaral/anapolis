package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
public class BoletoDTO {
    private BigDecimal competenciaSegundaVia;
    private String vencimentoSegundaVia;
    private String valorSegundaVia;
    private String codigoBarras;
}
