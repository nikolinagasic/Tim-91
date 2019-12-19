package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;

public class ClinicDTO {

    private long id;
    private String name;
    private double rating;         // ocena klinike
    private String address;
    private String description;
    private double price;       // cena pregleda

    public ClinicDTO() {

    }

    public ClinicDTO(long id, String name, String address, String description, double rating, double price) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
        this.price = price;
    }

    public ClinicDTO(Clinic c) {
        this.id = c.getId();
        this.name = c.getName();
        this.address = c.getAddress();
        this.description = c.getDescription();
        this.rating = c.getRating();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
