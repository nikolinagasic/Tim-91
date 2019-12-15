package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.service.NurseService;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/login/nurse")
public class NurseController {
    @Autowired
    private NurseService nurseService;

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
}
