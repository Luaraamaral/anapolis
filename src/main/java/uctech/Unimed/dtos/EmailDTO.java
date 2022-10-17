package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailDTO {
    private String cartao;
    private String nome;
    private String cpf;
    private String email;
    private String emailAlternativo;

}
