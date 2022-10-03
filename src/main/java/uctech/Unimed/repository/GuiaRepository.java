package uctech.Unimed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uctech.Unimed.domain.Guia;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, String> {
}
