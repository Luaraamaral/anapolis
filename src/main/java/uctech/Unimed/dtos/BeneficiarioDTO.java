package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BeneficiarioDTO {
    private String cpf;
    private String cartao;
    private String nome;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private LocalDate dataExclusao;
    private LocalDate dataInicioVigencia;
    private String grauDependencia;
    private String planoId;
    private String sexo;
}
