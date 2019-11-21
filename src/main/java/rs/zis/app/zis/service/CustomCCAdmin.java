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
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.repository.ClinicCentreAdminRepository;

@Service
public class CustomCCAdmin implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private ClinicCentreAdminRepository centre_admin_repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        ClinicCentreAdmin ca = centre_admin_repository.findOneByMail(mail);
        if (ca == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%mail'.", mail));
        } else {
            return (UserDetails) ca;
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
        ClinicCentreAdmin ca = (ClinicCentreAdmin) loadUserByUsername(username);

        // pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
        // ne zelimo da u bazi cuvamo lozinke u plain text formatu
        ca.setPassword(passwordEncoder.encode(newPassword));
        centre_admin_repository.save(ca);
    }

}