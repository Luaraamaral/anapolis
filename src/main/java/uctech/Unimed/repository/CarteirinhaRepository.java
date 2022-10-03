package uctech.Unimed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uctech.Unimed.domain.Carteirinha;

@Repository
public interface CarteirinhaRepository extends JpaRepository<Carteirinha, String> {
}
