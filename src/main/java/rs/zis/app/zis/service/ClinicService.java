package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.repository.ClinicRepository;

import java.util.List;

@Service
public class ClinicService implements UserDetailsService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    public List<Clinic> findAll() {return clinicRepository.findAll(); }

    public Clinic save(ClinicDTO clinicDTO) {
        Clinic c = new Clinic();
        c.setName(clinicDTO.getName());
        c.setAddress(clinicDTO.getAddress());
        c.setDescription(clinicDTO.getDescription());

        c = this.clinicRepository.save(c);
        return c;
    }

    public Clinic findOneByName(String name) {
        return clinicRepository.findOneByName(name);
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Clinic c = clinicRepository.findOneByName(name);
        if (c == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%name'.", name));
        } else {
            return (UserDetails) c;
        }
    }
}
