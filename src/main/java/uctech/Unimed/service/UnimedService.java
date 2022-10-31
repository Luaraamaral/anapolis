package uctech.Unimed.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import uctech.Unimed.dtos.*;
import uctech.Unimed.exception.DataNotFoundException;
import uctech.Unimed.repository.DBConection;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UnimedService {

    private static final String PATH_SERVER = "X:/0001509039-1.PDF";
    @Autowired
    private DBConection dbConection;

    public List<BeneficiarioDTO> getBeneficiarioByCpfOrCarteirinha(String cpfOrCard) {
        return dbConection.getBeneficiarioByCpfOrCarteirinha(cpfOrCard);
    }

    public void getBoletosAbertos(String cartao, String cpfTitular, HttpServletResponse response) throws DataNotFoundException, IOException {

        BeneficiarioCartaoDTO beneficiarioCartaoDTO = dbConection.getBeneficiarioBoleto(cartao);

        if ("TITULAR".equals(beneficiarioCartaoDTO.getGrauDependencia().toUpperCase())) {
            if (dbConection.getBoletosAtrasadosApartir60Dias(cartao).intValue() == 0) {
                buscaPdfBaseSamba(cartao, response);
            } else {
                throw new DataNotFoundException("Boleto atrasado a mais de 60 dias");
            }
        } else {
            if (ObjectUtils.isEmpty(cpfTitular) ||
                    (!ObjectUtils.isEmpty(cpfTitular) && !ObjectUtils.isEmpty(beneficiarioCartaoDTO.getCpfTitular()) && !cpfTitular.equals(beneficiarioCartaoDTO.getCpfTitular()))) {
                throw new DataNotFoundException("Você não é o titular do contrato, informe o CPF do titular para continuar!");
            } else {
                buscaPdfBaseSamba(cartao, response);
            }
        }
    }

    public void buscaPdfBaseSamba(String cartaoTitular, HttpServletResponse response) throws IOException, DataNotFoundException {

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

            response.addHeader("Content-Type", "application/force-download");
            response.addHeader("Content-Disposition", "attachment; filename=\"boleto.pdf\"");

            document.save(response.getOutputStream());
        }
    }

    public GuiaDTO getStatusGuia(String numeroGuia) {
        return dbConection.getStatusGuia(numeroGuia);
    }

    public EmailDTO getEmailByCpf(String cpf) throws DataNotFoundException {
        return dbConection.getEmailByCpf(cpf);
    }

    public List<BoletoDTO> getCodigoDeBarras(String cartao) {
        return dbConection.getCodigoDeBarras(cartao);
    }

    public BeneficiarioSolicitacaoDTO getBeneficiarioSolicitacao (String cod) throws DataNotFoundException {
        return dbConection.getBeneficiarioSolicitacao(cod);
    }

    public List<ComplementoSolicitacaoDTO> getComplementoSolicitacao (String cod) throws DataNotFoundException {
        return dbConection.getComplementoSolicitacao(cod);
    }

    public ObservacaoSolicitacaoDTO getObservacaoSolicitacao (String cod) throws DataNotFoundException {
        return dbConection.getObservacaoSolicitacao(cod);
    }
}
