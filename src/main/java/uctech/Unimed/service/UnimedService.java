package uctech.Unimed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uctech.Unimed.dtos.BeneficiarioDTO;
import uctech.Unimed.dtos.BoletoDTO;
import uctech.Unimed.dtos.GuiaDTO;
import uctech.Unimed.repository.DBConection;

import java.util.List;

@Service
public class UnimedService {
    @Autowired
    private DBConection dbConection;

    public List<BeneficiarioDTO> getBeneficiarioByCpfOrCarteirinha(String cpfOrCard) {
        return dbConection.getBeneficiarioByCpfOrCarteirinha(cpfOrCard);
    }

    public List<BoletoDTO> getBoletosAbertos(String cartao) {
        return dbConection.getBoletosAbertos(cartao);
    }

    public GuiaDTO getStatusGuia(String numeroGuia) {
        return dbConection.getStatusGuia(numeroGuia);
    }

}
