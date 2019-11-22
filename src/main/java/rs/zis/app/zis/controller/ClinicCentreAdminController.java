package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.ClinicCentreAdminDTO;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.service.*;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping(value = "/ccadmin")
public class ClinicCentreAdminController extends WebConfig
{

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;
    @Autowired
    private ClinicCentreAdminService clinicCentreAdminService;
    @Autowired
    private ClinicService clinicservice;

    @PostMapping(consumes = "application/json" , value = "/register_admin")
    public ResponseEntity<Integer> saveClinicAdministrator(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO) {
       // System.out.println("usao sam u post i primio: " + clinicAdministratorDTO.getMail());
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
       // System.out.println("usao sam u post i primio: " + clinicCentreAdminDTO.getMail());
        ClinicCentreAdmin proveriMail = clinicCentreAdminService.findOneByMail(clinicCentreAdminDTO.getMail());
        if(proveriMail != null)
            System.out.println("primio sam:" +proveriMail.getMail());

        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        ClinicCentreAdmin clinicCentreAdmin = clinicCentreAdminService.save(clinicCentreAdminDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json" , value = "/register_clinic")
    public ResponseEntity<Integer> saveClinic(@RequestBody ClinicDTO clinicDTO){
        System.out.println("usao sam u post i primio: " + clinicDTO.getName());
        Clinic proveriName = clinicservice.findOneByName(clinicDTO.getName());
        if(proveriName != null)
            System.out.println("primio sam:"+proveriName.getName());

        if(proveriName != null)
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej

        Clinic clinic = clinicservice.save(clinicDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej


    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ClinicCentreAdminDTO> getAdmin(@PathVariable Long id) {

        ClinicCentreAdmin admin = clinicCentreAdminService.findOne(id);

        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ClinicCentreAdminDTO(admin), HttpStatus.OK);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<List<ClinicCentreAdminDTO>> getAllCentreAdmin() {

        List<ClinicCentreAdmin> admins = clinicCentreAdminService.findAll();

        // convert admins to DTOs
        List<ClinicCentreAdminDTO> adminsDTO = new ArrayList<>();
        for (ClinicCentreAdmin s : admins) {
            adminsDTO.add(new ClinicCentreAdminDTO(s));
        }

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCCAdmin(@PathVariable Long id) {

        ClinicCentreAdmin admin = clinicCentreAdminService.findOne(id);

        if (admin != null) {
            clinicCentreAdminService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
