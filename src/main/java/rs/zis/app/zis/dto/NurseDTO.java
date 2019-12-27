package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.*;


public class NurseDTO {
    private Long id;
    private String mail;
    private String password;
    private String role;
    private boolean firstLogin;

    public NurseDTO() {
    }

    public NurseDTO(Long id, String mail, String password, String role, boolean firstLogin) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.firstLogin = firstLogin;
    }

    public NurseDTO(Nurse nurse) {
        this.id = nurse.getId();
        this.mail = nurse.getMail();
        this.password = nurse.getPassword();
        this.role = nurse.getRole();
        this.firstLogin = nurse.isFirstLogin();
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

    public boolean isFirstLogin() {
        return firstLogin;
    }
}
