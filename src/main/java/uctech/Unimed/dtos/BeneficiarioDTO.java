package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class BeneficiarioDTO {
    private String cpf;
    private String cartao;
    private String nome;
    private String nomeCompleto;
    private Date dataNascimento;
    private Date dataExclusao;
    private Date dataInicioVigencia;
    private String grauDependencia;
    private String planoId;
    private String sexo;
}
