package uctech.Unimed.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uctech.Unimed.domain.Email;

@Getter
@NoArgsConstructor
@ApiModel(value = "EmailResponseDTO")
public class EmailResponseDTO {

    @ApiModelProperty(notes = "Endereço de e-mail")
    private String email;

    public EmailResponseDTO(Email email) {
        this.email = email.getEmail();
    }
}
