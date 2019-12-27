package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.*;


public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private double rating;
    private String mail;
    private String password;
    private String role;
    private boolean firstLogin;

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String firstName, String lastName,String mail, String password, String role, double rating,
                     boolean firstLogin) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.rating = rating;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstLogin = firstLogin;
    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.mail = doctor.getMail();
        this.password = doctor.getPassword();
        this.role = doctor.getRole();
        this.rating = doctor.getRating();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.firstLogin = doctor.isFirstLogin();
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
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

    public boolean isFirstLogin() {
        return firstLogin;
    }
}
