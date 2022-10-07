package uctech.Unimed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uctech.Unimed.dtos.BeneficiarioDTO;
import uctech.Unimed.dtos.BoletoDTO;
import uctech.Unimed.dtos.GuiaDTO;
import uctech.Unimed.repository.DBConection;
import uctech.Unimed.service.UnimedService;

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
    public ResponseEntity<List<BoletoDTO>> getBoletosAbertos(@RequestParam("cartao") String cartao) {
        List<BoletoDTO> boleto = unimedService.getBoletosAbertos(cartao);
        if (ObjectUtils.isEmpty(boleto)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(boleto);
    }

    @GetMapping("/getStatusGuia")
    public ResponseEntity<GuiaDTO> getStatusGuia(@RequestParam("numeroGuia") String numeroGuia) {
        GuiaDTO guiaDTO = unimedService.getStatusGuia(numeroGuia);
        if (ObjectUtils.isEmpty(guiaDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(guiaDTO);
    }


}
