package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.service.ClinicService;
import rs.zis.app.zis.service.DoctorTermsService;
import rs.zis.app.zis.service.RoomService;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping("/room")
public class RoomController extends WebConfig {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DoctorTermsService doctorTermsService;

    @GetMapping(produces = "application/json", value = "/getByClinic/{clinic}")
    public ResponseEntity<?> getRoomsInClinic(@PathVariable("clinic") String clinic){
        Clinic c = clinicService.findOneByName(clinic);
        List<Room> listRoom = roomService.findRoomByClinic(c);

        ArrayList<RoomDTO> roomsDTO = new ArrayList<>();
        for (Room r: listRoom) {
            if (r.isEnabled()) {
                roomsDTO.add(new RoomDTO(r));
            }
        }
        System.out.println(roomsDTO);
        return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
    }
    @PostMapping(consumes = "application/json", value = "/save")
    public ResponseEntity<?> saveRoom(@RequestBody RoomDTO roomDTO) {
        System.out.println("usao da doda");
        Room room = roomService.save(roomDTO);
        System.out.println(room.getNumber());
        if (room == null) {
            return new ResponseEntity<>("postoji", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
    }
    @PostMapping(value = "/changeAttribute/{changed}/{value}/{number}")
    // @PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> changeAttribute(@PathVariable("changed") String changed,@PathVariable("value") String value,@PathVariable("number") String number) {
        Room room = roomService.findOneByNumber(number);
        if(room == null)
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        if (changed.equals("naziv")) {
            room.setName(value);
            System.out.println(room.getName());
        } else if (changed.equals("broj")) {
            room.setNumber(value);
            System.out.println(room.getNumber());
        }
        roomService.update(room);
        return new ResponseEntity<>(new RoomDTO(room), HttpStatus.OK);
    }
    @PostMapping(produces = "application/json", value = "/delete/{number}/{clinic}")
    public ResponseEntity<?> delete(@PathVariable("number") String number,@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        List<Room> rooms = roomService.findRoomByClinic(c);
        for (Room r : rooms) {
            if (r.getNumber().equals(number)) {
                r.setEnabled(false);
                roomService.update(r);
            }
        }

        List<Room> lista = roomService.findRoomByClinic(c);
        List<RoomDTO> listaDTO = new ArrayList<>();
        for (Room r: lista) {
            if (r.isEnabled()) {
                listaDTO.add(new RoomDTO(r));
            }
        }

        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }
    @PostMapping(produces = "application/json", consumes = "application/json", value = "/find/{naziv}/{broj}/{date}")
    public ResponseEntity<?> findRoom(@RequestBody List<RoomDTO> lista, @PathVariable("naziv") String naziv,
                                        @PathVariable("broj") String broj,@PathVariable("date") String date) {
        if(naziv.equals("~")){
            naziv = "";
        }if(broj.equals("~")){
            broj = "";
        }if (date.equals("-1")) {
            date = "";
        }
        List<RoomDTO> listaDTO = roomService.findRoom(lista, naziv, broj,date);
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }
    @PostMapping(produces = "application/json", value = "/reserveRoom/{id}/{idr}/{date}")
    public ResponseEntity<?> reserveRoom(@PathVariable("id") Long id,@PathVariable("idr") Long idr,@PathVariable("date") long date) {
        DoctorTerms term = doctorTermsService.findOneById(id);

        int uspeo = doctorTermsService.reserveRoom(id,idr,date);
        if (uspeo == 1) {
            return new ResponseEntity<>(0, HttpStatus.CONFLICT);
        } else if (uspeo == 2) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        DoctorTerms t = doctorTermsService.findOneById(id);
        System.out.println("procesuiran:"+t.isProcessedByAdmin());
        Room room = roomService.findOneById(idr);
        room.addDoctorTerms(t);
        roomService.update(room);
        return new ResponseEntity<>(0, HttpStatus.OK);
    }


}
