package rs.zis.app.zis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    MedicalRecord findOneById(Long id);
}
