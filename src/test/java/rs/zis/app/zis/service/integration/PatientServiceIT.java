package rs.zis.app.zis.service.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.PatientService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.UserConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientServiceIT {

    @Autowired
    PatientService patientService;

    @Test
    public void testFindAll() {
        List<Patient> patients = patientService.findAll();
        assertThat(patients).hasSize(DB_PATIENT_COUNT);
    }

    @Test
    public void testFindAllByLbo() {
        Patient patient = patientService.findAllByLbo(DB_PATIENT_LBO);
        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isEqualTo(DB_PATIENT_ID);
    }

    @Test
    public void testFindOneById() {
        Patient patient = patientService.findOneById(DB_PATIENT_ID);
        assertThat(patient).isNotNull();

        assertThat(patient.getFirstName()).isEqualTo(DB_PATIENT_FIRST_NAME);
        assertThat(patient.getLastName()).isEqualTo(DB_PATIENT_LAST_NAME);
        assertThat(patient.getMail()).isEqualTo(DB_PATIENT_MAIL);
    }

    @Test
    public void testFindOneByMail() {
        Patient patient = patientService.findOneByMail(DB_PATIENT_MAIL);
        assertThat(patient.getId()).isEqualTo(DB_PATIENT_ID);
    }

    @Test
    public void testSave() {
        List<Patient> patients = patientService.findAll();
        assertThat(patients).hasSize(DB_PATIENT_COUNT);

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setMail("aca@gmail.com");
        patientDTO.setPassword("aca12345678");
        patientDTO.setFirstName("Aca");
        patientDTO.setLastName("Beker");
        patientDTO.setAddress("Pionirska 78");
        patientDTO.setCity("Novi Sad");
        patientDTO.setLbo(12345678954L);
        patientDTO.setCountry("Srbija");
        patientService.save(patientDTO);
        List<Patient> patients1 = patientService.findAll();
        assertThat(patients1).hasSize(DB_PATIENT_COUNT + 1);
    }

}
