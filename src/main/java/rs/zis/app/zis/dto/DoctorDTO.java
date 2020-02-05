package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.*;


public class DoctorDTO {
    private Long id;//
    private String firstName;//
    private String lastName;//
    private double rating;//
    private String mail;//
    private String password;//
    private  String clinic;//
    private String tip;//
    private String role;//
    private boolean firstLogin;//
    private int workShift;//
    private int discount;//

    public DoctorDTO() {
    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.mail = doctor.getMail();
        this.password = doctor.getPassword();
        this.role = doctor.getRole();
        if(doctor.getNumber_rating() != 0){
            this.rating = doctor.getSum_ratings() / doctor.getNumber_rating();
        }
        else{
            this.rating = 0;
        }
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.firstLogin = doctor.isFirstLogin();
        this.workShift = doctor.getWorkShift();
        this.discount = doctor.getDiscount();
        this.clinic = doctor.getClinic().getName();
        this.tip = doctor.getTip().getName();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getRating() {
        return rating;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getClinic() {
        return clinic;
    }

    public String getTip() {
        return tip;
    }

    public String getRole() {
        return role;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public int getWorkShift() {
        return workShift;
    }

    public int getDiscount() {
        return discount;
    }
}
