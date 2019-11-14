package rs.zis.app.zis.repository;

import com.sun.tools.javac.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Klinika;
import rs.zis.app.zis.domain.Patient;

import java.awt.print.Pageable;

/*
* - Struktura naziva metode:   find____________By_________________
*                                  (All,One..)  (naziv atributa)
* - Nazivi atributa mogu biti konkatenirani sa And, Or, Between, LessThan, GreaterThan, Like ...
* - Uz dodavanje pomocnih uslova poput Containing, AllIgnoringCase ...
* */

public interface ClinicAdministratorRepository extends JpaRepository<ClinicAdministrator, Long> {
  //  Page<ClinicAdministrator> findAll(Pageable pageable);
  //  ClinicAdministrator findOneByMail(String mail);
    ClinicAdministrator findOneByClinic(Klinika clinic);
    ClinicAdministrator save(ClinicAdministrator clinicAdministrator);
}
