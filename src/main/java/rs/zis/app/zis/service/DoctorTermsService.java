package rs.zis.app.zis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
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
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorTermsRepository;

import javax.persistence.LockModeType;
import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings({"unused", "SpellCheckingInspection", "DefaultAnnotationParam", "NumberEquality", "UnusedAssignment", "RedundantIfStatement"})
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

    @Autowired
    private TipPregledaService tipPregledaService;

    @Autowired
    private VacationService vacationService;

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
    public List<DoctorTerms> findAllByClinic(Clinic clinic) {
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
    public List<DoctorTerms> findAllByDoctor(Long id) {
        return doctorTermsRepository.findAllByDoctor(doctorService.findOneById(id));
    }
    
    public List<DoctorTerms> findAllByDoctor(Doctor doctor)
    {
         List<DoctorTerms> all_terms = findAll();
         List<DoctorTerms> ret = new ArrayList<>();
         Set<Doctor> dodatni = new HashSet<>();
         for(DoctorTerms d : all_terms){
             if(d.getDoctor().getId()==doctor.getId()){
                 ret.add(d);
                 continue;
             }
             dodatni = d.getDodatni_lekari();
             for(Doctor doctor1 : dodatni){
                 if(doctor1.getId()==doctor.getId()){
                     ret.add(d);
                     break;
                 }
             }
         }
         return ret;
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
        List<DoctorTerms> listaTermina = doctorTermsRepository.findAllByDoctor(doctor);           // lista termina mog doktora

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

        if(doctor == null){
            return null;
        }
        if(termDefinition == null){
            return null;
        }

        return new DoctorTermsDTO(date, termDefinition, doctor, new Patient());
    }

    ReentrantLock lock = new ReentrantLock();
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean reserveTerm(String mail_patient, DoctorTermsDTO doctorTermsDTO,boolean examination){
        lock.lock();

        DoctorTerms dt = new DoctorTerms();
        try {
            Doctor doctor = doctorService.findDoctorByFirstNameAndLastName(doctorTermsDTO.getFirstNameDoctor(),
                    doctorTermsDTO.getLastNameDoctor());
            TermDefinition term_def = termDefinitionService.findOneByStart_term(doctorTermsDTO.getStart_term());
            Patient patient = patientService.findOneByMail(mail_patient);

            if(patient == null){
                return false;
            }
            if(term_def == null){
                return false;
            }
            if(doctor == null){
                return false;
            }

            dt = doctorTermsRepository.findOneByDateAndStartTermAndDoctorId(doctorTermsDTO.getDate(),
                    term_def,
                    doctor);

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
            doctorTerms.setExamination(examination);
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
            String textBody = "Поштовани,\n\n\tУспешно сте резервисали термин:\n" +
                        "\tДоктор: " + doctor.getFirstName() + " " + doctor.getLastName() + "\n" +
                        "\tКлиника: " + doctor.getClinic().getName() + "\n" +
                        "\tЦена: " + doctor.getPrice() + "рсд\n" +
                        "\n\nСвако добро";
            notificationService.SendNotification(mail_patient, "billypiton43@gmail.com",
                    "Успешна резервација термина", textBody);
          
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
                                    if (doctorService.doctor_free_at_date(doctorTerms.getDoctor(),date)){
                                        for (DoctorTerms t : doctors) {
                                            if (t.getDate() == doctorTerms.getDate()) {
                                                if (t.getTerm() == doctorTerms.getTerm()) {
                                                    moze = false;
                                                    break;
                                                }
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
        } finally {
            lock.unlock();
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

    public List<DoctorTerms> findAllByProcessedByAdmin (boolean is) {
        return doctorTermsRepository.findAllByProcessedByAdmin(is);
    }
    public void sendMail(Long id,long date,TermDefinition termin,Room soba,Doctor doktor,Patient pacijent) {
        DoctorTerms doctorTerms = findOneById(id);
        String pregled;
        if (!doctorTerms.isExamination()) {
            pregled = "Операција";
        }
        else {
            pregled = "Преглед";
        }
        Date d=new Date(doctorTerms.getDate());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(d);
        String napomena = "";
        if (date != -1) {
            napomena = "*Напомена: Датум је промењен на "+dateText+"\n";
        }

        String tb="Поштовани," + "\n" +
                pregled+" ће се одржати у: "+soba.getName() +".\n"+ napomena +
                "Датум: "+dateText+"\nВреме: "+
                termin.getStartTerm() +"-"+
                termin.getEndTerm()+"\nДоктор: "+
                doktor.getFirstName()+" "+
                doktor.getLastName() +
                "\nПацијент: "+pacijent.getFirstName()+" "+
                pacijent.getLastName();
        System.out.println(tb);
        notificationService.SendNotification(doktor.getMail(), "billypiton43@gmail.com",
                "Обавештење", tb);
        notificationService.SendNotification(pacijent.getMail(), "billypiton43@gmail.com",
                "Обавештење", tb);
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
        for (DoctorTerms doctorTerm : findAllByDoctor(doctor.getId())) {
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
        for (DoctorTerms doctorTerm : doctorTermsRepository.findAllByRoom(room)) {
            if(doctorTerm.getDate() == date){
                if(doctorTerm.getTerm().equals(termDefinition)){
                    return false;
                }
            }
        }

        return true;
    }

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> getAllExaminations(Patient patient) {
        List<DoctorTermsDTO> dtoList = new ArrayList<>();
        for (DoctorTerms doctorTerms : findAll()) {
            if(doctorTerms.getPatient() != null){
                if(doctorTerms.getPatient().equals(patient) && doctorTerms.isProcessedByAdmin()){
                    dtoList.add(new DoctorTermsDTO(doctorTerms));
                }
            }
        }

        return dtoList;
    }

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> getSortExaminations(List<DoctorTermsDTO> listaTermina,
                                                    Long datum, String tip, String vrsta) {
        List<DoctorTermsDTO> retList = new ArrayList<>();
        boolean examination = false;
        if(vrsta.toLowerCase().equals("преглед")){
            examination = true;
        }
        for (DoctorTermsDTO doctorTermsDTO : listaTermina) {
            if(doctorTermsDTO.getDate() == datum || datum == -1){
                if(doctorTermsDTO.getType().toLowerCase().equals(tip.toLowerCase()) || tip.toLowerCase().equals("сви типови")){
                    if( notXorFunc(doctorTermsDTO.isExamination(), examination) || vrsta.toLowerCase().equals("прегледи и операције") ){
                        retList.add(doctorTermsDTO);
                    }
                }
            }
        }

        return retList;
    }

    private boolean notXorFunc(boolean d_term_exam, boolean examination) {
        if( (d_term_exam && examination) || (!d_term_exam && !examination) ){
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> sortByDate(List<DoctorTermsDTO> listaTermina, String order) {
        ArrayList<Long> lista_datuma = new ArrayList<>();
        for (DoctorTermsDTO doctorTermsDTO : listaTermina) {
            lista_datuma.add(doctorTermsDTO.getDate());
        }
        java.util.Collections.sort(lista_datuma);
        if(order.equals("d")) {         // descending
            java.util.Collections.reverse(lista_datuma);
        }

        ArrayList<DoctorTermsDTO> retList = new ArrayList<>();
        for (Long date : lista_datuma) {
            for (DoctorTermsDTO doctorTermsDTO : listaTermina) {
                if(doctorTermsDTO.getDate() == date && !retList.contains(doctorTermsDTO)){
                    retList.add(doctorTermsDTO);
                }
            }
        }

        return retList;
    }

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> sortByTip(List<DoctorTermsDTO> listaTermina, String order) {
        ArrayList<String> lista_tipova = new ArrayList<>();
        for (DoctorTermsDTO doctorTermsDTO : listaTermina) {
            lista_tipova.add(doctorTermsDTO.getType());
        }
        java.util.Collections.sort(lista_tipova);
        if(order.equals("d")) {         // descending
            java.util.Collections.reverse(lista_tipova);
        }

        ArrayList<DoctorTermsDTO> retList = new ArrayList<>();
        for (String tip : lista_tipova) {
            for (DoctorTermsDTO doctorTermsDTO : listaTermina) {
                if(doctorTermsDTO.getType().equals(tip) && !retList.contains(doctorTermsDTO)){
                    retList.add(doctorTermsDTO);
                }
            }
        }

        return retList;

    }

    public DoctorTerms update (DoctorTerms doctorTerms){
       return doctorTermsRepository.save(doctorTerms);
    }
}
