package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Diagnosis;
import rs.zis.app.zis.dto.DiagnosisDTO;
import rs.zis.app.zis.repository.DiagnosisRepository;

import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;


    public List<Diagnosis> findAll(){return diagnosisRepository.findAll();}

   // public Diagnosis findOneByCurePassword(String cure_password){return  diagnosisRepository.findOneByCurePassword(cure_password);}

   // public Diagnosis findOneByDiagnosisPassword(String diagnosis_password){return diagnosisRepository.findOneByDiagnosisPassword(diagnosis_password);}


    public Diagnosis save(Diagnosis diagnosis){return diagnosisRepository.save(diagnosis);}

    public Diagnosis save(DiagnosisDTO diagnosisDTO){
        Diagnosis d= new Diagnosis(diagnosisDTO.getId(),diagnosisDTO.getCure_password(),diagnosisDTO.getCure_name(),diagnosisDTO.getDiagnosis_password(),diagnosisDTO.getDiagnosis_name());
        d=diagnosisRepository.save(d);
        return d;
    }


}
