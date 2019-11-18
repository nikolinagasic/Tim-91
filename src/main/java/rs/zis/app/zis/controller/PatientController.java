package rs.zis.app.zis.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/patient")
public class PatientController extends WebConfig {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientService customPatientService;

    @PostMapping(consumes = "application/json" , value = "/register")
    public ResponseEntity<Integer> savePatient(@RequestBody PatientDTO patientDTO) {
        System.out.println("usao sam u post i primio: " + patientDTO.getFirstName() + patientDTO.getLastName());

        Patient proveriLbo = patientService.findOneByLbo(patientDTO.getLbo());
        Patient proveriMail = patientService.findOneByMail(patientDTO.getMail());
        if(proveriLbo != null){
            return new ResponseEntity<>(-1, HttpStatus.CONFLICT);       // -1 -> lbo nije okej
        }
        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Patient patient = patientService.save(patientDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @GetMapping(produces = "application/json", value = "/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PatientDTO>> getPatient() {
        List<Patient> listPatient = patientService.findAll();

        ArrayList<PatientDTO> listDTO = new ArrayList<>();
        for (Patient p: listPatient) {
            listDTO.add(new PatientDTO(p));
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "getByMail/{mail}")
    public ResponseEntity<PatientDTO> getPatientByMail(@PathVariable String mail) {
        Patient patient = patientService.findOneByMail(mail);
        if(patient != null)
            System.out.println(patient.getFirstName() + " " + patient.getLastName());
        return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
    }


}
