package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.repository.NurseRepository;

import java.util.List;

    @SuppressWarnings("SpellCheckingInspection")
    @Service
    public class NurseService implements UserDetailsService {

        @Autowired
        private NurseRepository nurseRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private AuthorityService authService;

        public List<Nurse> findAll() {
            return nurseRepository.findAll();
        }

        public Page<Nurse> findAll(Pageable page) {
            return nurseRepository.findAll(page);
        }

        public Nurse save(NurseDTO nurseDTO) {
            Nurse d = new Nurse();
            d.setMail(nurseDTO.getMail());
            d.setPassword(passwordEncoder.encode(nurseDTO.getPassword()));
            d.setEnabled(true);
            List<Authority> auth = authService.findByname("ROLE_NURSE");
            d.setAuthorities(auth);

            d = this.nurseRepository.save(d);
            return d;
        }

        public void remove(Long id) {
            nurseRepository.deleteById(id);
        }

        public Nurse findOneByMail(String mail) {
            return nurseRepository.findOneByMail(mail);
        }

        public List<Nurse> findNurseByLastName(String lastName) {
            return nurseRepository.findNurseByLastName(lastName);
        }

        @Override
        public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
            Nurse n = nurseRepository.findOneByMail(mail);
            if (n == null) {
                throw new UsernameNotFoundException(String.format("No user found with email '%mail'.", mail));
            } else {
                return (UserDetails) n;
            }
        }
    }
