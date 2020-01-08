package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.MedicalRecord;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.MedicalRecordDTO;
import rs.zis.app.zis.service.MedicalRecordService;
import rs.zis.app.zis.service.PatientService;

import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordController extends WebConfig {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PatientService patientService;

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




}
