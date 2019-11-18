package rs.zis.app.zis.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import rs.zis.app.zis.auth.JwtAuthenticationRequest;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.domain.UserTokenState;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.CustomPatientService;
import rs.zis.app.zis.service.PatientService;

@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomPatientService userDetailsService;

    @Autowired
    private PatientService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                      HttpServletResponse response) throws AuthenticationException, IOException {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        // Ubaci username + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token
        Patient user = (Patient) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesno autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

}
