package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.model.Patient;
import rs.zis.app.zis.repository.InMemoryLoginRepository;

@Service
@SuppressWarnings("SpellCheckingInspection")
public class LoginPatientServiceImpl implements LoginPatientService{

    @Autowired
    private InMemoryLoginRepository loginRepository;

    @Override
    public Patient create(Patient patient) throws Exception {
        if (patient.getMail() != null) {
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }

        Patient savedPatient = loginRepository.create(patient);
        return savedPatient;
    }
}
