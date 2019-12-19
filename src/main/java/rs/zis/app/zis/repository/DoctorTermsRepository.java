package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public interface DoctorTermsRepository extends JpaRepository<DoctorTerms, Long> {
    Page<DoctorTerms> findAll(Pageable pageable);
    DoctorTerms findOneById(long id);
    DoctorTerms findOneByDate(long date);

    // vraca sve termine za tog doktora
    List<DoctorTerms> findAllByDoctor(Doctor d);
}
