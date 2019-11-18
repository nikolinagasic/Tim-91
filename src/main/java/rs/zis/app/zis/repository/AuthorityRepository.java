package rs.zis.app.zis.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}

