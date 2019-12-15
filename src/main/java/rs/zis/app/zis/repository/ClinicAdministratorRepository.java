package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.ClinicAdministrator;


/*
 * - Struktura naziva metode:   find____________By_________________
 *                                  (All,One..)  (naziv atributa)
 * - Nazivi atributa mogu biti konkatenirani sa And, Or, Between, LessThan, GreaterThan, Like ...
 * - Uz dodavanje pomocnih uslova poput Containing, AllIgnoringCase ...
 * */

public interface ClinicAdministratorRepository extends JpaRepository<ClinicAdministrator, Long> {
    Page<ClinicAdministrator> findAll(Pageable pageable);
    ClinicAdministrator findOneById(long id);
    ClinicAdministrator findOneByMail(String mail);

}
