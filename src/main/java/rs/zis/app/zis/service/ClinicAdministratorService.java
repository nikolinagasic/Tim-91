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
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.repository.ClinicAdministratorRepository;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class ClinicAdministratorService implements UserDetailsService {

    @Autowired
    private ClinicAdministratorRepository clinicAdministartorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    public List<ClinicAdministrator> findAll() {
        return clinicAdministartorRepository.findAll();
    }

    public Page<ClinicAdministrator> findAll(Pageable page) {
        return clinicAdministartorRepository.findAll(page);
    }

    public ClinicAdministrator save(ClinicAdministratorDTO clinicAdministartorDTO) {
        ClinicAdministrator a = new ClinicAdministrator();
        a.setMail(clinicAdministartorDTO.getMail());
        a.setPassword(passwordEncoder.encode(clinicAdministartorDTO.getPassword()));
        a.setEnabled(true);
        List<Authority> auth = authService.findByname("ROLE_CLINIC_ADMINISTRATOR");
        a.setAuthorities(auth);

        a = this.clinicAdministartorRepository.save(a);
        return a;
    }

    public void remove(Long id) {
        clinicAdministartorRepository.deleteById(id);
    }

    public ClinicAdministrator findOneByMail(String mail) {
        return clinicAdministartorRepository.findOneByMail(mail);
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        ClinicAdministrator d = clinicAdministartorRepository.findOneByMail(mail);
        if (d == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%mail'.", mail));
        } else {
            return (UserDetails) d;
        }
    }
}