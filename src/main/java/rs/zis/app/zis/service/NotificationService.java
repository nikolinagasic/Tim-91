package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.dto.PatientDTO;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void SendNotification(PatientDTO patientDTO) throws MailException {
        System.out.println("OVDE SAM ");
        SimpleMailMessage mail= new SimpleMailMessage();
        //mail.setTo(patientDTO.getMail());
        mail.setTo("billypiton43@gmail.com");
        mail.setFrom("billypiton43@gmail.com");
        mail.setSubject("PSW");
        mail.setText("Usepsna registracija");
        System.out.println("OVDE SAM1 ");
        javaMailSender.send(mail);
        System.out.println("OVDE SAM 2");
    }

}
