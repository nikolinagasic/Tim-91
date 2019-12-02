package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.User;
import rs.zis.app.zis.repository.UserRepository;

import java.util.UnknownFormatConversionException;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UnknownFormatConversionException {
        //System.out.println("stiglo: "+ username);
        User user = userRepository.findOneByMail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email "+username+""));
        } else {
            return (UserDetails) user;
        }
    }
}
