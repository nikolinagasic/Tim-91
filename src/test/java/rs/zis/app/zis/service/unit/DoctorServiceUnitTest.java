package rs.zis.app.zis.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.service.DoctorService;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorServiceUnitTest {

    @Autowired
    DoctorService doctorService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllByClinic() {
    }

    @Test
    void searchDoctors() {
    }
}