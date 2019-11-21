package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.ClinicAdministratorService;
import rs.zis.app.zis.service.DoctorService;
import rs.zis.app.zis.service.NurseService;
import rs.zis.app.zis.service.PatientService;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping("/clinicAdministrator")
public class ClinicAdministratorController extends WebConfig {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorService customDoctorService;

    @PostMapping(consumes = "application/json" , value = "/registerDoctor")
    public ResponseEntity<Integer> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        System.out.println("usao sam u post i primio: " + doctorDTO.getMail());
        Doctor proveriMail = doctorService.findOneByMail(doctorDTO.getMail());
//        if(proveriMail != null)
//            System.out.println("primio sam:" +proveriMail.getMail());

        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Doctor doctor = doctorService.save(doctorDTO);
//        System.out.println("upisao sam:"+doctor.getMail());
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }


    @Autowired
    private NurseService nurseService;

    @Autowired
    private NurseService customNurseService;

    @PostMapping(consumes = "application/json" , value = "/registerNurse")
    public ResponseEntity<Integer> saveNurse(@RequestBody NurseDTO nurseDTO) {
        System.out.println("usao sam u post i primio: " + nurseDTO.getMail());

        Nurse proveriMail = nurseService.findOneByMail(nurseDTO.getMail());

        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Nurse nurse = nurseService.save(nurseDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }


}