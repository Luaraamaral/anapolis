package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ComplementoSolicitacaoDTO {
    private String item;
    private String complemento;
    private String qtdSolic;
    private String qtdAut;
    private String situacao;
}
