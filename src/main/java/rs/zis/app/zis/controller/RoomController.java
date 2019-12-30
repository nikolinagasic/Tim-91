package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Room;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.service.ClinicService;
import rs.zis.app.zis.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping("/room")
public class RoomController extends WebConfig {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ClinicService clinicService;

    @GetMapping(produces = "application/json", value = "/getByClinic/{clinic}")
    public ResponseEntity<?> getRoomsInClinic(@PathVariable("clinic") String clinic){
        Clinic c = clinicService.findOneByName(clinic);
        List<Room> rooms = roomService.findRoomByClinic(c);

        ArrayList<RoomDTO> roomsDTO = new ArrayList<>();
        for (Room r: rooms) {
            roomsDTO.add(new RoomDTO(r));
        }
        return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
    }
    @PostMapping(consumes = "application/json", value = "/save")
    public ResponseEntity<?> saveRoom(@RequestBody RoomDTO roomDTO) {
        System.out.println("usao da doda");
        Room room = roomService.save(roomDTO);
        if(room == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
    }
    @PostMapping(value = "/changeAttribute/{changed}/{value}/{name}")
    // @PreAuthorize("hasRole('CADMIN')")
    public ResponseEntity<?> changeAttribute(@PathVariable("changed") String changed,@PathVariable("value") String value,@PathVariable("name") String name) {
        Room room = roomService.findOneByName(name);
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
    @PostMapping(value = "/delete/{name}")
    public ResponseEntity<?> deleteType(@PathVariable("name") String name) {
        System.out.println("usao da brise");
        Room room = roomService.findOneByName(name);
        //ubaciti proveru za preglede
        roomService.remove(room.getId());
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
