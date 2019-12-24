package rs.zis.app.zis.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.auth.JwtAuthenticationRequest;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.*;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.*;

@SuppressWarnings({"SpellCheckingInspection", "unused", "unchecked"})
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ClinicCentreAdminService cc_admin_service;

    @Autowired
    private ClinicAdministratorService c_admin_service;

    @Autowired
    private DoctorService doctor_service;

    @Autowired
    private NurseService nurse_service;

    @Autowired
    private UserService user_service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    @SuppressWarnings("RedundantThrows")
    @PostMapping(consumes = "application/json", value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                      HttpServletResponse response) throws AuthenticationException, IOException{

        String mail = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(mail, password));
        }catch (BadCredentialsException e){
            System.out.println("Nisu dobri kredencijali [BadCredentialsException]");
            return new ResponseEntity("Bad credencials", HttpStatus.CONFLICT);
        }catch (DisabledException e){
            System.out.println("Korisnik jos nije prihvacen [DisabledException]");
            return new ResponseEntity("Disabled exception", HttpStatus.CONFLICT);
        }

        // Ubaci username + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Patient pat = patientService.findOneByMail(mail);
        Doctor doc = doctor_service.findOneByMail(mail);
        Nurse nur = nurse_service.findOneByMail(mail);
        ClinicAdministrator cadmin = c_admin_service.findOneByMail(mail);
        ClinicCentreAdmin ccadmin = cc_admin_service.findOneByMail(mail);

        if(pat != null){
            // Kreiraj token
            Patient user = (Patient) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();

            // Vrati token kao odgovor na uspesno autentifikaciju
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }
        else if(doc != null){
            Doctor user = (Doctor) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }
        else if(nur != null){
            Nurse user = (Nurse) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }
        else if(cadmin != null){
            ClinicAdministrator user = (ClinicAdministrator) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }
        else if(ccadmin != null){
            System.out.println("u adminu sam");
            ClinicCentreAdmin user = (ClinicCentreAdmin) authentication.getPrincipal();
            System.out.println("ime: " + user.getFirstName());
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }
        else {
            System.out.println("Doslo je do greske u prijavi");
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader("Auth-Token") String token){
        SecurityContextHolder.getContext().setAuthentication(null);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // 'http://localhost:8081/patient/getByToken/dksamd8sajidn328d8i32jd82'
    @GetMapping(produces = "application/json", value = "/getByToken/{token}")
    public ResponseEntity<?> getPatientByMail(@PathVariable("token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);

        Patient patient = patientService.findOneByMail(mail);
        Doctor doc = doctor_service.findOneByMail(mail);
        Nurse nurse = nurse_service.findOneByMail(mail);
        ClinicAdministrator cadmin = c_admin_service.findOneByMail(mail);
        ClinicCentreAdmin ccadmin = cc_admin_service.findOneByMail(mail);
        if(patient != null) {
            System.out.println(patient.getFirstName() + " " + patient.getLastName());
            return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
        }
        else if(doc != null){
            return new ResponseEntity<>(new DoctorDTO(doc), HttpStatus.OK);
        }
        else if(nurse != null){
            return new ResponseEntity<>(new NurseDTO(nurse), HttpStatus.OK);
        }
        else if(cadmin != null){
            return new ResponseEntity<>(new ClinicAdministratorDTO(cadmin), HttpStatus.OK);
        }
        else if(ccadmin != null){
            return new ResponseEntity<>(new ClinicCentreAdminDTO(ccadmin), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/forgetPassword/{mail}/{ime}/{prezime}")
    public ResponseEntity<?> getPatientByMail(@PathVariable("mail") String mail, @PathVariable("ime") String ime,
                                        @PathVariable("prezime") String prezime) {

        Users users = user_service.findOneByMail(mail);
        if(users == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        boolean patient = patientService.checkFirstLastName(mail, ime, prezime);
        boolean nurse = nurse_service.checkFirstLastName(mail, ime, prezime);
        boolean doctor = doctor_service.checkFirstLastName(mail, ime, prezime);
        boolean cadmin = c_admin_service.checkFirstLastName(mail, ime, prezime);
        boolean ccadmin = cc_admin_service.checkFirstLastName(mail, ime, prezime);

        if(patient || nurse || doctor || cadmin || ccadmin){
            String new_pass = ime.toLowerCase();
            int brojac = 1;
            while(new_pass.length() < 8){
                new_pass = new_pass + Integer.toString(brojac);
                brojac++;
            }
            users.setPassword(passwordEncoder.encode(new_pass));
            user_service.save(users);

            String body = "Поштовани, Ваша лозинка је промењена. Исту можете променити на личном профилу.\n"
                    + "Тренутна лозинка је: " + new_pass;

            try{
                notificationService.SendNotification(mail, "billypiton43@gmail.com",
                        "Promena lozinke", body);
            }catch (MailException e){
                logger.info("Error Sending Mail:" + e.getMessage());
            }

            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
    }
}
