package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.MedicalRecord;
import rs.zis.app.zis.dto.MedicalRecordDTO;
import rs.zis.app.zis.repository.MedicalRecordRepository;

import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    PatientService patientService;

    public List<MedicalRecord> findAll() {return medicalRecordRepository.findAll(); }

    public Page<MedicalRecord> findAll(Pageable page) {return medicalRecordRepository.findAll(page); }

    public MedicalRecord findOneById(Long id) {return medicalRecordRepository.findOneById(id); }

    public MedicalRecord save(MedicalRecord medicalRecord) {return medicalRecordRepository.save(medicalRecord); }

    public MedicalRecord save(MedicalRecordDTO medicalRecordDTO){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(medicalRecordDTO.getId());
        medicalRecord.setHeight(medicalRecordDTO.getHeight());
        medicalRecord.setWeight(medicalRecordDTO.getWeight());
        medicalRecord.setDioptreRightEye(medicalRecordDTO.getDioptreRightEye());
        medicalRecord.setDioptreLeftEye(medicalRecordDTO.getDioptreLeftEye());
        medicalRecord.setBloodGroup(medicalRecordDTO.getBloodGroup());
        medicalRecord.setAllergy(medicalRecordDTO.getAllergy());
        medicalRecord.setPatient(patientService.findOneByMail(medicalRecordDTO.getPatientMail()));
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecord;
    }


}
