package uctech.Unimed.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uctech.Unimed.dtos.CarteirinhaResponseDTO;
import uctech.Unimed.dtos.CpfResponseDTO;
import uctech.Unimed.dtos.EmailResponseDTO;
import uctech.Unimed.dtos.GuiaResponseDTO;
import uctech.Unimed.service.UnimedService;

import java.util.Objects;

@RestController
public class UnimedController {

    @Autowired
    private UnimedService unimedService;

    @ApiOperation(nickname = "findByCarteirinha", value = "Busca número da carteirinha na base de dados")
    @GetMapping("/carteirinha")
    public ResponseEntity<CarteirinhaResponseDTO> findByCarteirinha(@RequestParam("carteirinha") String carteirinha) {
        CarteirinhaResponseDTO carteirinha1 = this.unimedService.carteirinha(carteirinha);
        if (Objects.isNull(carteirinha1)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carteirinha1);
    }


    @ApiOperation(nickname = "findByCpf", value = "Busca número do cpf na base de dados")
    @GetMapping("/cpf")
    public ResponseEntity<CpfResponseDTO> findByCpf(@RequestParam("cpf") String cpf) {
        CpfResponseDTO cpf1 = this.unimedService.cpf(cpf);
        if (Objects.isNull(cpf1)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cpf1);
    }

    @ApiOperation(nickname = "selecionarOpcao", value = "Retorna mensagem atrelada a opção selecionada")
    @GetMapping("/opcao")
    public String selecionarOpcao(@RequestParam Integer opc) {
        return unimedService.selecionarOpcao(opc);
    }

    @ApiOperation(nickname = "findByGuia", value = "Busca status da guia na base de dados")
    @GetMapping("/guia")
    public ResponseEntity<GuiaResponseDTO> findByGuia(@RequestParam("numero") String numero) {
        GuiaResponseDTO guia = this.unimedService.findByGuia(numero);
        if (Objects.isNull(guia)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guia);

    }

    @ApiOperation(nickname = "findByEmail", value = "Busca email na base de dados")
    @GetMapping("/email")
    public ResponseEntity<EmailResponseDTO> findByEmail(@RequestParam("email") String email) {
        EmailResponseDTO email1 = this.unimedService.findByEmail(email);
        if (Objects.isNull(email1)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(email1);
    }

}
