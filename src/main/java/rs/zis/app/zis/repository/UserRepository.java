package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.Users;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    Page<Users> findAll(Pageable pageable);
    List<Users> findAll(); //vracam sve usere
    Users findOneByMail(String mail);
    List<Users> findAllByMail(String mail);
    Users findOneById(Long id);
}
