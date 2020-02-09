package rs.zis.app.zis.service.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.service.DoctorService;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings({"WrapperTypeMayBePrimitive", "CatchMayIgnoreException"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DoctorServiceTest {

    @Autowired
    DoctorService doctorService;

    @Test
    public void testFindAll() {
        List<Doctor> doctorList = doctorService.findAll();

        assertThat(doctorList).hasSize(DB_DOCTOR_COUNT);
        assertThat(doctorList.get(0).getRole()).isEqualTo("doctor");
        assertThat(doctorList.get(0).getRole().equals("doctor") && doctorList.get(1).getRole().equals("doctor")
                && doctorList.get(2).getRole().equals("doctor")).isTrue();
        assertThat(doctorList.get(0).getFirstName()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorList.get(0).getLastName()).isEqualTo(DB_DOCTOR_LAST_NAME);
    }

    @Test
    public void testFindDoctorByFirstNameAndLastName() {
        Doctor doctor = doctorService.findDoctorByFirstNameAndLastName(DB_DOCTOR_FIRST_NAME, DB_DOCTOR_LAST_NAME);

        assertThat(doctor).isNotNull();
        assertThat(doctor.getRole()).isEqualTo("doctor");
        assertThat(doctor.getId()).isEqualTo(3L);
        assertThat(doctor.getFirstName()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctor.getLastName()).isEqualTo(DB_DOCTOR_LAST_NAME);
    }

    @Test
    public void testFindOneByMail() {
        Doctor doctor = doctorService.findOneByMail("doctor@gmail.com");

        assertThat(doctor).isNotNull();
        assertThat(doctor.getId()).isEqualTo(3L);
        assertThat(doctor.getFirstName()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctor.getLastName()).isEqualTo(DB_DOCTOR_LAST_NAME);
        assertThat(doctor.getRole()).isEqualTo("doctor");
    }

    @Test
    public void testFindDoctorByLastName() {
        List<Doctor> doctorList = doctorService.findDoctorByLastName(DB_DOCTOR_LAST_NAME);

        assertThat(doctorList).isNotNull();
        assertThat(doctorList).isNotEmpty();
        assertThat(doctorList.get(0).getFirstName()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorList.get(0).getRole()).isEqualTo("doctor");
    }

    @Test
    public void testFindDoctorByNonexistentLastName() {
        List<Doctor> doctorList = doctorService.findDoctorByLastName("Презименовчић");

        assertThat(doctorList).isNotNull();
        assertThat(doctorList).isEmpty();
    }



}
