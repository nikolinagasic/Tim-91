package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.MedicalRecord;
import rs.zis.app.zis.domain.MedicalReview;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.MedicalRecordDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.dto.ReviewsRecordDTO;
import rs.zis.app.zis.service.DoctorService;
import rs.zis.app.zis.service.MedicalRecordService;
import rs.zis.app.zis.service.MedicalReviewService;
import rs.zis.app.zis.service.PatientService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordController extends WebConfig {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    MedicalReviewService medicalReviewService;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @PostMapping(consumes = "application/json" , value = "/save_record")
    public ResponseEntity<Integer> saveMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO){
        //proveravam da li postoji pacijent sa tim mejlom
        //ne treba da otvaram karton za pacijenta koji ne postoji u sistemu
        Patient patient = patientService.findOneByMail(medicalRecordDTO.getPatientMail());
        if(patient==null)
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> pacijent ne postoji u sistemu
        MedicalRecord medicalRecord = medicalRecordService.save(medicalRecordDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    //na osnovu mejla pacijenta trazimo njegov zdravstveni karton
    @GetMapping(value= "/getRecord/{mail}")
    public ResponseEntity<MedicalRecordDTO> getRecord(@PathVariable("mail") String mail){
        List<MedicalRecord>medicalRecordList = medicalRecordService.findAll();
        MedicalRecord medicalRecord = new MedicalRecord();
        for(MedicalRecord temp:medicalRecordList){
            if(temp.getPatintMail().equals(mail))
                medicalRecord=temp;
        }
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(medicalRecord);
        return new ResponseEntity<>(medicalRecordDTO,HttpStatus.OK);
     }

     //na osnovu mejla pacijenta vrati njegovo ime i prezime
    @GetMapping(value= "/getNamePatient/{mail}")
    public ResponseEntity<?> getNamePatient(@PathVariable("mail")String mail){
        Patient p = patientService.findOneByMail(mail);
        PatientDTO patientDTO = new PatientDTO(p);
        return new ResponseEntity<>(patientDTO,HttpStatus.OK);
    }


     //cuvam azuriran zdravstveni karton pacijenta
    @PostMapping(value = "/changeRecord/{naziv}/{vrednost}/{mail}")
    public ResponseEntity<?> changeRecord(@PathVariable("naziv") String naziv,
                                          @PathVariable("vrednost") String vrednost,
                                          @PathVariable("mail") String mail) {
        System.out.println("primio change: naziv{" + naziv + "}, vrednost{" + vrednost + "}, mail{" + mail + "}");
        //trazim zdravstveni karton pacijenta ciji je mejl prosledjen
        List<MedicalRecord>medicalRecordList = medicalRecordService.findAll();
        MedicalRecord medicalRecord = new MedicalRecord();
        for(MedicalRecord temp:medicalRecordList){
            if(temp.getPatintMail().equals(mail))
                medicalRecord=temp;
        }
        if(medicalRecord==null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        if(naziv.equals("height")){
            medicalRecord.setHeight(Integer.parseInt(vrednost));
        }
        else if(naziv.equals("weight")) {
           medicalRecord.setWeight(Integer.parseInt(vrednost));
        }
        else if(naziv.equals("dioptreRightEye")){
            medicalRecord.setDioptreRightEye(Float.parseFloat(vrednost));
        }
        else if(naziv.equals("diopreLeftEye")){
            medicalRecord.setDioptreLeftEye(Float.parseFloat(vrednost));
        }
        else if(naziv.equals("bloodGroup")) {
            medicalRecord.setBloodGroup(vrednost);
        }
        else if(naziv.equals("allergy")){
            medicalRecord.setAllergy(vrednost);
        }

        medicalRecordService.save(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(medicalRecord);
        return new ResponseEntity<>(medicalRecordDTO,HttpStatus.OK);

    }

    //vracam listu medicinskih izvestaja koji postoje u kartonu
    //mail pacijenta ciji je karton
    //id lekara koji trazi istoriju bolesti
    @GetMapping(value="/getReviewsinRecord/{mail}/{id}")
    public ResponseEntity<List<ReviewsRecordDTO>>getReviewsinRecord(@PathVariable("mail") String mail,
                                                                    @PathVariable("id") Long id){

        MedicalRecord md = medicalRecordService.findOneByPatientMail(mail);
        List<MedicalReview>medicalReviewList = medicalReviewService.getListReviews(md.getId());
        List<ReviewsRecordDTO> reviewsRecordDTOS = new ArrayList<>();
        for (MedicalReview medicalReview: medicalReviewList){
            ReviewsRecordDTO reviewsRecordDTO = new ReviewsRecordDTO();
            reviewsRecordDTO.setDate(medicalReview.getDate());
            Doctor doctor = doctorService.findOneById(medicalReview.getId_doctor()); //doctor koji je napravio izvestaj
            reviewsRecordDTO.setFirstName(doctor.getFirstName());
            reviewsRecordDTO.setLastName(doctor.getLastName());
            if(doctor.getId()==id){ //ako je isti doktor koji trazi istoriju bolesti i koji je napravio taj izvestaj
                reviewsRecordDTO.setCouldChange(true);
            }else{
                reviewsRecordDTO.setCouldChange(false);
            }
            reviewsRecordDTO.setReviewId(medicalReview.getId());
            reviewsRecordDTOS.add(reviewsRecordDTO);
        }
        return new ResponseEntity<>(reviewsRecordDTOS, HttpStatus.OK);
    }



}
