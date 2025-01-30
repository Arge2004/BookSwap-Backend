package BookSwap.emailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendExchangeAcceptedNotification(String to, String name, String emailRequested) throws MessagingException {
        String subject = "Tu solicitud de intercambio ha sido aceptada";
        String content = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h2 style='color: #2c3e50;'>¡Buenas noticias!</h2>"
                + "<p>Tu solicitud de intercambio realizada a <strong>" + name + "</strong> ha sido aceptada.</p>"
                + "<p>Por favor, coordina con el otro usuario para finalizar el intercambio.</p>"
                + "<p><strong>Correo del usuario:</strong> <a href='mailto:" + emailRequested + "'>" + emailRequested + "</a></p>"
                + "<br><p>¡Gracias por usar BookSwap!</p></div>";

        sendHtmlEmail(to, subject, content);
    }

    public void sendExchangePendingNotification(String to, String requesterName, String bookTitle) throws MessagingException {
        String subject = "Tienes una solicitud de intercambio pendiente";
        String content = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h2 style='color: #e67e22;'>Tienes un intercambio pendiente</h2>"
                + "<p><strong>" + requesterName + "</strong> quiere intercambiar el libro <strong>'" + bookTitle + "'</strong> contigo.</p>"
                + "<p>Puedes <a href='https://bookswap.com/mis-intercambios'>aceptar o rechazar</a> la solicitud.</p>"
                + "<br><p>¡Gracias por usar BookSwap!</p></div>";

        sendHtmlEmail(to, subject, content);
    }

    public void sendExchangeRejectedNotification(String to, String bookTitle) throws MessagingException {
        String subject = "Tu solicitud de intercambio ha sido rechazada";
        String content = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h2 style='color: #c0392b;'>Solicitud rechazada</h2>"
                + "<p>Lamentamos informarte que tu solicitud de intercambio por el libro <strong>'" + bookTitle + "'</strong> ha sido rechazada.</p>"
                + "<p>Puedes buscar otro libro disponible en <a href='https://bookswap.com'>BookSwap</a>.</p>"
                + "<br><p>¡Gracias por usar BookSwap!</p></div>";

        sendHtmlEmail(to, subject, content);
    }

    private void sendHtmlEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // Activamos HTML

        mailSender.send(message);
    }
}
