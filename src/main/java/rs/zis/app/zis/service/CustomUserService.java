package rs.zis.app.zis.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Users;
import rs.zis.app.zis.repository.UserRepository;

import javax.persistence.NonUniqueResultException;
import java.util.UnknownFormatConversionException;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class CustomUserService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UnknownFormatConversionException {
        //System.out.println("stiglo: "+ username);
        Users users = userService.findAllByMail(username);
        if (users == null) {
            throw new UsernameNotFoundException(String.format("No user found with email "+username+""));
        } else {
            return (UserDetails) users;
        }
    }

    public boolean changePassword(String username, String password){
        try {
            applicationContext.getBean(AuthenticationManager.class);
            LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");
        } catch (BeansException e) {
            LOGGER.debug("No authentication manager set. can't change Password!");
            return false;
        }

        LOGGER.debug("Changing password for user '" + username + "'");

        Users users = (Users) loadUserByUsername(username);

        // hesovanje lozinke pre cuvanja u bazi
        users.setPassword(applicationContext.getBean(PasswordEncoder.class).encode(password));
        users.setFirstLogin(false);
        userRepository.save(users);
        return true;
    }
}
