package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.*;


public class DoctorDTO {
    private Long id;
    private String mail;
    private String password;
    private String role;

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String mail, String password, String role) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.mail = doctor.getMail();
        this.password = doctor.getPassword();
        this.role = doctor.getRole();
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
}
