package uctech.Unimed.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uctech.Unimed.domain.Carteirinha;

@Getter
@NoArgsConstructor
@ApiModel(value = "CarteirinhaResponseDTO")
public class CarteirinhaResponseDTO {
    @ApiModelProperty(notes = "Número da carteirinha")
    private String carteirinha;

    public CarteirinhaResponseDTO(Carteirinha carteirinha) {
        this.carteirinha = carteirinha.getCarteirinha();
    }
}
