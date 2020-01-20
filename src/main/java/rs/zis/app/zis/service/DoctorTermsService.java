package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorTermsRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection", "DefaultAnnotationParam", "NumberEquality", "UnusedAssignment", "RedundantIfStatement"})
@Service
@Transactional(readOnly = true)
public class DoctorTermsService {

    @Autowired
    private DoctorTermsRepository doctorTermsRepository;

    @Autowired
    private TermDefinitionService termDefinitionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TipPregledaService tipPregledaService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAll() {
        return doctorTermsRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Page<DoctorTerms> findAll(Pageable page) {
        return doctorTermsRepository.findAll(page);
    }

    @Transactional(readOnly = false)
    public void remove(Long id) {
        doctorTermsRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void removeLogical(Long id) {
        DoctorTerms doctorTerms = findOneById(id);
        if(doctorTerms != null){
            doctorTerms.setActive(false);
        }
    }

    @Transactional(readOnly = false)
    public DoctorTerms findOneById(Long id) {
        return doctorTermsRepository.findOneById(id);
    }

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAllByDate(Long date) {
        return doctorTermsRepository.findAllByDate(date);
    }

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAllByDoctor(Doctor doctor){
        return doctorTermsRepository.findAllByDoctor(doctor);
    }

    @Transactional(readOnly = false)
    public DoctorTerms save(DoctorTerms u) {return doctorTermsRepository.save(u);}

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAllByRoom(Room room) {
        return doctorTermsRepository.findAllByRoom(room);
    }

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> getTermine(long date, Doctor doctor){
        List<DoctorTermsDTO> retList = new ArrayList<>();
        List<DoctorTerms> listaTermina = findAllByDoctor(doctor);           // lista termina mog doktora

        // svi termini za tu smenu (prva/druga smena)
        List<TermDefinition> listaSvihTermina = termDefinitionService.findAllByWorkShift(doctor.getWorkShift());

        // izbaciti sve termine koji su zauzeti (u retList hocu samo one slobodne termine za tog doktora)
        for (TermDefinition termDefinition : listaSvihTermina) {
            boolean zauzet = false;
            for (DoctorTerms doctorTerms : listaTermina) {
                if(doctorTerms.getDate() == date) {                                 // ako je za taj datum
                    if (doctorTerms.getTerm().getId() == termDefinition.getId()) {  // i ako je ta satnica
                        zauzet = true;                                           // => nemoj da ga dodajes
                        break;
                    }
                }
            }
            if(!zauzet) {
                retList.add(new DoctorTermsDTO(date, termDefinition, doctor, new Patient()));
            }
        }

        return retList;
    }

    @Transactional(readOnly = false)
    public DoctorTermsDTO detailTerm(Long doctor_id, Long date, String start_term, String mail_patient){
        Doctor doctor = doctorService.findOneById(doctor_id);
        TermDefinition termDefinition = termDefinitionService.findOneByStart_term(start_term);
        Patient patient = patientService.findOneByMail(mail_patient);

        return new DoctorTermsDTO(date, termDefinition, doctor, new Patient());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean reserveTerm(String mail_patient, DoctorTermsDTO doctorTermsDTO){
        Doctor doctor = doctorService.findDoctorByFirstNameAndLastName(doctorTermsDTO.getFirstNameDoctor(),
                doctorTermsDTO.getLastNameDoctor());
        TermDefinition term_def = termDefinitionService.findOneByStart_term(doctorTermsDTO.getStart_term());
        Patient patient = patientService.findOneByMail(mail_patient);

        DoctorTerms dt = new DoctorTerms();
        try {
            dt = doctorTermsRepository.findOneByDateAndStartTermAndDoctorId(doctorTermsDTO.getDate(),
                    term_def,
                    doctor);
        }catch (Exception e){
            System.out.println("Okinut exception: " + e.getClass());
            return false;
        }

        List<ClinicAdministrator> lista_cadmina = new ArrayList<>();
        for (ClinicAdministrator clinicAdministrator : clinicAdministratorService.findAll()) {
            if(clinicAdministrator.getClinic().getId() == doctor.getClinic().getId()){
                lista_cadmina.add(clinicAdministrator);
            }
        }

        if(dt == null) {
            DoctorTerms doctorTerms = new DoctorTerms();
            doctorTerms.setDate(doctorTermsDTO.getDate());
            doctorTerms.setDoctor(doctor);
            doctorTerms.setPatient(patient);
            doctorTerms.setTerm(term_def);

            doctorTerms.setProcessedByAdmin(false);
            doctorTermsRepository.save(doctorTerms);
            // poslati mejl administratoru/ima klinike
            for (ClinicAdministrator clinicAdministrator : lista_cadmina) {
                String textBody = "Поштовани,\nПристигао вам је нови захтев за прегледом у Вашој клиници. Захтев је следећи:\n" +
                        "Доктор: " + doctor.getFirstName() + " " + doctor.getLastName() + "\n" +
                        "Пацијент: " + patient.getFirstName() + " " + patient.getLastName() + "\n";
                notificationService.SendNotification(clinicAdministrator.getMail(), "billypiton43@gmail.com",
                        "Нови захтев за преглед", textBody);
            }
            return true;        // uspesno sam napravio tvoj termin
        }
        else{
            return false ;      // vec postoji jedan takav kreiran termin
        }
    }

    @Transactional(readOnly = false)
    public int createPredefinedTerm(Long date, Long satnica_id, Long room_id, Long type_id, Long doctor_id,
                                        double price, int discount){
        TermDefinition termDefinition = termDefinitionService.findOneById(satnica_id);
        Room room = roomService.findOneById(room_id);
        Doctor doctor = doctorService.findOneById(doctor_id);
        TipPregleda tipPregleda = tipPregledaService.findOneById(type_id);

        if(!checkDoctor(doctor, date, termDefinition)){
            return -1;
        }
        if(!checkRoom(room, date, termDefinition)){
            return -2;
        }

        DoctorTerms doctorTerms = new DoctorTerms();
        doctorTerms.setDate(date);
        doctorTerms.setDoctor(doctor);
        doctorTerms.setTerm(termDefinition);
        doctorTerms.setRoom(room);
        doctorTerms.setExamination(true);
        doctorTerms.setPrice(price);
        doctorTerms.setDiscount(discount);
        doctorTerms.setPredefined(true);
        doctorTerms.setProcessedByAdmin(true);

        save(doctorTerms);
        return 0;
    }

    private boolean checkDoctor(Doctor doctor, Long date, TermDefinition termDefinition){
        for (DoctorTerms doctorTerm : findAllByDoctor(doctor)) {
            if(doctorTerm.getDate() == date){
                if(doctorTerm.getTerm().equals(termDefinition)){
                    return false;
                }
            }
        }

        for (Vacation vacation : vacationService.findAllByDoctor(doctor)) {
            if(vacation.getPocetak() < date && vacation.getKraj() > date){
                return false;
            }
        }

        return true;
    }

    private boolean checkRoom(Room room, Long date, TermDefinition termDefinition){
        for (DoctorTerms doctorTerm : findAllByRoom(room)) {
            if(doctorTerm.getDate() == date){
                if(doctorTerm.getTerm().equals(termDefinition)){
                    return false;
                }
            }
        }

        return true;
    }


}
