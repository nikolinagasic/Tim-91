package rs.zis.app.zis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.MedicalRecipe;

public interface MedicalRecipeRepository extends JpaRepository<MedicalRecipe,Long> {

       MedicalRecipe findOneById(Long id);
}

