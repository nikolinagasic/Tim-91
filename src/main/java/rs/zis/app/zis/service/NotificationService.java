package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.dto.PatientDTO;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void SendNotification(String to, String from, String subject, String textBody) throws MailException {
        SimpleMailMessage mail= new SimpleMailMessage();
        //mail.setTo(patientDTO.getMail());
        mail.setTo(to);
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.setText(textBody);
        javaMailSender.send(mail);
    }

}
