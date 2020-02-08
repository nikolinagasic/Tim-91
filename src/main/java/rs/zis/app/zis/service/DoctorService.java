package rs.zis.app.zis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.controller.ClinicCentreAdminController;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused", "UnusedReturnValue", "RedundantIfStatement", "RedundantSuppression", "IfStatementMissingBreakInLoop"})
@Service
public class DoctorService {
    private Logger logger = LoggerFactory.getLogger(ClinicCentreAdminController.class);

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private ClinicService clinicService;
    @Autowired
    private TipPregledaService tipPregledaService;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Page<Doctor> findAll(Pageable page) {
        return doctorRepository.findAll(page);
    }

    public Doctor save(DoctorDTO doctorDTO) {
        Doctor d = new Doctor();
        d.setMail(doctorDTO.getMail());
        d.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        d.setClinic(clinicService.findOneByName(doctorDTO.getClinic()));
        d.setTip(tipPregledaService.findOneByName(doctorDTO.getTip()));
        d.setWorkShift(doctorDTO.getWorkShift());
        d.setEnabled(true);
        d.setActive(true);
        List<Authority> auth = authService.findByname("ROLE_DOCTOR");
        d.setAuthorities(auth);
        d.setSum_ratings(0);
        d.setNumber_rating(0);

        Clinic clinic = clinicService.findOneByName(doctorDTO.getClinic());
        clinic.getDoctors().add(d);
        clinicService.update(clinic);

//        d = this.doctorRepository.save(d);
        return d;
    }

    public void remove(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findOneByMail(String mail) {
        return doctorRepository.findOneByMail(mail);
    }

    public List<Doctor> findDoctorByLastName(String lastName) {
        return doctorRepository.findDoctorByLastName(lastName);
    }

    public List<Doctor> findDoctorByClinic(Clinic clinic) {
        return doctorRepository.findDoctorByClinic(clinic);
    }

    public Doctor update(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public boolean checkFirstLastName(String mail, String firstName, String lastName){
        Doctor doctor = doctorRepository.findOneByMail(mail);
        if(doctor != null){
            if(doctor.getFirstName().equals(firstName) && doctor.getLastName().equals(lastName)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public Doctor findOneById(Long id){return doctorRepository.findOneById(id); }

    public List<Doctor> findDoctorByType(TipPregleda tp){
        return doctorRepository.findAllByTip(tp);
    }

    public List<Doctor> findAllByClinic(Clinic c) {
        List<Doctor> svi = doctorRepository.findAllByClinic(c);
        List<Doctor> retVal = new ArrayList<>();
        for (Doctor d : svi) {
            if (d.isEnabled()) {
                retVal.add(d);
            }
        }
        return retVal;
    }

    public List<DoctorDTO> searchDoctors(List<DoctorDTO> lista_lekara, String ime, String prezime, double ocena) {
        List<DoctorDTO> retList = new ArrayList<>();
        for (DoctorDTO doctorDTO: lista_lekara) {
            if((doctorDTO.getFirstName() == null ? "" : doctorDTO.getFirstName())
                    .toLowerCase().contains(ime.toLowerCase())){
                if((doctorDTO.getLastName() == null ? "" : doctorDTO.getLastName())
                        .toLowerCase().contains(prezime.toLowerCase())){
                    if(ocena != -1) {
                        if (doctorDTO.getRating() == ocena) {
                            retList.add(doctorDTO);
                        }
                    }
                    else{
                        retList.add(doctorDTO);
                    }
                }
            }
        }

        return retList;
    }
    public List<DoctorDTO> findDoctor(List<DoctorDTO> lista_lekara, String ime, String prezime) {
        List<DoctorDTO> retList = new ArrayList<>();
        for (DoctorDTO doctorDTO: lista_lekara) {
            if(doctorDTO.getFirstName().toLowerCase().contains(ime.toLowerCase())){
                if(doctorDTO.getLastName().toLowerCase().contains(prezime.toLowerCase())){
                        retList.add(doctorDTO);
                }
            }
        }

        return retList;
    }
    public List<DoctorDTO> filterDoctorByType(List<DoctorDTO> lista_lekara, String tip) {
        List<DoctorDTO> retList = new ArrayList<>();
        for (DoctorDTO doctorDTO: lista_lekara) {
            if(doctorDTO.getTip().toLowerCase().equals(tip.toLowerCase())){

                    retList.add(doctorDTO);

            }
        }

        return retList;
    }

    public Doctor findDoctorByFirstNameAndLastName(String ime, String prezime) {
        return doctorRepository.findDoctorByFirstNameAndLastName(ime, prezime);
    }

    public List<DoctorDTO> filterDoctor(List<DoctorDTO> lista_lekara, String ocOd, String ocDo){
        List<DoctorDTO> retList = new ArrayList<>();
        double ocenaOd, ocenaDo;
        if(ocOd.equals("min")){
            ocenaOd = 0;
        }else{
            ocenaOd = Double.parseDouble(ocOd);
        }
        if(ocDo.equals("max")){
            ocenaDo = Double.MAX_VALUE;
        }else{
            ocenaDo = Double.parseDouble(ocDo);
        }

        for (DoctorDTO doctorDTO: lista_lekara) {
            if(doctorDTO.getRating() >= ocenaOd && doctorDTO.getRating() <= ocenaDo){
                retList.add(doctorDTO);
            }
        }

        return retList;
    }


    public List<DoctorDTO> expandedSearchDoctor(String ime, String prezime, double ocena, long datum, String tip,
                                                List<DoctorDTO> lista_lekara){
        List<DoctorDTO> retList = new ArrayList<>();
        if(ime.equals("~")){
            ime = "";
        }if(prezime.equals("~")){
            prezime = "";
        }if(tip.equals("Сви типови")){
            tip = "";
        }

        for (DoctorDTO doctorDTO : lista_lekara) {
            Doctor doctor = findOneById(doctorDTO.getId());
            if(doctor.getTip().getName().toLowerCase().contains(tip.toLowerCase())){
                if(doctor_free_at_date(doctor, datum) || datum == -1){
                    if((doctor.getSum_ratings()/doctor.getNumber_rating()) == ocena || ocena == -1){
                        if((doctor.getLastName() == null ? "" : doctor.getLastName())
                                .toLowerCase().contains(prezime.toLowerCase())){
                            if((doctor.getFirstName() == null ? "" : doctor.getFirstName())
                                    .toLowerCase().contains(ime.toLowerCase())){
                                retList.add(doctorDTO);
                            }
                        }
                    }
                }
            }
        }

        return retList;
    }

    public boolean doctor_free_at_date(Doctor doctor, long datum) {
        List<DoctorTerms> doctorTermsList = doctorTermsService.findAllByDoctor(doctor.getId());

        boolean godisnji = false;
        for (Vacation vacation : vacationService.findAllByDoctor(doctor)) {
            if(vacation.isActive() && vacation.isEnabled() && datum >= vacation.getPocetak() && datum <= vacation.getKraj()){
                godisnji = true;
            }
        }

        if(!godisnji) {
            int term_counter = 0;
            for (DoctorTerms doctorTerms : doctorTermsList) {
                if (doctorTerms.getDate() == datum) {
                    term_counter++;
                }
            }

            if (term_counter == 10) {
                return false;
            }
        }else{
            return false;
        }

        return true;
    }

    // vrati sve doktore kod kojih je ovaj pacijent bio, a da ih nije pre toga ocenio
    public List<DoctorDTO> getPatientHistoryDoctors(Patient patient) {
        List<Doctor> tmpList = new ArrayList<>();
        for (DoctorTerms doctorTerms : doctorTermsService.findAll()) {
            if(doctorTerms.isProcessedByAdmin() && doctorTerms.getPatient() != null){
                if(doctorTerms.getPatient().equals(patient) && !doctorTerms.isRate_doctor()){
                    if(!tmpList.contains(doctorTerms.getDoctor())){
                        tmpList.add(doctorTerms.getDoctor());
                    }
                }
            }
        }

        List<DoctorDTO> retList = new ArrayList<>();
        for (Doctor doctor : tmpList) {
            retList.add(new DoctorDTO(doctor));
        }

        return retList;
    }

    public boolean oceniDoktora(Doctor doctor_param, double ocena, Patient patient) {
        // TODOO uradi zakljucavanje na findById
        //  mora biti zakljucavanje (pesimistic) - zbog ucitavanja trenutne ocene i dodavanja na sum
        Doctor doctor;
        try {
            doctor = findOneById(doctor_param.getId());
        }catch (Exception e){
            return false;
        }
        if(doctor == null){
            return false;
        }

        doctor.setSum_ratings(doctor.getSum_ratings() + ocena);
        doctor.setNumber_rating(doctor.getNumber_rating() + 1);
        doctorRepository.save(doctor);

        for (DoctorTerms doctorTerms : doctorTermsService.findAll()) {
            if(doctorTerms.getDoctor().equals(doctor) && doctorTerms.getPatient() != null && !doctorTerms.isRate_doctor()) {
                if (doctorTerms.getPatient().equals(patient)) {
                    doctorTerms.setRate_doctor(true);
                    doctorTermsService.save(doctorTerms);
                }
            }
        }

        return true;

    }
}

