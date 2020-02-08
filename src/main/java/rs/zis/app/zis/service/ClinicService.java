package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.DoctorTermsDTO;
import rs.zis.app.zis.repository.ClinicRepository;

import javax.persistence.OptimisticLockException;
import java.util.*;

@SuppressWarnings({"SpellCheckingInspection", "unused", "MalformedFormatString", "CollectionAddAllCanBeReplacedWithConstructor", "UseBulkOperation", "UnusedAssignment", "DefaultAnnotationParam"})
@Service
public class ClinicService implements UserDetailsService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private TermDefinitionService termDefinitionService;

    @Autowired
    private TipPregledaService tipPregledaService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private NotificationService notificationService;

    public List<Clinic> findAll() {return clinicRepository.findAll(); }

    public Clinic save(ClinicDTO clinicDTO) {
        Clinic c = new Clinic();
        c.setName(clinicDTO.getName());
        c.setAddress(clinicDTO.getAddress());
        c.setDescription(clinicDTO.getDescription());

        c = this.clinicRepository.save(c);
        return c;
    }
    public Clinic update(Clinic clinic){
        return clinicRepository.save(clinic);
    }

    public Clinic findOneByName(String name) {
        return clinicRepository.findOneByName(name);
    }

    public Clinic findOneById(Long id) { return clinicRepository.findOneById(id); }

    // TODO test 3.13
    public List<ClinicDTO> searchClinic(long datum, String tip, double ocena){
        // 1) return: doktori koji su datog tipa
        List<Doctor> doctorsType;
        if(tip.equals("Сви типови")){
            doctorsType = doctorService.findAll();
        }
        else {
            TipPregleda tipPregleda = tipPregledaService.findOneByName(tip);
            doctorsType = doctorService.findDoctorByType(tipPregleda);
        }

        // 2) return: doktori koji imaju slobodnih termina u tom danu
        List<Doctor> slobodni_doktori = new ArrayList<>();
        for (Doctor d : doctorsType) {                      // za sve doktore koji su tog tipa (stomatolog, urolog, ...)
            List<DoctorTerms> doctorTerm = doctorTermsService.findAllByDoctor(d.getId());       // svi zauzeti termini doktora
            List<Vacation> vacationList = vacationService.findAllByDoctor(d);           // godisnji odmori tog doktora
            int counter_term = 0;

            boolean godisnji = false;
            for (Vacation vacation : vacationList) {
                if(vacation.isActive() && datum <= vacation.getKraj() && datum >= vacation.getPocetak()){      // proveri da li sam tada na godisnjem
                    godisnji = true;
                    break;
                }
            }

            if(!godisnji) {
                if (!doctorTerm.isEmpty()) {                             // ima zauzetih termina taj doca
                    for (DoctorTerms dt : doctorTerm) {                 // iteriraj kroz sve njegove zauzete termine
                        if (dt.getDate() == datum) {            // proveri da li medju njima ima nekog za taj moj datum
                            counter_term++;
                        }
                    }
                    // nisu svi termini zauzeti (imam 10 termina za svaku smenu)
                    if (counter_term < 10) {
                        slobodni_doktori.add(d);
                        counter_term = 0;
                    }
                } else {                                       // nema zauzetih
                    slobodni_doktori.add(d);                // odmah ga upisi u listu
                }
            }
        }

        // 3) return: klinike u kojima rade slobodni doktori
        Set<ClinicDTO> retSet = new HashSet<>();
        for (Doctor d : slobodni_doktori) {
            Long id = d.getClinic().getId();
            Clinic clinic = findOneById(id);
            double prosecna_ocena = izracunaj_ocenu(clinic);
            if(prosecna_ocena == ocena || ocena == -1){
                ClinicDTO clinicDTO = new ClinicDTO(clinic);
                clinicDTO.setPrice(d.getPrice());                   // uzmi cenu doktora

                // prolazim kroz sve dodate, da bih stavio cenu najjeftinijeg doktora
                boolean flag = false;           // flag da li ova cena nije najniza za tu kliniku
                for (ClinicDTO cDTO : retSet) {
                    if(cDTO.getId() == clinicDTO.getId()){
                        if(cDTO.getPrice() < clinicDTO.getPrice()){     // imam vec nizu cenu za tu kliniku
                            flag = true;                        // flag = true -> nemoj dodavati ovu kliniku sa visom cenom (clinicDTO)
                        }
                    }
                }

                if(!flag){
                    retSet.add(clinicDTO);       // dodaj u listu klinika
                }
            }
        }

        List<ClinicDTO> retList = new ArrayList<>();
        retList.addAll(retSet);

        return  retList;
    }

    private double izracunaj_ocenu(Clinic clinic) {
        if(clinic.getNumber_ratings() != 0){
            return clinic.getSum_ratings() / clinic.getNumber_ratings();
        }
        else if(clinic.getNumber_ratings() == 0){
            return 0;
        }
        else{
            return -1;
        }
    }

    // TODO test 3.13
    public List<Doctor> findDoctorsByClinic(String clinic_name, Long date){
        List<Doctor> retList = new ArrayList<>();
        Clinic clinic = findOneByName(clinic_name);
        if(clinic != null) {
            for (Doctor doctor: doctorService.findAllByClinic(clinic)) {
                List<DoctorTerms> doctorTermsList = doctorTermsService.findAllByDoctor(doctor.getId());
                List<Vacation> vacationList = vacationService.findAllByDoctor(doctor);

                boolean godisnji = false;
                for (Vacation vacation : vacationList) {
                    if(vacation.getDoctor().getId().equals(doctor.getId())) {
                        if (date >= vacation.getPocetak() && date <= vacation.getKraj()) {
                            godisnji = true;
                            break;
                        }
                    }
                }

                if(!godisnji) {
                    int term_counter = 0;
                    for (DoctorTerms doctorTerms : doctorTermsList) {
                        if (doctorTerms.getDate() == date) {
                            term_counter++;
                        }
                    }

                    if (term_counter < 10) {
                        retList.add(doctor);
                    }
                }
            }
        }

        return retList;
    }

    // TODO test 3.13
    public List<ClinicDTO> filterClinic(String cOd, String cDo, String ocOd, String ocDo, String naziv, List<ClinicDTO> listaKlinika){
        double cenaOd, cenaDo, ocenaOd, ocenaDo;
        if(cOd.equals("min")){
            cenaOd = 0;
        }else{
            cenaOd = Double.parseDouble(cOd);
        }
        if(cDo.equals("max")){
            cenaDo = Double.MAX_VALUE;
        }else{
            cenaDo = Double.parseDouble(cDo);
        }
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
        if(naziv.equals("~")){
            naziv = "";
        }
        List<ClinicDTO> retList = new ArrayList<>();
        double prosecna_ocena_klinike;
        for (ClinicDTO clinicDTO : listaKlinika) {
            Clinic clinic = findOneById(clinicDTO.getId());
            prosecna_ocena_klinike = izracunaj_ocenu(clinic);
            if(clinicDTO.getPrice() >= cenaOd && clinicDTO.getPrice() <= cenaDo){
                if(prosecna_ocena_klinike >= ocenaOd && prosecna_ocena_klinike <= ocenaDo){
                    if(clinicDTO.getName().toLowerCase().contains(naziv.toLowerCase())){
                        retList.add(clinicDTO);
                    }
                }
            }
        }

        return retList;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Clinic c = clinicRepository.findOneByName(name);
        if (c == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%name'.", name));
        } else {
            return (UserDetails) c;
        }
    }

    @Transactional(readOnly = false)
    public List<DoctorTermsDTO> getPredefinedTerms(long clinic_id) {
        List<DoctorTermsDTO> retList = new ArrayList<>();
        Clinic clinic = clinicRepository.findOneById(clinic_id);
        if(clinic == null){
            return retList;
        }

        for (DoctorTerms doctorTerm : doctorTermsService.findAll()) {
            for (Doctor doctor : clinic.getDoctors()) {
                if(doctorTerm.getDoctor().getId().equals(doctor.getId())){       // isti doktor => to je ta klinika
                    if(doctorTerm.isPredefined() && doctorTerm.isActive()){
                        retList.add(new DoctorTermsDTO(doctorTerm));
                    }
                }
            }
        }

        return retList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean reservePredefinedTerm(Long id_term, Patient patient) {
        DoctorTerms doctorTerms = doctorTermsService.findOneById(id_term);
        try {
            if (doctorTerms != null) {
                if (doctorTerms.isActive() && doctorTerms.isPredefined()) {
                    doctorTerms.setPatient(patient);
                    doctorTerms.setActive(false);
                    String textBody = "Поштовани "+patient.getFirstName()+ " " + patient.getLastName() + "," +
                            "\n\nУспешно сте заказали термин у клиници "+ doctorTerms.getDoctor().getClinic().getName() +
                            ", код доктора др "+ doctorTerms.getDoctor().getFirstName() + " " + doctorTerms.getDoctor().getLastName() + ".";
                    notificationService.SendNotification(patient.getMail(), "billypiton43@gmail.com",
                            "Успешно заказан преглед", textBody);
                    doctorTermsService.save(doctorTerms);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (OptimisticLockException e){
            System.out.println("Optimistic lock exception je okinut (klasa: " + e.getClass() + " )");
            return false;
        }

        return true;
    }

    public List<ClinicDTO> getPatientHistoryClinics(Patient patient) {
        List<Clinic> tmpList = new ArrayList<>();
        for (DoctorTerms doctorTerms : doctorTermsService.findAll()) {
            if(doctorTerms.isProcessedByAdmin() && doctorTerms.getPatient() != null){
                if(doctorTerms.getPatient().equals(patient) && !doctorTerms.isRate_clinic()){
                    if(!tmpList.contains(doctorTerms.getDoctor().getClinic())){
                        tmpList.add(doctorTerms.getDoctor().getClinic());
                    }
                }
            }
        }

        List<ClinicDTO> retList = new ArrayList<>();
        for (Clinic clinic : tmpList) {
            retList.add(new ClinicDTO(clinic));
        }

        return retList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean oceniKliniku(Clinic clinic_param, double ocena, Patient patient) {
        // uradjeno zakljucavanje na findById
        // mora biti zakljucavanje (optimistic) - zbog ucitavanja trenutne ocene i dodavanja na sum
        Clinic clinic;
        try {
            clinic = findOneById(clinic_param.getId());
        }catch (Exception e){
            return false;
        }
        if(clinic == null){
            return false;
        }

        clinic.setSum_ratings(clinic.getSum_ratings() + ocena);
        clinic.setNumber_ratings(clinic.getNumber_ratings() + 1);
        clinicRepository.save(clinic);
        List<DoctorTerms> list = doctorTermsService.findAll();
        for (DoctorTerms doctorTerms : doctorTermsService.findAll()) {
            if(doctorTerms.getDoctor().getClinic().equals(clinic) && doctorTerms.getPatient() != null
                    && !doctorTerms.isRate_clinic()){
                if(doctorTerms.getPatient().equals(patient)) {
                    doctorTerms.setRate_clinic(true);
                    doctorTermsService.save(doctorTerms);
                }
            }
        }

        return true;
    }

    public List<ClinicDTO> sortClinicByName(List<ClinicDTO> lista_klinika, String order) {
        ArrayList<String> lista_naziva = new ArrayList<>();
        for (ClinicDTO clinicDTO : lista_klinika) {
            lista_naziva.add(clinicDTO.getName());
        }
        java.util.Collections.sort(lista_naziva);
        ArrayList<ClinicDTO> retList = new ArrayList<>();
        if(order.equals("d")) {         // descending
            java.util.Collections.reverse(lista_naziva);
        }

        for (String naz : lista_naziva) {
            Clinic clinic = findOneByName(naz);
            ClinicDTO clinicDTO = new ClinicDTO(clinic);
            retList.add(clinicDTO);
        }

        return retList;
    }

    public Object sortClinicByAddress(List<ClinicDTO> lista_klinika, String order) {
        ArrayList<String> lista_adresa = new ArrayList<>();
        for (ClinicDTO clinicDTO : lista_klinika) {
            lista_adresa.add(clinicDTO.getAddress());
        }
        java.util.Collections.sort(lista_adresa);
        ArrayList<ClinicDTO> retList = new ArrayList<>();
        if(order.equals("d")) {         // descending
            java.util.Collections.reverse(lista_adresa);
        }

        for (String adresa : lista_adresa) {
            for (ClinicDTO clinicDTO : lista_klinika) {
                if(clinicDTO.getAddress().equals(adresa) && !retList.contains(clinicDTO)){
                    retList.add(clinicDTO);
                }
            }
        }

        return retList;
    }
}
