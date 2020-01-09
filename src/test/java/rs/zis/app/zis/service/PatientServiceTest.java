package rs.zis.app.zis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Patient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.UserConstants.DB_PATIENT_COUNT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientServiceTest {

    @Autowired
    PatientService patientService;

    @Test
    public void testFindAll() {
        List<Patient> patients = patientService.findAll();
        assertThat(patients).hasSize(DB_PATIENT_COUNT);
    }


}
