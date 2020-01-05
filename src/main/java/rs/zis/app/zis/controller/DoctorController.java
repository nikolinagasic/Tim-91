package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.dto.NurseDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.ClinicService;
import rs.zis.app.zis.service.CustomUserService;
import rs.zis.app.zis.service.DoctorService;
import rs.zis.app.zis.service.DoctorTermsService;

import javax.print.Doc;
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
    private ClinicService clinicService;
    @Autowired
    private DoctorTermsService doctorTermsService;

    @GetMapping(produces = "application/json", value = "/getAll")
   // @PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<List<DoctorDTO>> getDoctor() {
        List<Doctor> listDoctor = doctorService.findAll();

        ArrayList<DoctorDTO> listDTO = new ArrayList<>();
        for (Doctor d: listDoctor) {
            listDTO.add(new DoctorDTO(d));
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }


    @GetMapping(produces = "application/json", value = "/getDoctors/{clinic}")
    // @PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<List<DoctorDTO>> getDoctorByClinic(@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        List<Doctor> listDoctor = doctorService.findAllByClinic(c);

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

    @GetMapping(produces = "application/json", value = "/getTermini/{ime}/{prezime}")
    public ResponseEntity<?> getTermine(@PathVariable String ime, @PathVariable String prezime) {
        Doctor doctor = doctorService.findDoctorByFirstNameAndLastName(ime, prezime);
        List<DoctorTerms> listTerms = doctorTermsService.getTermine(doctor);
        List<DoctorTermsDTO> listaTerminaDTO = new ArrayList<>();
        for (DoctorTerms doctorTerms : listTerms) {
            listaTerminaDTO.add(new DoctorTermsDTO(doctorTerms));
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @PostMapping(produces = "application/json", consumes = "application/json", value = "/find/{ime}/{prezime}")
    public ResponseEntity<?> findDoctor(@RequestBody List<DoctorDTO> listaLekara, @PathVariable("ime") String ime,
                                        @PathVariable("prezime") String prezime) {
        if(ime.equals("~")){
            ime = "";
        }if(prezime.equals("~")){
            prezime = "";
        }
        List<DoctorDTO> listaDoktoraDTO = doctorService.findDoctor(listaLekara, ime, prezime);
        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }
    @PostMapping(produces = "application/json", value = "/delete/{id}/{clinic}")
    public ResponseEntity<?> delete(@PathVariable("id") long id,@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        Doctor doctor = doctorService.findOneById(id);
        doctor.setEnabled(false);
        doctorService.update(doctor);
        List<Doctor> listaDoktora = doctorService.findAllByClinic(c);
        List<DoctorDTO> listaDoktoraDTO = new ArrayList<>();
        for (Doctor d: listaDoktora) {
            listaDoktoraDTO.add(new DoctorDTO(d));
        }
        Doctor doc = doctorService.findOneById(id);
        System.out.println(doc.isEnabled());
        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }
}