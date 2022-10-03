package uctech.Unimed.domain.enums;

import lombok.Getter;

@Getter
public enum StatusGuia {
    EM_ESTUDO("Em estudo. Posso ajudar em algo mais? Se sim, aguardar em linha, vamos transferir " +
            "para um de nossos agentes de atendimento"),
    AUTORIZADA("Posso ajudar em algo mais? Se sim, aguardar em linha, vamos transferir para um de nossos agentes de atendimento"),
    CANCELADA("Cancelada. Posso ajudar em algo mais? Se sim, aguardar em linha, vamos transferir para um de nossos agentes de atendimento"),
    NEGADA("Negada. Posso ajudar em algo mais? Se sim, aguardar em linha, vamos transferir para um de nossos agentes de atendimento"),
    EXECUTADA("Executada. Posso ajudar em algo mais? Se sim, aguardar em linha, vamos transferir para um de nossos agentes de atendimento");

    private String status;


    private StatusGuia(String status) {
        this.status = status;
    }

}
