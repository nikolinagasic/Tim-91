package rs.zis.app.zis.service.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.service.ClinicService;
import rs.zis.app.zis.service.PatientService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings({"WrapperTypeMayBePrimitive", "CatchMayIgnoreException"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClinicServiceTest {

    @Autowired
    ClinicService clinicService;

    @Autowired
    PatientService patientService;      // samo za findById kod reservePredefinedTerm

    @Test
    public void testFindAll() {
        List<Clinic> clinicList = clinicService.findAll();

        assertThat(clinicList).isNotNull();
        assertThat(clinicList).isNotEmpty();
        assertThat(clinicList).hasSize(DB_CLINIC_COUNT);
        assertThat(clinicList.get(0).getName()).isEqualTo("Моја клиника");
        assertThat(clinicList.get(1).getName()).isEqualTo("Наша клиника");
    }

    @Test
    public void testFindOneByNonexistentName() {
        Clinic clinic = clinicService.findOneByName("Nonexistent name");

        assertThat(clinic).isNull();
    }

    @Test
    public void testFindOneByName() {
        Clinic clinic = clinicService.findOneByName("Моја клиника");

        assertThat(clinic).isNotNull();
        assertThat(clinic.getName()).isEqualTo("Моја клиника");
        assertThat(clinic.getAddress()).isEqualTo("Тополска 18");
        assertThat(clinic.getId()).isEqualTo(1L);
    }

    @Test
    public void testFindOneById() {
        Clinic clinic = clinicService.findOneById(DB_CLINIC_ID);

        assertThat(clinic).isNotNull();
        assertThat(clinic.getName()).isEqualTo("Моја клиника");
        assertThat(clinic.getAddress()).isEqualTo("Тополска 18");
    }

    @Test
    public void testGetPredefinedTerms() {
        List<DoctorTermsDTO> dtoList = clinicService.getPredefinedTerms(DB_CLINIC_ID);

        assertThat(dtoList).isNotNull();
        assertThat(dtoList).isNotEmpty();
        assertThat(dtoList).hasSize(1);
        assertThat(dtoList.get(0).getFirstNameDoctor()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(dtoList.get(0).getLastNameDoctor()).isEqualTo(DB_DOCTOR_LAST_NAME);
        assertThat(dtoList.get(0).getType()).isEqualTo("Стоматологија");
        assertThat(dtoList.get(0).getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
    }

    @Test()
    public void testGetPredefinedTermsNonexistentClinic() {
        List<DoctorTermsDTO> dtoList = clinicService.getPredefinedTerms(-1L);

        assertThat(dtoList).isNotNull();
        assertThat(dtoList).isEmpty();
    }

    @Test
    public void testReservePredefinedTerm() {
        final CountDownLatch latch = new CountDownLatch(2);
        Runnable r1 = () -> {
            Patient patient = patientService.findOneByMail(DB_PATIENT_MAIL);
            try {
                boolean isReserved = clinicService.reservePredefinedTerm(DB_DOCTOR_TERMS_PREDEFINED_ID, patient);
                assertTrue(isReserved);         // prvi je prihvacen
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        };

        Runnable r2 = () -> {
            try { Thread.sleep(3000); } catch (InterruptedException e) { }
            Patient patient2 = patientService.findOneByMail(DB_PATIENT2_MAIL);
            try {
                boolean isReserved = clinicService.reservePredefinedTerm(DB_DOCTOR_TERMS_PREDEFINED_ID, patient2);
                assertFalse(isReserved);            // drugi odbijen
            }catch(Exception e) {
                assertTrue(e instanceof OptimisticLockingFailureException);
            }

            latch.countDown();
        };

        new Thread(r1).start();
        new Thread(r2).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
