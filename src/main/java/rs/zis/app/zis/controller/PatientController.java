package rs.zis.app.zis.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.domain.Users;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.CustomUserService;
import rs.zis.app.zis.service.NotificationService;
import rs.zis.app.zis.service.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import rs.zis.app.zis.service.UserService;

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
    private TokenUtils tokenUtils;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(consumes = "application/json" , value = "/register")
    public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) {
        System.out.println("usao sam u post i primio: " + patientDTO.getFirstName() + patientDTO.getLastName());

        Patient proveriLbo = patientService.findOneByLbo(patientDTO.getLbo());
        if(proveriLbo != null){
            return new ResponseEntity<>(patientDTO, HttpStatus.CONFLICT);       // lbo nije okej
        }

        Users proveriMail = userService.findOneByMail(patientDTO.getMail());
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
            listDTO.add(new PatientDTO(p));
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
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

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping(produces = "application/json", value = "/getPat")
    public ResponseEntity<?> changeAttribute(@RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        return new ResponseEntity<>(patientService.findOneByMail(mail), HttpStatus.OK);
    }
}
