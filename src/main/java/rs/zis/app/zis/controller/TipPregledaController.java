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
    public ResponseEntity<List<TipPregledaDTO>> getAll() {
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

    @PostMapping(produces = "application/json",value = "/delete/{name}")
    public ResponseEntity<?> deleteType(@PathVariable("name") String name) {
        TipPregleda tip = tipPregledaService.findOneByName(name);
        tip.setActive(false);
        tipPregledaService.update(tip);
        List<TipPregleda> tipovi = tipPregledaService.findAll();
        List<TipPregledaDTO> tipoviDTO = new ArrayList<>();
        for (TipPregleda t: tipovi) {
            tipoviDTO.add(new TipPregledaDTO(t));
        }
        return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/search/{naziv}")
    // @PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> findByName(@PathVariable("naziv") String naziv) {
        if(naziv.equals("~")){
            naziv = "";
        }
        List<TipPregledaDTO> listaTipovaDTO = tipPregledaService.search(naziv);
        return new ResponseEntity<>(listaTipovaDTO, HttpStatus.OK);
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
