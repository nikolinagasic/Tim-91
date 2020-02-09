package rs.zis.app.zis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.MedicalReview;

public interface MedicalReviewRepository extends JpaRepository<MedicalReview,Long> {

    MedicalReview findOneById(Long id);

}
