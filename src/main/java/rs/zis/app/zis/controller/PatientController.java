package rs.zis.app.zis.controller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.MedicalRecord;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.domain.Users;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.dto.MedicalRecordDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/patient")
public class PatientController extends WebConfig {

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DoctorTermsService doctorTermsService;

    @PostMapping(consumes = "application/json" , value = "/register")
    public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) {
        System.out.println("usao sam u post i primio: " + patientDTO.getFirstName() + patientDTO.getLastName());

        Patient proveriLbo = patientService.findAllByLbo(patientDTO.getLbo());
        if(proveriLbo != null){
            return new ResponseEntity<>(patientDTO, HttpStatus.CONFLICT);       // lbo nije okej
        }

        Users proveriMail = userService.findAllByMail(patientDTO.getMail());
        if(proveriMail != null){
            return new ResponseEntity<>(patientDTO, HttpStatus.CONFLICT);  // mejl nije okej (na nivou svih korisika)
        }
        Patient patient = patientService.save(patientDTO);                  //sacuvam u registerService


        return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);     // sve okej
    }

    //@PreAuthorize("hasRole('PATIENT')")
    @GetMapping(produces = "application/json", value = "/getAll")
    public ResponseEntity<List<PatientDTO>> getPatient() {
        List<Patient> listPatient = patientService.findAll();

        ArrayList<PatientDTO> listDTO = new ArrayList<>();
        for (Patient p: listPatient) {
            if (p.isEnabled()) {
                listDTO.add(new PatientDTO(p));
            }
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }


    @GetMapping(produces = "application/json", value = "/getPatientsSorted")
    public ResponseEntity<List<PatientDTO>> getPatientsSorted() {
        List<Patient>patientList = patientService.findAll();
        List<PatientDTO>patientDTOS = new ArrayList<>();
        for(Patient patient: patientList){
            PatientDTO patientDTO = new PatientDTO(patient);
            patientDTOS.add(patientDTO);
        }
        return new ResponseEntity<>(patientService.sortPatientByLastName(patientDTOS),HttpStatus.OK);
    }

   @GetMapping(produces = "application/json", value = "/find/{ime}/{prezime}/{lbo}/{city}")
    public ResponseEntity<?> findPatient(@PathVariable("ime") String ime, @PathVariable("prezime") String prezime,
                                        @PathVariable("lbo") String lbo,@PathVariable("city") String city) {
        if(ime.equals("~")){
            ime = "";
        }
        if(prezime.equals("~")){
            prezime = "";
        }
        if(lbo.equals("~")){
            lbo = "";
        }
        if(city.equals("~")){
            city = "";
        }
        List<Patient> lista = patientService.findAll();
        List<PatientDTO> ret = new ArrayList<>();
        for (Patient p : lista) {
            if (p.isEnabled() && p.isActive()) {
                ret.add(new PatientDTO(p));
            }
        }
        List<PatientDTO> listaDTO = patientService.findPatient(ret, ime, prezime,lbo,city);
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('PATIENT')")
    @PostMapping(value = "/changeAttribute/{naziv}/{vrednost}/{mail}")
    public ResponseEntity<?> changeAttribute(@PathVariable("naziv") String naziv,
                                             @PathVariable("vrednost") String vrednost,
                                             @PathVariable("mail") String mail) {
        System.out.println("primio change: naziv{"+naziv+"}, vrednost{"+vrednost+"}, mail{"+mail+"}");
        Patient patient = patientService.findOneByMail(mail);
        if (patient == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        if(naziv.equals("ime")){
            patient.setFirstName(vrednost);
        }
        else if(naziv.equals("prezime")){
            patient.setLastName(vrednost);
        }
        else if(naziv.equals("adresa")){
            patient.setAddress(vrednost);
        }
        else if(naziv.equals("grad")){
            patient.setCity(vrednost);
        }
        else if(naziv.equals("drzava")){
            patient.setCountry(vrednost);
        }
        else if(naziv.equals("telefon")){
            patient.setTelephone(vrednost);
        }

        patientService.update(patient);
        PatientDTO patientDTO = new PatientDTO(patient);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "text/plain", value = "/changePassword")
    public ResponseEntity<?> changeAttribute(@RequestHeader("Auth-Token") String token, @RequestBody String password) {
        //System.out.println("Pass: " + password + ", token: " +token);
        String mail = tokenUtils.getUsernameFromToken(token);
        
        boolean flag_ok = customUserService.changePassword(mail, password);
        if(flag_ok) {
            return new ResponseEntity<>("uspesno",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("neuspesno", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json", value = "/getPat")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getPatProfile(@RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        return new ResponseEntity<>(patientService.findOneByMail(mail), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/getAllExaminations")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getAllExaminations(@RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        Patient patient = patientService.findOneByMail(mail);
        return new ResponseEntity<>(doctorTermsService.getAllExaminations(patient), HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", value = "/sortTerms/{date}/{tip}/{vrsta}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getSortTerms(@RequestHeader("Auth-Token") String token,
                                          @RequestBody List<DoctorTermsDTO> listaTermina,
                                          @PathVariable("date") Long datum,
                                          @PathVariable("tip") String tip,
                                          @PathVariable("vrsta") String vrsta) {
        return new ResponseEntity<>(doctorTermsService.getSortExaminations(listaTermina, datum, tip, vrsta),
                HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", value = "/sortOrderTermsDate/{order}")
    public ResponseEntity<?> sortOrderDate(@RequestBody List<DoctorTermsDTO> listaTermina,
                                           @PathVariable("order") String order) {
        return new ResponseEntity<>(doctorTermsService.sortByDate(listaTermina, order),
                HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", value = "/sortOrderTermsTip/{order}")
    public ResponseEntity<?> sortOrderTip(@RequestBody List<DoctorTermsDTO> listaTermina,
                                           @PathVariable("order") String order) {
        return new ResponseEntity<>(doctorTermsService.sortByTip(listaTermina, order),
                HttpStatus.OK);
    }
  
    @GetMapping(produces = "application/json", value = "/getByMail/{mail}")
    public ResponseEntity<?> getPatient(@PathVariable("mail") String mail) {
        Patient patient = patientService.findOneByMail(mail);
        return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
    }

    @PostMapping(consumes="application/json", produces = "application/json", value = "/getSortByLastName")
    public ResponseEntity<List<PatientDTO>> getSortByLastName(@RequestBody List<PatientDTO> patientDTO) {
        System.out.println("usao");
        return new ResponseEntity<>(patientService.sortPatientByLastName(patientDTO),HttpStatus.OK);
    }
}
