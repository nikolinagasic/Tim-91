package rs.zis.app.zis.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorTermsRepository;
import rs.zis.app.zis.repository.TermDefinitionRepository;
import rs.zis.app.zis.service.*;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static rs.zis.app.zis.constants.UserConstants.*;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorTermsServiceUnitTest {

    @Autowired
    private DoctorTermsService doctorTermsService;

    @MockBean
    private DoctorTermsRepository doctorTermsRepository;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private TipPregledaService tipPregledaService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private ClinicAdministratorService clinicAdministratorService;

    @MockBean
    private TermDefinitionService termDefinitionService;

    @MockBean
    private TermDefinitionRepository termDefinitionRepository;

    private Doctor doctor = new Doctor();
    private Doctor doctorFail = new Doctor();
    private TermDefinition termDefinition = new TermDefinition();
    private Patient patient = new Patient();
    private Room room = new Room();
    private Room room2 = new Room();
    private TipPregleda tipPregleda =  new TipPregleda();

    @BeforeEach
    void setUp() {
        Mockito.when(termDefinitionService.findAllByWorkShift(any(Integer.class)))
                .then(invocationOnMock -> new ArrayList<TermDefinition>() {{
                        add(new TermDefinition(1L, 1, "08:15", "08:24"));
                        add(new TermDefinition(2L, 1, "08:32", "08:44"));
                        add(new TermDefinition(3L, 1, "09:00", "09:30"));
                        add(new TermDefinition(4L, 1, "09:30", "10:00"));
                        add(new TermDefinition(5L, 1, "10:30", "11:00"));
                        add(new TermDefinition(6L, 1, "11:00", "11:30"));
                        add(new TermDefinition(7L, 1, "11:30", "12:00"));
                        add(new TermDefinition(8L, 1, "12:00", "12:30"));
                        add(new TermDefinition(9L, 1, "12:30", "13:00"));
                    }});

        Mockito.when(doctorTermsRepository.findAllByDoctor(any(Doctor.class)))
                .then(invocationOnMock -> new ArrayList<DoctorTerms>() {{
                    DoctorTerms doctorTerms = new DoctorTerms();
                    doctorTerms.setDate(DB_DOCTOR_TERMS_DATE);
                    TermDefinition termDefinition = new TermDefinition();
                    termDefinition.setId(1L);
                    doctorTerms.setTerm(termDefinition);
                    doctorTerms.setId(1L);         // u ovom terminu je doktor zauzet [1. termin iz liste]
                    add(doctorTerms);
                }});

        Mockito.when(doctorTermsRepository.findAll())
                .then(invocationOnMock -> new ArrayList<DoctorTerms>() {{
                    Clinic clinic = new Clinic();
                    clinic.setName("Моја клиника");
                    Doctor doctor = new Doctor();
                    doctor.setClinic(clinic);
                    DoctorTerms doctorTerms = new DoctorTerms();
                    doctorTerms.setDoctor(doctor);
                    doctorTerms.setId(64L);
                    doctorTerms.setDate(123456789L);
                    add(doctorTerms);
                }});


        doctor = new Doctor();
        doctor.setId(3L);
        doctor.setFirstName(DB_DOCTOR_FIRST_NAME);
        doctor.setLastName(DB_DOCTOR_LAST_NAME);

        doctorFail.setId(-2L);
        doctorFail.setFirstName("No first name");
        doctorFail.setLastName("No last name");

        DoctorTerms doctorTerms = new DoctorTerms();
        doctorTerms.setId(500L);
        doctorTerms.setDate(DB_DOCTOR_TERMS_DATE);
        doctorTerms.setDoctor(doctor);
        Mockito.when(doctorService.findOneById(doctor.getId()))
                .then(invocationOnMock -> doctor);
        Mockito.when(doctorService.findOneById(doctorFail.getId()))
                .then(invocationOnMock -> null);
        Mockito.when(doctorService.findDoctorByFirstNameAndLastName(any(String.class), any(String.class)))
                .then(invocationOnMock -> doctor);
        // ako je datum slobodan, vrati null
        Mockito.when(doctorTermsRepository.findOneByDateAndStartTermAndDoctorId(eq(DB_DOCTOR_TERMS_FREE_DATE),
                any(TermDefinition.class), any(Doctor.class))).then(invocationOnMock -> null);
        // ako je datum zauzet, vrati nesto
        Mockito.when(doctorTermsRepository.findOneByDateAndStartTermAndDoctorId(eq(DB_DOCTOR_TERMS_DATE),
                any(TermDefinition.class), any(Doctor.class))).then(invocationOnMock -> doctorTerms);
        Mockito.when(clinicAdministratorService.findAll())
                .then(invocationOnMock -> new ArrayList<ClinicAdministrator>(){{
                    ClinicAdministrator clinicAdministrator = new ClinicAdministrator();
                    clinicAdministrator.setId(500L);
                    clinicAdministrator.setFirstName("Admin");
                    clinicAdministrator.setLastName("Admin");
                }});
        Mockito.when(doctorTermsRepository.save(any(DoctorTerms.class)))
                .then(invocationOnMock -> doctorTerms);

        termDefinition = new TermDefinition();
        termDefinition.setId(500L);
        termDefinition.setWorkShift(1);
        termDefinition.setStartTerm(DB_DOCTOR_TERMS_START_TERM);
        termDefinition.setEndTerm(DB_DOCTOR_TERMS_END_TERM);
        Mockito.when(termDefinitionService.findOneByStart_term(any(String.class)))
                .then(invocationOnMock -> termDefinition);

        patient.setMail(DB_PATIENT_MAIL);
        patient.setFirstName(DB_PATIENT_FIRST_NAME);
        patient.setLastName(DB_PATIENT_LAST_NAME);
        Mockito.when(patientService.findOneByMail(any(String.class)))
                .then(invocationOnMock -> patient);

        Mockito.when(termDefinitionService.findOneById(any(Long.class)))
                .then(invocationOnMock -> termDefinition);

        room.setId(500L);
        room.setActive(true);
        room.setName("Soba 22");
        room.setNumber("22");

        room2.setId(501L);
        Mockito.when(roomService.findOneById(room.getId()))
                .then(invocationOnMock -> room);
        Mockito.when(roomService.findOneById(room2.getId()))
                .then(invocationOnMock -> room2);
        doctorTerms.setTerm(termDefinition);
        Mockito.when(doctorTermsRepository.findAllByRoom(room2))
                .then(invocationOnMock -> new ArrayList<DoctorTerms>(){{
                    add(doctorTerms);
                }});

        tipPregleda.setActive(true);
        tipPregleda.setName("Нови тип прегледа");
        tipPregleda.setId(500L);
        Mockito.when(tipPregledaService.findOneById(any(Long.class)))
                .then(invocationOnMock -> tipPregleda);
    }

    @Test
    void testGetTermine() {
        List<DoctorTermsDTO> dtoList = doctorTermsService.getTermine(DB_DOCTOR_TERMS_DATE, doctor);

        assertThat(dtoList).isNotNull();
        assertThat(dtoList).isNotEmpty();
        assertThat(dtoList).hasSize(8);
        assertThat(dtoList.get(0).getStart_term()).isEqualTo("08:32");
        assertThat(dtoList.get(0).getEnd_term()).isEqualTo("08:44");
    }

    @Test
    void testDetailTerm() {
        DoctorTermsDTO doctorTermsDTO = doctorTermsService.detailTerm(doctor.getId(),
                DB_DOCTOR_TERMS_DATE, termDefinition.getStartTerm(), patient.getMail());

        assertThat(doctorTermsDTO).isNotNull();
        assertThat(doctorTermsDTO.getDate()).isEqualTo(DB_DOCTOR_TERMS_DATE);
        assertThat(doctorTermsDTO.getStart_term()).isEqualTo(DB_DOCTOR_TERMS_START_TERM);
        assertThat(doctorTermsDTO.getFirstNameDoctor()).isEqualTo(DB_DOCTOR_FIRST_NAME);
        assertThat(doctorTermsDTO.getLastNameDoctor()).isEqualTo(DB_DOCTOR_LAST_NAME);
    }

    @Test
    void testDetailTermNonexistentDoctor() {
        DoctorTermsDTO doctorTermsDTO = doctorTermsService.detailTerm(doctorFail.getId(),
                DB_DOCTOR_TERMS_DATE, termDefinition.getStartTerm(), patient.getMail());

        assertThat(doctorTermsDTO).isNull();
    }

    @Test
    void testFindAllByClinic() {
        Clinic clinic = new Clinic();
        clinic.setName("Моја клиника");

        List<DoctorTerms> doctorTermsList = doctorTermsService.findAllByClinic(clinic);
        assertThat(doctorTermsList).isNotNull();
        assertThat(doctorTermsList).isNotNull();
        assertThat(doctorTermsList.get(0).getId()).isEqualTo(64L);
        assertThat(doctorTermsList.get(0).getDate()).isEqualTo(123456789L);
    }

    @Test
    void testFindAllByNonexistentClinic() {
        Clinic clinic = new Clinic();
        clinic.setName("Nonexistent clinic");

        List<DoctorTerms> doctorTermsList = doctorTermsService.findAllByClinic(clinic);
        assertThat(doctorTermsList).isNotNull();
        assertThat(doctorTermsList).isEmpty();
    }

    @Test
    void testReserveTerm() {
        DoctorTermsDTO doctorTermsDTO = new DoctorTermsDTO();
        doctorTermsDTO.setFirstNameDoctor(DB_DOCTOR_FIRST_NAME);
        doctorTermsDTO.setLastNameDoctor(DB_DOCTOR_LAST_NAME);
        doctorTermsDTO.setStart_term(DB_DOCTOR_TERMS_START_TERM);
        doctorTermsDTO.setDate(DB_DOCTOR_TERMS_FREE_DATE);
        boolean isReserved = doctorTermsService.reserveTerm(patient.getMail(), doctorTermsDTO);

        assertThat(isReserved).isNotNull();
        assertThat(isReserved).isTrue();
    }

    @Test
    void testReserveBusyTerm() {
        DoctorTermsDTO doctorTermsDTO = new DoctorTermsDTO();
        doctorTermsDTO.setFirstNameDoctor(DB_DOCTOR_FIRST_NAME);
        doctorTermsDTO.setLastNameDoctor(DB_DOCTOR_LAST_NAME);
        doctorTermsDTO.setStart_term(DB_DOCTOR_TERMS_START_TERM);
        doctorTermsDTO.setDate(DB_DOCTOR_TERMS_DATE);
        boolean isReserved = doctorTermsService.reserveTerm(patient.getMail(), doctorTermsDTO);

        assertThat(isReserved).isNotNull();
        assertThat(isReserved).isFalse();
    }

    @Test
    void testCreatePredefinedTerm() {
        Long satnica_id = 500L;
        Long room_id = 500L;
        Long type_id = 500L;
        int retVal = doctorTermsService.createPredefinedTerm(DB_DOCTOR_TERMS_DATE, satnica_id,
                room_id, type_id, doctor.getId(), 999.99, 5);

        assertThat(retVal).isNotNull();
        assertThat(retVal).isEqualTo(0);        // uspesno kreiran
    }

    @Test
    void testCreatePredefinedTermRoomBusy() {
        Long satnica_id = 500L;
        int retVal = doctorTermsService.createPredefinedTerm(DB_DOCTOR_TERMS_DATE, satnica_id,
                room2.getId(), tipPregleda.getId(), doctor.getId(), 999.99, 5);

        assertThat(retVal).isNotNull();
        assertThat(retVal).isEqualTo(-2);        // soba zauzeta
    }

}