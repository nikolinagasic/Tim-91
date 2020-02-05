package rs.zis.app.zis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rs.zis.app.zis.controller.ClinicAdministratorController;
import rs.zis.app.zis.controller.ClinicCentreAdminController;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorTermsRepository;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "SpellCheckingInspection", "DefaultAnnotationParam", "NumberEquality"})
@Service
@Transactional(readOnly = true)
public class DoctorTermsService {
    private Logger logger = LoggerFactory.getLogger(ClinicCentreAdminController.class);

    @Autowired
    private DoctorTermsRepository doctorTermsRepository;

    @Autowired
    private ClinicAdministratorController clinicAdministratorController;

    @Autowired
    private TermDefinitionService termDefinitionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ClinicAdministratorService clinicAdministratorService;
    @Autowired
    private NotificationService notificationService;

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
    public List<DoctorTerms> findAllByClinic(Clinic clinic)
    {
        List<DoctorTerms> all = doctorTermsRepository.findAll();
        List<DoctorTerms> retVal = new ArrayList<>();
        for (DoctorTerms t: all) {
            if (t.getDoctor().getClinic().getName().equals(clinic.getName())) {
                retVal.add(t);
            }
        }
        return retVal;
    }


    @Transactional(readOnly = false)
    public List<DoctorTerms> findAllByDoctor(Doctor doctor){
        return doctorTermsRepository.findAllByDoctor(doctor);
    }

    @Transactional(readOnly = false)
    public DoctorTerms save(DoctorTerms u) {return doctorTermsRepository.save(u);}

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
    public boolean reserveTerm(String mail_patient, DoctorTermsDTO doctorTermsDTO,boolean examination){
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

        if(dt == null) {
            DoctorTerms doctorTerms = new DoctorTerms();
            doctorTerms.setDate(doctorTermsDTO.getDate());
            doctorTerms.setDoctor(doctor);
            doctorTerms.setPatient(patient);
            doctorTerms.setTerm(term_def);
            doctorTerms.setExamination(examination);
            doctorTerms.setProcessedByAdmin(false);
            doctorTermsRepository.save(doctorTerms);
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.HOURS.sleep(24);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!doctorTerms.isProcessedByAdmin()) {
                            boolean promenjen = false;
                            boolean moze = true;
                            long date = doctorTerms.getDate();
                            do {
                                for (Room room : roomService.findRoomByClinic(doctorTerms.getDoctor().getClinic())) {
                                    for (DoctorTerms t : room.getDoctorTerms()) {
                                        if (t.getDate() == doctorTerms.getDate()) {
                                            if (t.getTerm() == doctorTerms.getTerm()) {
                                                moze = false;
                                                break;
                                            }
                                        }
                                    }
                                    if (moze) {
                                        doctorTerms.setProcessedByAdmin(true);
                                        doctorTerms.setRoom(room);
                                        save(doctorTerms);
                                        room.addDoctorTerms(doctorTerms);
                                        roomService.update(room);
                                        long prosledi;
                                        if (promenjen) {
                                            prosledi = doctorTerms.getDate();
                                        } else {
                                            prosledi = -1;
                                        }
                                        try {
                                            sendMail(doctorTerms.getId(), prosledi,
                                                    doctorTerms.getTerm(),
                                                    room, doctorTerms.getDoctor(),
                                                    doctorTerms.getPatient());
                                        }  catch (MailException e) {
                                        System.out.println("Error sending message.");
                                        logger.info("Error Sending Mail:" + e.getMessage());
                                        }
                                        break;
                                    }
                                }
                                while (!moze) {
                                    moze = true;
                                    date = date + (1000 * 60 * 60 * 24);
                                    List<DoctorTerms> doctors = findAllByDoctor(doctorTerms.getDoctor());
                                    for (DoctorTerms t : doctors) {
                                        if (t.getDate() == doctorTerms.getDate()) {
                                            if (t.getTerm() == doctorTerms.getTerm()) {
                                                moze = false;
                                                break;
                                            }
                                        }
                                    }
                                    if (moze) {
                                        doctorTerms.setDate(date);
                                        promenjen = true;
                                        moze = false;
                                        break;
                                    }
                                }

                            } while (!moze);
                        }
                    }

            });
            t.start();
            return true;        // uspesno sam napravio tvoj termin
        }
        else{
            return false ;      // vec postoji jedan takav kreiran termin
        }
    }
    public int reserveRoom(Long id,Long idr,long date) {
        DoctorTerms term = doctorTermsRepository.findOneById(id);
        Room room = roomService.findOneById(idr);
        if (date != -1) {
            List<DoctorTerms> lista = doctorTermsRepository.findAllByDoctor(term.getDoctor());
            for (DoctorTerms t : lista) {
                if (t.getDate() == date) {
                    if (t.getTerm().getStartTerm().equals(term.getTerm().getStartTerm())) {
                        return 2;
                    }
                }
            }
            term.setDate(date);
        }
            for (DoctorTerms t : room.getDoctorTerms()) {
                if (t.getDate() == term.getDate()) {
                    if (t.getTerm().getStartTerm() == term.getTerm().getStartTerm()) {
                        System.out.println("false:"+t.getTerm().getStartTerm()+term.getTerm().getStartTerm());
                        return 1;
                    }
                }
            }

        term.setProcessedByAdmin(true);
        term.setRoom(room);
        doctorTermsRepository.save(term);
        return 0;
    }

    public List<DoctorTerms> findAllByRoom (Room room) {
        return doctorTermsRepository.findAllByRoom(room);
    }
    public List<DoctorTerms> findAllByProcessedByAdmin (boolean is) {
        return doctorTermsRepository.findAllByProcessedByAdmin(is);
    }
    public void sendMail(Long id,long date,TermDefinition termin,Room soba,Doctor doktor,Patient pacijent) {
        DoctorTerms doctorTerms = findOneById(id);
        String pregled;
        if (!doctorTerms.isExamination()) {
            pregled = "Operacija";
        }
        else {
            pregled = "Pregled";
        }
        Date d=new Date(doctorTerms.getDate());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(d);
        String napomena = "";
        if (date != -1) {
            napomena = "*Napomena: Datum je promenjen na "+dateText+"\n";
        }

        String tb="Postovani," + "\n" +
                pregled+" ce se odrzati u sali: "+soba.getName() +".\n"+ napomena +
                "Datum: "+dateText+"\nVreme: "+
                termin.getStartTerm() +"-"+
                termin.getEndTerm()+"\nDoktor: "+
                doktor.getFirstName()+" "+
                doktor.getLastName() +
                "\nPacijent: "+pacijent.getFirstName()+" "+
                pacijent.getLastName();
        System.out.println(tb);
        notificationService.SendNotification(doktor.getMail(), "billypiton43@gmail.com",
                "OBAVESTENJE", tb);
        notificationService.SendNotification(pacijent.getMail(), "billypiton43@gmail.com",
                "OBAVESTENJE", tb);
    }
}
