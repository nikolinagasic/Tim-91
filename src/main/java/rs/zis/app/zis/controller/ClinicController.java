package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Vacation;
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
        List<Clinic> listaKlinika = clinicService.searchClinic(datum, tip, ocena);
        if(listaKlinika == null) {
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        List<ClinicDTO> listaKlinikaDTO = new ArrayList<>();
        for (Clinic c: listaKlinika) {
            listaKlinikaDTO.add(new ClinicDTO(c));
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

}
