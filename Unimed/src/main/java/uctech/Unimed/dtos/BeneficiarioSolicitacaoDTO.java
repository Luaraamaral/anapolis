package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BeneficiarioSolicitacaoDTO {
    private String beneficiario;
    private String nomeCompleto;
    private String solicitante;
    private String solicitacao;
    private String validade;
}
