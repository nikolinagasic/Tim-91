package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.domain.Vacation;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.dto.VacationDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.CustomUserService;
import rs.zis.app.zis.service.NurseService;
import rs.zis.app.zis.service.VacationService;

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
    private VacationService vacationService;

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


    //saljem zahtev za godisnjim odmorom
    //id je id medicinske sestre koja je kreirala zahtev
    @PostMapping(consumes="application/json", value = "/reserveVacationNurse/{id}")
    public ResponseEntity<?> reserveVacation(@RequestBody VacationDTO vacationDTO, @PathVariable("id") Long id) {
        Nurse nurse = nurseService.findOneById(id);
        vacationDTO.setFirstName(nurse.getFirstName());
        vacationDTO.setLastName(nurse.getLastName());
        Vacation vacation = vacationService.saveNurseVocation(vacationDTO,nurse);
        nurse.addVacation(vacation);
        nurseService.update(nurse);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //na osnovu id sestre vracam zahteve koji su odobreni
    //odobrene zahteve prikazujem na kalendaru
    @GetMapping(produces = "application/json", value = "/getScheduleVacation/{id}")
    public ResponseEntity<?>getScheduleVocation(@PathVariable Long id) {
        Nurse nurse = nurseService.findOneById(id);
        List<Vacation>vacationList = vacationService.findAllByNurse(nurse);
        List<VacationDTO>ret = new ArrayList<>();
        for(Vacation vacation:vacationList){
            VacationDTO vacationDTO = new VacationDTO(vacation);
            ret.add(vacationDTO);
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }


}
