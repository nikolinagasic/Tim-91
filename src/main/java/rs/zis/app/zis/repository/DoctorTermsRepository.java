package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rs.zis.app.zis.domain.*;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public interface DoctorTermsRepository extends JpaRepository<DoctorTerms, Long> {
    Page<DoctorTerms> findAll(Pageable pageable);
    DoctorTerms findOneById(long id);
    List<DoctorTerms> findAllByDate(long date);
    List<DoctorTerms> findAllByRoom(Room room);


    List<DoctorTerms> findAllByProcessedByAdmin(boolean is);

    // vraca sve termine za tog doktora
    List<DoctorTerms> findAllByDoctor(Doctor d);
    DoctorTerms save(DoctorTerms doctorTerms);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select dt from DoctorTerms dt where dt.date = :date and dt.term = :start and dt.doctor = :doctor")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    DoctorTerms findOneByDateAndStartTermAndDoctorId(@Param("date")Long date,
                                                     @Param("start") TermDefinition start,
                                                     @Param("doctor") Doctor doctor);
}
