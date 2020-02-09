package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Diagnosis;
import rs.zis.app.zis.dto.DiagnosisDTO;
import rs.zis.app.zis.repository.DiagnosisRepository;

import java.util.ArrayList;
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

    public Diagnosis save(Diagnosis diagnosis){return diagnosisRepository.save(diagnosis);}

    public Diagnosis save(DiagnosisDTO diagnosisDTO){
        Diagnosis d= new Diagnosis(diagnosisDTO.getId(),diagnosisDTO.getCure_password(),diagnosisDTO.getCure_name(),diagnosisDTO.getDiagnosis_password(),diagnosisDTO.getDiagnosis_name());
        d=diagnosisRepository.save(d);
        return d;
    }

    public List<DiagnosisDTO> filterDiagnosis(String naziv){
        if(naziv.equals("~")){
            naziv = "";
        }
        List<DiagnosisDTO>diagnosisDTOList=new ArrayList<>();
        List<Diagnosis>diagnosisList=diagnosisRepository.findAll();
        for(Diagnosis diagnosis:diagnosisList){
            if(diagnosis.getDiagnosis_name().toLowerCase().contains(naziv.toLowerCase())){
                diagnosisDTOList.add(new DiagnosisDTO(diagnosis));
            }
        }
        return diagnosisDTOList;
    }

    public List<DiagnosisDTO> filterCures(String diagName){
        if(diagName.equals("~")){
            diagName = "";
        }
        List<DiagnosisDTO>diagnosisDTOList = new ArrayList<>();
        List<Diagnosis>diagnosisList = diagnosisRepository.findAll();
        for(Diagnosis diagnosis:diagnosisList){
            if(diagnosis.getDiagnosis_name().toLowerCase().equals(diagName.toLowerCase())){
                diagnosisDTOList.add(new DiagnosisDTO(diagnosis));
            }
        }
        return diagnosisDTOList;
    }

}
