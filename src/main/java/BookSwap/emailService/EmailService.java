package BookSwap.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendExchangeNotification(String to, String name, String emailRequested) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Tu solicitud de intercambio ha sido aceptada");
        message.setText("¡Buenas noticias! Tu solicitud de intercambio realizada a '" + name + "' ha sido aceptada. Coordina con el otro usuario para finalizar el intercambio." +
                "\n\n" + "Correo del usuario al que solicitaste el/los libro(s): " + emailRequested);
        mailSender.send(message);
    }
}
