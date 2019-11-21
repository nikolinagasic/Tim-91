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
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.repository.DoctorRepository;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class DoctorService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Page<Doctor> findAll(Pageable page) {
        return doctorRepository.findAll(page);
    }

    public Doctor save(DoctorDTO doctorDTO) {
        Doctor d = new Doctor();
        d.setMail(doctorDTO.getMail());
        d.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        List<Authority> auth = authService.findByname("ROLE_DOCTOR");
        d.setAuthorities(auth);

        d = this.doctorRepository.save(d);
        return d;
    }

    public void remove(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findOneByMail(String mail) {
        return doctorRepository.findOneByMail(mail);
    }

    public List<Doctor> findDoctorByLastName(String lastName) {
        return doctorRepository.findDoctorByLastName(lastName);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Doctor d = doctorRepository.findOneByMail(mail);
        if (d == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%mail'.", mail));
        } else {
            return (UserDetails) d;
        }
    }
}

