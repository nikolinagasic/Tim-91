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
import rs.zis.app.zis.domain.UserTokenState;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.service.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings({"FieldCanBeLocal", "unused", "unchecked", "rawtypes", "ConstantConditions"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DoctorControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessTokenPatient;

    private String accessTokenDoctor;

    @Before
    public void login() {
        ResponseEntity<UserTokenState> responseEntityPat =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("patient@gmail.com", "12345678"), UserTokenState.class);
        // preuzmemo token jer ce nam trebati za testiranje REST kontrolera
        accessTokenPatient = responseEntityPat.getBody().getAccessToken();

        ResponseEntity<UserTokenState> responseEntityDoc =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("doctor@gmail.com", "admin"), UserTokenState.class);
        // preuzmemo token jer ce nam trebati za testiranje REST kontrolera
        accessTokenDoctor = responseEntityDoc.getBody().getAccessToken();
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
        assertEquals(doctorDTOS[0].getFirstName(), DB_DOCTOR_FIRST_NAME);
        assertEquals(doctorDTOS[0].getLastName(), DB_DOCTOR_LAST_NAME);
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
        assertThat(doctorTermsDTOS[0].getFirstNameDoctor()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorTermsDTOS[0].getLastNameDoctor()).isEqualTo(DB_DOCTOR_LAST_NAME);
        assertThat(doctorTermsDTOS[0].getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTermsDTOS[0].getStart_term()).isEqualTo("12:00");
        assertThat(doctorTermsDTOS[0].getEnd_term()).isEqualTo("12:30");
        assertThat(doctorTermsDTOS[0].getPatient_id()).isNull();
    }

    @Test
    public void testDetailTerm(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DoctorTermsDTO> responseEntity =
                restTemplate.exchange("/doctor/detailTermin/3/"+DB_DOCTOR_TERMS_DATE+"/"+DB_DOCTOR_TERMS_START_TERM,
                        HttpMethod.GET, httpEntity, DoctorTermsDTO.class);

        DoctorTermsDTO doctorTermsDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsDTO).isNotNull();
        assertThat(doctorTermsDTO.getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTermsDTO.getStart_term()).isEqualTo(DB_DOCTOR_TERMS_START_TERM);
        assertThat(doctorTermsDTO.getFirstNameDoctor()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorTermsDTO.getLastNameDoctor()).isEqualTo(DB_DOCTOR_LAST_NAME);
        assertThat(doctorTermsDTO.getEnd_term()).isEqualTo(DB_DOCTOR_TERMS_END_TERM);
    }

    @Test
    public void testDetailTermNonexistentTermDef(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DoctorTermsDTO> responseEntity =
                restTemplate.exchange("/doctor/detailTermin/3/"+DB_DOCTOR_TERMS_DATE+"/23:58",
                        HttpMethod.GET, httpEntity, DoctorTermsDTO.class);

        DoctorTermsDTO doctorTermsDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsDTO).isNull();
    }

    @Test
    public void testDetailTermNonexistentDoctor(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DoctorTermsDTO> responseEntity =
                restTemplate.exchange("/doctor/detailTermin/-1/"+DB_DOCTOR_TERMS_DATE+"/"+DB_DOCTOR_TERMS_START_TERM,
                        HttpMethod.GET, httpEntity, DoctorTermsDTO.class);

        DoctorTermsDTO doctorTermsDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsDTO).isNull();
    }

    @Test
    public void testReserveTerm() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        System.out.println(accessTokenPatient);

        DoctorTermsDTO doctorTermsDTO = new DoctorTermsDTO();
        doctorTermsDTO.setDate(DB_DOCTOR_TERMS_FREE_DATE);
        doctorTermsDTO.setStart_term(DB_DOCTOR_TERMS_START_TERM);
        doctorTermsDTO.setEnd_term(DB_DOCTOR_TERMS_END_TERM);
        doctorTermsDTO.setFirstNameDoctor(DB_DOCTOR_FIRST_NAME);
        doctorTermsDTO.setLastNameDoctor(DB_DOCTOR_LAST_NAME);

        HttpEntity<DoctorTermsDTO> httpEntity = new HttpEntity(doctorTermsDTO, headers);
        ResponseEntity<DoctorTermsDTO> responseEntity =
                restTemplate.exchange("/doctor/reserveTerm", HttpMethod.POST, httpEntity, DoctorTermsDTO.class);

        DoctorTermsDTO doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsResponse).isNotNull();
        assertThat(doctorTermsResponse.getDate()).isEqualTo(DB_DOCTOR_TERMS_FREE_DATE);
        assertThat(doctorTermsResponse.getStart_term()).isEqualTo(DB_DOCTOR_TERMS_START_TERM);
        assertThat(doctorTermsResponse.getEnd_term()).isEqualTo(DB_DOCTOR_TERMS_END_TERM);
        assertThat(doctorTermsResponse.getFirstNameDoctor()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorTermsResponse.getLastNameDoctor()).isEqualTo(DB_DOCTOR_LAST_NAME);
    }

    @Test
    public void testReserveTermUnexistentDoctor() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        DoctorTermsDTO doctorTermsDTO = new DoctorTermsDTO();
        doctorTermsDTO.setDate(DB_DOCTOR_TERMS_FREE_DATE);
        doctorTermsDTO.setFirstNameDoctor("Непостојећи");
        doctorTermsDTO.setLastNameDoctor("Доктор");

        HttpEntity<DoctorTermsDTO> httpEntity = new HttpEntity(doctorTermsDTO, headers);
        ResponseEntity<DoctorTermsDTO> responseEntity =
                restTemplate.exchange("/doctor/reserveTerm", HttpMethod.POST, httpEntity, DoctorTermsDTO.class);

        DoctorTermsDTO doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(doctorTermsResponse).isNull();
    }

    @Test
    public void testReserveTermUnexistentTermDef() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        DoctorTermsDTO doctorTermsDTO = new DoctorTermsDTO();
        doctorTermsDTO.setDate(DB_DOCTOR_TERMS_FREE_DATE);
        doctorTermsDTO.setStart_term("00:02");
        doctorTermsDTO.setEnd_term("00:05");

        HttpEntity<DoctorTermsDTO> httpEntity = new HttpEntity(doctorTermsDTO, headers);
        ResponseEntity<DoctorTermsDTO> responseEntity =
                restTemplate.exchange("/doctor/reserveTerm", HttpMethod.POST, httpEntity, DoctorTermsDTO.class);

        DoctorTermsDTO doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(doctorTermsResponse).isNull();
    }

    @Test
    public void testGetDoctorsByType() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<DoctorTermsDTO> httpEntity = new HttpEntity(headers);
        ResponseEntity<DoctorDTO[]> responseEntity =
                restTemplate.exchange("/doctor/getDoctorsByType/1", HttpMethod.GET, httpEntity, DoctorDTO[].class);

        DoctorDTO[] doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsResponse).hasSize(2);
        assertThat(doctorTermsResponse[0].getFirstName()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorTermsResponse[0].getLastName()).isEqualTo(DB_DOCTOR_LAST_NAME);
        assertThat(doctorTermsResponse[1].getFirstName()).isEqualTo("Маринко");
        assertThat(doctorTermsResponse[1].getLastName()).isEqualTo("Маринковић");
        assertThat(doctorTermsResponse[0].getTip()).isEqualTo("Стоматологија");
        assertThat(doctorTermsResponse[1].getTip()).isEqualTo("Стоматологија");
    }

    @Test
    public void testGetDoctorsByUnexistentType() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity(headers);
        ResponseEntity<DoctorDTO[]> responseEntity =
                restTemplate.exchange("/doctor/getDoctorsByType/-1", HttpMethod.GET, httpEntity, DoctorDTO[].class);

        DoctorDTO[] doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsResponse).isNotNull();
        assertThat(doctorTermsResponse).isEmpty();
    }

    @Test
    public void testGetTypeByDoctor() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity(headers);
        ResponseEntity<TipPregledaDTO> responseEntity =
                restTemplate.exchange("/doctor/getDoctor/3", HttpMethod.GET, httpEntity, TipPregledaDTO.class);

        TipPregledaDTO doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsResponse).isNotNull();
        assertThat(doctorTermsResponse.getId()).isEqualTo(1L);
        assertThat(doctorTermsResponse.getName()).isEqualTo("Стоматологија");
    }

    /**
     * return: svi termini za smenu u kojoj radi doktor sa id-jem [id]
     * */
    @Test
    public void testGetTermsByWorkShift() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenDoctor);

        HttpEntity<Object> httpEntity = new HttpEntity(headers);
        ResponseEntity<TipPregledaDTO[]> responseEntity =
                restTemplate.exchange("/doctor/getTermsByWorkShift/3", HttpMethod.GET, httpEntity, TipPregledaDTO[].class);

        TipPregledaDTO[] doctorTermsResponse = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorTermsResponse).isNotNull();
        assertThat(doctorTermsResponse).isNotEmpty();
        assertThat(doctorTermsResponse).hasSize(10);
    }

}
