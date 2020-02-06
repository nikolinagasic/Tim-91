package rs.zis.app.zis.service.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.service.DoctorTermsService;

import javax.persistence.Column;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings("WrapperTypeMayBePrimitive")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DoctorTermsServiceTest {

    @Autowired
    DoctorTermsService doctorTermsService;

    @Test
    public void testFindAll() {
        List<DoctorTerms> doctorTerms = doctorTermsService.findAll();
        assertThat(doctorTerms).hasSize(DB_DOCTOR_TERMS_COUNT);
    }

    @Test
    public void testFindAllByDate() {
        List<DoctorTerms> doctorTerms = doctorTermsService.findAllByDate(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTerms).hasSize(DB_DOCTOR_TERMS_DATE_COUNT);
    }

    @Test
    public void testFindAllByDoctor() {
        List<DoctorTerms> doctorTerms = doctorTermsService.findAllByDoctor(DB_DOCTOR_TERMS_DOCTOR_ID);
        assertThat(doctorTerms).hasSize(DB_DOCTOR_TERMS_DOCTOR_COUNT);
    }

    @Test
    public void testFindOneById() {
        DoctorTerms doctorTerms = doctorTermsService.findOneById(DB_DOCTOR_TERMS_ID_FIND_ONE);
        assertThat(doctorTerms).isNotNull();

        assertThat(doctorTerms.getDoctor().getId()).isEqualTo(DB_DOCTOR_TERMS_DOCTOR_FIND_ONE);
        assertThat(doctorTerms.getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
    }

    @Test
    public void testSave() {
        Long date = 1578441600054L;

        DoctorTerms doctorTerms = new DoctorTerms();
        doctorTerms.setDate(date);
        doctorTerms.setExamination(true);
        doctorTerms.setPredefined(false);
        double price = 1539.99;
        doctorTerms.setPrice(price);
        doctorTerms.setDiscount(5);

        int count_before_save = doctorTermsService.findAll().size();
        DoctorTerms doctorTerms1 = doctorTermsService.save(doctorTerms);
        assertThat(doctorTerms1).isNotNull();

        List<DoctorTerms> doctorTermsList = doctorTermsService.findAll();
        assertThat(doctorTermsList).hasSize(count_before_save + 1);

        assertThat(doctorTerms1.getDate()).isEqualTo(date);
        assertThat(doctorTerms1.getPrice()).isEqualTo(price);
    }

    @Test
    public void testRemoveLogical() {
        doctorTermsService.removeLogical(DB_DOCTOR_TERMS_ID_REMOVE);
        List<DoctorTerms> doctorTermsList = doctorTermsService.findAll();

        doctorTermsList.removeIf(doctorTerms -> !doctorTerms.isActive());

        assertThat(doctorTermsList).hasSize(DB_DOCTOR_TERMS_COUNT - 1);
    }

    @Test
    public void testNonexistentMail() {
        boolean reserved = doctorTermsService.reserveTerm(DB_PATIENT_NONEXISTENT_MAIL, new DoctorTermsDTO());
        assertThat(reserved).isEqualTo(false);
    }

    @Test
    public void testNonexistentTerm() {
        DoctorTermsDTO doctorTermDTO = new DoctorTermsDTO();
        doctorTermDTO.setStart_term("23:00");

        boolean reserved = doctorTermsService.reserveTerm(DB_PATIENT_MAIL, doctorTermDTO);
        assertThat(reserved).isEqualTo(false);
    }

    @Test
    public void testNonexistentDoctor() {
        DoctorTermsDTO doctorTermDTO = new DoctorTermsDTO();
        doctorTermDTO.setFirstNameDoctor("Doktor");
        doctorTermDTO.setLastNameDoctor("Doktorovic");

        boolean reserved = doctorTermsService.reserveTerm(DB_PATIENT_MAIL, doctorTermDTO);
        assertThat(reserved).isEqualTo(false);
    }

    @Test(expected = AssertionError.class)
    public void testGetTermineNonexistentDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(-2L);          // nepostojeci doktor
        doctor.setFirstName("Nemanja");
        doctor.setLastName("Matic");

        List<DoctorTermsDTO> doctorTermsDTOList = doctorTermsService.getTermine(DB_DOCTOR_TERMS_DATE, doctor);
        assertThat(doctorTermsDTOList).isNullOrEmpty();
    }

    @Test()
    public void testGetTermine() {
        Doctor doctor = new Doctor();
        doctor.setId(4L);
        doctor.setFirstName("Маринко");
        doctor.setLastName("Маринковић");

        List<DoctorTermsDTO> doctorTermsDTOList = doctorTermsService.getTermine(DB_DOCTOR_TERMS_DATE, doctor);
        assertThat(doctorTermsDTOList).isNotEmpty();
        assertThat(doctorTermsDTOList).hasSize(DB_DOCTOR_TERMS_NUMBER_OF_TERMS);

        assertThat(doctorTermsDTOList.get(0).getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTermsDTOList.get(0).getFirstNameDoctor()).isEqualTo("Маринко");
        assertThat(doctorTermsDTOList.get(0).getLastNameDoctor()).isEqualTo("Маринковић");
        assertThat(doctorTermsDTOList.get(6).getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTermsDTOList.get(6).getFirstNameDoctor()).isEqualTo("Маринко");
        assertThat(doctorTermsDTOList.get(6).getLastNameDoctor()).isEqualTo("Маринковић");
    }

    @Test(expected = NullPointerException.class)
    public void testDetailTermNonexistentDoctor() {
        Long doctor_id = -2L;
        doctorTermsService.detailTerm(doctor_id, DB_DOCTOR_TERMS_DATE, DB_DOCTOR_TERMS_START_TERM, DB_PATIENT_MAIL);
    }

    @Test(expected = NullPointerException.class)
    public void testDetailTermNonexistentStartTerm() {
        String start_term = "23:05";
        doctorTermsService.detailTerm(DB_DOCTOR_TERMS_DOCTOR_ID, DB_DOCTOR_TERMS_DATE, start_term, DB_PATIENT_MAIL);
    }

    @Test()
    public void testDetailTerm() {
        Long doctor_id = 3L;
        DoctorTermsDTO doctorTermsDTO = doctorTermsService.detailTerm(doctor_id, DB_DOCTOR_TERMS_DATE,
                DB_DOCTOR_TERMS_START_TERM, DB_PATIENT_MAIL);

        assertThat(doctorTermsDTO).isNotNull();
        assertThat(doctorTermsDTO.getStart_term()).isEqualTo(DB_DOCTOR_TERMS_START_TERM);
        assertThat(doctorTermsDTO.getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);

        assertThat(doctorTermsDTO.getPatient_id()).isNull();
        assertThat(doctorTermsDTO.getFirstNameDoctor()).isEqualTo("Марко");
        assertThat(doctorTermsDTO.getLastNameDoctor()).isEqualTo("Марковић");
    }

    @Test()
    public void testCreatePredefinedTermDoctorBusy() {
        Long satnica_id = 1L;
        Long room_id = 3L;
        Long type_id = 1L;
        Long doctor_id = 3L;
        double price = 7899.99;
        int discount = 5;

        int retVal = doctorTermsService.createPredefinedTerm(DB_DOCTOR_TERMS_DATE, satnica_id, room_id, type_id, doctor_id,
                price, discount);
        assertThat(retVal).isEqualTo(-1);           // doktor je zauzet
    }

    @Test(expected = NullPointerException.class)
    public void testCreatePredefinedTermNonexistentDoctor() {
        Long room_id = 2L;
        Long satnica_id = 1L;
        Long type_id = 1L;
        Long doctor_id = -1L;           // nepostojeci doktor
        double price = 7899.99;
        int discount = 5;

        int retVal = doctorTermsService.createPredefinedTerm(DB_DOCTOR_TERMS_DATE, satnica_id, room_id, type_id, doctor_id,
                price, discount);
        assertThat(retVal).isNull();
    }

    @Test()
    public void testCreatePredefinedTerm() {
        Long room_id = 2L;
        Long satnica_id = 1L;
        Long type_id = 1L;
        Long doctor_id = 3L;
        double price = 999.99;
        int discount = 10;
        Long date = 12029978058L;

        int db_before_insert = doctorTermsService.findAll().size();

        int retVal = doctorTermsService.createPredefinedTerm(date, satnica_id, room_id, type_id, doctor_id,
                price, discount);
        assertThat(retVal).isNotNull();

        assertThat(doctorTermsService.findAll()).hasSize(db_before_insert + 1);
        List<DoctorTerms> termsList = doctorTermsService.findAllByDate(date);
        assertThat(termsList).hasSize(1);
        assertThat(termsList.get(0).getDate()).isEqualTo(date);
        assertThat(termsList.get(0).getDoctor().getId()).isEqualTo(doctor_id);
        assertThat(termsList.get(0).getPrice()).isEqualTo(price);
        assertThat(termsList.get(0).getRoom().getId()).isEqualTo(room_id);
        assertThat(termsList.get(0).getDiscount()).isEqualTo(discount);
    }

    @Test
    public void testReserveTermConcurrent() {
//        final CountDownLatch latch = new CountDownLatch(2);
//        DoctorTermsDTO doctorTermsDTO = new DoctorTermsDTO();
//        doctorTermsDTO.setDate(1581033600000L);
//        doctorTermsDTO.setStart_term("10:30");
//        doctorTermsDTO.setStart_term("11:00");
//        doctorTermsDTO.setFirstNameDoctor("Марко");
//        doctorTermsDTO.setLastNameDoctor("Марковић");
//        doctorTermsDTO.setPrice(4200.0);
//        doctorTermsDTO.setType("Стоматологија");
//        doctorTermsDTO.setExamination(false);
//
//        Runnable r1 = () -> {
//            doctorTermsService.reserveTerm(DB_PATIENT_MAIL, doctorTermsDTO);
//            latch.countDown();
//        };
//
//        Runnable r2 = () -> {
//            try { Thread.sleep(3000); } catch (InterruptedException e) { }
//            try {
//                boolean reserve = doctorTermsService.reserveTerm(DB_PATIENT2_MAIL, doctorTermsDTO);
//                fail();
//            }catch(Exception e) {
//                assertTrue(e instanceof PessimisticLockingFailureException);
//            }
//
//            latch.countDown();
//        };
//
//        new Thread(r1).start();
//        new Thread(r2).start();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
