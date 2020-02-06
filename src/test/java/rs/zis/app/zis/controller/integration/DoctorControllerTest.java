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
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.dto.PatientDTO;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DoctorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessTokenPatient;

    private String accessTokenDoctor;

    @Before
    public void login() {
        ResponseEntity<String> responseEntityPat =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("patient@gmail.com", "12345678"), String.class);
        // preuzmemo token jer ce nam trebati za testiranje REST kontrolera
        accessTokenPatient = responseEntityPat.getBody();

        ResponseEntity<String> responseEntityDoc =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("doctor@gmail.com", "admin"), String.class);
        // preuzmemo token jer ce nam trebati za testiranje REST kontrolera
        accessTokenDoctor = responseEntityDoc.getBody();
    }

    @Test
    public void testGetAll(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DoctorDTO[]> responseEntity =
                restTemplate.exchange("/doctor/getAll", HttpMethod.GET, httpEntity, DoctorDTO[].class);

        DoctorDTO[] doctorDTOS = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorDTOS).isNotNull();
        assertThat(doctorDTOS).isNotEmpty();
        assertEquals(doctorDTOS.length, DB_DOCTOR_COUNT);
        assertEquals(doctorDTOS[0].getFirstName(), "Марко");
        assertEquals(doctorDTOS[0].getLastName(), "Марковић");
        assertEquals(doctorDTOS[1].getFirstName(), "Маринко");
        assertEquals(doctorDTOS[1].getLastName(), "Маринковић");
        assertEquals(doctorDTOS[2].getFirstName(), "Бојан");
        assertEquals(doctorDTOS[2].getLastName(), "Бојанић");
        assertThat(doctorDTOS[0].getRole().equals("doctor")).isEqualTo(true);
        assertThat(doctorDTOS[0].getRole().equals("doctor") && doctorDTOS[1].getRole().equals("doctor") &&
                doctorDTOS[2].getRole().equals("doctor")).isEqualTo(true);
    }

    @Test
    public void testGetTermini(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DoctorTermsDTO[]> responseEntity =
                restTemplate.exchange("/doctor/getTermini/3/"+DB_DOCTOR_TERMS_DATE, HttpMethod.GET,
                        httpEntity, DoctorTermsDTO[].class);

        DoctorTermsDTO[] doctorTermsDTOS = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsDTOS).isNotNull();
        assertThat(doctorTermsDTOS).isNotEmpty();
        assertThat(doctorTermsDTOS.length).isEqualTo(1);
        assertThat(doctorTermsDTOS[0].getFirstNameDoctor()).isEqualTo("Марко");
        assertThat(doctorTermsDTOS[0].getLastNameDoctor()).isEqualTo("Марковић");
        assertThat(doctorTermsDTOS[0].getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTermsDTOS[0].getStart_term()).isEqualTo("12:00");
        assertThat(doctorTermsDTOS[0].getEnd_term()).isEqualTo("12:30");
        assertThat(doctorTermsDTOS[0].getPatient_id()).isNull();
    }

}
