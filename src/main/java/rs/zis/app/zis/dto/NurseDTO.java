package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.*;


public class NurseDTO {
    private Long id;
    private String mail;
    private String password;
    private String role;

    public NurseDTO() {
    }

    public NurseDTO(Long id, String mail, String password, String role) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public NurseDTO(Nurse nurse) {
        this.id = nurse.getId();
        this.mail = nurse.getMail();
        this.password = nurse.getPassword();
        this.role = nurse.getRole();
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
