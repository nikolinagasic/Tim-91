package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Users;
import rs.zis.app.zis.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Page<Users> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteLogical(Long id) {
        Users users = userRepository.findOneById(id);
        users.setActive(false);
        userRepository.save(users);
    }

    public Users findOneByMail(String mail) {
        return userRepository.findOneByMail(mail);
    }

    public Users findAllByMail(String mail) {
        List<Users> users = userRepository.findAllByMail(mail);
        for (Users user : users) {
            if(user.isActive()){
                return user;
            }
        }

        return null;
    }

    public Users save(Users u) {return userRepository.save(u);}
}
