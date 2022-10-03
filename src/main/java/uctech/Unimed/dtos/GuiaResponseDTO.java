package uctech.Unimed.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uctech.Unimed.domain.Guia;

@Getter
@NoArgsConstructor
@ApiModel(value = "GuiaResponseDTO")
public class GuiaResponseDTO {
    @ApiModelProperty(notes = "Número da guia")
    private String numero;
    @ApiModelProperty(notes = "Retorna mensagem atrelada ao número da guia")
    private String statusGuia;

    public GuiaResponseDTO(Guia guia) {
        this.numero = guia.getNumero();
        this.statusGuia = guia.getStatusGuia();
    }

}
