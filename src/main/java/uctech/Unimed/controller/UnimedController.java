package uctech.Unimed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uctech.Unimed.dtos.BeneficiarioDTO;
import uctech.Unimed.dtos.EmailDTO;
import uctech.Unimed.dtos.GuiaDTO;
import uctech.Unimed.repository.DBConection;
import uctech.Unimed.service.UnimedService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UnimedController {

    @Autowired
    private DBConection dbConection;

    @Autowired
    private UnimedService unimedService;

    @GetMapping("/getBeneficiario")
    public ResponseEntity<List<BeneficiarioDTO>> getBeneficiario(@RequestParam("cpfOrCard") String cpfOrCard) {
        List<BeneficiarioDTO> beneficiarios = unimedService.getBeneficiarioByCpfOrCarteirinha(cpfOrCard);
        if (ObjectUtils.isEmpty(beneficiarios)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(beneficiarios);
    }

    @GetMapping("/getBoletosAbertos")
    public void getBoletosAbertos(@RequestParam("cartao") String cartao, @RequestParam(value = "cpf", required = false) String cpf, HttpServletResponse response) throws Exception {

        response.addHeader("Content-Type", "application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"boleto.pdf\"");

        unimedService.getBoletosAbertos(cartao, cpf, response);

    }

    @GetMapping("/getStatusGuia")
    public ResponseEntity<GuiaDTO> getStatusGuia(@RequestParam("numeroGuia") String numeroGuia) {
        GuiaDTO guiaDTO = unimedService.getStatusGuia(numeroGuia);
        if (ObjectUtils.isEmpty(guiaDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(guiaDTO);
    }

//    @GetMapping("/getPDF")
//    public void downloadPDFReport(HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Type", "application/force-download");
//        response.addHeader("Content-Disposition", "attachment; filename=\"boleto.pdf\"");
//
//        unimedService.buscaPdfBaseSamba("01787580010506007", response);
//    }

    @GetMapping("/getEmail")
    public ResponseEntity<List<EmailDTO>> getEmailByCpf(@RequestParam("cpf") String cpf) {
        List<EmailDTO> emailDTO = unimedService.getEmailByCpf(cpf);
        if (ObjectUtils.isEmpty(emailDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(emailDTO);
    }

}
