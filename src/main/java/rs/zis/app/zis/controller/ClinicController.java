package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.ReportDTO;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.security.TokenUtils;
import rs.zis.app.zis.service.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping("/clinic")
public class ClinicController extends WebConfig {

    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DoctorTermsService doctorTermsService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PatientService patientService;

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

        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }

    @GetMapping (produces = "application/json", value = "/searchClinic/{date}/{type}/{rating}")
    public ResponseEntity<?> getClinic(@PathVariable("date") long datum,
                                       @PathVariable("type") String tip,
                                       @PathVariable("rating") double ocena) {
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

    // TODO test 3.13
    @GetMapping (produces = "application/json", value = "/getDoctorsByClinic/{clinic_name}/{date}")
    public ResponseEntity<?> getDoctorsInClinic(@PathVariable("clinic_name") String clinic_name,
                                                @PathVariable("date") Long date){
        List<Doctor> listaDoktora = clinicService.findDoctorsByClinic(clinic_name, date);
        List<DoctorDTO> listaDoktoraDTO = new ArrayList<>();
        for (Doctor d : listaDoktora) {
            listaDoktoraDTO.add(new DoctorDTO(d));
        }
        return new ResponseEntity<>(listaDoktoraDTO, HttpStatus.OK);
    }

    // TODO test 3.13
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

    // TODO test 3.13
    @GetMapping (produces = "application/json", value = "/getClinicByName/{clinic_id}")
    public ResponseEntity<?> getClinicById(@PathVariable("clinic_id") Long clinic_id){
        Clinic clinic = clinicService.findOneById(clinic_id);
        if(clinic != null){
            return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("greska", HttpStatus.BAD_REQUEST);
        }
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

  @PostMapping(value = "/changeAttribute/{changedName}/{newValue}/{name}")
    public ResponseEntity<?> changeAttributes( @PathVariable("changedName") String changed, @PathVariable("newValue") String value,@PathVariable("name") String name) {
        System.out.println("usao sam ovde CHANGE" + changed + " " + value);
        Clinic clinic= clinicService.findOneByName(name);
        if(clinic==null){
            System.out.println("Nema ga");
        }else {
            if (changed.equals("adresa")) {
                clinic.setAddress(value);
                System.out.println(clinic.getAddress());
            } else if (changed.equals("opis")) {
                clinic.setDescription(value);
                System.out.println(clinic.getDescription());
            }
        }
        clinicService.update(clinic);
        return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
    }

    // vraca sale klinike sa imenom name
    @GetMapping (produces = "application/json", value = "/getRooms/{clinic_name}")
    public ResponseEntity<?> getRoomsByName(@PathVariable("clinic_name") String name) {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : roomService.getRoomsInClinic(name)) {
            roomDTOS.add(new RoomDTO(room));
        }

        return new ResponseEntity<>(roomDTOS, HttpStatus.OK);
    }

    // TODO test 3.12
    @PostMapping (consumes = "application/json", produces = "application/json", value = "/getPredefinedTerms")
    public ResponseEntity<?> getPredefinedTerms(@RequestBody ClinicDTO klinika) {
        return new ResponseEntity<>(clinicService.getPredefinedTerms(klinika.getId()), HttpStatus.OK);
    }

    // TODO test 3.12
    @PostMapping (produces = "application/json", value = "/reservePredefinedTerm/{id_term}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getPredefinedTerms(@PathVariable("id_term") Long id_term,
                                                @RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        Patient patient = patientService.findOneByMail(mail);
        return new ResponseEntity<>(clinicService.reservePredefinedTerm(id_term, patient), HttpStatus.OK);
    }

    @GetMapping (produces = "application/json", value = "/getPatientHistoryClinics")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getHistoryClinic(@RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        Patient patient = patientService.findOneByMail(mail);

        return new ResponseEntity<>(clinicService.getPatientHistoryClinics(patient), HttpStatus.OK);
    }

    @PostMapping (produces = "application/json", value = "/oceniKliniku/{clinic_id}/{ocena}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getHistoryClinic(@PathVariable("clinic_id") Long id,
                                              @PathVariable("ocena") double ocena,
                                              @RequestHeader("Auth-Token") String token) {
        String mail = tokenUtils.getUsernameFromToken(token);
        Patient patient = patientService.findOneByMail(mail);
        Clinic clinic = clinicService.findOneById(id);
        return new ResponseEntity<>(clinicService.oceniKliniku(clinic, ocena, patient), HttpStatus.OK);
    }

    @PostMapping (produces = "application/json",
                  consumes = "application/json",
                  value = "/sortClinicByName/{order}")
    public ResponseEntity<?> sortClinicByName(@RequestBody List<ClinicDTO> lista_klinika,
                                              @PathVariable("order") String order) {
        return new ResponseEntity<>(clinicService.sortClinicByName(lista_klinika, order), HttpStatus.OK);
    }

    @PostMapping (produces = "application/json",
            consumes = "application/json",
            value = "/sortClinicByAddress/{order}")
    public ResponseEntity<?> sortClinicByAddress(@RequestBody List<ClinicDTO> lista_klinika,
                                                 @PathVariable("order") String order) {
        return new ResponseEntity<>(clinicService.sortClinicByAddress(lista_klinika, order), HttpStatus.OK);
    }
    @GetMapping (produces = "application/json", value = "/getRatings/{clinic}")
    //@PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> getRatings(@PathVariable("clinic") String clinic) {
        Clinic c =clinicService.findOneByName(clinic);
        List<Doctor> doctors = doctorService.findAllByClinic(c);
        double numDoc = doctors.size();
        double sumDoc = 0;
        for (Doctor d : doctors) {
           sumDoc += d.getSum_ratings()/d.getNumber_rating();
        }
        double ocenaDoc = sumDoc/numDoc;
        double ocenaClinic = c.getSum_ratings()/c.getNumber_ratings();
        double ret[] = {ocenaDoc,ocenaClinic};

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
    @GetMapping (produces = "application/json", value = "/getReport/{clinic}/{date}")
    //@PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> getReport(@PathVariable("clinic") String clinic,@PathVariable("date") long date) {
        Clinic c =clinicService.findOneByName(clinic);
        long week = date -1000*60*60*24;
        double sumWeek[] = new double[7];
        double sumDay = 0;
        double sumDayTerm = 0;
        double sumWeekTerm[] = new double[7];
        int i=6;
        while (i>=0) {
            sumDay = 0;
            sumDayTerm =0;
            for (DoctorTerms term : doctorTermsService.findAllByClinic(c)) {
                System.out.println((new Date(term.getDate()))+" "+(new Date(week)));
                if (term.getDate() == week) {
                    sumDay += term.getDoctor().getPrice();
                    sumDayTerm+= 1;
                }
            }
            sumWeek[i] = sumDay;
            sumWeekTerm[i] = sumDayTerm;
            i--;
            week = week -1000*60*60*24;
            System.out.println("dan:"+sumDay+" "+sumDayTerm);
        }
        long month = date - 1000*60*60*24;

        double sumDays = 0;
        double sumMonth[] = new double[30];
        double sumDaysTerm =0;
        double sumMonthTerm[] = new double[30];
        i=29;
        while (i>=0) {
            sumDays = 0;
            sumDaysTerm = 0;
            for (DoctorTerms term : doctorTermsService.findAllByClinic(c)) {
                if (term.getDate() == month) {
                    sumDays += term.getDoctor().getPrice();
                    sumDaysTerm += 1;
                }
            }
            sumMonth[i] = sumDays;
            sumMonthTerm[i] = sumDaysTerm;
            i--;
            month = month - 1000*60*60*24;
            System.out.println("mesec dan:"+sumDays+" "+sumDaysTerm);

        }
        long year = date - 1000*60*60*24;
        double sumMonths = 0;
        double sumYear[] = new double[12];
        double sumMonthsTerm = 0;
        double sumYearTerm[] = new double[12];
        i=11;
        int j=0;
        while (j<360) {
            sumMonths = 0;
            sumMonthsTerm = 0;
            do {
                for (DoctorTerms term : doctorTermsService.findAllByClinic(c)) {
                    if (term.getDate() == year) {
                        sumMonths += term.getDoctor().getPrice();
                        sumMonthsTerm += 1;
                    }
                }
                j++;
                year = year - 1000*60*60*24;
            } while(!(j%30==0));
            sumYear[i] = sumMonths;
            sumYearTerm[i] = sumMonthsTerm;
            i--;
            System.out.println("godina mesec:"+sumMonths +" "+sumMonthsTerm);
        }
        ReportDTO reportDTO = new ReportDTO(sumWeek,sumMonth,sumYear,sumWeekTerm,sumMonthTerm,sumYearTerm);
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

}
