package uctech.Unimed.controller;

import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uctech.Unimed.dtos.*;
import uctech.Unimed.exception.DataNotFoundException;
import uctech.Unimed.repository.DBConection;
import uctech.Unimed.service.UnimedService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@CrossOrigin("*")
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
    public ResponseEntity<?> getBoletosAbertos(@RequestParam("cartao") String cartao, @RequestParam(value = "cpf", required = false) String cpf, HttpServletResponse response) throws DataNotFoundException, IOException {

        unimedService.getBoletosAbertos(cartao, cpf, response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getStatusGuia")
    public ResponseEntity<GuiaDTO> getStatusGuia(@RequestParam("numeroGuia") String numeroGuia) {
        GuiaDTO guiaDTO = unimedService.getStatusGuia(numeroGuia);
        if (ObjectUtils.isEmpty(guiaDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(guiaDTO);
    }

    @GetMapping("/getEmail")
    public ResponseEntity<EmailDTO> getEmailByCpf(@RequestParam("cpf") String cpf) throws DataNotFoundException {
        EmailDTO emailDTO = unimedService.getEmailByCpf(cpf);
        if (ObjectUtils.isEmpty(emailDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(emailDTO);
    }


    @GetMapping("/getCodigoDeBarras")
    public ResponseEntity<List<BoletoDTO>> getCodigoDeBarras(@RequestParam("cartao") String cartao) throws DataNotFoundException {
        List<BoletoDTO> boletoDTO = unimedService.getCodigoDeBarras(cartao);
        if (ObjectUtils.isEmpty(boletoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(boletoDTO);
    }
    @GetMapping(value = "/imposto/{cpfOrCartao}")
    public ModelAndView listarData(@PathVariable("cpfOrCartao") String cpfOrCartao){
        ModelAndView andView = new ModelAndView("impostoModel");
        return andView;
    }
    @GetMapping("/getBeneficiarioSolicitacao")
    public ResponseEntity<BeneficiarioSolicitacaoDTO> getBeneficiarioSolicitacao (@RequestParam("cod") String cod) throws DataNotFoundException {
        BeneficiarioSolicitacaoDTO beneficiarioSolicitacaoDTO = dbConection.getBeneficiarioSolicitacao(cod);
        if (ObjectUtils.isEmpty(beneficiarioSolicitacaoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(beneficiarioSolicitacaoDTO);
    }

    @GetMapping("/getComplementoSolicitacao")
    public ResponseEntity<List<ComplementoSolicitacaoDTO>> getComplementoSolicitacao (@RequestParam("cod") String cod) throws DataNotFoundException {
        List<ComplementoSolicitacaoDTO> complementoSolicitacaoDTO = dbConection.getComplementoSolicitacao(cod);
        if (ObjectUtils.isEmpty(complementoSolicitacaoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(complementoSolicitacaoDTO);
    }

    @GetMapping("/getObservacaoSolicitacao")
    public ResponseEntity<ObservacaoSolicitacaoDTO> getObservacaoSolicitacao (@RequestParam("cod") String cod) throws DataNotFoundException {
        ObservacaoSolicitacaoDTO observacaoSolicitacaoDTO = dbConection.getObservacaoSolicitacao(cod);
        if (ObjectUtils.isEmpty(observacaoSolicitacaoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(observacaoSolicitacaoDTO);
    }
    @GetMapping(value = "/lembrete/{card}")
    public ModelAndView Lembrete(@PathVariable("card") String card){
        ModelAndView andView = new ModelAndView("LembreteSolicitacao.html");
        return andView;
    }
}