package rs.zis.app.zis.domain;

import javax.persistence.*;

@Entity
@Table(name = "ClinicCentreAdmin")
public class ClinicCentreAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name= "predefined", nullable = true)
    private boolean predefined;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    public ClinicCentreAdmin() {

    }

    public ClinicCentreAdmin(Long id, String mail, String password, boolean predefined, String firstName, String lastName) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.predefined = predefined;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPredefined() {
        return predefined;
    }

    public void setPredefined(boolean predefined) {
        this.predefined = predefined;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
