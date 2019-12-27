package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


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
       /* String tb="\"<html>\\n\" +\n" +
                "                    \"<body>\\n\" +\n" +
                "                    \"\\n\" +\n" +
                "                    \"<a href=\\\"http://localhost:3000/#/login\\\">\\n\" +\n" +
                "                    \"This is a link</a>\\n\" +\n" +
                "                    \"\\n\" +\n" +
                "                    \"</body>\\n\" +\n" +
                "                    \"</html>\"";*/
        mail.setText(textBody);
        javaMailSender.send(mail);
    }

}
