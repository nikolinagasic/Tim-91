package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.ClinicCentreAdminDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.service.ClinicAdministratorService;
import rs.zis.app.zis.service.ClinicCentreAdminService;
import rs.zis.app.zis.service.CustomCCAdmin;
import rs.zis.app.zis.service.DoctorService;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping(value = "/ccadmin")
public class ClinicCentreAdminController {

    @Autowired
    private ClinicCentreAdminService ccadminservice;
    @Autowired
    private ClinicAdministratorService clinicAdministratorService;
    @Autowired
    private ClinicCentreAdminService clinicCentreAdminService;

    @PostMapping(consumes = "application/json" , value = "/register_admin")
    public ResponseEntity<Integer> saveClinicAdministrator(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO) {
        System.out.println("usao sam u post i primio: " + clinicAdministratorDTO.getMail());
        ClinicAdministrator proveriMail = clinicAdministratorService.findOneByMail(clinicAdministratorDTO.getMail());
        if(proveriMail != null)
            System.out.println("primio sam:" +proveriMail.getMail());

        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        ClinicAdministrator clinicAdministrator = clinicAdministratorService.save(clinicAdministratorDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json" , value = "/register_ccadmin")
    public ResponseEntity<Integer> saveClinicCentreAdministrator(@RequestBody ClinicCentreAdminDTO clinicCentreAdminDTO) {
        System.out.println("usao sam u post i primio: " + clinicCentreAdminDTO.getMail());
        ClinicCentreAdmin proveriMail = clinicCentreAdminService.findOneByMail(clinicCentreAdminDTO.getMail());
        if(proveriMail != null)
            System.out.println("primio sam:" +proveriMail.getMail());

        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        ClinicCentreAdmin clinicCentreAdmin = clinicCentreAdminService.save(clinicCentreAdminDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }
  /*  @PostMapping(consumes = "application/json")
    public ResponseEntity<ClinicCentreAdminDTO> saveCAdmin(@RequestBody ClinicCentreAdminDTO adminDTO) {
        ClinicCentreAdmin admin = new ClinicCentreAdmin(adminDTO.getId(),adminDTO.getMail(),adminDTO.getPassword(),
                adminDTO.isPredefined(),adminDTO.getFirstName(),adminDTO.getLastName());

        admin = ccadminservice.save(admin);
        return new ResponseEntity<>(new ClinicCentreAdminDTO(admin), HttpStatus.CREATED);
    }
*/
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClinicCentreAdminDTO> getAdmin(@PathVariable Long id) {

        ClinicCentreAdmin admin = ccadminservice.findOne(id);

        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ClinicCentreAdminDTO(admin), HttpStatus.OK);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<List<ClinicCentreAdminDTO>> getAllCentreAdmin() {

        List<ClinicCentreAdmin> admins = ccadminservice.findAll();

        // convert admins to DTOs
        List<ClinicCentreAdminDTO> adminsDTO = new ArrayList<>();
        for (ClinicCentreAdmin s : admins) {
            adminsDTO.add(new ClinicCentreAdminDTO(s));
        }

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    /*@PutMapping(consumes = "application/json")
    public ResponseEntity<ClinicCentreAdmin> updateCentreAdmin(@RequestBody ClinicCentreAdminDTO adminDTO) {

        // a admin must exist
        ClinicCentreAdmin admin = ccadminservice.findOne(adminDTO.getId());

        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());

        admin = ccadminservice.save(admin);
        return new ResponseEntity<>(new ClinicCentreAdminDTO(admin), HttpStatus.OK);
    }*/

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        ClinicCentreAdmin admin = ccadminservice.findOne(id);

        if (admin != null) {
            ccadminservice.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
