package rs.zis.app.zis.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.*;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@SuppressWarnings({"SpellCheckingInspection", "unused", "IfCanBeSwitch"})
@RestController
@RequestMapping("/clinicAdministrator")
public class ClinicAdministratorController extends WebConfig {
    private Logger logger = LoggerFactory.getLogger(ClinicCentreAdminController.class);

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private NurseService customNurseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private TipPregledaService tipPregledaService;

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;

    @PostMapping(consumes = "application/json" , value = "/registerDoctor")
    public ResponseEntity<Integer> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        Users proveriMail = userService.findOneByMail(doctorDTO.getMail());
        if(proveriMail != null){
            System.out.println("Adresa \""+proveriMail.getMail()+"\" nije jedinstvena u sistemu");
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }
        TipPregleda proveriTip = tipPregledaService.findOneByName(doctorDTO.getTip());
        if (proveriTip == null) {
            System.out.println("ovde usao"+ doctorDTO.getTip());
            return new ResponseEntity<>(-2, HttpStatus.NOT_FOUND);
        }
        System.out.println("klinika u dto: "+ doctorDTO.getClinic());
        Doctor doctor = doctorService.save(doctorDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json", value = "/registerNurse")
    public ResponseEntity<Integer> saveNurse(@RequestBody NurseDTO nurseDTO) {
        Users proveriMail = userService.findOneByMail(nurseDTO.getMail());
        if(proveriMail != null){
            System.out.println("Adresa \""+proveriMail.getMail()+"\" nije jedinstvena u sistemu");
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Nurse nurse = nurseService.save(nurseDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }
    @PreAuthorize("hasRole('CADMIN')")
    @GetMapping(produces = "application/json", value = "/getCadmin")
    public ResponseEntity<?> changeAttribute(@RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        return new ResponseEntity<>(clinicAdministratorService.findOneByMail(mail), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('CADMIN')")
    @PostMapping(value = "/changeAttribute/{naziv}/{vrednost}/{mail}")
    public ResponseEntity<?> changeAttribute(@PathVariable("naziv") String naziv,
                                             @PathVariable("vrednost") String vrednost,
                                             @PathVariable("mail") String mail) {
        System.out.println("primio change: naziv{"+naziv+"}, vrednost{"+vrednost+"}, mail{"+mail+"}");
        ClinicAdministrator clinicAdministrator = clinicAdministratorService.findOneByMail(mail);
        if (clinicAdministrator == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        if(naziv.equals("ime")){
            clinicAdministrator.setFirstName(vrednost);
        }
        else if(naziv.equals("prezime")){
            clinicAdministrator.setLastName(vrednost);
        }
        else if(naziv.equals("adresa")){
            clinicAdministrator.setAddress(vrednost);
        }
        else if(naziv.equals("grad")){
            clinicAdministrator.setCity(vrednost);
        }
        else if(naziv.equals("drzava")){
            clinicAdministrator.setCountry(vrednost);
        }
        else if(naziv.equals("telefon")){
            clinicAdministrator.setTelephone(vrednost);
        }

        clinicAdministratorService.update(clinicAdministrator);
        ClinicAdministratorDTO clinicAdministratorDTO = new ClinicAdministratorDTO(clinicAdministrator);
        return new ResponseEntity<>(clinicAdministratorDTO, HttpStatus.OK);
    }

    //vracam termine koje nije odobrio admin i koji su pregledi
    @GetMapping(produces = "application/json", value = "/getTerms/{clinic}")
    public ResponseEntity<?> getTerms(@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        List<DoctorTerms> terms = doctorTermsService.findAllByClinic(c);
        List<DoctorTermsDTO> termsDTO = new ArrayList<>();
        System.out.println("ovde je");
        for (DoctorTerms t : terms) {
            System.out.println(t.getDate());
            if (!t.isProcessedByAdmin() && t.isExamination()) {
                System.out.println(t.isProcessedByAdmin() + " " + t.isExamination());
                termsDTO.add(new DoctorTermsDTO(t));
            }
            System.out.println("nije:"+t.getDate());
        }
        return new ResponseEntity<>(termsDTO, HttpStatus.OK);

    }


    //vracam termine koje admin nije odobrio i koji su operacije
    @GetMapping(produces = "application/json", value = "/getTermsOperation/{clinic}")
    public ResponseEntity<?> getTermsOperation(@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        List<DoctorTerms> terms = doctorTermsService.findAllByClinic(c);
        List<DoctorTermsDTO> termsDTO = new ArrayList<>();
        System.out.println("ovde je");
        for (DoctorTerms t : terms) {
            System.out.println(t.getDate());
            if (!t.isProcessedByAdmin() && !(t.isExamination()))
                termsDTO.add(new DoctorTermsDTO(t));
            System.out.println("nije:"+t.getDate());
        }
        return new ResponseEntity<>(termsDTO, HttpStatus.OK);

    }





    @GetMapping(value = "/sendMail/{id}/{date}")
    public  ResponseEntity<?> sendMail(@PathVariable("id") Long id,@PathVariable("date") long date){
        try {
            DoctorTerms doctorTerms = doctorTermsService.findOneById(id);
            String pregled;
            Date d=new Date(doctorTerms.getDate());
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
            String dateText = df2.format(d);
            if (doctorTerms.isExamination()) {
                pregled = "Pregled";
            }
            else {
                pregled = "Operacija";
            }
            String napomena = "";
            if (date != -1) {
                napomena = "*Napomena: Datum je promenjen na "+dateText+"\n";
            }

            String tb="Postovani," + "\n" +
                    pregled+" ce se odrzati u sali: "+doctorTerms.getRoom().getName() +".\n"+ napomena +
                    "Pregled:\nDatum: "+dateText+"\nVreme: "+doctorTerms.getTerm().getStartTerm()+"-"+
                    doctorTerms.getTerm().getEndTerm()+"\nDoktor: "+doctorTerms.getDoctor().getFirstName()+" "+doctorTerms.getDoctor().getLastName() +
                    "\nPacijent: "+doctorTerms.getPatient().getFirstName()+" "+doctorTerms.getPatient().getLastName()+
                    "\nTip pregleda: "+doctorTerms.getDoctor().getTip().getName() +"\nKlinika: "+doctorTerms.getDoctor().getClinic().getName() +
                    ", "+doctorTerms.getDoctor().getClinic().getAddress();
            System.out.println(tb);
            notificationService.SendNotification(doctorTerms.getDoctor().getMail(), "billypiton43@gmail.com",
                    "OBAVESTENJE", tb);
            notificationService.SendNotification(doctorTerms.getPatient().getMail(), "billypiton43@gmail.com",
                    "OBAVESTENJE", tb);

        } catch (MailException e) {
            System.out.println("Error sending message.");
            logger.info("Error Sending Mail:" + e.getMessage());
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> nije okej
        }

        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej

    }

    // TODO obradi izuzetak za OPTIMISTIC LOCK
    @PostMapping(produces = "application/json",
            value = "/createPredefinedTerm/{date}/{sat_id}/{room_id}/{type_id}/{doctor_id}/{price}/{discount}")
    public ResponseEntity<?> createPredefinedTerm(@PathVariable("date") Long date,
                                                  @PathVariable("sat_id") Long satnica_id,
                                                  @PathVariable("room_id") Long room_id,
                                                  @PathVariable("type_id") Long type_id,
                                                  @PathVariable("doctor_id") Long doctor_id,
                                                  @PathVariable("price") double price,
                                                  @PathVariable("discount") int discount){

        return new ResponseEntity<>(doctorTermsService.createPredefinedTerm(date, satnica_id, room_id, type_id,
                        doctor_id, price, discount), HttpStatus.OK);
    }

    //ovde vracamo godisnje odmore lekara samo
    @GetMapping(produces = "application/json", value = "/getVacation/{clinic}")
    public ResponseEntity<?> getVacation(@PathVariable("clinic") String clinic){
        List<VacationDTO> vacations = new ArrayList<>();
        for (Vacation v: vacationService.findAll()) {
            if(v.getDoctor_nurse().equals("doctor")) {
                if (v.getDoctor().getClinic().getName().equals(clinic)) {
                    if (!v.isEnabled() && v.isActive())
                        vacations.add(new VacationDTO(v));
                }
            }
        }
         return new ResponseEntity<>(vacations,HttpStatus.OK);
    }

    //ovde vracam listu SVIH zahteva za godisnji odmor
    @GetMapping(produces = "application/json", value = "/getAllVacation/{clinic}")
    public ResponseEntity<?> getAllVacation(@PathVariable("clinic") String clinic){
        List<VacationDTO> vacations = new ArrayList<>();
        for (Vacation v: vacationService.findAll()) {
            if(v.getDoctor_nurse().equals("doctor")) {
                if (v.getDoctor().getClinic().getName().equals(clinic)) {
                    if (!v.isEnabled() && v.isActive())
                        vacations.add(new VacationDTO(v));
                }
            }else{
                if(v.getNurse().getClinic().getName().equals(clinic)) {
                    if (!v.isEnabled() && v.isActive())
                        vacations.add(new VacationDTO(v));
                }
            }
        }
        return new ResponseEntity<>(vacations,HttpStatus.OK);
    }


   //obrada zahteva za godisnji odmor
   @PostMapping(value = "/obradiZahtev/{id}/{odobren}/{razlog}")
    public ResponseEntity<?> obradiZahtev(@PathVariable("id") Long id,
                                                  @PathVariable("odobren") boolean odobren,
                                                  @PathVariable("razlog") String razlog){

        clinicAdministratorService.obradiZahtev(id,odobren,razlog);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}