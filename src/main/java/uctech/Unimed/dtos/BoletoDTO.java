package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BoletoDTO {
    private String competenciaSegundaVia;
    private LocalDate vencimentoSegundaVia;
    private String valorSegundaVia;
    private String codigoBarras;

}






