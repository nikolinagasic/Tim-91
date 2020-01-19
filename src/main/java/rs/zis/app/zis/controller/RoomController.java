package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Room;
import rs.zis.app.zis.dto.RoomDTO;
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
        List<Room> listRoom = roomService.findRoomByClinic(c);

        ArrayList<RoomDTO> roomsDTO = new ArrayList<>();
        for (Room r: listRoom) {
            roomsDTO.add(new RoomDTO(r));
        }
        System.out.println(roomsDTO);
        return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
    }
    @PostMapping(consumes = "application/json", value = "/save")
    public ResponseEntity<?> saveRoom(@RequestBody RoomDTO roomDTO) {
        System.out.println("usao da doda");
        Clinic clinic = clinicService.findOneByName(roomDTO.getClinic());
        List<Room> sobe = roomService.findRoomByClinic(clinic);
        for (Room r: sobe) {
            if (r.getName().equals(roomDTO.getName())) {
                    return new ResponseEntity<>("postoji", HttpStatus.CONFLICT);
                }

        }
        Room room = roomService.save(roomDTO);
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
    @PostMapping(produces = "application/json", value = "/delete/{name}/{clinic}")
    public ResponseEntity<?> delete(@PathVariable("name") String name,@PathVariable("clinic") String clinic) {
        Clinic c = clinicService.findOneByName(clinic);
        Room room = roomService.findOneByName(name);
        room.setActive(false);
        roomService.update(room);
        List<Room> lista = roomService.findRoomByClinic(c);
        List<RoomDTO> listaDTO = new ArrayList<>();
        for (Room r: lista) {
            listaDTO.add(new RoomDTO(r));
        }

        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }
    @PostMapping(produces = "application/json", consumes = "application/json", value = "/find/{naziv}/{broj}")
    public ResponseEntity<?> findDoctor(@RequestBody List<RoomDTO> lista, @PathVariable("naziv") String naziv,
                                        @PathVariable("broj") String broj) {
        if(naziv.equals("~")){
            naziv = "";
        }if(broj.equals("~")){
            broj = "";
        }
        List<RoomDTO> listaDTO = roomService.findRoom(lista, naziv, broj);
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }
}
