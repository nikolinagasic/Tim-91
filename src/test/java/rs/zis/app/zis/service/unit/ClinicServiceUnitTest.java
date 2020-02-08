package rs.zis.app.zis.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.ClinicRepository;
import rs.zis.app.zis.service.ClinicService;
import rs.zis.app.zis.service.DoctorTermsService;

import java.util.ArrayList;
import java.util.List;
import static rs.zis.app.zis.constants.UserConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SuppressWarnings({"unused", "unchecked"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClinicServiceUnitTest {

    @Autowired
    ClinicService clinicService;

    @MockBean
    DoctorTermsService doctorTermsService;

    @MockBean
    ClinicRepository clinicRepository;

    private Clinic clinic = new Clinic();
    private Clinic clinicFail = new Clinic();

    private Doctor doctor1 = new Doctor();
    private Doctor doctor2 = new Doctor();
    private Doctor doctor3 = new Doctor();
    private Doctor doctor4 = new Doctor();

    private DoctorTerms doctorTerms1 = new DoctorTerms();
    private DoctorTerms doctorTerms2 = new DoctorTerms();
    private DoctorTerms doctorTerms3 = new DoctorTerms();
    private DoctorTerms doctorTerms4 = new DoctorTerms();
    private DoctorTerms doctorTermsFail = new DoctorTerms();

    private Patient patient = new Patient();

    @BeforeEach
    void setUp() {
        initializeDoctors();
        initializeDoctorTerms();
        clinic.setId(500L);
        clinicFail.setId(404L);
        clinicFail.setName("Фаил");
        clinic.setName("Ацина клиника");
        clinic.getDoctors().add(doctor1);
        clinic.getDoctors().add(doctor2);
        clinic.getDoctors().add(doctor3);
        patient.setId(500L);
        patient.setMail(DB_PATIENT_MAIL);
        patient.setFirstName(DB_PATIENT_FIRST_NAME);
        patient.setLastName(DB_PATIENT_LAST_NAME);
        Mockito.when(clinicRepository.findOneById(clinic.getId()))
                .then(InvocationOnMock -> clinic);
        Mockito.when(clinicRepository.findOneById(clinicFail.getId()))
                .then(InvocationOnMock -> null);
        Mockito.when(clinicRepository.findOneByName(clinic.getName()))
                .then(InvocationOnMock -> clinic);
        Mockito.when(clinicRepository.findOneByName(clinicFail.getName()))
                .then(InvocationOnMock -> null);

        Mockito.when(doctorTermsService.findAll())
                .then(InvocationOnMock -> new ArrayList<DoctorTerms>(){{
                    add(doctorTerms1);
                    add(doctorTerms2);
                    add(doctorTerms3);
                    add(doctorTerms4);
                }});
        Mockito.when(doctorTermsService.findOneById(doctorTerms1.getId()))
                .then(InvocationOnMock -> doctorTerms1);
        Mockito.when(doctorTermsService.findOneById(doctorTerms3.getId()))
                .then(InvocationOnMock -> doctorTerms3);
        Mockito.when(doctorTermsService.findOneById(doctorTerms4.getId()))
                .then(InvocationOnMock -> doctorTerms4);
        Mockito.when(doctorTermsService.findOneById(doctorTermsFail.getId()))
                .then(InvocationOnMock -> null);
        Mockito.when(doctorTermsService.save(any(DoctorTerms.class)))
                .then(InvocationOnMock -> doctorTerms1);

        Mockito.when(clinicRepository.findAll())
                .then(InvocationOnMock -> new ArrayList<Clinic>(){{
                    add(clinic);
                    add(clinicFail);
                }});
    }

    private void initializeDoctorTerms() {
        TermDefinition termDefinition1 = new TermDefinition();
        TermDefinition termDefinition2 = new TermDefinition();
        termDefinition1.setId(500L);
        termDefinition2.setId(501L);
        termDefinition1.setStartTerm("12:40");
        termDefinition2.setStartTerm("13:05");
        termDefinition1.setEndTerm("07:45");
        termDefinition2.setEndTerm("08:05");
        doctorTerms1.setId(500L);
        doctorTerms2.setId(501L);
        doctorTerms3.setId(502L);
        doctorTerms4.setId(503L);
        doctorTermsFail.setId(404L);
        doctorTerms1.setPredefined(true);
        doctorTerms2.setPredefined(true);
        doctorTerms3.setPredefined(false);
        doctorTerms4.setPredefined(false);
        doctorTerms4.setActive(false);
        doctorTerms1.setDoctor(doctor1);
        doctorTerms2.setDoctor(doctor2);
        doctorTerms3.setDoctor(doctor3);
        doctorTerms4.setDoctor(doctor4);
        doctorTerms1.setTerm(termDefinition1);
        doctorTerms2.setTerm(termDefinition2);
        doctorTerms3.setTerm(termDefinition2);
        doctorTerms4.setTerm(termDefinition1);
    }

    private void initializeDoctors() {
        TipPregleda tipPregleda1 = new TipPregleda();
        TipPregleda tipPregleda2 = new TipPregleda();
        tipPregleda1.setId(500L);
        tipPregleda2.setId(501L);
        tipPregleda1.setName("Novi tip");
        tipPregleda2.setName("Novi tip2");
        doctor1.setClinic(clinic);
        doctor2.setClinic(clinic);
        doctor3.setClinic(clinic);
        doctor1.setId(500L);
        doctor2.setId(501L);
        doctor3.setId(502L);
        doctor4.setId(503L);
        doctor1.setFirstName("Марко");
        doctor1.setLastName("Марковић");
        doctor2.setFirstName("Дарко");
        doctor2.setLastName("Дарковић");
        doctor3.setFirstName("Славко");
        doctor3.setLastName("Славковић");
        doctor4.setFirstName("Мирко");
        doctor4.setLastName("Мирковић");
        doctor1.setTip(tipPregleda1);
        doctor2.setTip(tipPregleda2);
        doctor3.setTip(tipPregleda2);
        doctor4.setTip(tipPregleda1);
    }

    @Test
    void getPredefinedTerms() {
        List<DoctorTermsDTO> dtoList = clinicService.getPredefinedTerms(clinic.getId());

        assertThat(dtoList).isNotNull();
        assertThat(dtoList).isNotEmpty();
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getId()).isEqualTo(500L);
        assertThat(dtoList.get(1).getId()).isEqualTo(501L);
        assertThat(dtoList.get(0).getFirstNameDoctor()).isEqualTo("Марко");
        assertThat(dtoList.get(0).getLastNameDoctor()).isEqualTo("Марковић");
        assertThat(dtoList.get(1).getFirstNameDoctor()).isEqualTo("Дарко");
        assertThat(dtoList.get(1).getLastNameDoctor()).isEqualTo("Дарковић");
    }

    @Test()
    void getPredefinedTermsClinicFail() {
        List<DoctorTermsDTO> dtoList = clinicService.getPredefinedTerms(clinicFail.getId());

        assertThat(dtoList).isNotNull();
        assertThat(dtoList).isEmpty();
    }

    @Test
    void reservePredefinedTerm() {
        boolean isReserved = clinicService.reservePredefinedTerm(doctorTerms1.getId(), patient);

        assertThat(isReserved).isNotNull();
        assertThat(isReserved).isTrue();
    }

    @Test
    void reservePredefinedDoctorTermsFail() {
        // clinic doesn't exist
        boolean isReserved = clinicService.reservePredefinedTerm(doctorTermsFail.getId(), patient);

        assertThat(isReserved).isNotNull();
        assertThat(isReserved).isFalse();
    }

    @Test
    void reservePredefinedDoctorTermsInactive() {
        // doctorTerms4 is not active
        boolean isReserved = clinicService.reservePredefinedTerm(doctorTerms4.getId(), patient);

        assertThat(isReserved).isNotNull();
        assertThat(isReserved).isFalse();
    }

    @Test
    void reservePredefinedDoctorTermsNotPredefined() {
        // doctorTerms3 is not predefined
        boolean isReserved = clinicService.reservePredefinedTerm(doctorTerms3.getId(), patient);

        assertThat(isReserved).isNotNull();
        assertThat(isReserved).isFalse();
    }

    @Test
    void findAll() {
        List<Clinic> clinicList = clinicService.findAll();

        assertThat(clinicList).isNotNull();
        assertThat(clinicList).isNotEmpty();
        assertThat(clinicList).hasSize(2);
        assertThat(clinicList.get(1).getId()).isEqualTo(404L);
        assertThat(clinicList.get(0).getId()).isEqualTo(500L);
        assertThat(clinicList.get(0).getName()).isEqualTo("Ацина клиника");
        assertThat(clinicList.get(1).getName()).isEqualTo("Фаил");
    }

    @Test
    void findOneById() {
        Clinic clinic = clinicService.findOneById(500L);
        assertThat(clinic).isNotNull();
        assertThat(clinic.getName()).isEqualTo("Ацина клиника");
    }

    @Test
    void findOneByIdFailClinic() {
        Clinic clinic = clinicService.findOneById(clinicFail.getId());
        assertThat(clinic).isNull();
    }

    @Test
    void findOneByName() {
        Clinic clinic = clinicService.findOneByName("Ацина клиника");
        assertThat(clinic).isNotNull();
        assertThat(clinic.getId()).isEqualTo(500L);
    }

    @Test
    void findOneByNameClinicFail() {
        Clinic clinic = clinicService.findOneByName("Фаил");
        assertThat(clinic).isNull();
    }

    @Test
    void findDoctorsByClinic() {
    }

    @Test
    void searchClinic() {
    }

    @Test
    void filterClinic() {
    }
}