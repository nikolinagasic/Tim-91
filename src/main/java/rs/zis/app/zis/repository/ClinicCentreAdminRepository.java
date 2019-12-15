package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import java.util.List;

public interface ClinicCentreAdminRepository extends JpaRepository<ClinicCentreAdmin, Long> {

    ClinicCentreAdmin findOneByMail(String mail);
    Page<ClinicCentreAdmin> findAll(Pageable pageable);
    List<ClinicCentreAdmin> findAllByLastName(String lastName);
//    @Query("select s from ClinicCentreAdmin s where s.predefined = ?true")
//    ClinicCentreAdmin findPredefinedAdmin();

}
