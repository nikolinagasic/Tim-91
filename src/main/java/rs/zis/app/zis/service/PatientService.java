package rs.zis.app.zis.service;

import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.repository.PatientRepository;

import java.awt.print.Pageable;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    /*public List<Patient> findAll() {
        return patientRepository.findAll();
    }*/

    public Page<Patient> findAll(Pageable page) {
        return patientRepository.findAll(page);
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
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
}
