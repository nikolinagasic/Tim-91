package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Room;

import java.util.List;
@SuppressWarnings("SpellCheckingInspection")

public interface RoomRepository extends JpaRepository<Room,Long> {
    Page<Room> findAll(Pageable pageable);
    Room findOneById(Long id);
    Room findOneByName(String name);
    Room findOneByNumber(String number);
    List<Room> findRoomByClinic(Clinic clinic);
}
