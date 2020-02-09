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
import org.springframework.web.client.RestClientException;
import rs.zis.app.zis.auth.JwtAuthenticationRequest;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.UserTokenState;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;

import javax.validation.constraints.Null;

import static org.assertj.core.api.Assertions.assertThat;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings({"FieldCanBeLocal", "unused", "unchecked", "rawtypes", "ConstantConditions"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClinicControllerIT {

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
        ResponseEntity<ClinicDTO[]> responseEntity =
                restTemplate.exchange("/clinic/getAll", HttpMethod.GET, httpEntity, ClinicDTO[].class);

        ClinicDTO[] clinicDTOS = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clinicDTOS).isNotNull();
        assertThat(clinicDTOS).isNotEmpty();
        assertThat(clinicDTOS).hasSize(DB_CLINIC_COUNT);
        assertThat(clinicDTOS[0].getName()).isEqualTo("Моја клиника");
        assertThat(clinicDTOS[1].getName()).isEqualTo("Наша клиника");
        assertThat(clinicDTOS[0].getLocation()).isEqualTo("Нови Сад");
        assertThat(clinicDTOS[1].getLocation()).isEqualTo("Футог");
        assertThat(clinicDTOS[0].getId()).isEqualTo(1L);
        assertThat(clinicDTOS[0].getAddress()).isEqualTo("Тополска 18");
    }

    @Test
    public void testPredefinedTerms(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        ClinicDTO clinicBody = new ClinicDTO();
        clinicBody.setId(1L);
        clinicBody.setName("Моја клиника");
        clinicBody.setLocation("Нови Сад");

        HttpEntity<Object> httpEntity = new HttpEntity<>(clinicBody, headers);
        ResponseEntity<DoctorTermsDTO[]> responseEntity =
                restTemplate.exchange("/clinic/getPredefinedTerms", HttpMethod.POST, httpEntity, DoctorTermsDTO[].class);

        DoctorTermsDTO[] listDoctorTermsDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listDoctorTermsDTO).isNotNull();
        assertThat(listDoctorTermsDTO).isNotEmpty();
        DoctorTermsDTO termDTO = listDoctorTermsDTO[0];
        assertThat(termDTO).isNotNull();
        assertThat(termDTO.getId()).isEqualTo(2L);
        assertThat(termDTO.getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(termDTO.getPatient_id()).isEqualTo(DB_DOCTOR_TERMS_PREDEFINED_ID);
        assertThat(termDTO.getFirstNameDoctor()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(termDTO.getLastNameDoctor()).isEqualTo(DB_DOCTOR_LAST_NAME);
    }

    @Test()
    public void testPredefinedTermsUnexistentClinic(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        ClinicDTO clinicBody = new ClinicDTO();
        clinicBody.setId(-1L);

        HttpEntity<Object> httpEntity = new HttpEntity<>(clinicBody, headers);
        ResponseEntity<DoctorTermsDTO[]> responseEntity =
                restTemplate.exchange("/clinic/getPredefinedTerms", HttpMethod.POST, httpEntity, DoctorTermsDTO[].class);

        DoctorTermsDTO[] listDoctorTermsDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testReservePredefined() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange("/clinic/reservePredefinedTerm/"+DB_DOCTOR_TERMS_PREDEFINED_ID,
                        HttpMethod.POST, httpEntity, Boolean.class);

        Boolean isReserved = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isReserved).isTrue();
    }

    @Test
    public void testReservePredefinedUnexistentTerm() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange("/clinic/reservePredefinedTerm/-1",
                        HttpMethod.POST, httpEntity, Boolean.class);

        Boolean isReserved = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isReserved).isFalse();
    }

    @Test
    public void testGetOne() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ClinicDTO> responseEntity =
                restTemplate.exchange("/clinic/getOne/Моја клиника",
                        HttpMethod.GET, httpEntity, ClinicDTO.class);

        ClinicDTO clinicDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clinicDTO).isNotNull();
        assertThat(clinicDTO.getName()).isEqualTo("Моја клиника");
        assertThat(clinicDTO.getLocation()).isEqualTo("Нови Сад");
        assertThat(clinicDTO.getAddress()).isEqualTo("Тополска 18");
        assertThat(clinicDTO.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetClinicById() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ClinicDTO> responseEntity =
                restTemplate.exchange("/clinic/getClinicByName/1",
                        HttpMethod.GET, httpEntity, ClinicDTO.class);

        ClinicDTO clinicDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clinicDTO).isNotNull();
        assertThat(clinicDTO.getId()).isEqualTo(1L);
        assertThat(clinicDTO.getName()).isEqualTo("Моја клиника");
        assertThat(clinicDTO.getLocation()).isEqualTo("Нови Сад");
        assertThat(clinicDTO.getAddress()).isEqualTo("Тополска 18");
    }

    @Test
    public void testGetClinicByUnexistentId() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenPatient);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/clinic/getClinicByName/-1",
                        HttpMethod.GET, httpEntity, String.class);

        String clinicDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(clinicDTO).isEqualTo("greska");
    }

}
