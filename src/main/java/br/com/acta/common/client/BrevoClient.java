package br.com.acta.common.client;

import br.com.acta.common.handler.exception.BusinessRuleException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Component
public class BrevoClient {
    @Value("${BREVO_SMTP_USERNAME:}")
    private String smtpUsername;

    @Value("${BREVO_SMTP_PASSWORD:}")
    private String smtpPassword;

    @Value("${BREVO_SENDER_EMAIL:}")
    private String senderEmail;

    public void enviarConvite(String email, String nome, OffsetDateTime expiraEm, String token) {
        try {
            JavaMailSenderImpl sender = criarSender();
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            String html = carregarTemplate()
                    .replace("{{nome}}", HtmlUtils.htmlEscape(nome))
                    .replace("{{expiraEm}}", expiraEm.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")))
                    .replace("{{codigo}}", HtmlUtils.htmlEscape(token));

            helper.setFrom(senderEmail, "ACTA");
            helper.setTo(email);
            helper.setSubject("Convite para acessar o ACTA");
            helper.setText(html, true);
            helper.addInline("acta-logo", new ClassPathResource("templates/email/logo.png"), "image/png");

            sender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BusinessRuleException("Não foi possível enviar o convite por e-mail");
        }
    }

    private JavaMailSenderImpl criarSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        // configurações do servidor da brevo
        sender.setHost("smtp-relay.brevo.com");
        sender.setPort(587);
        sender.setUsername(smtpUsername);
        sender.setPassword(smtpPassword);

        Properties properties = sender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true"); // exige auth com e-mail e senha
        properties.put("mail.smtp.starttls.enable", "true"); // criptografia tls
        return sender;
    }

    private String carregarTemplate() {
        try (InputStream input = new ClassPathResource("templates/email/convite-usuario.html").getInputStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            throw new BusinessRuleException("Não foi possível carregar o e-mail de convite");
        }
    }

}