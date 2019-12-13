package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.domain.User;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.service.DoctorService;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/login/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping(produces = "application/json", value = "/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorDTO>> getDoctor() {
        List<Doctor> listDoctor = doctorService.findAll();

        ArrayList<DoctorDTO> listDTO = new ArrayList<>();
        for (Doctor d: listDoctor) {
            listDTO.add(new DoctorDTO(d));
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "getByMail/{mail}")
    public ResponseEntity<DoctorDTO> getDoctorByMail(@PathVariable String mail) {
        Doctor doctor = doctorService.findOneByMail(mail);
        if(doctor != null)
            System.out.println(doctor.getFirstName() + " " + doctor.getLastName());
        return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json" , value = "/changeAttribute/{changedName}/{newValue}/{email}")
    public ResponseEntity<Integer> changeAttributes(@PathVariable("email") String mail, @PathVariable("changedName") String changed, @PathVariable("newValue") String value) {
        Doctor d= doctorService.findOneByMail(mail);
        if(d==null){
            System.out.println("Nema ga");
        }else {
            if (changed == "ime") {
                d.setFirstName(value);
                System.out.println(d.getFirstName());
            } else if (changed == "prezime") {
                d.setLastName(value);
                System.out.println(d.getLastName());
            } else if (changed == "oblast") {
                d.setField(value);
            } else
                System.out.println("Greska");
        }
            return new ResponseEntity<>(0, HttpStatus.OK);     // 0 -> sve okej
    }
}
