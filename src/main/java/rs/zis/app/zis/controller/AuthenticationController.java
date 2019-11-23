package rs.zis.app.zis.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.auth.JwtAuthenticationRequest;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.*;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PatientService userService;

    @Autowired
    private ClinicCentreAdminService cc_admin;

    @Autowired
    private ClinicAdministratorService c_admin;

    @Autowired
    private DoctorService doctor_service;

    @Autowired
    private NurseService nurse_service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(consumes = "application/json", value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                      HttpServletResponse response) throws AuthenticationException, IOException {

        String mail = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        System.out.println("mejl: "+mail);
        System.out.println("password: "+password);
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(mail, password));

        // Ubaci username + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Patient pat = userService.findOneByMail(mail);
        Doctor doc = doctor_service.findOneByMail(mail);
        Nurse nur = nurse_service.findOneByMail(mail);
        ClinicAdministrator cadmin = c_admin.findOneByMail(mail);
        ClinicCentreAdmin ccadmin = cc_admin.findOneByMail(mail);

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
            ClinicCentreAdmin user = (ClinicCentreAdmin) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }
        else {
            System.out.println("Doslo je do greske u prijavi");
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
    }

}
