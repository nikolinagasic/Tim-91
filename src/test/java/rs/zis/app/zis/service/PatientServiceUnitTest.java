package rs.zis.app.zis.service;

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

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

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
            add(patient);
            add(new Patient());
        }});

        //Patient p = new Patient(null, )
        Patient p1 = new Patient();
        p1.setActive(true);
        p1.setPassword("password");
        p1.setRole("PATIENT");
        p1.setId(null);

        Patient p2 = new Patient();
        p2.setId(5L);

//        Mockito.when(patientRepository.save(p1)).then(invocationOnMock -> p2);
        Mockito.when(patientRepository.save(any(Patient.class))).then(invocationOnMock -> p2);
        Mockito.when(passwordEncoder.encode("")).then(invocationOnMock -> "password");
        Mockito.when(authService.findByname("ROLE_PATIENT")).then(invocationOnMock -> new ArrayList<Authority>(){{
            add(new Authority(1L, "PATIENT"));
        }});


    }


    @Test
    void findAll() {
        assertThat(patientService.findAll()).hasSize(1);
    }

    @Test
    void save() {
        PatientDTO p1 = new PatientDTO();
//        p1.setActive(true);
//        p1.setPassword("password");
//        p1.setRole("PATIENT");
//        p1.setId(null);
//
        Patient result = patientService.save(p1);
        assertThat(result.getId()).isEqualTo(5L);
    }
}