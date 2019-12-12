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
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.ClinicCentreAdminDTO;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.dto.PatientDTO;
import rs.zis.app.zis.service.*;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
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

    @PostMapping(consumes = "application/json" , value = "/register_admin")
    public ResponseEntity<Integer> saveClinicAdministrator(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO) {
        User proveriMail = userService.findOneByMail(clinicAdministratorDTO.getMail());
        if(proveriMail != null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> mejl nije okej
        }

        ClinicAdministrator clinicAdministrator = clinicAdministratorService.save(clinicAdministratorDTO);
        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
    }

    @PostMapping(consumes = "application/json" , value = "/register_ccadmin")
    public ResponseEntity<Integer> saveClinicCentreAdministrator(@RequestBody ClinicCentreAdminDTO clinicCentreAdminDTO) {
        User proveriMail = userService.findOneByMail(clinicCentreAdminDTO.getMail());
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

        List<User> users = userService.findAll();
        List<PatientDTO> request = new ArrayList<>();

        for(User u:users){
            if((u.isEnabled())==false){//to znaci da pacijent nije registrovan
                PatientDTO pd = new PatientDTO(patientService.findOneById(u.getId()));
                request.add(pd);
            }
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    //odobravanje ili ne zahteva za registraciju
    // 'http://localhost:8081/ccadmin/accept/nesto@gmail.com/br'
    //br=1(zahtev odobren),br=2(zahtev odbijen)
    @GetMapping( value = "accept/{mail}/{br}")
    public  ResponseEntity<Integer> acceptRequest(@PathVariable("mail") String mail, @PathVariable("br") Integer br){
         if(br==1){
             User u= userService.findOneByMail(mail);
             if(u==null){
                 System.out.println("USER JE NULL");
             }else {
                 //System.out.println("PODACI:" + u.getId());
                 u.setEnabled(true); //sada je registrovan
                 userService.save(u);
                // System.out.println("sacuvao sam izasao");
                 try {
                     //System.out.println("usao sam u pisanje mejla ");
                     notificationService.SendNotification(mail, "Zahtev prihvacen");
                 } catch (MailException e) {
                     logger.info("Error Sending Mail:" + e.getMessage());
                     return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> nije okej
                 }
             }
         }else{
            // System.out.println("KLIKNUTO NA ODBIJ"+mail+br);
             User u= userService.findOneByMail(mail);
             userService.remove(u.getId());    //brisem ga i iz liste usera

             try{
                 //System.out.println("usao sam u pisanje mejla ");
                 notificationService.SendNotification(mail,"Zahtev odbijen!");
             }catch (MailException e){
                 logger.info("Error Sending Mail:" + e.getMessage());
                 return new ResponseEntity<>(-2, HttpStatus.CONFLICT);  // -2 -> nije okej
             }

         }


        return new ResponseEntity<>(0, HttpStatus.CREATED);     // 0 -> sve okej
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
