package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.*;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping("/clinicAdministrator")
public class ClinicAdministratorController extends WebConfig {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private NurseService customNurseService;

    @PostMapping(consumes = "application/json" , value = "/registerDoctor")
    public ResponseEntity<Integer> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        User proveriMail = userService.findOneByMail(doctorDTO.getMail());
        if(proveriMail != null){
            System.out.println("Adresa \""+proveriMail.getMail()+"\" nije jedinstvena u sistemu");
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Doctor doctor = doctorService.save(doctorDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json" , value = "/registerNurse")
    public ResponseEntity<Integer> saveNurse(@RequestBody NurseDTO nurseDTO) {
        User proveriMail = userService.findOneByMail(nurseDTO.getMail());
        if(proveriMail != null){
            System.out.println("Adresa \""+proveriMail.getMail()+"\" nije jedinstvena u sistemu");
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Nurse nurse = nurseService.save(nurseDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }


}