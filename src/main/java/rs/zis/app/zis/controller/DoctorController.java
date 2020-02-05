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
import rs.zis.app.zis.service.*;

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
    @Autowired
    private TipPregledaService tipPregledaService;
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

    @GetMapping(produces = "application/json", value = "/getTermini/{id}/{date}")
    public ResponseEntity<?> getTermine(@PathVariable("id") Long id,
                                        @PathVariable("date") long datum) {

        Doctor doctor = doctorService.findOneById(id);
        List<DoctorTermsDTO> listTerms = doctorTermsService.getTermine(datum, doctor);
        return new ResponseEntity<>(listTerms, HttpStatus.OK);
    }
  
    @GetMapping(produces = "application/json", value = "/detailTermin/{id_doctor}/{date}/{start_term}")
    public ResponseEntity<?> getDetailTermin(@RequestHeader("Auth-Token") String token,
                                        @PathVariable("id_doctor") Long id_doctor,
                                        @PathVariable("date") long datum,
                                        @PathVariable("start_term") String start_term) {

        String mail = tokenUtils.getUsernameFromToken(token);
        DoctorTermsDTO doctorTermsDTO = doctorTermsService.detailTerm(id_doctor, datum, start_term, mail);
        return new ResponseEntity<>(doctorTermsDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json", value = "/reserveTerm")
    public ResponseEntity<?> reserveTerm(@RequestHeader("Auth-Token") String token,
                                         @RequestBody DoctorTermsDTO doctorTermsDTO) {

        String mail = tokenUtils.getUsernameFromToken(token);
        boolean isReserved = doctorTermsService.reserveTerm(mail, doctorTermsDTO,true);
        if(isReserved){
            return new ResponseEntity<>(doctorTermsDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(consumes = "application/json", produces = "application/json", value = "/reserveTermDoctor/{mail}/{examination}")
    public ResponseEntity<?> reserveTermDoctor(@RequestHeader("Auth-Token") String token,
                                         @RequestBody DoctorTermsDTO doctorTermsDTO,@PathVariable("mail") String mail,@PathVariable("examination") boolean examination) {

        boolean isReserved = doctorTermsService.reserveTerm(mail, doctorTermsDTO,examination);
        if(isReserved){
            doctorService.sendMailAdministrator(doctorTermsDTO);
            return new ResponseEntity<>(doctorTermsDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(produces = "application/json", consumes = "application/json",
            value = "/expandedSearchDoctor/{ime}/{prezime}/{ocena}/{date}/{select}")
    public ResponseEntity<?> expandedSearchDoctor(@RequestBody List<DoctorDTO> listaLekara,
                                        @PathVariable("ime") String ime,
                                        @PathVariable("prezime") String prezime,
                                        @PathVariable("ocena") double ocena,
                                        @PathVariable("date") long datum,
                                        @PathVariable("select") String tip) {

        return new ResponseEntity<>(doctorService.expandedSearchDoctor(ime, prezime, ocena, datum, tip, listaLekara)
                , HttpStatus.OK);
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
    @PostMapping(produces = "application/json", consumes = "application/json", value = "/filterByType/{type}")
    public ResponseEntity<?> filterDoctorByType(@RequestBody List<DoctorDTO> listaLekara, @PathVariable("type") String type) {
        List<DoctorDTO> listaDoktoraDTO = doctorService.filterDoctorByType(listaLekara, type);
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