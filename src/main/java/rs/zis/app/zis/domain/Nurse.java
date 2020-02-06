package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Nurse")
public class Nurse extends Users {

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name= "role")
    private String role;

    @Column(name= "work_shift")     // smena
    private int workShift;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;                     // klinika u kojoj je zaposlen

    //formiram medjutabelu sestre-recepti->sistem pamti koja sestra je overila recept
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MedicalRecipe> medicalRecipes =  new HashSet<MedicalRecipe>();


 //   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 //   private Set<Godisnji_odmor> vacation;

    public Nurse() {
        this.role = "nurse";
      //  vacation = new HashSet<Godisnji_odmor>();
    }

    public Nurse(String mail, String password, String firstName, String lastName, int workShift, Clinic clinic, Set<Vacation> vacation,
                 Timestamp lastPasswordResetDate, List<Authority> authorities, boolean firstLogin, Set<MedicalRecipe> medicalRecipes) {
        super(mail, password,true, lastPasswordResetDate, authorities, firstLogin);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        this.role = "nurse";
        this.workShift = workShift;
        this.medicalRecipes = medicalRecipes;
      //  this.vacation = vacation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getWorkShift() {
        return workShift;
    }

    public void setWorkShift(int workShift) {
        this.workShift = workShift;
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

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

  /*  public Set<Godisnji_odmor> getVacation() {
        return vacation;
    }

    public void setVacation(Set<Godisnji_odmor> vacation) {
        this.vacation = vacation;
    }
*/

    public Set<MedicalRecipe> getMedicalRecipes() {
        return medicalRecipes;
    }

    public void addMedicalRecipe(MedicalRecipe medicalRecipe){
        this.medicalRecipes.add(medicalRecipe);
    }
}
