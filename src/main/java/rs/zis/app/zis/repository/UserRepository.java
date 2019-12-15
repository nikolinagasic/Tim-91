package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    List<User> findAll(); //vracam sve usere
    User findOneByMail(String mail);

}
