package uctech.Unimed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uctech.Unimed.domain.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
}
