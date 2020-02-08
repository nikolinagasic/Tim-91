package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.Vacation;

import java.io.Serializable;

public class VacationDTO {
    private Long id;
    private long pocetak;
    private long kraj;
    private String firstName;
    private String lastName;

    public VacationDTO(Long id, long pocetak, long kraj, String firstName, String lastName) {
        this.id = id;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public VacationDTO(Vacation vacation) {
        this.id = vacation.getId();
        this.pocetak = vacation.getPocetak();
        this.kraj = vacation.getKraj();
        if(vacation.getDoctor_nurse().equals("doctor")){
            this.firstName = vacation.getDoctor().getFirstName();
            this.lastName = vacation.getDoctor().getLastName();
        }
        else{
            this.firstName = vacation.getNurse().getFirstName();
            this.lastName = vacation.getNurse().getLastName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPocetak(long pocetak) {
        this.pocetak = pocetak;
    }

    public void setKraj(long kraj) {
        this.kraj = kraj;
    }

    public long getPocetak() {
        return pocetak;
    }

    public long getKraj() {
        return kraj;
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
