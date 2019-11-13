package rs.zis.app.zis.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.PatientService;

@RestController
@RequestMapping("/login/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient(patientDTO.getId(), patientDTO.getMail(), patientDTO.getPassword(),
                patientDTO.getFirstName(), patientDTO.getLastName(), patientDTO.getAddress(), patientDTO.getCity(),
                patientDTO.getCountry(), patientDTO.getTelephone(), patientDTO.getLbo());

        patient = patientService.save(patient);
        return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.CREATED);
    }


}
