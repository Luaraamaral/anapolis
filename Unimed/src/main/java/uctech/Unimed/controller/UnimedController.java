package uctech.Unimed.controller;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
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
    public ModelAndView listarData(@PathVariable("cpfOrCartao") String cpfOrCartao) {
        ModelAndView andView = new ModelAndView("impostoModel");
        return andView;
    }

    @GetMapping("/getBeneficiarioSolicitacao")
    public ResponseEntity<BeneficiarioSolicitacaoDTO> getBeneficiarioSolicitacao(@RequestParam("cod") String cod) throws DataNotFoundException {
        BeneficiarioSolicitacaoDTO beneficiarioSolicitacaoDTO = dbConection.getBeneficiarioSolicitacao(cod);
        if (ObjectUtils.isEmpty(beneficiarioSolicitacaoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(beneficiarioSolicitacaoDTO);
    }

    @GetMapping("/getComplementoSolicitacao")
    public ResponseEntity<List<ComplementoSolicitacaoDTO>> getComplementoSolicitacao(@RequestParam("cod") String cod) throws DataNotFoundException {
        List<ComplementoSolicitacaoDTO> complementoSolicitacaoDTO = dbConection.getComplementoSolicitacao(cod);
        if (ObjectUtils.isEmpty(complementoSolicitacaoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(complementoSolicitacaoDTO);
    }

    @GetMapping("/getObservacaoSolicitacao")
    public ResponseEntity<ObservacaoSolicitacaoDTO> getObservacaoSolicitacao(@RequestParam("cod") String cod) throws DataNotFoundException {
        ObservacaoSolicitacaoDTO observacaoSolicitacaoDTO = dbConection.getObservacaoSolicitacao(cod);
        if (ObjectUtils.isEmpty(observacaoSolicitacaoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(observacaoSolicitacaoDTO);
    }

    @GetMapping("/getValorMensalidade")
    public ResponseEntity<?> getValorMensalidade(@RequestParam("cpf") String cartao, @RequestParam(value = "cpf", required = false) String cpf, HttpServletResponse response) throws DataNotFoundException, IOException {

        unimedService.getValorMensalidade(cpf);

        return ResponseEntity.ok(unimedService.getValorMensalidade(cpf));
    }

    @GetMapping("/getPlanoByCpfOrCard")
    public ResponseEntity<List<PlanoDTO>> getPlanoByCpfOrCard(@RequestParam("cpfOrCard") String cpfOrCard) throws DataNotFoundException {
        List<PlanoDTO> planoDTO = dbConection.getPlanoByCpfOrCard(cpfOrCard);
        if (ObjectUtils.isEmpty(planoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(planoDTO);

    }
    @GetMapping("/EnvioPdfEmail")
    public ResponseEntity<?> getJustEmailByCard(@RequestParam("e-mail") String email, @RequestParam(value = "cartaoTitular") String cartaoTitular, HttpServletResponse response) throws DataNotFoundException, IOException {
        EnvioPDF post = new EnvioPDF();

        BoletoPathDTO boletoPath = dbConection.getDadosBoletoPendente(cartaoTitular);
        String pdfName = boletoPath.getFileName();

        String filePath = "X:/";
        String originalFileName = pdfName;
        String newFileName = "test.pdf";

        byte[] input_file = Files.readAllBytes(Paths.get(filePath+originalFileName));

        byte[] encodedBytes = Base64.getEncoder().encode(input_file);
        String encodedString =  new String(encodedBytes);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes());

        FileOutputStream fos = new FileOutputStream(filePath+newFileName);
        fos.write(decodedBytes);
        fos.flush();
        fos.close();

        String attachment = encodedString;
        String fileName = pdfName;
        String extensao = "pdf";

        String adress = email;
        String subject = null;
        String message = null;

        String Json = "{\n" +
                "\"attachment\": \""+attachment+"\",\n" +
                "\"fileName\": \""+fileName+"\",\n" +
                "\"extensao\": \""+extensao+"\",\n" +
                "\"adress\": ["+adress+"],\n" +
                "\"subject\": \""+subject+"\",\n" +
                "\"message\": \""+message+"\"\n" +
                "}";

        return ResponseEntity.ok(post.postDados("http://187.60.56.85:5414/envioEmail",Json));
    }
}