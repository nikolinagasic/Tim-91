package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.*;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.*;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings({"SpellCheckingInspection", "unused", "IfCanBeSwitch"})
@RestController
@RequestMapping("/clinicAdministrator")
public class ClinicAdministratorController extends WebConfig {

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private NurseService customNurseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private TipPregledaService tipPregledaService;

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;

    @PostMapping(consumes = "application/json" , value = "/registerDoctor")
    public ResponseEntity<Integer> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        Users proveriMail = userService.findOneByMail(doctorDTO.getMail());
        if(proveriMail != null){
            System.out.println("Adresa \""+proveriMail.getMail()+"\" nije jedinstvena u sistemu");
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }
        TipPregleda proveriTip = tipPregledaService.findOneByName(doctorDTO.getTip());
        if (proveriTip == null) {
            System.out.println("ovde usao"+ doctorDTO.getTip());
            return new ResponseEntity<>(-2, HttpStatus.NOT_FOUND);
        }
        System.out.println("klinika u dto: "+ doctorDTO.getClinic());
        Doctor doctor = doctorService.save(doctorDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json", value = "/registerNurse")
    public ResponseEntity<Integer> saveNurse(@RequestBody NurseDTO nurseDTO) {
        Users proveriMail = userService.findOneByMail(nurseDTO.getMail());
        if(proveriMail != null){
            System.out.println("Adresa \""+proveriMail.getMail()+"\" nije jedinstvena u sistemu");
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        Nurse nurse = nurseService.save(nurseDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }
    @PreAuthorize("hasRole('CADMIN')")
    @GetMapping(produces = "application/json", value = "/getCadmin")
    public ResponseEntity<?> changeAttribute(@RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        return new ResponseEntity<>(clinicAdministratorService.findOneByMail(mail), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('CADMIN')")
    @PostMapping(value = "/changeAttribute/{naziv}/{vrednost}/{mail}")
    public ResponseEntity<?> changeAttribute(@PathVariable("naziv") String naziv,
                                             @PathVariable("vrednost") String vrednost,
                                             @PathVariable("mail") String mail) {
        System.out.println("primio change: naziv{"+naziv+"}, vrednost{"+vrednost+"}, mail{"+mail+"}");
        ClinicAdministrator clinicAdministrator = clinicAdministratorService.findOneByMail(mail);
        if (clinicAdministrator == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        if(naziv.equals("ime")){
            clinicAdministrator.setFirstName(vrednost);
        }
        else if(naziv.equals("prezime")){
            clinicAdministrator.setLastName(vrednost);
        }
        else if(naziv.equals("adresa")){
            clinicAdministrator.setAddress(vrednost);
        }
        else if(naziv.equals("grad")){
            clinicAdministrator.setCity(vrednost);
        }
        else if(naziv.equals("drzava")){
            clinicAdministrator.setCountry(vrednost);
        }
        else if(naziv.equals("telefon")){
            clinicAdministrator.setTelephone(vrednost);
        }

        clinicAdministratorService.update(clinicAdministrator);
        ClinicAdministratorDTO clinicAdministratorDTO = new ClinicAdministratorDTO(clinicAdministrator);
        return new ResponseEntity<>(clinicAdministratorDTO, HttpStatus.OK);
    }
    @GetMapping(produces = "application/json", value = "/getTerms/{clinic}")
    public ResponseEntity<?> getTerms(@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        List<DoctorTerms> terms = doctorTermsService.findAllByClinic(c);
        List<DoctorTermsDTO> termsDTO = new ArrayList<>();
        System.out.println("ovde je");
        for (DoctorTerms t : terms) {
            System.out.println(t.getDate());
            if (!t.isProcessedByAdmin())
                termsDTO.add(new DoctorTermsDTO(t));
            System.out.println("nije:"+t.getDate());
        }
        return new ResponseEntity<>(termsDTO, HttpStatus.OK);

    }

    // TODO obradi izuzetak za OPTIMISTIC LOCK
    @PostMapping(produces = "application/json",
            value = "/createPredefinedTerm/{date}/{sat_id}/{room_id}/{type_id}/{doctor_id}/{price}/{discount}")
    public ResponseEntity<?> createPredefinedTerm(@PathVariable("date") Long date,
                                                  @PathVariable("sat_id") Long satnica_id,
                                                  @PathVariable("room_id") Long room_id,
                                                  @PathVariable("type_id") Long type_id,
                                                  @PathVariable("doctor_id") Long doctor_id,
                                                  @PathVariable("price") double price,
                                                  @PathVariable("discount") int discount){

        return new ResponseEntity<>(doctorTermsService.createPredefinedTerm(date, satnica_id, room_id, type_id,
                        doctor_id, price, discount), HttpStatus.OK);
    }

}