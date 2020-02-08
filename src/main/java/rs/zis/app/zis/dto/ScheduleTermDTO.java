package rs.zis.app.zis.dto;

public class ScheduleTermDTO {

    private Long id;
    private String naziv_pregleda;
    private long date;
    private String startTime;
    private String endTime;
    private String patient_mail;

    public ScheduleTermDTO() {
    }

    public ScheduleTermDTO(Long id, String naziv_pregleda, long date, String startTime, String endTime, String patient_mail) {
        this.id = id;
        this.naziv_pregleda = naziv_pregleda;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patient_mail = patient_mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv_pregleda() {
        return naziv_pregleda;
    }

    public void setNaziv_pregleda(String naziv_pregleda) {
        this.naziv_pregleda = naziv_pregleda;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPatient_mail() {
        return patient_mail;
    }

    public void setPatient_mail(String patient_mail) {
        this.patient_mail = patient_mail;
    }
}
