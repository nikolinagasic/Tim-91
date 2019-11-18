package rs.zis.app.zis.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.repository.PatientRepository;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class CustomPatientService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Patient p = patientRepository.findOneByMail(mail);
        if (p == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", mail));
        } else {
            return p;
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();    // vrati "sessiju"
        String username = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. Can't change Password!");
            return;
        }
        LOGGER.debug("Changing password for user '" + username + "'");
        Patient p = (Patient) loadUserByUsername(username);

        // pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
        // ne zelimo da u bazi cuvamo lozinke u plain text formatu
        p.setPassword(passwordEncoder.encode(newPassword));
        patientRepository.save(p);
    }

}
