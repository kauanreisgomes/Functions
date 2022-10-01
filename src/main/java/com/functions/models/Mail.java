package com.functions.models;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


public class Mail{

    static Properties pro = new Properties();
    static Session session;

    public Mail(){
        pro.put("mail.smtp.host", "mailpro03.redehost.com.br");
        pro.put("mail.smtp.socketFactory.port", "587");
        pro.put("mail.smtp.socketFactory.class",
        "javax.net.ssl.SSLSocketFactory");
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.port", "587");
        
    
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        MailcapCommandMap mailcapCommandMap = new MailcapCommandMap(); 
        mailcapCommandMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed; x-java-fallback-entry=true"); 
        mailcapCommandMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
        mailcapCommandMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
        mailcapCommandMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
        mailcapCommandMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
        CommandMap.setDefaultCommandMap(mailcapCommandMap); 
        session = Session.getDefaultInstance(pro,
       
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                        
                            try {
                                return new PasswordAuthentication("no-reply@maissabor.com.br",
                                new String(Hex.decodeHex("4d4031732e7334623072")));
                            } catch (DecoderException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                System.out.println("Erro no PasswordAuthentication!\r\nClasse: Mail");
                                return null;
                            }
                    
                }
            });
       
    }

    
    public void sendSimpleMail(String subject,String texto,String emails) {
        //session.setDebug(true);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("no-reply@maissabor.com.br", "SSI - Suporte de Tecnologia da Informação"));

            Address[] toUser = InternetAddress //Destinatário(s)
            .parse(emails);
            
            msg.setRecipients(Message.RecipientType.TO,toUser);
            msg.setSubject(subject);
            msg.setText(texto);

            Transport.send(msg);
        }catch(UnsupportedEncodingException | MessagingException e){
    
        }
    }

    public void SendMessageHTML(String subject,String html,String emails){
        //session.setDebug(true);
        String htmlBody = html;         
       // byte[] attachmentData = null; 
        Multipart mp = new MimeMultipart();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        Message msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress("no-reply@maissabor.com.br", "SSI - Suporte de Tecnologia da Informação"));
            Address[] toUser = InternetAddress //Destinatário(s)
            .parse(emails);
            
            msg.setRecipients(Message.RecipientType.TO,toUser);
            msg.setSubject(subject);
        
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            mp.addBodyPart(htmlPart);
           
            /* MimeBodyPart attachment = new MimeBodyPart();
            InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
            attachment.setFileName("manual.pdf");
            attachment.setContent(attachmentDataStream, "application/pdf");
            mp.addBodyPart(attachment);*/

            msg.setContent(mp);
            Transport.send(msg);
        } catch (UnsupportedEncodingException | MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao mandar o E-mail!\r\nFunção: Mail.SendMessageHTML()");
        }
    }

}
