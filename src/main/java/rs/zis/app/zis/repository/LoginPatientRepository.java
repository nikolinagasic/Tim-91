package rs.zis.app.zis.repository;

import rs.zis.app.zis.model.Patient;

public interface LoginPatientRepository {
    Patient create(Patient patient) throws Exception;
}
