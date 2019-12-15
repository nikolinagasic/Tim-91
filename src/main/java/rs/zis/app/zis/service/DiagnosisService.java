package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Diagnosis;
import rs.zis.app.zis.dto.DiagnosisDTO;
import rs.zis.app.zis.repository.DiagnosisRepository;

import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public List<Diagnosis> findAll(){return diagnosisRepository.findAll();}

    public Diagnosis save(Diagnosis diagnosis){return diagnosisRepository.save(diagnosis);}

    public Diagnosis save(DiagnosisDTO diagnosisDTO){
        Diagnosis d= new Diagnosis(diagnosisDTO.getId(),diagnosisDTO.getCure_password(),diagnosisDTO.getCure_name(),diagnosisDTO.getDiagnosis_password(),diagnosisDTO.getDiagnosis_name());
        d=diagnosisRepository.save(d);
        return d;
    }


}
