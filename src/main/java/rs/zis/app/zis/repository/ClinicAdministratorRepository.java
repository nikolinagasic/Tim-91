package rs.zis.app.zis.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Klinika;
import rs.zis.app.zis.domain.Patient;

public interface ClinicAdministratorRepository extends JpaRepository<ClinicAdministrator, Long> {
  //  Page<ClinicAdministrator> findAll(Pageable pageable);
  //  ClinicAdministrator findOneByMail(String mail);
//    ClinicAdministrator findOneByClinic(Klinika clinic);
//    ClinicAdministrator save(ClinicAdministrator clinicAdministrator);
}
