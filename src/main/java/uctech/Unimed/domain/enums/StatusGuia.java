package uctech.Unimed.domain.enums;

import lombok.Getter;

@Getter
public enum StatusGuia {
    EM_ESTUDO("Em estudo"),
    AUTORIZADA("Autorirzada"),
    CANCELADA("Cancelada"),
    NEGADA("Negada"),
    EXECUTADA("Executada");

    private String status;


    private StatusGuia(String status) {
        this.status = status;
    }

}
