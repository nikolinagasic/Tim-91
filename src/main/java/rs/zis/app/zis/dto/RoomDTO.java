package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Room;

public class RoomDTO {
    private long id;
    private String name;
    private String number;
    private String clinic;
    private long first_free_date;

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
    public long getFirst_free_date() {
        return first_free_date;
    }

    public void setFirst_free_date(long first_free_date) {
        this.first_free_date = first_free_date;
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
