package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Room;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused", "MalformedFormatString", "CollectionAddAllCanBeReplacedWithConstructor", "UseBulkOperation", "UnusedAssignment"})
@Service
public class RoomService implements UserDetailsService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private ClinicService clinicService;

    public List<Room> findAll() {return roomRepository.findAll(); }

    public Room save(RoomDTO roomDTO) {
        Room c = new Room();
        c.setName(roomDTO.getName());
        c.setNumber(roomDTO.getNumber());
        Clinic clinic = clinicService.findOneByName(roomDTO.getClinic());
        c.setClinic(clinic);
        c.setActive(true);
        c = this.roomRepository.save(c);
        return c;
    }
    public Room update(Room room){
        return roomRepository.save(room);
    }

    public void remove(Long id) {
        roomRepository.deleteById(id);
    }

    public Room findOneByName(String name) {
        return roomRepository.findOneByName(name);
    }

    public Room findOneByNumber(String number) {
        return roomRepository.findOneByNumber(number);
    }

    public List<Room> findRoomByClinic(Clinic clinic) {

        List<Room> svi = roomRepository.findRoomByClinic(clinic);
        List<Room> retVal = new ArrayList<>();
        for (Room r : svi) {
            if (r.isActive()) {
                retVal.add(r);
            }
        }
        return retVal;
    }

    public Room findOneById(Long id) { return roomRepository.findOneById(id); }

    public List<RoomDTO> findRoom(List<RoomDTO> lista, String naziv, String broj) {
        List<RoomDTO> retList = new ArrayList<>();
        for (RoomDTO roomDTO: lista) {
            if(roomDTO.getName().toLowerCase().contains(naziv.toLowerCase())){
                if(roomDTO.getNumber().toLowerCase().contains(broj.toLowerCase())){
                    retList.add(roomDTO);
                }
            }
        }

        return retList;
    }

    public List<Room> getRoomsInClinic(String clinic_name){
        List<Room> retList = new ArrayList<>();

        for (Room room : clinicService.findOneByName(clinic_name).getRooms()) {
            if(room.isActive()) {
                retList.add(room);
            }
        }
        return retList;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
