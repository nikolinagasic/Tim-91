package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.domain.TipPregleda;

import java.util.List;

/*
* - Struktura naziva metode:   find____________By_________________
*                                  (All,One..)  (naziv atributa)
* - Nazivi atributa mogu biti konkatenirani sa And, Or, Between, LessThan, GreaterThan, Like ...
* - Uz dodavanje pomocnih uslova poput Containing, AllIgnoringCase ...
* */

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAll(Pageable pageable);
    Doctor findOneById(long id);
    Doctor findOneByMail(String mail);

    // ?1 uzima 1. parametar
    @Query("select d from Doctor d where d.lastName = ?1")
    List<Doctor> findDoctorByLastName(String lastName);

//    @Query("select d from Doctor d where d.tip = ?1")
    List<Doctor> findAllByTip(TipPregleda tp);

    List<Doctor> findAllByClinic(Clinic c);
}
