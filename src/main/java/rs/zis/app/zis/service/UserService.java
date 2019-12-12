package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.User;
import rs.zis.app.zis.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public User findOneByMail(String mail) {
        return userRepository.findOneByMail(mail);
    }

    public User save(User u) {return userRepository.save(u);}


}
