package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;

public class ClinicDTO {

    private long id;
    private String name;
    private String address;
    private String description;

    public ClinicDTO() {

    }

    public ClinicDTO(long id, String name, String address, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public ClinicDTO(Clinic c) {
        this.id = c.getId();
        this.name = c.getName();
        this.address = c.getAddress();
        this.description = c.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
