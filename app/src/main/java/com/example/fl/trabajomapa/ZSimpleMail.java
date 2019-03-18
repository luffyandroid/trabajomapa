package com.example.fl.trabajomapa;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class ZSimpleMail {
    /*private String publicKey="";
    private String privateKey="";
    private byte[] encodeData = null;*/

    /**CHANGE ACCORDINGLY**/

    /////////////////////////////////

    //PARA GMAIL ¡¡¡OJO, HAY QUE ACTIVAR LOS PROTOCOLOS DE SEGURIDAD EN LA CUENTA DE GMAIL!!!
    //private static final String SMTP_HOST_NAME = "smtp.gmail.com"; //can be your host server smtp@yourdomain.com

    //PARA HOTMAIL
    private static final String SMTP_HOST_NAME = "smtp-mail.outlook.com"; //can be your host server smtp@yourdomain.com

    //PARA SERVICIODECORREO
    //private static final String SMTP_HOST_NAME = "smtp.serviciodecorreo.es"; //can be your host server smtp@yourdomain.com

    /////////////////////////////////

    //PONER EMAIL
    private static final String SMTP_AUTH_USER = "nomegustaapp@outlook.es"; //your login username/email

    //PONER CONTRASEÑA DEL EMAIL
    private static final String SMTP_AUTH_PWD  = "LuffyFull1734"; //password/secret

    private static Message message;


    public static void sendEmail(String to, String subject, String msg){
        // Recipient's email ID needs to be mentioned.

        // PONER EL EMAIL QUE ENVIA
        String from = "nomegustaapp@outlook.es"; //from

        final String username = SMTP_AUTH_USER;
        final String password = SMTP_AUTH_PWD;

        // Assuming you are sending email through relay.jangosmtp.net
        String host = SMTP_HOST_NAME;

        Properties props = new Properties();

        //PROTOCOLO PARA HOTMAIL
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        /////////////////////////////////

        //PARA PROTOCOLOS SSL (EL DE DIDACT ES serviciodecorreo)
        //props.put("mail.smtp.ssl.enable","true");
        //PARA PROTOCOLOS TTL (GMAIL Y HOTMAIL)
        props.put("mail.smtp.starttls.enable","true");

        /////////////////////////////////

        //PUERTOS
        //PARA GMAIL Y HOTMAIL
        props.put("mail.smtp.port", "587");
        //PARA EL serviciodecorreo (DIDACT)
        //props.put("mail.smtp.port", "465");

        /////////////////////////////////

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setContent(msg, "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

//            // Part two is attachment
//            messageBodyPart = new MimeBodyPart();
//            String filename = Context.;
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(filename);
//            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        // Send message in another thread
                        Transport.send(message);
                        System.out.println("Mensaje enviado satisfactoríamente....");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
