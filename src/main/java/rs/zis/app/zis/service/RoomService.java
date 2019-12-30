package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Room;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.repository.ClinicRepository;
import rs.zis.app.zis.repository.RoomRepository;

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
        c.setClinic(roomDTO.getClinic());

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
        return roomRepository.findRoomByClinic(clinic);
    }

    public Room findOneById(Long id) { return roomRepository.findOneById(id); }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
