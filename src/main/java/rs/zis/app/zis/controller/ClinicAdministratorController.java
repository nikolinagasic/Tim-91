package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.ClinicAdministratorService;
import rs.zis.app.zis.service.PatientService;

@RestController
@RequestMapping("/login/clinicAdministrator")
public class ClinicAdministratorController {

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ClinicAdministratorDTO> saveClinicAdministrator(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO) {
        ClinicAdministrator clinicAdministrator = new ClinicAdministrator(clinicAdministratorDTO.getId(), clinicAdministratorDTO.getMail(), clinicAdministratorDTO.getPassword(),
                clinicAdministratorDTO.getFirstName(), clinicAdministratorDTO.getLastName(), clinicAdministratorDTO.getAddress(), clinicAdministratorDTO.getCity(),
                clinicAdministratorDTO.getCountry(), clinicAdministratorDTO.getTelephone(), clinicAdministratorDTO.getClinic());

        clinicAdministrator = clinicAdministratorService.save(clinicAdministrator);
        return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.CREATED);
    }
}