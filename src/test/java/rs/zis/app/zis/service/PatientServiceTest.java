package rs.zis.app.zis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Patient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.PatientConstants.DB_COUNT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:test.properties")
public class PatientServiceTest {

    @Autowired
    PatientService patientService;

    @Test
    public void testFindAll() {
        List<Patient> patients = patientService.findAll();
        assertThat(patients).hasSize(DB_COUNT);
    }


}
