package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;

import java.util.List;

/*
* - Struktura naziva metode:   find____________By_________________
*                                  (All,One..)  (naziv atributa)
* - Nazivi atributa mogu biti konkatenirani sa And, Or, Between, LessThan, GreaterThan, Like ...
* - Uz dodavanje pomocnih uslova poput Containing, AllIgnoringCase ...
* */

public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Page<Nurse> findAll(Pageable pageable);
    Nurse findOneById(long id);
    Nurse findOneByMail(String mail);

    // ?1 uzima 1. parametar
    @Query("select n from Nurse n where n.lastName = ?1")
    List<Nurse> findNurseByLastName(String lastName);
}
