package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Vacation;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.service.ClinicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping("/clinic")
public class ClinicController extends WebConfig {

    @Autowired
    private ClinicService clinicService;

    // dobijam sve doktore koji rade u klinici sa imenom NAME
    @GetMapping (produces = "application/json", value = "/getDoctors/{name}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorDTO>> getDoctors(@PathVariable("name") String name) {
        Clinic clinic = clinicService.findOneByName(name);
        Set<Doctor> listaDoktoraSet =  clinic.getDoctors();
        List<Doctor> listaDoktora = new ArrayList<>();
        listaDoktora.addAll(listaDoktoraSet);

        List<DoctorDTO> listaDoktoraDTO = new ArrayList<>();
        for (Doctor d : listaDoktora) {
            listaDoktoraDTO.add(new DoctorDTO(d));
        }

        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.CONFLICT);
    }

    @GetMapping (produces = "application/json", value = "/searchClinic/{date}/{type}/{rating}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getClinic(@PathVariable("date") long datum,
                                       @PathVariable("type") String tip,
                                       @PathVariable("rating") int ocena) {
        System.out.println("datum: " + datum + ", tip: " + tip + ", ocena: " + ocena);
        List<ClinicDTO> listaKlinikaDTO = clinicService.searchClinic(datum, tip, ocena);
        if(listaKlinikaDTO == null) {
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(listaKlinikaDTO, HttpStatus.OK);
    }

    @GetMapping (produces = "application/json", value = "/getAll")
    public ResponseEntity<?> getAllClinics(){
        List<ClinicDTO> listaKlinika = new ArrayList<>();
        for (Clinic c: clinicService.findAll()) {
            listaKlinika.add(new ClinicDTO(c));
        }

        return new ResponseEntity<>(listaKlinika, HttpStatus.OK);
    }

    @GetMapping (produces = "application/json", value = "/getDoctorsByClinic/{clinic_name}")
    public ResponseEntity<?> getDoctorsInClinic(@PathVariable("clinic_name") String clinic_name){
        List<Doctor> listaDoktora = clinicService.findDoctorsByClinic(clinic_name);
        List<DoctorDTO> listaDoktoraDTO = new ArrayList<>();
        for (Doctor d : listaDoktora) {
            listaDoktoraDTO.add(new DoctorDTO(d));
        }
        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", consumes = "application/json" ,
            value = "/getFilterClinic/{cenaOd}/{cenaDo}/{ocenaOd}/{ocenaDo}/{naziv}")
    public ResponseEntity<?> getDoctorsInClinic(@RequestBody List<ClinicDTO> lKlinika,
                                                @PathVariable("cenaOd") String cenaOd,
                                                @PathVariable("cenaDo") String cenaDo,
                                                @PathVariable("ocenaOd") String ocenaOd,
                                                @PathVariable("ocenaDo") String ocenaDo,
                                                @PathVariable("naziv") String naziv){
        System.out.println(cenaOd + "-" + cenaDo + ", " + ocenaOd + "-" + ocenaDo + ", " + naziv);
        List<ClinicDTO> listaKlinikaDTO = clinicService.filterClinic(cenaOd, cenaDo, ocenaOd, ocenaDo, naziv, lKlinika);
        return new ResponseEntity<>(listaKlinikaDTO, HttpStatus.OK);
    }
    @GetMapping (produces = "application/json", value = "/getOne/{name}")
    //@PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> getClinicByName(@PathVariable("name") String name) {
        System.out.println("ime klinike: " + name);
        Clinic pronadjiKliniku = clinicService.findOneByName(name);
        if(pronadjiKliniku == null) {
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new ClinicDTO(pronadjiKliniku), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('CADMIN')")
    @PostMapping(value = "/changeAttribute/{naziv}/{vrednost}/{name}")
    public ResponseEntity<?> changeAttribute(@PathVariable("naziv") String naziv,
                                             @PathVariable("vrednost") String vrednost,
                                             @PathVariable("name") String name) {
        System.out.println("primio change: naziv{"+naziv+"}, vrednost{"+vrednost+"}, name{"+name+"}");
        Clinic clinic = clinicService.findOneByName(name);
        if (clinic == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        if(naziv.equals("adresa")){
            clinic.setAddress(vrednost);
        }
        else if(naziv.equals("opis")){
            clinic.setDescription(vrednost);
        }

        clinicService.update(clinic);
        return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
    }
}
