package uctech.Unimed.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UnimedService {
    Integer opc;
    public void selecionarOpcao() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite 1 caso queira adquirir um plano Unimed");
        System.out.println("Digite 2 caso seja beneficiário de outra Unimed");
        System.out.println("Digite 3 para encerrar o sistema");
        opc = sc.nextInt();

        if ((opc == 1) | (opc == 2))
            System.out.println("Aguardar em linha, vamos transferir " +
                    "para um de nossos agentes de atendimento");
        else if (opc == 3) {
            System.out.println("Unimed Agradece o contato, lembre-se: Cuidar de você, esse é o plano!");

        } else System.out.println("Opção inválida");
    }
}
