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
public class Carteirinha {
    @Id
    private String carteirinha;

    public Carteirinha(String carteirinha) {
        this.carteirinha = carteirinha;
    }
}
