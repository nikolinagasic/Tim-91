package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.service.DoctorService;
import rs.zis.app.zis.service.TipPregledaService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/type")
public class TipPregledaController extends WebConfig {

    @Autowired
    private TipPregledaService tipPregledaService;
    @Autowired
    private DoctorService doctorService;
    @GetMapping(produces = "application/json", value = "/getAll")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TipPregledaDTO>> getAll() {
        System.out.println("usao u ispis");
        List<TipPregleda> listPregleda = tipPregledaService.findAll();

        ArrayList<TipPregledaDTO> listDTO = new ArrayList<>();
        for (TipPregleda p: listPregleda) {
            listDTO.add(new TipPregledaDTO(p));
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/save")
    public ResponseEntity<?> saveType(@RequestBody TipPregledaDTO typeDTO) {
        System.out.println("usao da doda");
        TipPregleda tip = tipPregledaService.save(typeDTO);
        if(tip == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(typeDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/delete/{name}")
    public ResponseEntity<?> deleteType(@PathVariable("name") String name) {
        System.out.println("usao da brise");
        TipPregleda tip = tipPregledaService.findOneByName(name);
        List<Doctor> doctorList = doctorService.findDoctorByType(tip);
        if (doctorList.size()!=0) {
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
        tipPregledaService.remove(tip.getId());
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/findName/{name}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByName(@PathVariable("name") String naziv) {
        TipPregleda tip = tipPregledaService.findOneByName(naziv);
        if(tip == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new TipPregledaDTO(tip), HttpStatus.OK);
    }
    @PostMapping(value = "/changeAttribute/{changed}/{value}/{name}")
    // @PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> changeAttribute(@PathVariable("changed") String changed,@PathVariable("value") String value,@PathVariable("name") String name) {
        TipPregleda tip = tipPregledaService.findOneByName(name);
        if(tip == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }else {
            if (changed.equals("ime")) {
                tip.setName(value);
                System.out.println(tip.getName());
            }
        }
        tipPregledaService.update(tip);
        return new ResponseEntity<>(new TipPregledaDTO(tip), HttpStatus.OK);
    }
}
