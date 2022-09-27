package uctech.Unimed.service;

import org.springframework.stereotype.Service;

@Service
public class UnimedService {

    public String selecionarOpcao(Integer opc) {

        if ((opc == 1) | (opc == 2))
            return ("Aguardar em linha, vamos transferir " +
                    "para um de nossos agentes de atendimento");
        else if (opc == 3) {
            return ("Unimed Agradece o contato, lembre-se: Cuidar de você, esse é o plano!");

        } else return ("Opção inválida");

    }

}
