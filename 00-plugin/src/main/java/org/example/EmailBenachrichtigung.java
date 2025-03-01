package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailBenachrichtigung {

    final private static String from = "your_gmail_address@gmail.com";
    final private static String password = "your_app_password";

    public static void sendeEmail(String to, String subject, String body) {
        Properties props = konfiguriereEmailProperties();
        Session session = erstelleEmailSession(props);
        Message message = erstelleEmailNachricht(session, to, subject, body);
        versendeEmail(message);
    }

    private static Properties konfiguriereEmailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return props;
    }

    private static Session erstelleEmailSession(Properties props) {
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        return session;
    }

    private static Message erstelleEmailNachricht(Session session, String to, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            return message;

        } catch (MessagingException e) {
            System.err.println("Fehler beim Senden der E-Mail: " + e.getMessage());
            return null;
        }

    }

    private static void versendeEmail(Message message) {
        try{
            Transport.send(message);
            System.out.println("E-Mail erfolgreich gesendet!");
        }catch (MessagingException e){
            System.err.println("Fehler beim Senden der E-Mail: " + e.getMessage());
        }
    }


    public static void main(String args) {
        sendeEmail("recipient@example.com", "Test Email", "This is a test email from my Java code.");
    }
}