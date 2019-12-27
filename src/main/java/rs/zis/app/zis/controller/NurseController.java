package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.CustomUserService;
import rs.zis.app.zis.service.NurseService;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/nurse")
public class NurseController {
    @Autowired
    private NurseService nurseService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private NurseService customNurseService;
    @GetMapping(produces = "application/json", value = "/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NurseDTO>> getNurse() {
        List<Nurse> listNurse = nurseService.findAll();

        ArrayList<NurseDTO> listDTO = new ArrayList<>();
        for (Nurse n: listNurse) {
            listDTO.add(new NurseDTO(n));
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "getByMail/{mail}")
    public ResponseEntity<NurseDTO> getNurseByMail(@PathVariable String mail) {
        Nurse nurse = nurseService.findOneByMail(mail);
        if(nurse != null)
            System.out.println(nurse.getFirstName() + " " + nurse.getLastName());
        return new ResponseEntity<>(new NurseDTO(nurse), HttpStatus.OK);
    }
    @PostMapping(value = "/changeAttribute/{changedName}/{newValue}/{email}")
    public ResponseEntity<?> changeAttributes( @PathVariable("changedName") String changed, @PathVariable("newValue") String value,@PathVariable("email") String mail) {
        System.out.println("usao sam ovde CHANGE" + changed + " " + value);
        Nurse d= nurseService.findOneByMail(mail);
        if(d==null){
            System.out.println("Nema ga");
        }else {
            if (changed.equals("ime")) {
                d.setFirstName(value);
                System.out.println(d.getFirstName());
            } else if (changed.equals("prezime")) {
                d.setLastName(value);
                System.out.println(d.getLastName());
            } else
                System.out.println("Greska");
        }
        nurseService.update(d);
        NurseDTO nurseDTO = new NurseDTO(d);
        return new ResponseEntity<>(nurseDTO, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('NURSE')")
    @PostMapping(consumes = "text/plain", value = "/changePassword")
    public ResponseEntity<?> changePassword(@RequestHeader("Auth-Token") String token, @RequestBody String password) {
        String mail = tokenUtils.getUsernameFromToken(token);

        boolean flag_ok = customUserService.changePassword(mail, password);
        if(flag_ok) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
