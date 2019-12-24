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
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.repository.DoctorRepository;
import rs.zis.app.zis.repository.DoctorTermsRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class DoctorTermsService {

    @Autowired
    private DoctorTermsRepository doctorTermsRepository;

    public List<DoctorTerms> findAll() {
        return doctorTermsRepository.findAll();
    }

    public Page<DoctorTerms> findAll(Pageable page) {
        return doctorTermsRepository.findAll(page);
    }

    public void remove(Long id) {
        doctorTermsRepository.deleteById(id);
    }

    public void removeLogical(Long id) {
        DoctorTerms doctorTerms = findOneById(id);
        if(doctorTerms != null){
            doctorTerms.setActive(false);
        }
    }

    public DoctorTerms findOneById(Long id) {
        return doctorTermsRepository.findOneById(id);
    }

    public List<DoctorTerms> findAllByDate(Long date) {
        return doctorTermsRepository.findAllByDate(date);
    }

    public List<DoctorTerms> findAllByDoctor(Doctor doctor){
        return doctorTermsRepository.findAllByDoctor(doctor);
    }

    public DoctorTerms save(DoctorTerms u) {return doctorTermsRepository.save(u);}


    // TODO vratiti sve slobodne termine od tog doktora
    public List<DoctorTerms> getTermine(Doctor doctor){
        List<DoctorTerms> retList = new ArrayList<>();


        return retList;
    }
}
