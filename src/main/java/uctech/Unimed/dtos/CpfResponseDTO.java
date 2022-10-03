package uctech.Unimed.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uctech.Unimed.domain.Cpf;

@Getter
@NoArgsConstructor
@ApiModel(value = "CpfResponseDTO")
public class CpfResponseDTO {
    @ApiModelProperty(notes = "Número do cpf")
    private String cpf;

    public CpfResponseDTO(Cpf cpf) {
        this.cpf = cpf.getCpf();
    }
}
