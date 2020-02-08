package rs.zis.app.zis.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.repository.PatientRepository;
import rs.zis.app.zis.service.AuthorityService;
import rs.zis.app.zis.service.PatientService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientServiceUnitTest {

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthorityService authService;

    @Autowired
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        Mockito.when(patientRepository.findAll()).then(invocationOnMock -> new ArrayList<Patient>() {{
            Patient patient = new Patient();
            patient.setActive(false);
            add(patient);           // 1. pacijent nije aktivan
            add(new Patient());     // 2. aktivan
        }});

        //Patient p = new Patient(null, )
        Patient p1 = new Patient();
        p1.setId(null);
        p1.setActive(true);
        p1.setPassword("password");
        p1.setRole("patient");
        p1.setMail("p@gmail.com");

        Patient p2 = new Patient();
        p2.setId(5L);
        p1.setMail(DB_NEKI_MAIL);

//        Mockito.when(patientRepository.save(p1)).then(invocationOnMock -> p2);
        Mockito.when(patientRepository.save(any(Patient.class))).then(invocationOnMock -> p2);
        Mockito.when(passwordEncoder.encode("")).then(invocationOnMock -> "password");
        Mockito.when(authService.findByname("ROLE_PATIENT")).then(invocationOnMock -> new ArrayList<Authority>(){{
            add(new Authority(1L, "PATIENT"));
        }});

        Mockito.when(patientService.findOneByMail(DB_NEKI_MAIL)).then(invocationOnMock -> p2);
    }

    @Test
    void testFindAll() {
        assertThat(patientService.findAll()).isNotNull();
        assertThat(patientService.findAll()).isNotEmpty();
        assertThat(patientService.findAll()).hasSize(1);
    }

    @Test
    void testSave() {
        PatientDTO p1 = new PatientDTO();

        Patient savedPatient = patientService.save(p1);
        assertThat(savedPatient.getId()).isEqualTo(5L);
    }

    @Test
    void testFindOneByMail(){
        PatientDTO p1 = new PatientDTO();

        Patient findedPatient = patientService.findOneByMail(DB_NEKI_MAIL);
        assertThat(findedPatient).isNotNull();
        assertThat(findedPatient.getMail()).isEqualTo(DB_NEKI_MAIL);
        assertThat(findedPatient.getId()).isEqualTo(5L);
    }
}