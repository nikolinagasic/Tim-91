package rs.zis.app.zis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.repository.PatientRepository;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Page<Patient> findAll(Pageable page) {
        return patientRepository.findAll(page);
    }

    public Patient save(PatientDTO patientDTO) {
        Patient p = new Patient();
        p.setMail(patientDTO.getMail());
        p.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        p.setFirstName(patientDTO.getFirstName());
        p.setLastName(patientDTO.getLastName());
        p.setAddress(patientDTO.getAddress());
        p.setCity(patientDTO.getCity());
        p.setCountry(patientDTO.getCountry());
        p.setTelephone(patientDTO.getTelephone());
        p.setLbo(patientDTO.getLbo());
        p.setEnabled(true);                    // TREBA ADMIN DA ODOBRI/NE ODOBRI
        List<Authority> auth = authService.findByname("ROLE_PATIENT");
        p.setAuthorities(auth);

        p = this.patientRepository.save(p);
        return p;
    }

    public void remove(Long id) {
        patientRepository.deleteById(id);
    }

    public Patient findOneByMail(String mail) {
        return patientRepository.findOneByMail(mail);
    }

    public Patient findOneByLbo(long lbo) {
        return patientRepository.findOneByLbo(lbo);
    }

    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findPatientByLastName(lastName);
    }

    public boolean checkFirstLastName(String mail, String firstName, String lastName){
        Patient patient = patientRepository.findOneByMail(mail);
        if(patient != null){
            if(patient.getFirstName().equals(firstName) && patient.getLastName().equals(lastName)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
