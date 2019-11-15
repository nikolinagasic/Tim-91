package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.dto.ClinicCentreAdminDTO;
import rs.zis.app.zis.service.ClinicCentreAdminService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "login/ccadmin")
public class ClinicCentreAdminController {

    @Autowired
    private ClinicCentreAdminService ccadminservice;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ClinicCentreAdminDTO> saveCAdmin(@RequestBody ClinicCentreAdminDTO adminDTO) {
        ClinicCentreAdmin admin = new ClinicCentreAdmin(adminDTO.getId(),adminDTO.getMail(),adminDTO.getPassword(),
                adminDTO.isPredefined(),adminDTO.getFirstName(),adminDTO.getLastName());

        admin = ccadminservice.save(admin);
        return new ResponseEntity<>(new ClinicCentreAdminDTO(admin), HttpStatus.CREATED);
    }

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
