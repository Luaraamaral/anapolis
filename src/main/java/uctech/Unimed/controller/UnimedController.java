package uctech.Unimed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uctech.Unimed.service.UnimedService;

@RestController
public class UnimedController {

    @Autowired
    private UnimedService unimedService;

    @GetMapping("/opcao")
    public void selecionarOpcao() {
        unimedService.selecionarOpcao();
    }
}
