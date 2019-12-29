package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.domain.Patient;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

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
        d.setEnabled(true);
        List<Authority> auth = authService.findByname("ROLE_DOCTOR");
        d.setAuthorities(auth);

        d = this.doctorRepository.save(d);
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

    public List<Doctor> findAllByClinic(Clinic c) { return doctorRepository.findAllByClinic(c); }

    public List<DoctorDTO> searchDoctors(List<DoctorDTO> lista_lekara, String ime, String prezime, double ocena) {
        List<DoctorDTO> retList = new ArrayList<>();
        for (DoctorDTO doctorDTO: lista_lekara) {
            if(doctorDTO.getFirstName().toLowerCase().contains(ime.toLowerCase())){
                if(doctorDTO.getLastName().toLowerCase().contains(prezime.toLowerCase())){
                    if(doctorDTO.getRating() == ocena){
                        retList.add(doctorDTO);
                    }
                }
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

}

