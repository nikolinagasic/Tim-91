package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorTermsRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class DoctorTermsService {

    @Autowired
    private DoctorTermsRepository doctorTermsRepository;

    @Autowired
    private TermDefinitionService termDefinitionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    public List<DoctorTerms> findAll() {
        return doctorTermsRepository.findAll();
    }

    public Page<DoctorTerms> findAll(Pageable page) {
        return doctorTermsRepository.findAll(page);
    }

    public void remove(Long id) {
        doctorTermsRepository.deleteById(id);
    }

    public void removeLogical(Long id) {
        DoctorTerms doctorTerms = findOneById(id);
        if(doctorTerms != null){
            doctorTerms.setActive(false);
        }
    }

    public DoctorTerms findOneById(Long id) {
        return doctorTermsRepository.findOneById(id);
    }

    public List<DoctorTerms> findAllByDate(Long date) {
        return doctorTermsRepository.findAllByDate(date);
    }

    public List<DoctorTerms> findAllByDoctor(Doctor doctor){
        return doctorTermsRepository.findAllByDoctor(doctor);
    }

    public DoctorTerms save(DoctorTerms u) {return doctorTermsRepository.save(u);}

    public List<DoctorTermsDTO> getTermine(long date, Doctor doctor){
        List<DoctorTermsDTO> retList = new ArrayList<>();
        List<DoctorTerms> listaTermina = findAllByDoctor(doctor);

        // svi termini za tu smenu (prva/druga)
        List<TermDefinition> listaSvihTermina = termDefinitionService.findAllByWorkShift(doctor.getWorkShift());

        // izbaciti sve termine koji su zauzeti (u retList hocu samo one slobodne termine za tog doktora)
        for (TermDefinition termDefinition : listaSvihTermina) {
            boolean flag = false;           // nemoj dodavati u retList ako je zauzet
            for (DoctorTerms doctorTerms : listaTermina) {
                if(doctorTerms.getTerm().getId() == termDefinition.getId()){
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                retList.add(new DoctorTermsDTO(date, termDefinition, doctor));
            }
        }

        return retList;
    }

    public DoctorTermsDTO detailTerm(Long doctor_id, Long date, String start_term, String mail_patient){
        Doctor doctor = doctorService.findOneById(doctor_id);
        TermDefinition termDefinition = termDefinitionService.findOneByStart_term(start_term);
        Patient patient = patientService.findOneByMail(mail_patient);

        return new DoctorTermsDTO(date, termDefinition, doctor);
    }

    // TODO uraditi rezervaciju termina
    public DoctorTermsDTO reserveTerm(String mail_patient, DoctorTermsDTO doctorTermsDTO){


        return new DoctorTermsDTO();
    }
}
