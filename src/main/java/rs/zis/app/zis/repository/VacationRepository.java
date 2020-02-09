package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.domain.Vacation;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Page<Vacation> findAll(Pageable pageable);
    Vacation findOneById(Long id);
    List<Vacation> findAllByDoctor(Doctor doctor);
    List<Vacation> findAllByNurse(Nurse nurse);
}
