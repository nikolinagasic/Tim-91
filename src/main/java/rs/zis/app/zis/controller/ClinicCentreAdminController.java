package rs.zis.app.zis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.*;
import rs.zis.app.zis.service.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping(value = "/ccadmin")
public class ClinicCentreAdminController extends WebConfig
{

    private Logger logger = LoggerFactory.getLogger(ClinicCentreAdminController.class);

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;
    @Autowired
    private ClinicCentreAdminService clinicCentreAdminService;
    @Autowired
    private ClinicService clinicservice;
    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired

    private MedicalRecordService medicalRecordService;
    private ClinicService clinicService;

    @PostMapping(consumes = "application/json" , value = "/register_admin")
    public ResponseEntity<Integer> saveClinicAdministrator(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO) {
        Users proveriMail = userService.findOneByMail(clinicAdministratorDTO.getMail());
        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }
        ClinicAdministrator clinicAdministrator = clinicAdministratorService.save(clinicAdministratorDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json" , value = "/register_ccadmin")
    public ResponseEntity<Integer> saveClinicCentreAdministrator(@RequestBody ClinicCentreAdminDTO clinicCentreAdminDTO) {
        Users proveriMail = userService.findOneByMail(clinicCentreAdminDTO.getMail());
        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        ClinicCentreAdmin clinicCentreAdmin = clinicCentreAdminService.save(clinicCentreAdminDTO);
        //posalji mejl


        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json" , value = "/register_clinic")
    public ResponseEntity<Integer> saveClinic(@RequestBody ClinicDTO clinicDTO){
        System.out.println("usao sam u post i primio: " + clinicDTO.getName());
        Clinic proveriName = clinicservice.findOneByName(clinicDTO.getName());
        if(proveriName != null)
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej

        Clinic clinic = clinicservice.save(clinicDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej


    }

    //trazim listu svih zahteva za registraciju
    @GetMapping(value = "/requests")
    public ResponseEntity<List<PatientDTO>> getAllRequest() {

        List<Users> users = userService.findAll();
        List<PatientDTO> request = new ArrayList<>();

        for(Users u:users){
            if((u.isEnabled())==false && u.isActive()){         //to znaci da pacijent nije registrovan
                PatientDTO pd = new PatientDTO(patientService.findOneById(u.getId()));
                request.add(pd);
            }
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    //odobravanje ili ne zahteva za registraciju
    // 'http://localhost:8081/ccadmin/accept/nesto@gmail.com/br/razlog'
    //br=1(zahtev odobren),br=2(zahtev odbijen)

    //razlog je neki string
    @GetMapping( value = "accept/{mail}/{br}/{reason}")
    public  ResponseEntity<Integer> acceptRequest(@PathVariable("mail") String mail, @PathVariable("br") Integer br,
                                                  @PathVariable("reason") String reason ){

             if(br==1){
             Users u= userService.findAllByMail(mail);
             if(u==null){
                 System.out.println("USER JE NULL");
             }else {
                 u.setEnabled(true); //sada je registrovan
                 userService.save(u);
                 //kada registrujem novog pacijenta otvaram mu odmah i karton
                 MedicalRecord medicalRecord = new MedicalRecord();
                 Patient p = patientService.findOneByMail(mail);
                 medicalRecord.setPatient(p);
                 medicalRecord.setBloodGroup("");
                 medicalRecord.setAllergy("");
                 medicalRecord = medicalRecordService.save(medicalRecord);

                 try {
                     String tb="Поштовани,\n\t" +
                                "Ваш захтев за регистрацију је прихваћен! Активирајте Ваш налог кликом на следећи линк: "+
                                "http://localhost:3000/#/login";
                     notificationService.SendNotification(mail, "billypiton43@gmail.com",
                             "PSW", tb);

                 } catch (MailException e) {
                     logger.info("Error Sending Mail:" + e.getMessage());
                     return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> nije okej
                 }
             }
         }else{
             Users u= userService.findOneByMail(mail);
             userService.deleteLogical(u.getId());       // brisem ga logicki
//             userService.remove(u.getId());    //brisem ga i iz liste usera
             try{
                 notificationService.SendNotification(mail, "billypiton43@gmail.com",
                         "PSW", reason);
             }catch (MailException e){
                 logger.info("Error Sending Mail:" + e.getMessage());
                 return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> nije okej
             }
         }


        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    //trazim sifarnik-listu svih dijagnoza i lekova
    @GetMapping(value = "/diagnosis")
    public ResponseEntity<List<DiagnosisDTO>> getAllDiagnosis() {

        List<DiagnosisDTO> diagnosisListDTO = new ArrayList<>();
        List<Diagnosis> diagnosisList = diagnosisService.findAll();
        for(Diagnosis d: diagnosisList){
            DiagnosisDTO dto=new DiagnosisDTO(d);
            diagnosisListDTO.add(dto);
        }

        return new ResponseEntity<>(diagnosisListDTO, HttpStatus.OK);
    }

    //filtriram listu svih dijagnoza koje imam u bazi na osnovu prosledjenog naziva
    @GetMapping(value="/diagnosisByName/{naziv}")
    public ResponseEntity<List<DiagnosisDTO>> getDiagnosisByName(@PathVariable("naziv") String naziv){
       System.out.println(naziv);
       List<DiagnosisDTO>diagnosisDTOList = diagnosisService.filterDiagnosis(naziv);
       return new ResponseEntity<>(diagnosisDTOList, HttpStatus.OK);
    }

    //na osnovu dobijenog naziva dijagnoze, na front vracam listu lekova
    @GetMapping(value="/curesByDiagnosis/{diagName}")
    public ResponseEntity<List<DiagnosisDTO>> getCuresByName(@PathVariable("diagName") String diagName){
        System.out.println(diagName);
        List<DiagnosisDTO>diagnosisDTOList = diagnosisService.filterCures(diagName);
        return new ResponseEntity<>(diagnosisDTOList, HttpStatus.OK);
    }


    //cuvam u bazi azuriran sifarnik koji je poslat sa fronta
    @PostMapping(consumes = "application/json" , value = "/savediagnosis")
    public ResponseEntity<Integer> saveDiagnosis(@RequestBody DiagnosisDTO d){
         Diagnosis diag = new Diagnosis();
         boolean origin= true; //ako je jedinstvena kombinacija

         List<Diagnosis>diagnosisList=diagnosisService.findAll();
         if(diagnosisList.size()>0) {
             for (Diagnosis diagnosis : diagnosisList) {
                 //provera da li je komb sifra diag-sifra leka jedinstvena
                 //provera da li su lekovi sa istom sifrom i istog naziva
                 //provera da li su lekovi sa istom sifrom i istog naziva
                 if (diagnosis.getDiagnosis_password().equals(d.getDiagnosis_password())) {
                     if (diagnosis.getCure_password().equals(d.getCure_password()))
                         origin = false;
                 }
                 if (diagnosis.getDiagnosis_password().equals(d.getDiagnosis_password())) {
                     if(diagnosis.getDiagnosis_name().equals(d.getDiagnosis_name())){

                     }else{
                         origin=false;
                     }

                 }
                 if(diagnosis.getCure_password().equals(d.getCure_password())){
                     if(diagnosis.getCure_name().equals(d.getCure_name())){

                     }else{
                         origin=false;
                     }

                 }

             }
             if (origin) {
                     diag.setCure_password(d.getCure_password());
                     diag.setCure_name(d.getCure_name());
                     diag.setDiagnosis_password(d.getDiagnosis_password());
                     diag.setDiagnosis_name(d.getDiagnosis_name());
                     diagnosisService.save(diag);
             }

         }else{
             diag.setCure_password(d.getCure_password());
             diag.setCure_name(d.getCure_name());
             diag.setDiagnosis_password(d.getDiagnosis_password());
             diag.setDiagnosis_name(d.getDiagnosis_name());
             diagnosisService.save(diag);
         }
         if(origin==false) {
             return new ResponseEntity<>(-2, HttpStatus.CONFLICT); //vec postoji u bazi ta kombinacija
         }
         else{
             return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
         }
    }




    //cuvam azuriran profil admina koji je poslat sa fronta
    @PostMapping(value = "/changeAttribute/{naziv}/{vrednost}/{mail}")
    public ResponseEntity<?> changeAttribute(@PathVariable("naziv") String naziv,
                                             @PathVariable("vrednost") String vrednost,
                                             @PathVariable("mail") String mail) {
        System.out.println("primio change: naziv{"+naziv+"}, vrednost{"+vrednost+"}, mail{"+mail+"}");
        ClinicCentreAdmin clinicCentreAdmin = clinicCentreAdminService.findOneByMail(mail);
        if (clinicCentreAdmin == null){
            return new ResponseEntity<>("greska", HttpStatus.CONFLICT);
        }

        if(naziv.equals("ime")){
           clinicCentreAdmin.setFirstName(vrednost);
        }
        else if(naziv.equals("prezime")) {
            clinicCentreAdmin.setLastName(vrednost);
        }
        clinicCentreAdminService.update(clinicCentreAdmin);
        ClinicCentreAdminDTO clinicCentreAdminDTO = new ClinicCentreAdminDTO(clinicCentreAdmin);
        return new ResponseEntity<>(clinicCentreAdminDTO, HttpStatus.OK);
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<ClinicCentreAdminDTO> getAdmin(@PathVariable Long id) {

        ClinicCentreAdmin admin = clinicCentreAdminService.findOne(id);

        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ClinicCentreAdminDTO(admin), HttpStatus.OK);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<List<ClinicCentreAdminDTO>> getAllCentreAdmin() {

        List<ClinicCentreAdmin> admins = clinicCentreAdminService.findAll();

        // convert admins to DTOs
        List<ClinicCentreAdminDTO> adminsDTO = new ArrayList<>();
        for (ClinicCentreAdmin s : admins) {
            adminsDTO.add(new ClinicCentreAdminDTO(s));
        }

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCCAdmin(@PathVariable Long id) {

        ClinicCentreAdmin admin = clinicCentreAdminService.findOne(id);

        if (admin != null) {
            clinicCentreAdminService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
