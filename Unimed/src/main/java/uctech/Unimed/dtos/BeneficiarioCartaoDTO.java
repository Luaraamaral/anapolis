package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BeneficiarioCartaoDTO {

    private String cartao;
    private String nomeCompleto;
    private String grauDependencia;
    private String tipoContrato;
    private String cpfTitular;
    private String cartaoTitular;
}
