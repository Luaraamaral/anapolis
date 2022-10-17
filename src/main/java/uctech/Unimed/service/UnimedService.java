package uctech.Unimed.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import uctech.Unimed.dtos.*;
import uctech.Unimed.repository.DBConection;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UnimedService {

    private static final String PATH_SERVER = "file:///X:/0001466183-1.PDF";
    @Autowired
    private DBConection dbConection;

    public List<BeneficiarioDTO> getBeneficiarioByCpfOrCarteirinha(String cpfOrCard) {
        return dbConection.getBeneficiarioByCpfOrCarteirinha(cpfOrCard);
    }

    public void getBoletosAbertos(String cartao, String cpfTitular, HttpServletResponse response) throws Exception {

        BeneficiarioCartaoDTO beneficiarioCartaoDTO = dbConection.getBeneficiarioBoleto(cartao);

        if ("TITULAR".equals(beneficiarioCartaoDTO.getGrauDependencia().toUpperCase())) {
            if (dbConection.getBoletosAtrasadosApartir60Dias(cartao) == 0) {
                buscaPdfBaseSamba(cartao, response);
            } else {
                throw new Exception("Além de 60 dias");
            }
        } else {
            if (ObjectUtils.isEmpty(cpfTitular) ||
                    (!ObjectUtils.isEmpty(cpfTitular) && !cpfTitular.equals(beneficiarioCartaoDTO.getCpfTitular()))) {
                throw new Exception("Você não é o titular do contrato, informe o CPF do titular para continuar!");
            } else {
                buscaPdfBaseSamba(cartao, response);
            }
        }
    }

    public void buscaPdfBaseSamba(String cartaoTitular, HttpServletResponse response) throws IOException {

        BoletoPathDTO boletoPath = dbConection.getDadosBoletoPendente(cartaoTitular);

        String path = boletoPath.getPath();
        path = path.replace(".\\", "");
        path = path.replace("\\", "/");

        File file = new File(PATH_SERVER);

        PDDocument document = PDDocument.load(file);

        if (!ObjectUtils.isEmpty(document)) {
            while (document.getNumberOfPages() != 1) {
                document.removePage(1);
            }

            document.save(response.getOutputStream());
        }
    }

    public GuiaDTO getStatusGuia(String numeroGuia) {
        return dbConection.getStatusGuia(numeroGuia);
    }

    public List<EmailDTO> getEmailByCpf(String cpf) {
        return dbConection.getEmailByCpf(cpf);
    }
}
