package rs.zis.app.zis.repository;

import com.sun.tools.javac.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.Patient;

import java.awt.print.Pageable;

/*
* - Struktura naziva metode:   find____________By_________________
*                                  (All,One..)  (naziv atributa)
* - Nazivi atributa mogu biti konkatenirani sa And, Or, Between, LessThan, GreaterThan, Like ...
* - Uz dodavanje pomocnih uslova poput Containing, AllIgnoringCase ...
* */

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAll(Pageable pageable);
    Patient findOneByMail(String mail);
    Patient findOneByLbo(long lbo);

    // ?1 uzima 1. parametar
    @Query("select p from Patient p where p.lastName = ?1")
    List<Patient> findPatientByLastName(String lastName);
}
