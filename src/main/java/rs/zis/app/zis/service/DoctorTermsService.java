package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorTermsRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection", "DefaultAnnotationParam", "NumberEquality"})
@Service
@Transactional(readOnly = true)
public class DoctorTermsService {

    @Autowired
    private DoctorTermsRepository doctorTermsRepository;

    @Autowired
    private TermDefinitionService termDefinitionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAll() {
        return doctorTermsRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Page<DoctorTerms> findAll(Pageable page) {
        return doctorTermsRepository.findAll(page);
    }

    @Transactional(readOnly = false)
    public void remove(Long id) {
        doctorTermsRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void removeLogical(Long id) {
        DoctorTerms doctorTerms = findOneById(id);
        if(doctorTerms != null){
            doctorTerms.setActive(false);
        }
    }

    @Transactional(readOnly = false)
    public DoctorTerms findOneById(Long id) {
        return doctorTermsRepository.findOneById(id);
    }

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAllByDate(Long date) {
        return doctorTermsRepository.findAllByDate(date);
    }

    @Transactional(readOnly = false)
    public List<DoctorTerms> findAllByDoctor(Doctor doctor){
        List<DoctorTerms> doctorTermsList = new ArrayList<>();
        for (DoctorTerms doctorTerms : doctorTermsRepository.findAllByDoctor(doctor)) {
            if(doctorTerms.isProcessedByAdmin()){
                doctorTermsList.add(doctorTerms);
            }
        }

        return doctorTermsList;
    }

    @Transactional(readOnly = false)
    public DoctorTerms save(DoctorTerms u) {return doctorTermsRepository.save(u);}

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> getTermine(long date, Doctor doctor){
        List<DoctorTermsDTO> retList = new ArrayList<>();
        List<DoctorTerms> listaTermina = findAllByDoctor(doctor);           // lista termina mog doktora

        // svi termini za tu smenu (prva/druga smena)
        List<TermDefinition> listaSvihTermina = termDefinitionService.findAllByWorkShift(doctor.getWorkShift());

        // izbaciti sve termine koji su zauzeti (u retList hocu samo one slobodne termine za tog doktora)
        for (TermDefinition termDefinition : listaSvihTermina) {
            boolean zauzet = false;
            for (DoctorTerms doctorTerms : listaTermina) {
                if(doctorTerms.getDate() == date) {                                 // ako je za taj datum
                    if (doctorTerms.getTerm().getId() == termDefinition.getId()) {  // i ako je ta satnica
                        zauzet = true;                                           // => nemoj da ga dodajes
                        break;
                    }
                }
            }
            if(!zauzet) {
                retList.add(new DoctorTermsDTO(date, termDefinition, doctor));
            }
        }

        return retList;
    }

    @Transactional(readOnly = false)
    public DoctorTermsDTO detailTerm(Long doctor_id, Long date, String start_term, String mail_patient){
        Doctor doctor = doctorService.findOneById(doctor_id);
        TermDefinition termDefinition = termDefinitionService.findOneByStart_term(start_term);
        Patient patient = patientService.findOneByMail(mail_patient);

        return new DoctorTermsDTO(date, termDefinition, doctor);
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean reserveTerm(String mail_patient, DoctorTermsDTO doctorTermsDTO){
        Doctor doctor = doctorService.findDoctorByFirstNameAndLastName(doctorTermsDTO.getFirstNameDoctor(),
                doctorTermsDTO.getLastNameDoctor());
        TermDefinition term_def = termDefinitionService.findOneByStart_term(doctorTermsDTO.getStart_term());
        Patient patient = patientService.findOneByMail(mail_patient);

        DoctorTerms dt = doctorTermsRepository.findOneByDateAndStartTermAndDoctorId(doctorTermsDTO.getDate(),
                                                                                    term_def,
                                                                                    doctor);

        if(dt == null) {
            DoctorTerms doctorTerms = new DoctorTerms();
            doctorTerms.setDate(doctorTermsDTO.getDate());
            doctorTerms.setDoctor(doctor);
            doctorTerms.setPatient(patient);
            doctorTerms.setReport("Нема извештаја");
            doctorTerms.setTerm(term_def);

            // TODO staviti processed_by_admin na false (ne cuvam ga odmah)
            doctorTerms.setProcessedByAdmin(false);
            doctorTermsRepository.save(doctorTerms);
            return true;        // uspesno sam napravio tvoj termin
        }
        else{
            return false ;      // vec postoji jedan takav kreiran termin
        }
    }
}
