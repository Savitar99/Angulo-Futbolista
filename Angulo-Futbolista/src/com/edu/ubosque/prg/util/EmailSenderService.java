package com.edu.ubosque.prg.util;
 
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
/**
 * @author datojava.blogspot.com
 */
public class EmailSenderService {
 
 public void mandarCorreo(String correo, String password,String userName) {
  // El correo gmail de envío
  String correoEnvia = "angulofutbolista1@gmail.com";
  String claveCorreo = "AnguloFutbolista123";
 
  // La configuración para enviar correo
  Properties properties = new Properties();
  properties.put("mail.smtp.host", "smtp.gmail.com");
  properties.put("mail.smtp.starttls.enable", "true");
  properties.put("mail.smtp.port", "587");
  properties.put("mail.smtp.auth", "true");
  properties.put("mail.user", correoEnvia);
  properties.put("mail.password", claveCorreo);
 
  // Obtener la sesion
  Session session = Session.getInstance(properties, null);
 
  try {
   // Crear el cuerpo del mensaje
   MimeMessage mimeMessage = new MimeMessage(session);
 
   // Agregar quien envía el correo
   mimeMessage.setFrom(new InternetAddress(correoEnvia, "Completa tu registro! Angulo Futbolista"));
    
   // Los destinatarios
   InternetAddress[] internetAddresses = {
     new InternetAddress(correo) };
 
   // Agregar los destinatarios al mensaje
   mimeMessage.setRecipients(Message.RecipientType.TO,
     internetAddresses);
 
   // Agregar el asunto al correo
   mimeMessage.setSubject("Angulo Futbolista.");
 
   // Creo la parte del mensaje
   MimeBodyPart mimeBodyPart = new MimeBodyPart();
   mimeBodyPart.setText("Bienvenid@ a la comunidad de Ángulo Futbolista\n" 
                         +"\nTus datos de ingreso son:\n"
		                 + "\nNombre de usuario: "+ userName+"\n"
		                 + "\nContraseña: "+password+"\n"
		                 +"\nGracias por elegirnos!.");
 
   // Crear el multipart para agregar la parte del mensaje anterior
   Multipart multipart = new MimeMultipart();
   multipart.addBodyPart(mimeBodyPart);
 
   // Agregar el multipart al cuerpo del mensaje
   mimeMessage.setContent(multipart);
 
   // Enviar el mensaje
   Transport transport = session.getTransport("smtp");
   transport.connect(correoEnvia, claveCorreo);
   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
   transport.close();
 
  } catch (Exception ex) {
   ex.printStackTrace();
  }
  System.out.println("Correo enviado");
 }
 
 public void mandarCorreoBloqueo(String correo, String userName) {
	  // El correo gmail de envío
	  String correoEnvia = "angulofutbolista1@gmail.com";
	  String claveCorreo = "AnguloFutbolista123";
	 
	  // La configuración para enviar correo
	  Properties properties = new Properties();
	  properties.put("mail.smtp.host", "smtp.gmail.com");
	  properties.put("mail.smtp.starttls.enable", "true");
	  properties.put("mail.smtp.port", "587");
	  properties.put("mail.smtp.auth", "true");
	  properties.put("mail.user", correoEnvia);
	  properties.put("mail.password", claveCorreo);
	 
	  // Obtener la sesion
	  Session session = Session.getInstance(properties, null);
	 
	  try {
	   // Crear el cuerpo del mensaje
	   MimeMessage mimeMessage = new MimeMessage(session);
	 
	   // Agregar quien envía el correo
	   mimeMessage.setFrom(new InternetAddress(correoEnvia, "Bloqueo de usuario! Angulo Futbolista"));
	    
	   // Los destinatarios
	   InternetAddress[] internetAddresses = {
	     new InternetAddress(correoEnvia) };
	 
	   // Agregar los destinatarios al mensaje
	   mimeMessage.setRecipients(Message.RecipientType.TO,
	     internetAddresses);
	 
	   // Agregar el asunto al correo
	   mimeMessage.setSubject("Angulo Futbolista.");
	 
	   // Creo la parte del mensaje
	   MimeBodyPart mimeBodyPart = new MimeBodyPart();
	   mimeBodyPart.setText("Un usuario ha sido bloqueado por maximo de intentos permitidos\n" 
			                 + "\nNombre de usuario: "+ userName+"\n"
			                 + "\nCorreo electrónico: "+correo+"\n");
	 
	   // Crear el multipart para agregar la parte del mensaje anterior
	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(mimeBodyPart);
	 
	   // Agregar el multipart al cuerpo del mensaje
	   mimeMessage.setContent(multipart);
	 
	   // Enviar el mensaje
	   Transport transport = session.getTransport("smtp");
	   transport.connect(correoEnvia, claveCorreo);
	   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	   transport.close();
	 
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  System.out.println("Correo enviado");
	 }
 
 
 public void mandarCorreoRestaurar(String correo, String password,String userName) {
	  // El correo gmail de envío
	  String correoEnvia = "angulofutbolista1@gmail.com";
	  String claveCorreo = "AnguloFutbolista123";
	 
	  // La configuración para enviar correo
	  Properties properties = new Properties();
	  properties.put("mail.smtp.host", "smtp.gmail.com");
	  properties.put("mail.smtp.starttls.enable", "true");
	  properties.put("mail.smtp.port", "587");
	  properties.put("mail.smtp.auth", "true");
	  properties.put("mail.user", correoEnvia);
	  properties.put("mail.password", claveCorreo);
	 
	  // Obtener la sesion
	  Session session = Session.getInstance(properties, null);
	 
	  try {
	   // Crear el cuerpo del mensaje
	   MimeMessage mimeMessage = new MimeMessage(session);
	 
	   // Agregar quien envía el correo
	   mimeMessage.setFrom(new InternetAddress(correoEnvia, "Restauración de contraseña! Angulo Futbolista"));
	    
	   // Los destinatarios
	   InternetAddress[] internetAddresses = {
	     new InternetAddress(correo) };
	 
	   // Agregar los destinatarios al mensaje
	   mimeMessage.setRecipients(Message.RecipientType.TO,
	     internetAddresses);
	 
	   // Agregar el asunto al correo
	   mimeMessage.setSubject("Angulo Futbolista.");
	 
	   // Creo la parte del mensaje
	   MimeBodyPart mimeBodyPart = new MimeBodyPart();
	   mimeBodyPart.setText("Clave para el proceso de restauración de contraseña\n" 
			                 + "\nClave: "+password+"\n"
			                 +"\nGracias por elegirnos!.");
	 
	   // Crear el multipart para agregar la parte del mensaje anterior
	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(mimeBodyPart);
	 
	   // Agregar el multipart al cuerpo del mensaje
	   mimeMessage.setContent(multipart);
	 
	   // Enviar el mensaje
	   Transport transport = session.getTransport("smtp");
	   transport.connect(correoEnvia, claveCorreo);
	   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	   transport.close();
	 
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  System.out.println("Correo enviado");
	 }
 
}