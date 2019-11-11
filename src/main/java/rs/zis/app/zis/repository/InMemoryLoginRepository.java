package rs.zis.app.zis.repository;

import org.springframework.stereotype.Repository;
import rs.zis.app.zis.model.Patient;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
@SuppressWarnings("SpellCheckingInspection")
public class InMemoryLoginRepository implements LoginPatientRepository {

    // kljuc je email(jedinstven je), a vrednost sam pacijent
    private final ConcurrentMap<String, Patient> patients = new ConcurrentHashMap<String, Patient>();

    @Override
    public Patient create(Patient patient) throws Exception {
        String mail = patient.getMail();
        if (mail == null) {
            System.out.println("Nije prosledjena vrednost za email (obavezno je!)");
        }

        this.patients.put(mail, patient);
        return patient;
    }
}
