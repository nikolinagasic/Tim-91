package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;

import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
public interface ClinicRepository extends JpaRepository<Clinic,Long> {
    Page<Clinic> findAll(Pageable pageable);
    Clinic findOneById(Long id);
    Clinic findOneByName(String name);

}
