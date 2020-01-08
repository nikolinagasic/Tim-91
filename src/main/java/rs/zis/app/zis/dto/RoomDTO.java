package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Room;

public class RoomDTO {
    private long id;
    private String name;
    private String number;
    private String clinic;


    public RoomDTO() {

    }

    public RoomDTO(long id, String name, String number, String clinic) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.clinic = clinic;
    }

    public RoomDTO(Room c) {
        this.id = c.getId();
        this.name = c.getName();
        this.number = c.getNumber();
        this.clinic = c.getClinic().getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getClinic() {
        return clinic;
    }
}
