package uctech.Unimed.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
public class Guia {
    @Id
    private String numero;
    private String statusGuia;

    public Guia(String numero, String statusGuia) {
        this.numero = numero;
        this.statusGuia = statusGuia;
    }
}

