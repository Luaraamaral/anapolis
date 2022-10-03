package uctech.Unimed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uctech.Unimed.domain.Cpf;

@Repository
public interface CpfRepository extends JpaRepository<Cpf, String> {
}
