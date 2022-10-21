package uctech.Unimed.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
public class ImpostoRendaDTO {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    String nomeEmpresarial;
    String cnpj;
    @Column(name = "NOME_BENEFICIARIO")
    String nomeCompleto;
    @Column(name = "NOME_BENEFICIARIO")
    String naturezaRendimento;
    @Column(name = "CPF")
    String cpf;
}
