package rs.zis.app.zis.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.PathVariable;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.domain.Users;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.dto.RoomDTO;
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
        List<Patient> tmpList = patientRepository.findAll();
        List<Patient> retList = new ArrayList<>();
        for (Patient patient : tmpList) {
            if(patient.isActive()){
                retList.add(patient);
            }
        }

        return retList;
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
        p.setEnabled(false);                    // TREBA ADMIN DA ODOBRI/NE ODOBRI
        p.setFirstLogin(true);
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

    public Patient findAllByLbo(long lbo) {
        List<Patient> patientList = patientRepository.findAllByLbo(lbo);
        for (Patient patient : patientList) {
            if(patient.isActive()){
                return patient;
            }
        }

        return null;
    }

    public Patient findOneById(Long id){return patientRepository.findOneById(id); }

    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findPatientByLastName(lastName);
    }

    public Patient update(Patient patient){
        return patientRepository.save(patient);
    }

    public List<PatientDTO> findPatient(List<PatientDTO> lista, String ime, String prezime,String lbo) {
        List<PatientDTO> retList = new ArrayList<>();
        for (PatientDTO patientDTO: lista) {
            String strLbo = String.valueOf(patientDTO.getLbo());
            if(patientDTO.getFirstName().toLowerCase().contains(ime.toLowerCase())){

                if(patientDTO.getLastName().toLowerCase().contains(prezime.toLowerCase())){
                    if(strLbo.contains(lbo)) {
                        retList.add(patientDTO);

                    }

                }
            }
        }

        return retList;
    }

    public boolean checkFirstLastName(String mail, String firstName, String lastName) {
        Patient patient = patientRepository.findOneByMail(mail);
        if (patient != null) {
            if (patient.getFirstName().equals(firstName) && patient.getLastName().equals(lastName)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
