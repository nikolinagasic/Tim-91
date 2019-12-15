package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.service.TipPregledaService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/type")
public class TipPregledaController extends WebConfig {

    @Autowired
    private TipPregledaService tipPregledaService;

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

    @GetMapping(produces = "application/json", value = "/findName/{name}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByName(@PathVariable("name") String naziv) {
        TipPregleda tip = tipPregledaService.findOneByName(naziv);
        if(tip == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new TipPregledaDTO(tip), HttpStatus.OK);
    }
}
