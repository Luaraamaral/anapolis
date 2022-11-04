package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ObservacaoSolicitacaoDTO {
    private String observacao;
    private Date dataDeNasc;
    private String indicacaoClinica;

}
