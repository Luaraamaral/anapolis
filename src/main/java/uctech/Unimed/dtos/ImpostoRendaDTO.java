package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImpostoRendaDTO {
    String nomeEmpresarial;
    String cnpj;
    String nomeCompleto;
    String naturezaRendimento;
}
