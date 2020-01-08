package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.MedicalRecord;
import rs.zis.app.zis.domain.MedicalReview;
import rs.zis.app.zis.dto.MedicalReviewDTO;
import rs.zis.app.zis.service.MedicalRecordService;
import rs.zis.app.zis.service.MedicalReviewService;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/medicalreview")
public class MedicalReviewController extends WebConfig {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    MedicalReviewService medicalReviewService;


    @PostMapping(consumes = "application/json" , value = "/save_review")
    public ResponseEntity<Integer> saveMedicalReview(@RequestBody MedicalReviewDTO medicalReviewDTO){
        System.out.println("OVDEE SAM");
        MedicalRecord medicalRecord = medicalRecordService.findOneByPatientMail(medicalReviewDTO.getPatient_mail());
        if(medicalRecord == null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> karton ne postoji u sistemu
        }
        System.out.println("SAD CU U SAVE");
        MedicalReview medicalReview = medicalReviewService.save(medicalReviewDTO);
        System.out.println(medicalReview.getId_doctor());
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }
}
