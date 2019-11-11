package rs.zis.app.zis.service;

import rs.zis.app.zis.model.Patient;

public interface LoginPatientService {
    Patient create(Patient patient) throws Exception;
}
