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
import rs.zis.app.zis.domain.UserTokenState;
import rs.zis.app.zis.dto.RoomDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static rs.zis.app.zis.constants.UserConstants.*;
import static rs.zis.app.zis.constants.UserConstants.DB_DOCTOR_LAST_NAME;

@SuppressWarnings({"FieldCanBeLocal", "unused", "unchecked", "rawtypes", "ConstantConditions"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private String accessTokenClinicAdministrator;

    @Before
    public void login() {
        ResponseEntity<UserTokenState> responseEntityPat =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("cadmin@gmail.com", "admin"), UserTokenState.class);
        // preuzmemo token jer ce nam trebati za testiranje REST kontrolera
        accessTokenClinicAdministrator = responseEntityPat.getBody().getAccessToken();


    }

    @Test
    public void testGetRoomsInClinic(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", accessTokenClinicAdministrator);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RoomDTO[]> responseEntity =
                restTemplate.exchange("/room/getByClinic/Моја клиника", HttpMethod.GET, httpEntity, RoomDTO[].class);

        RoomDTO[] roomDTOS = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(roomDTOS).isNotNull();
        assertThat(roomDTOS).isNotEmpty();
        assertEquals(roomDTOS.length, 4);
        assertEquals(roomDTOS[0].getName(), DB_ROOM_NAME1);
        assertEquals(roomDTOS[0].getNumber(), DB_ROOM_NUMBER1);
        assertEquals(roomDTOS[1].getName(), DB_ROOM_NAME2);
        assertEquals(roomDTOS[1].getNumber(), DB_ROOM_NUMBER2);
        assertEquals(roomDTOS[2].getName(), DB_ROOM_NAME3);
        assertEquals(roomDTOS[2].getNumber(), DB_ROOM_NUMBER3);
        assertEquals(roomDTOS[3].getName(), DB_ROOM_NAME4);
        assertEquals(roomDTOS[3].getNumber(), DB_ROOM_NUMBER4);

    }

    @Test
    public void testReserveRoom(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange("/room/reserveRoom/13/1/"+DB_DATE+"/false", HttpMethod.POST,
                        httpEntity, Boolean.class);

        assertThat(responseEntity.getBody()).isEqualTo(false);

    }

}
