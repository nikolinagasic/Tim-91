package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;

public class ClinicDTO {

    private long id;
    private String name;
    private double rating;         // ocena klinike
    private String address;
    private String location;
    private String description;
    private double price;       // cena pregleda

    public ClinicDTO() {

    }

    public ClinicDTO(long id, String name, String address, String description, double rating, double price,
                             String location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.location = location;
    }

    public ClinicDTO(Clinic clinic) {
        this.id = clinic.getId();
        this.name = clinic.getName();
        this.address = clinic.getAddress();
        this.description = clinic.getDescription();
        this.rating = clinic.getRating();
        this.location = clinic.getLocation();
        double suma = 0;
        for (Doctor doctor : clinic.getDoctors()) {
            suma += doctor.getPrice();
        }
        this.price = suma / clinic.getDoctors().size();             // prosecna cena svih doktora koji rade u klinici
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
