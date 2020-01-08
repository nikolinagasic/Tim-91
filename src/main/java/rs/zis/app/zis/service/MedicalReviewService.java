package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.MedicalRecord;
import rs.zis.app.zis.domain.MedicalReview;
import rs.zis.app.zis.dto.MedicalReviewDTO;
import rs.zis.app.zis.repository.MedicalReviewRepository;

import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class MedicalReviewService  {

    @Autowired
    MedicalReviewRepository medicalReviewRepository;

    @Autowired
    MedicalRecordService medicalRecordService;

    public List<MedicalReview> findAll(){return medicalReviewRepository.findAll();}

    public Page<MedicalReview> findAll(Pageable page){return medicalReviewRepository.findAll(page);}

    public MedicalReview findOneById(Long id){return  medicalReviewRepository.findOneById(id);}

    public MedicalReview save(MedicalReview medicalReview){return medicalReviewRepository.save(medicalReview);}

    public MedicalReview save(MedicalReviewDTO medicalReviewDTO){
        System.out.println("USAO U SAVE");
        MedicalRecord medicalRecord = medicalRecordService.findOneByPatientMail(medicalReviewDTO.getPatient_mail());
       // System.out.println(medicalRecord.getPatintMail());
        MedicalReview medicalReview = new MedicalReview();
        medicalReview.setId(medicalReviewDTO.getId());
        medicalReview.setDate(medicalReviewDTO.getDate());
        medicalReview.setMedicalResults(medicalReviewDTO.getMedicalResults());
        medicalReview.setDiagnosis(medicalReviewDTO.getDiagnosis());
        medicalReview.setTherapy(medicalReviewDTO.getTherapy());
        medicalReview.setId_doctor(medicalReviewDTO.getId_doctor());
        medicalReview.setMedicalRecord(medicalRecord);
        medicalReview = medicalReviewRepository.save(medicalReview);
        medicalRecord.addMedicalReviews(medicalReview);
        medicalRecordService.save(medicalRecord);
        System.out.println("Zavrsio sam save");
        return medicalReview;
    }
}
