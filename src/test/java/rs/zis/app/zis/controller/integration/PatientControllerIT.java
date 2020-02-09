package rs.zis.app.zis.controller.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.auth.JwtAuthenticationRequest;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.PatientDTO;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.UserConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    @Before
    public void login() {
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("patient@gmail.com", "12345678"), String.class);
        // preuzmemo token jer ce nam trebati za testiranje REST kontrolera
        accessToken = responseEntity.getBody();
    }

    @Test
    public void testGetAll(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessToken);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        ResponseEntity<PatientDTO[]> responseEntity =
                restTemplate.exchange("/patient/getAll", HttpMethod.GET, httpEntity, PatientDTO[].class);

        PatientDTO[] patientDTOS = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(patientDTOS.length).isEqualTo(DB_PATIENT_COUNT);
        assertEquals(patientDTOS[0].getMail(), DB_PATIENT_MAIL);
        assertEquals(patientDTOS[1].getMail(), DB_PATIENT2_MAIL);
        assertEquals(patientDTOS[0].getFirstName(), "Петар");
        assertEquals(patientDTOS[0].getLastName(), "Петровић");
        assertThat(patientDTOS[0].getRole().equals("patient"));
        assertThat(patientDTOS[0].getRole().equals("patient") && patientDTOS[1].getRole().equals("patient")).isEqualTo(true);
    }

}
