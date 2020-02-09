package rs.zis.app.zis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.Diagnosis;
import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {
    List<Diagnosis> findAll();
    //Diagnosis findOneByCurePassword(String cure_password);
    //Diagnosis findOneByDiagnosisPassword(String diagnosis_password);

}
