package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.CustomUserService;
import rs.zis.app.zis.service.DoctorService;
import rs.zis.app.zis.service.DoctorTermsService;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings({"SpellCheckingInspection", "unused", "StringEquality"})
@RestController
@RequestMapping("/doctor")
public class DoctorController extends WebConfig {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private DoctorTermsService doctorTermsService;

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

    @PostMapping(value = "/changeAttribute/{changedName}/{newValue}/{email}")
    public ResponseEntity<?> changeAttributes( @PathVariable("changedName") String changed, @PathVariable("newValue") String value,@PathVariable("email") String mail) {
        System.out.println("usao sam ovde CHANGE" + changed + " " + value);
        Doctor d= doctorService.findOneByMail(mail);
        if(d==null){
            System.out.println("Nema ga");
        }else {
            if (changed.equals("ime")) {
                d.setFirstName(value);
                System.out.println(d.getFirstName());
            } else if (changed.equals("prezime")) {
                d.setLastName(value);
                System.out.println(d.getLastName());
            }
//            else if (changed == "oblast") {
//                d.setField(value);
//            }
            else
                System.out.println("Greska");
        }
        doctorService.update(d);
        DoctorDTO doctorDTO = new DoctorDTO(d);
        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('DOCTOR')")
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


    // NAPOMENA: moram poslati i celu listu, da bih znao sta treba da pretrazim (da ne pretrazuje medju svim lekarima)
    @PostMapping(produces = "application/json", consumes = "application/json", value = "/searchDoctors/{ime}/{prezime}/{ocena}")
    public ResponseEntity<?> searchDoctors(@RequestBody List<DoctorDTO> listaLekara, @PathVariable("ime") String ime,
                                                @PathVariable("prezime") String prezime,
                                                    @PathVariable("ocena") double ocena) {
        if(ime.equals("~")){
            ime = "";
        }if(prezime.equals("~")){
            prezime = "";
        }
        List<DoctorDTO> listaDoktoraDTO = doctorService.searchDoctors(listaLekara, ime, prezime, ocena);
        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", consumes = "application/json",
            value = "/getFilterDoctor/{ocenaOd}/{ocenaDo}")
    public ResponseEntity<?> searchDoctors(@RequestBody List<DoctorDTO> listaLekara,
                                           @PathVariable("ocenaOd") String ocenaOd,
                                           @PathVariable("ocenaDo") String ocenaDo){

        List<DoctorDTO> listaDoktoraDTO = doctorService.filterDoctor(listaLekara, ocenaOd, ocenaDo);
        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/getTermini/{ime}/{prezime}/{date}")
    public ResponseEntity<?> getTermine(@PathVariable("ime") String ime,
                                        @PathVariable("prezime") String prezime,
                                        @PathVariable("date") long datum) {

        Doctor doctor = doctorService.findDoctorByFirstNameAndLastName(ime, prezime);
        List<DoctorTermsDTO> listTerms = doctorTermsService.getTermine(datum, doctor);
        return new ResponseEntity<>(listTerms, HttpStatus.OK);
    }

}