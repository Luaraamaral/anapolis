package uctech.Unimed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uctech.Unimed.domain.Carteirinha;
import uctech.Unimed.domain.Cpf;
import uctech.Unimed.domain.Email;
import uctech.Unimed.domain.Guia;
import uctech.Unimed.dtos.CarteirinhaResponseDTO;
import uctech.Unimed.dtos.CpfResponseDTO;
import uctech.Unimed.dtos.EmailResponseDTO;
import uctech.Unimed.dtos.GuiaResponseDTO;
import uctech.Unimed.repository.CarteirinhaRepository;
import uctech.Unimed.repository.CpfRepository;
import uctech.Unimed.repository.EmailRepository;
import uctech.Unimed.repository.GuiaRepository;

import java.util.Optional;

@Service
public class UnimedService {

    @Autowired
    private CpfRepository cpfRepository;
    @Autowired
    private CarteirinhaRepository carteirinhaRepository;
    @Autowired
    private GuiaRepository guiaRepository;
    @Autowired
    private EmailRepository emailRepository;


    public CpfResponseDTO cpf(String cpf) {
        Optional<Cpf> cpf1 = cpfRepository.findById(cpf);
        if (cpf1.isPresent()) {
            return new CpfResponseDTO(cpf1.get());
        }
        return null;
    }

    public CarteirinhaResponseDTO carteirinha(String carteirinha) {
        Optional<Carteirinha> carteirinha1 = carteirinhaRepository.findById(carteirinha);
        if (carteirinha1.isPresent()) {
            return new CarteirinhaResponseDTO(carteirinha1.get());
        }
        return null;
    }

    public String selecionarOpcao(Integer opc) {

        if ((opc == 1) | (opc == 2))
            return ("Aguardar em linha, vamos transferir " +
                    "para um de nossos agentes de atendimento");
        else if (opc == 3) {
            return ("Unimed Agradece o contato, lembre-se: Cuidar de você, esse é o plano!");

        } else return ("Opção inválida");

    }

    public GuiaResponseDTO findByGuia(String numero) {

        Optional<Guia> guia = guiaRepository.findById(numero);
        if (guia.isPresent()) {
            return new GuiaResponseDTO(guia.get());

        }
        return null;
    }

    public EmailResponseDTO findByEmail(String email) {
        Optional<Email> email1 = emailRepository.findById(email);
        if (email1.isPresent()) {
            return new EmailResponseDTO(email1.get());
        }
        return null;
    }

}
