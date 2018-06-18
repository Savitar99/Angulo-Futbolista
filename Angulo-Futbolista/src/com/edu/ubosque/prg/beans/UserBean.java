package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.GoalScorerDAO;
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.ParameterDAO;
import com.edu.ubosque.prg.dao.RefereeDAO;
import com.edu.ubosque.prg.dao.StadiumDAO;
import com.edu.ubosque.prg.dao.TeamDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.GoalScorerDAOImpl;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.dao.impl.ParameterDAOImpl;
import com.edu.ubosque.prg.dao.impl.RefereeDAOImpl;
import com.edu.ubosque.prg.dao.impl.StadiumDAOImpl;
import com.edu.ubosque.prg.dao.impl.TeamDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.GoalScorer;
import com.edu.ubosque.prg.entity.New;
import com.edu.ubosque.prg.entity.Parameter;
import com.edu.ubosque.prg.entity.Referee;
import com.edu.ubosque.prg.entity.Stadium;
import com.edu.ubosque.prg.entity.Team;
import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.util.EmailSenderService;
import com.edu.ubosque.prg.util.TextManager;
import com.edu.ubosque.prg.util.UtilMD5;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.primefaces.component.password.Password;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable
{
	
	public static final String GMAIL = "gmail.com";
	public static final String OUTLOOK = "outlook.com";
	public static final String YAHOO = "yahoo.com";
	public static final String HOTMAIL = "hotmail.com";
	public static final String BOSQUE = "unbosque.edu.co";
	public static final String ADMINMAIL = "angulofutbolista1@gmail.com";

	
	private static final Logger logger = Logger.getLogger(UserBean.class);

	private String loginUsuario,password,error,idUsuarioAutenticado,confirmarPassword;
	private String errorUsuarioCorriente,loginBuscar;
	User usuarioBuscado;
	private String noEncontrado,errorIndex;

	
	private int intentos = 0;
	private int intentosAdministrador = 0;
	
	
	
	private User usuario;
	private DataModel listaUsuarios;
	private Audit auditoria;
	
	
	private Parameter parameter;
	private DataModel listaParameter;
	
	
	
	private String userNameLogin;
	private String passwordLogin;
	
	private boolean autenticado = false;
	
	
	 private static SecureRandom random = new SecureRandom();
	
	
	//Prueba
	
	EntityManager em;
	
	UserDAO userDao;

	
	
	public UserBean()
	{
		usuario = new User();
		auditoria = new Audit();
		parameter = new Parameter();
		
		
		
		usuario.setActive("A");
		usuario.setPhoneNumber("N.A");
		usuario.setFullName("N.A");
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		usuario.setUserType("OPERATIVO");
		usuario.setNumber(0);
		usuario.setFavorite("N.A");
	
	}
	
	
	public String prepararAdicionarUsuario() {
		usuario = new User();
		usuario.setActive("A");
		loginUsuario = null;
		password= null;
		return "/user/index?faces-redirect=true";
	}
	
	
	 public String contraseniaAleatoria(int len) {
		    char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
		        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		        'w', 'x', 'y', 'z' };
		    
		    char[] c=new char[len];
		    Random random=new Random();
		    for (int i = 0; i < len; i++) {
		      c[i]=ch[random.nextInt(ch.length)];
		    }
		    
		    return new String(c);
		  }
	 
	 
	public String adicionarUsuario() throws UnknownHostException {
		
		
		
		UserDAO dao = new UserDAOImpl();
		 
	
		String password = contraseniaAleatoria(5);
	
		   
	        
		usuario.setPassword(password);
		
		String passwordUser = usuario.getPassword();
		String passwordMD5 = UtilMD5.getStringMessageDigest(passwordUser, UtilMD5.MD5);
		String miIp = InetAddress.getLocalHost().getHostAddress();
		
		usuario.setIp(miIp);
		usuario.setPhoneNumber("N.A");
		usuario.setFullName("N.A");
		usuario.setNumber(0);
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		usuario.setFavorite("N.A");

		usuario.setPassword(passwordMD5);
		
		dao.save(usuario);

		EmailSenderService correo = new EmailSenderService();
		correo.mandarCorreo(usuario.getEmailAddress(),password,usuario.getUserName());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		logger.info("Se agregó un usuario");


		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("Se registró en el sistema");
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuario.getId());
		auditoria.setUserName(usuario.getUserName());
		
		auditDao.save(auditoria);
		
		logger.info("Se actualizó la auditoria");
		
		//Paramteros
		ParameterDAO paramDao = new ParameterDAOImpl();
		parameter.setParameterType("Expiración de contraseña");
		parameter.setParameterCode("E.C");
		parameter.setNumberValue(30);
		parameter.setDescriptionParameter("La contraseña del usuario "+usuario.getUserName()+" expirará en "+parameter.getNumberValue()+" días");
		parameter.setTextValue("Contraseña");
		
		paramDao.save(parameter);
		
		logger.info("Se actualizó un parametro");
				
		return "/user/registrado.xhtml?faces-redirect=true";
	}
	
	
	public String adicionarUsuarioLogin() throws UnknownHostException {
		UserDAO dao = new UserDAOImpl();
		String passwordUser = usuario.getPassword();
		String passwordMD5 = UtilMD5.getStringMessageDigest(passwordUser, UtilMD5.MD5);
		String miIp = InetAddress.getLocalHost().getHostAddress();

		usuario.setIp(miIp);
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		EmailSenderService correo = new EmailSenderService();
		correo.mandarCorreo(usuario.getEmailAddress(),usuario.getPassword(),usuario.getUserName());
		usuario.setPassword(passwordMD5);
		dao.save(usuario);

		//Auditoria
				AuditDAO auditDao = new AuditDAOImpl();
				
				auditoria.setCreateDate(new GregorianCalendar().getTime());
				auditoria.setOperation("Se registró en el sistema");
				auditoria.setTableId((long) 1);
				auditoria.setTableName("Usuario");
				auditoria.setUserId(usuario.getId());
				auditoria.setUserName(usuario.getUserName());
				
				auditDao.save(auditoria);

		return "login?faces-redirect=true";
	}
	
	
	
	
	
	public String cambiarPassword() throws UnknownHostException
	{
        UserDAO dao = new UserDAOImpl();
		
		String miIp = InetAddress.getLocalHost().getHostAddress();
		usuario.setIp(miIp);
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		String password = usuario.getPassword();
		
		
		dao.update(usuario);
		
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
								
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation(usuario.getUserName()+" Ha modificado su contraseña");
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuario.getId());
		auditoria.setUserName(usuario.getUserName());
								
		auditDao.save(auditoria);
				
		logger.info("Un usuario modificó su contraseña");
		
		
		return "registrado.xhtml?faces-redirect=true";
		
		
		
	}
	
	public String cambiarPasswordRestore() throws UnknownHostException
	{
		
        UserDAO dao = new UserDAOImpl();
        
        User usuarioLogin;
        User usuario2;
		
		usuarioLogin = dao.buscarUsuarioRepetidoCorreo(usuario.getEmailAddress());
		usuario2 = dao.buscarUsuarioLogin(usuario.getUserName());
		
		
		if(usuarioLogin != null)
		{
			
		
		
		String miIp = InetAddress.getLocalHost().getHostAddress();
		usuarioLogin.setIp(miIp);
		usuarioLogin.setDateLastPassword(Calendar.getInstance().getTime());
		String password = usuarioLogin.getPassword();
		usuarioLogin.setActive("A");
		usuarioLogin.setNumber(1);
		
		
		dao.update(usuarioLogin);
		
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
								
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation(usuarioLogin.getUserName()+" Ha modificado su contraseña");
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuarioLogin.getId());
		auditoria.setUserName(usuarioLogin.getUserName());
								
		auditDao.save(auditoria);
		
		logger.info("Un usuario modificó su contraseña");
		}
		
		else
		{
			
			String miIp = InetAddress.getLocalHost().getHostAddress();
			usuario2.setIp(miIp);
			usuario2.setDateLastPassword(Calendar.getInstance().getTime());
			String password = usuario2.getPassword();
			
			
			
			dao.update(usuario2);
			
			
			//Auditoria
			AuditDAO auditDao = new AuditDAOImpl();
									
			auditoria.setCreateDate(new GregorianCalendar().getTime());
			auditoria.setOperation(usuario2.getUserName()+" Ha modificado su contraseña");
			auditoria.setTableId((long) 1);
			auditoria.setTableName("Usuario");
			auditoria.setUserId(usuario2.getId());
			auditoria.setUserName(usuario2.getUserName());
									
			auditDao.save(auditoria);
			
			logger.info("Un usuario modificó su contraseña");
		}
				
		
		
		return "contrasenaRestaurada.xhtml?faces-redirect=true";
		
		
		
	}
	
	public String cambiarPasswordRestore2() throws UnknownHostException
	{
		
        UserDAO dao = new UserDAOImpl();
        
        User usuarioLogin;
        User usuario2;
        int num = 0;
		
		usuarioLogin = dao.buscarUsuarioRepetidoCorreo(usuario.getEmailAddress());
		usuario2 = dao.buscarUsuarioLogin(usuario.getUserName());
		
		
		if(usuarioLogin != null)
		{
			
		
		
		String miIp = InetAddress.getLocalHost().getHostAddress();
		usuarioLogin.setIp(miIp);
		usuarioLogin.setDateLastPassword(Calendar.getInstance().getTime());
		String password = usuarioLogin.getPassword();
		num++;
		usuarioLogin.setNumber(num);
		
		
		
		dao.update(usuarioLogin);
		
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
								
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation(usuarioLogin.getUserName()+" Ha modificado su contraseña");
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuarioLogin.getId());
		auditoria.setUserName(usuarioLogin.getUserName());
								
		auditDao.save(auditoria);
		logger.info("Un usuario modificó su contraseña");
		}
		
		else
		{
			
			String miIp = InetAddress.getLocalHost().getHostAddress();
			usuario2.setIp(miIp);
			usuario2.setDateLastPassword(Calendar.getInstance().getTime());
			String password = usuario2.getPassword();
			num++;
			usuario2.setNumber(num);

			
			
			
			dao.update(usuario2);
			
			
			//Auditoria
			AuditDAO auditDao = new AuditDAOImpl();
									
			auditoria.setCreateDate(new GregorianCalendar().getTime());
			auditoria.setOperation(usuario2.getUserName()+" Ha modificado su contraseña");
			auditoria.setTableId((long) 1);
			auditoria.setTableName("Usuario");
			auditoria.setUserId(usuario2.getId());
			auditoria.setUserName(usuario2.getUserName());
									
			auditDao.save(auditoria);
			logger.info("Un usuario modificó su contraseña");
			
		}
				
		
		
		return "index-inicio.xhtml?faces-redirect=true";
		
		
		
	}
	
	
	
//	public String iniciar()
//	{
//		UserDAO dao = new UserDAOImpl();
//	
//		
//		User usuarioLogin = new User();	
//        dao.iniciarSesion(usuarioLogin);
//        
//        
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("User", usuarioLogin);
//		return "index-inicio.xhtml?faces-redirect=true";
//        		
//	}
	
	
	public String cambiarPerfil()
	{
		UserDAO dao = new UserDAOImpl();
		
	    User usuarioLogin;
	    User usuario2;
		
		usuarioLogin = dao.iniciarSesion(usuario);
		usuario2 = dao.buscarUsuarioLogin(usuario.getUserName());
		
		if(usuarioLogin != null)
		{
			
			usuarioLogin.setEmailAddress(usuario.getEmailAddress());
			usuarioLogin.setFavorite(usuario.getFavorite());
			usuarioLogin.setFullName(usuario.getFullName());
			
			dao.update(usuarioLogin);
			logger.info("Un usuario modificó su perfil");
			return "index-inicio.xhtml?faces-redirect=true";
		}
		else
		{
			usuario2.setEmailAddress(usuario.getEmailAddress());
			usuario2.setFavorite(usuario.getFavorite());
			usuario2.setFullName(usuario.getFullName());
			
			dao.update(usuario2);
			logger.info("Un usuario modificó su perfil");
			return "index-inicio.xhtml?faces-redirect=true";
		}
	}
	
	
	
	public boolean isAutenticado() {
		return autenticado;
	}


	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}


	public String iniciar() throws UnknownHostException
	{
		UserDAO dao = new UserDAOImpl();
		String passwordUser = usuario.getPassword();
		String passwordMD5 = UtilMD5.getStringMessageDigest(passwordUser, UtilMD5.MD5);
		usuario.setPassword(passwordMD5);
		
		User usuarioLogin;
		
		usuarioLogin = dao.iniciarSesion(usuario);
		
		
		if(usuarioLogin != null)
		{
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("User", usuarioLogin);
			String miIp = InetAddress.getLocalHost().getHostAddress();
			usuarioLogin.setIp(miIp);
			
			Date fecha1 = usuarioLogin.getDateLastPassword();
		
			
			Date fecha2 = Calendar.getInstance().getTime();
			fecha2.setDate(fecha2.getDate()-31);
		
			
			usuarioLogin.setDateLastPassword(fecha1);
			
			
			
		if(usuarioLogin.getActive().equals("I"))
		{
			return "usuarioBloqueado.xhtml?faces-redirect=true";

		}
			
		if(fecha2.getDay() == usuarioLogin.getDateLastPassword().getDay())
		{
			return "cambioPassword.xhtml?faces-redirect=true";

		}
		
			
			
			if(usuarioLogin.getNumber() == 0)
			{
				return "cambioPassword.xhtml?faces-redirect=true";

			}
			
			if(usuarioLogin.getActive().equals("I"))
			{
				return "usuarioBloqueado.xhtml?faces-redirect=true";
			}
			
			dao.update(usuarioLogin);
			
			//Auditoria
			AuditDAO auditDao = new AuditDAOImpl();
			
			auditoria.setCreateDate(new GregorianCalendar().getTime());
			auditoria.setOperation(usuarioLogin.getUserName()+" inició sesión"+" IP: "+usuarioLogin.getIp());
			auditoria.setTableId((long) 1);
			auditoria.setTableName("Usuario");
			auditoria.setUserId(usuarioLogin.getId());
			auditoria.setUserName(usuarioLogin.getUserName());
									
			auditDao.save(auditoria);
			
			if(usuarioLogin.getUserType().equals("OPERATIVO"))
			{
				logger.info("Un usuario inició sesión como OPERATIVO");

				return "index-inicio.xhtml?faces-redirect=true";
				
			}
			
			else if(usuarioLogin.getUserType().equals("FUNCIONAL"))
			{
				logger.info("Un usuario inició sesión como FUNCIONAL");

				return "/funcional/index-funcional.xhtml?faces-redirect=true";

			}
			
			else if(usuarioLogin.getUserType().equals("ADMIN"))
			{
				logger.info("Un usuario inició sesión como ADMINISTRADOR");

				return "/administrador/index-administrador.xhtml?faces-redirect=true";

			}
			
			
			
		}
		
		else
		{
			intentos++;
			
		}
		
		
		 if(intentos == 3)
		{
			 
			User newUser;
			
			newUser = dao.buscarUsuarioLogin(usuario.getUserName());
			
			if(newUser.getUserType().equals("ADMIN"))
			{
				
				return "login.xhtml?faces-redirect=true";

			}
			else
			{
				
			
			newUser.setActive("I");
			newUser.setNumber(0);
			dao.update(newUser);
			
			
			
			//Auditoria
			AuditDAO auditDao = new AuditDAOImpl();
			
			auditoria.setCreateDate(new GregorianCalendar().getTime());
			auditoria.setOperation("Se bloqueó la cuenta de "+newUser.getUserName());
			auditoria.setTableId((long) 1);
			auditoria.setTableName("Usuario");
			auditoria.setUserId(newUser.getId());
			auditoria.setUserName(newUser.getUserName());
									
			auditDao.save(auditoria);
			
			EmailSenderService correo = new EmailSenderService();
			correo.mandarCorreoBloqueo(newUser.getEmailAddress(), newUser.getUserName());
			
			logger.info("Se bloqueó un usuario");

			
			return "usuarioBloqueado.xhtml?faces-redirect=true";
			
			
		}
		}
		 	 
		return null;
		
		
          		
	}
	
	
	public void verificarSesion() throws IOException
	{
		
		    FacesContext context = FacesContext.getCurrentInstance();
			User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
			
			if(u == null)
			{
				context.getExternalContext().redirect("/user/index.xhtml");
			}
	}
	
	
	
	
	
	public String cerrar()
	{
		
		TextManager text = new TextManager();
		
		if(text.getText().equals("Cerrar sesión"))
		{
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Un usuario cerró su sesión");

		
		return "/user/index.xhtml?faces-redirect=true";
		}
		
		else
		{
			return "/user/sesion.xhtml?faces-redirect=true";
		}
		
	}
	
	public String redirigir()
	{
		return "index.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	
	
	
	
	public void validateBloquear(FacesContext context, UIComponent component, Object value)
	{
		String userName = value.toString();
		UserDAO dao = new UserDAOImpl();
		User login = dao.buscarUsuarioLogin(userName);
		boolean aceptar = false;
		

		
		if(login != null)
		{
			aceptar = true;
			usuario.setUserName(login.getUserName());
			usuario.setPassword(login.getPassword());
			usuario.setEmailAddress(login.getEmailAddress());
			usuario.setFavorite(usuario.getFavorite());
			usuario.setUserType(usuario.getUserType());
		}
		
		else
		{
			throw new ValidatorException(new FacesMessage("Usuario no existe"));
		}
	}
	
	public void validatePasswordBloquear(FacesContext context, UIComponent component, Object value)
	{
		boolean aceptar = false;

		String password = value.toString();
		
		
		String passwordMD5 = UtilMD5.getStringMessageDigest(password, UtilMD5.MD5);
		usuario.setPassword(passwordMD5);
			
		if(usuario.getPassword().equals(passwordMD5))
		{
			aceptar = true;
		}
		
		else
		{
			intentos++;
			throw new ValidatorException(new FacesMessage("Contraseña incorrecta"));


		}
		
		
	}
	
	public void  validateMailExistente(FacesContext context, UIComponent component, Object value)
	{
		boolean aceptar = false;

		String passwordDigitado = value.toString();
		
		UserDAO dao = new UserDAOImpl();
		User userNew = dao.buscarUsuarioRepetidoCorreo(passwordDigitado);
		
		if(userNew != null)
		{
			aceptar = true;
		}
		
		else
		{
			throw new ValidatorException(new FacesMessage("Correo electronico no registrado"));

		}
	}
	
	
	
	
	public String restaurarPassword()
	{
		
		UserDAO dao = new UserDAOImpl();
		User usuarioLogin;
		
		usuarioLogin = dao.buscarUsuarioRepetidoCorreo(usuario.getEmailAddress());
		
		if(usuarioLogin != null)
		{
			
			String password = contraseniaAleatoria(5);
			usuarioLogin.setPassword(password);
			dao.update(usuarioLogin);
			
			EmailSenderService correo = new EmailSenderService();
			correo.mandarCorreoRestaurar(usuarioLogin.getEmailAddress(), usuarioLogin.getPassword(), usuarioLogin.getUserName());
			return "passwordRestore.xhtml?faces-redirect=true";
		}
		return null;
	}
	
	public String restorePassword()
	{
		return "newPassword.xhtml?faces-redirect=true";
		
	}
	
	public void validatePasswordR(FacesContext context, UIComponent component, Object value)
	{
		UserDAO dao = new UserDAOImpl();
		User usuarioLogin;
		
		usuarioLogin = dao.buscarUsuarioRepetidoCorreo(usuario.getEmailAddress());
		
		
		String correoDigitado = value.toString();
		boolean pasar = false;
		
		if(usuarioLogin != null)
	    {
		
		  if(usuarioLogin.getPassword().equals(correoDigitado))
		  {
			pasar = true;
		  }
		
		  else
		  {
			throw new ValidatorException(new FacesMessage("Contraseña incorrecta"));
		  }
		
	    }
		
		else
		{
			if(usuario.getPassword().equals(correoDigitado))
			{
				pasar = true;
			}
			
			else
			{
				throw new ValidatorException(new FacesMessage("Contraseña incorrecta"));

			}
		}
		
}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	//------------------------------------------------------------------------------
	
	/**
	 * Administrador
	 */
	//------------------------------------------------------------------------------
	
	public String prepararAdicionarUsuarioLogin() throws UnknownHostException 
	{
		usuario = new User();
		usuario.setActive("A");
		 
		String miIp = InetAddress.getLocalHost().getHostAddress();
			
		usuario.setIp(miIp);
		usuario.setPhoneNumber("N.A");
		usuario.setFullName("N.A");
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		usuario.setUserType("Corriente");
		usuario.setPassword("Hola");
		
		
	
		
		loginUsuario = null;
		password = null;
		return "/administrador/registrarseLogin?faces-redirect=true";
	}
	
	
	
	

	public String prepararModificarUsuario() {
		usuario = (User) (listaUsuarios.getRowData());
		return "/administrador/usuarioRegistrado?faces-redirect=true";
	}

	public String bloquearUsuario() throws UnknownHostException {
		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOImpl();
		
        String miIp = InetAddress.getLocalHost().getHostAddress();
		
		usuario.setIp(miIp);
		usuarioTemp.setActive("I");
		usuario.setPhoneNumber("N.A");
		usuario.setFullName("N.A");
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		usuario.setUserType("Corriente");
		usuario.setPassword("Hola");
		
		dao.update(usuarioTemp);
		logger.info("El administrador dbloqueó un usuario");
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El Administrador bloqueó a "+usuarioTemp.getUserName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuarioTemp.getId());
		auditoria.setUserName(usuarioTemp.getUserName());
				
		auditDao.save(auditoria);
		
		
		
		return "/administrador/index-administrador.xhtml?faces-redirect=true";
	}
	
	public String desbloquearUsuario() throws UnknownHostException {
		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOImpl();
		
        String miIp = InetAddress.getLocalHost().getHostAddress();
		
		usuario.setIp(miIp);
		usuarioTemp.setActive("A");
		usuario.setPhoneNumber("N.A");
		usuario.setFullName("N.A");
		usuario.setDateLastPassword(Calendar.getInstance().getTime());
		usuario.setUserType("Corriente");
		
		dao.update(usuarioTemp);
		logger.info("El administrador desbloqueó un usuario");

		
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
						
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El Administrador desbloqueó a "+usuarioTemp.getUserName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuarioTemp.getId());
		auditoria.setUserName(usuarioTemp.getUserName());
						
		auditDao.save(auditoria);
		
		
		
		
		return "/administrador/index-administrador.xhtml?faces-redirect=true";
	}

	
	
	
	
	

	public String modificarUsuario() throws UnknownHostException {
		UserDAO dao = new UserDAOImpl();
		
		String miIp = InetAddress.getLocalHost().getHostAddress();
		usuario.setIp(miIp);
		

		dao.update(usuario);
		logger.info("El administrador modificó un usuario");
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El Administrador modificó el registro de "+usuario.getUserName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId(usuario.getId());
		auditoria.setUserName(usuario.getUserName());
				
		auditDao.save(auditoria);

		return "/administrador/index-administrador.xhtml?faces-redirect=true";
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public DataModel getListarUsuarios() {
		List<User> lista = new UserDAOImpl().list();
		listaUsuarios = new ListDataModel(lista);
		return listaUsuarios;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
	
	
	
	//-------------------------------------------------------------------------------------
	/**
	 * Validaciones 
	 */
	//-------------------------------------------------------------------------------------
	
	public String getError() throws UnknownHostException {
		
		if(loginUsuario == null && password == null)
		{
			error ="";
		}
		else if(intentosAdministrador == 4)
		{
			error = "Usuario bloqueado por máximo de intentos";
			intentosAdministrador = 0;
		}
		else if(login().equals("login?faces-redirect=true"))
		{
			User usuarioLogin = new UserDAOImpl().buscarUsuarioRepetidoLogin(loginUsuario);
			if(usuarioLogin != null)
			{
				String estado = usuarioLogin.getActive().trim();
				if(estado.equals("I"))
				{
					error = "Usuario bloqueado";

				}
				else
				{
					error = "Usuario incorrecto";
				}
			}
			else
			{
				error = "Usuario incorrecto";
			}
		}
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		String telefono = value.toString();

		try
		{
			Integer.parseInt(telefono);
		} catch (NumberFormatException e) 
		{
			throw new ValidatorException(new FacesMessage("debes ingresar valores númericos"));
		}
	}
	
	
	public void validateCorreo(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		String correoDigitado = value.toString();

		boolean encontrado = false;
		
		UserDAO dao = new UserDAOImpl();
		User mailLogin = dao.buscarUsuarioRepetidoCorreo(correoDigitado);
		
		if(mailLogin != null)
		{
			
			throw new ValidatorException(new FacesMessage("Este correo ya esta registrado"));

		}

		for (int i = 0; i < correoDigitado.length() && !encontrado; i++)
		{
			char letra = correoDigitado.charAt(i);

			if(letra == '@')
			{

				if(i == correoDigitado.length() - 1)
				{
					encontrado = false;
				}
				else
				{
					String[] descomposicionCorreo = correoDigitado.split("@");

					if(descomposicionCorreo[1] != null)
					{
						if(descomposicionCorreo[1].equals(GMAIL))
						{
							encontrado = true;
						}
						else if(descomposicionCorreo[1].equals(OUTLOOK))
						{
							encontrado = true;
						}
						else if(descomposicionCorreo[1].equals(YAHOO))
						{
							encontrado = true;
						}
						else if(descomposicionCorreo[1].equals(HOTMAIL))
						{
							encontrado = true;
						}
						

					}
				}
			}
		}

		if(encontrado == false)
		{
			throw new ValidatorException(new FacesMessage("El correo no es el adecuando,intentelo de nuevo"));
		}
	}
	
	public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		String userNameIngresado = value.toString();
		
		User usuario = new UserDAOImpl().buscarUsuarioRepetidoLogin(userNameIngresado);

		if(usuario != null)
		{
			throw new ValidatorException(new FacesMessage("El UserName ya existe"));
		}
	}
	
	
	public void validateEmailPassword(FacesContext context, UIComponent component, Object value)
	{
		String correoDigitado = value.toString();
		boolean pasar = false;
		
		if(usuario.getPassword().equals(correoDigitado))
		{
			pasar = true;
		}
		
		else
		{
			throw new ValidatorException(new FacesMessage("Contraseña incorrecta"));
		}
		
	}
	
	public String pasarPassword()
	{
		return "nuevacontrasena.xhtml?faces-redirect=true";
		
	}
	
	
	public void validateClaveR(FacesContext context, UIComponent component, Object value)
	{
		String claveUsuario = value.toString();
		int numeroMayusculas = 0;
		int numeroMinusculas = 0;
		int numeros = 0;
		boolean aceptar = false;
		
		UserDAO dao = new UserDAOImpl();
		User usuarioLogin;
		User usuario2;
		
		usuarioLogin = dao.buscarUsuarioRepetidoCorreo(usuario.getEmailAddress());
		usuario2 = dao.buscarUsuarioLogin(usuario.getUserName());
		if(usuarioLogin != null)
		{

		for (int i = 0; i < claveUsuario.length(); i++) 
		{
			char letra = claveUsuario.charAt(i);
			String passValue = String.valueOf(letra);
			if (passValue.matches("[A-Z]")) 
			{
				numeroMayusculas++;
			}
			else if (passValue.matches("[a-z]")) 
			{
				numeroMinusculas++;
			} 
			else if (passValue.matches("[0-9]")) 
			{
				numeros++;
			}

		}

		if(numeroMayusculas == 0)
		{
			throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));
		}
		if(numeroMinusculas == 0)
		{
			throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));

		}
		if(numeros == 0)
		{
			throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));

		}
		
		if(claveUsuario.length()<6)
		{
			throw new ValidatorException(new FacesMessage("La contraseña debe tener minimo 6 caracteres"));
		}
		
		
		if(numeroMayusculas>=1 && numeroMinusculas>=1 && numeros>=1)
		{
			
				String passwordUser = claveUsuario;
				String passwordMD5 = UtilMD5.getStringMessageDigest(passwordUser, UtilMD5.MD5);
				usuarioLogin.setPassword(passwordMD5);
				dao.update(usuarioLogin);
		
			
		}
		
	    }
		
		else
		{
			for (int i = 0; i < claveUsuario.length(); i++) 
			{
				char letra = claveUsuario.charAt(i);
				String passValue = String.valueOf(letra);
				if (passValue.matches("[A-Z]")) 
				{
					numeroMayusculas++;
				}
				else if (passValue.matches("[a-z]")) 
				{
					numeroMinusculas++;
				} 
				else if (passValue.matches("[0-9]")) 
				{
					numeros++;
				}

			}

			if(numeroMayusculas == 0)
			{
				throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));
			}
			if(numeroMinusculas == 0)
			{
				throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));

			}
			if(numeros == 0)
			{
				throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));

			}
			
			if(claveUsuario.length()<6)
			{
				throw new ValidatorException(new FacesMessage("La contraseña debe tener minimo 6 caracteres"));
			}
			
			
			if(numeroMayusculas>=1 && numeroMinusculas>=1 && numeros>=1)
			{
				
					String passwordUser = claveUsuario;
					String passwordMD5 = UtilMD5.getStringMessageDigest(passwordUser, UtilMD5.MD5);
					usuario2.setPassword(passwordMD5);
					dao.update(usuario2);
			
				
			}
			
		}
			
		
	}

	
	
	
	public void validateClave(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		String claveUsuario = value.toString();
		int numeroMayusculas = 0;
		int numeroMinusculas = 0;
		int numeros = 0;
		boolean aceptar = false;

		for (int i = 0; i < claveUsuario.length(); i++) 
		{
			char letra = claveUsuario.charAt(i);
			String passValue = String.valueOf(letra);
			if (passValue.matches("[A-Z]")) 
			{
				numeroMayusculas++;
			}
			else if (passValue.matches("[a-z]")) 
			{
				numeroMinusculas++;
			} 
			else if (passValue.matches("[0-9]")) 
			{
				numeros++;
			}

		}

		if(numeroMayusculas == 0)
		{
			throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));
		}
		if(numeroMinusculas == 0)
		{
			throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));

		}
		if(numeros == 0)
		{
			throw new ValidatorException(new FacesMessage("La clave debe contener 1 mayuscula, 1 minuscula y 1 número"));

		}
		
		if(claveUsuario.length()<6)
		{
			throw new ValidatorException(new FacesMessage("La contraseña debe tener minimo 6 caracteres"));
		}
		
		
		if(numeroMayusculas>=1 && numeroMinusculas>=1 && numeros>=1)
		{
			String passwordUser = claveUsuario;
			String passwordMD5 = UtilMD5.getStringMessageDigest(passwordUser, UtilMD5.MD5);
			usuario.setPassword(passwordMD5);
		}
		
		
		
	}
	
	public String pasarContraseña()
	{
		return "nuevacontrasena.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	public void validateConfirmar(FacesContext context, UIComponent component, Object value)
	{
		
		String confirmarClave = value.toString();
		boolean confirmar = false;
		
		String passwordMD5 = UtilMD5.getStringMessageDigest(confirmarClave, UtilMD5.MD5);
		
		if(passwordMD5.equals(usuario.getPassword()))
		{
			confirmar = true;
		}
		
		else
		{
			throw new ValidatorException(new FacesMessage("Las contraseñas no coinciden"));

		}
		
	}
	
	
	public void validateConfirmarR(FacesContext context, UIComponent component, Object value)
	{
		String confirmarClave = value.toString();
		boolean confirmar = false;
         User usuarioLogin,usuario2;
		UserDAO dao = new UserDAOImpl();
		usuarioLogin = dao.buscarUsuarioRepetidoCorreo(usuario.getEmailAddress());
		usuario2 = dao.buscarUsuarioLogin(usuario.getUserName());
		
		
			
		String passwordMD5 = UtilMD5.getStringMessageDigest(confirmarClave, UtilMD5.MD5);
		
		if(usuarioLogin != null)
		{
			if(passwordMD5.equals(usuarioLogin.getPassword()))
			{
				confirmar = true;
			}
			
			else
			{
				throw new ValidatorException(new FacesMessage("Las contraseñas no coinciden"));

			}
		}
		
		
		else
		{
			if(passwordMD5.equals(usuario2.getPassword()))
			{
				confirmar = true;
			}
			
			else
			{
				throw new ValidatorException(new FacesMessage("Las contraseñas no coinciden"));

			}
			
		}
			
			
		
		
		
	}
	
	public void validateUserName(FacesContext context, UIComponent component, Object value)
	{
		String userDigitado = value.toString();
		int caracteres = userDigitado.length();
		boolean pasa = false;
		
		UserDAO dao = new UserDAOImpl();
		User userNameLogin = dao.buscarUsuarioLogin(userDigitado);
		
		if(userNameLogin != null)
		{
			
			throw new ValidatorException(new FacesMessage("Este usuario ya existe"));

		}
		
		
		if(userDigitado.length()>=6)
		{
			pasa = true;
		}
		
		else
		{
			throw new ValidatorException(new FacesMessage("El nombre de usuario debe tener minimo 6 caracteres"));
		}
		
	}
		
//		public void validatePassword(FacesContext context, UIComponent component, Object value)
//		{
//			String passwordDigitado = value.toString();
//			int caracteres = passwordDigitado.length();
//			boolean pasa = false;
//			
//			
//			if(passwordDigitado.length()>=6)
//			{
//				pasa = true;
//			}
//			
//			else
//			{
//				throw new ValidatorException(new FacesMessage("La contraseña debe tener minimo 6 caracteres"));
//			}
//			
		

	//}
	
	

	
	
	
	
	
	
	
	
	//-------------------------------------------------------------------------------------
		/**
		 * No usados 
		 */
	//-------------------------------------------------------------------------------------
		
	public String login() throws UnknownHostException 
	{

		       String passwordMD5 = UtilMD5.getStringMessageDigest(password, UtilMD5.MD5);
				User usu = new UserDAOImpl().buscarUsuario(loginUsuario, passwordMD5);
				User usuarioLogin = new UserDAOImpl().buscarUsuarioRepetidoLogin(loginUsuario);

				if(usu != null)
				{
					String miIp = InetAddress.getLocalHost().getHostAddress();

				
					setUsuario(usu);
					usu.setIp(miIp);
					intentos = 0;
					
					
					return "index-inicio?faces-redirect=true";

				}
				else if(usuarioLogin != null)
				{
					if(intentosAdministrador == 3)
					{

						UserDAO dao = new UserDAOImpl();
						usuarioLogin.setActive("I");
						dao.update(usuarioLogin);
					}

					intentosAdministrador++;

				}

			
				return "index?faces-redirect=true";
	}
	
	public String user() throws UnknownHostException 
	{

		String claveMD5 = UtilMD5.getStringMessageDigest(password, UtilMD5.MD5);
		User usu = new UserDAOImpl().buscarUsuarioCorriente(loginUsuario, claveMD5);
		User usuarioLogin = new UserDAOImpl().buscarUsuarioLogin(loginUsuario);

		if(usu != null)
		{
			String miIp = InetAddress.getLocalHost().getHostAddress();

		

			setUsuario(usu);
			usu.setIp(miIp);
			intentos = 0;
			logger.info("Un usuario inició sesión");
			return "registrado.xhtml?faces-redirect=true";
		}
		else if(usuarioLogin != null)
		{
			System.out.println(intentos);
			if(intentos == 4)
			{
				System.out.println("Entró");
				UserDAO dao = new UserDAOImpl();
				usuarioLogin.setActive("I");
				dao.update(usuarioLogin);
			}
			else 
			{
				intentos++;
				
			}

			intentos++;

		}

		return "index.xhtml?faces-redirect=true";

	}
	
	
	
	
	
	public String submit() throws UnknownHostException 
	{
		
				String claveMD5 = UtilMD5.getStringMessageDigest(password, UtilMD5.MD5);
				User usu = new UserDAOImpl().buscarUsuario(loginUsuario, claveMD5);
				if(usu == null)
				{
					return "login?faces-redirect=true";

				}

				String miIp = InetAddress.getLocalHost().getHostAddress();

				Audit auditoria = new Audit();
				auditoria.setUserId(usu.getId()); 
				auditoria.setOperation("El usuario:" + usu.getUserName() + " inició sesión");
				//auditoria.setComentario("USER LOGIN");
				auditoria.setCreateDate(new GregorianCalendar().getTime());
				auditoria.setTableName("usuario");
				//auditoria.setIp(miIp);
				auditoria.setTableId(usu.getId());		
				AuditDAO daoAudit = new AuditDAOImpl();

				daoAudit.save(auditoria);

				usu.setIp(miIp);
				return "index-administrador?faces-redirect=true";
	}
	
	
	
	public String cerrarSesion() throws UnknownHostException
	{
		String miIp = InetAddress.getLocalHost().getHostAddress();
		Audit auditoria = new Audit();
		auditoria.setUserId(usuario.getId()); 
		auditoria.setOperation("El usuario:" + usuario.getUserName() + " cerró sesión");
		//auditoria.setComentario("USER CLOSE");
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setTableName("usuario");
		//auditoria.setIp(miIp);
		auditoria.setTableId(usuario.getId());		
		AuditDAO daoAudit = new AuditDAOImpl();

		daoAudit.save(auditoria);

		resetearVariables();
		logger.info("El usuario cerró sesión");
		return "index.xhtml?faces-redirect=true";
	}
	
	public String cerrarSesionAdministrador() throws UnknownHostException
	{
		String claveMD5UsuActual = UtilMD5.getStringMessageDigest(password, UtilMD5.MD5);
		String miIp = InetAddress.getLocalHost().getHostAddress();
		Audit auditoria = new Audit();
		auditoria.setUserId(usuario.getId()); 
		auditoria.setOperation("El usuario:" + usuario.getUserName() + " cerró sesión");
		//auditoria.setComentario("USER CLOSE");
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setTableName("usuario");
		//auditoria.setIp(miIp);
		auditoria.setTableId(usuario.getId());		
		AuditDAO daoAudit = new AuditDAOImpl();

		daoAudit.save(auditoria);

		resetearVariables();
		logger.info("El usuario cerró sesión");
		return "login.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	
	
	public String adicionarUsuarioCorriente() throws UnknownHostException
	{

	
				UserDAO dao = new UserDAOImpl();

				String miIp = InetAddress.getLocalHost().getHostAddress();
				
				

				usuario.setIp(miIp);
				usuario.setDateLastPassword(Calendar.getInstance().getTime());
				String claveUsuario = usuario.getPassword();
				String claveMD5 = UtilMD5.getStringMessageDigest(claveUsuario, UtilMD5.MD5);
				usuario.setPassword(claveMD5);
				usuario.setId((long) 2);
				
				EmailSenderService correo = new EmailSenderService();
				correo.mandarCorreo(usuario.getEmailAddress(),usuario.getPassword(),usuario.getUserName());
				dao.save(usuario);

				
				User usuarioActual = new UserDAOImpl().buscarUsuarioCorriente(usuario.getUserName(), claveMD5);

				Audit auditoria = new Audit();
				auditoria.setUserId(usuario.getId()); 
				auditoria.setOperation("El usuario:" + usuario.getUserName() + " cerró sesión");
				//auditoria.setComentario("USER CREATE");
				auditoria.setCreateDate(new GregorianCalendar().getTime());
				auditoria.setTableName("usuario");
				//auditoria.setIp(miIp);
				auditoria.setTableId(usuario.getId());		
				AuditDAO daoAudit = new AuditDAOImpl();

				daoAudit.save(auditoria);
				logger.info("Se registró un usuario");
				return "index?faces-redirect=true";
			}
	
	public String getErrorUsuarioCorriente() throws UnknownHostException
	{
		if(usuario == null)
		{
			errorUsuarioCorriente = "";
		}
		else if(usuario.getUserName() == null)
		{
			errorUsuarioCorriente = "";
		}
		else if(adicionarUsuarioCorriente().equals("registrarse"))
		{
			errorUsuarioCorriente = "Revisar el re-Captcha";
		}

		return errorUsuarioCorriente;
	}

	public void setErrorUsuarioCorriente(String errorUsuarioCorriente) {
		this.errorUsuarioCorriente = errorUsuarioCorriente;
	}

	public String getLoginBuscar() {
		return loginBuscar;
	}

	public void setLoginBuscar(String loginBuscar) {
		this.loginBuscar = loginBuscar;
	}

	public String buscarUsuario()
	{
		User usuarioBuscado = new UserDAOImpl().buscarUsuarioRepetidoLogin(loginBuscar);

		if(usuarioBuscado == null)
		{
			return "gestionUsuarios?faces-redirect=true";
		}
		setUsuarioBuscado(usuarioBuscado);
		noEncontrado = "";
		return "buscarUsuario?faces-redirect=true";
	}

	public User getUsuarioBuscado() {
		return usuarioBuscado;
	}

	public void setUsuarioBuscado(User usuarioBuscado) {
		this.usuarioBuscado = usuarioBuscado;
	}

	public String getNoEncontrado()
	{
		if(loginBuscar == null)
		{
			noEncontrado= "";
		}
		else if(buscarUsuario().equals("gestionUsuarios?faces-redirect=true"))
		{
			noEncontrado ="No existe usuario con ese login";
		}

		return noEncontrado;
	}

	public void setNoEncontrado(String noEncontrado) {
		this.noEncontrado = noEncontrado;
	}

	public String getErrorIndex() throws UnknownHostException 
	{


		if(loginUsuario == null && password == null)
		{
			errorIndex = "";
		}	
		else if(intentos == 5)
		{
			errorIndex = "Usuario bloqueado por máximo de intentos";
			intentos = 0;
		}
		else if(user().equals("index.xhtml?faces-redirect=true"))
		{
			String claveMD5 = UtilMD5.getStringMessageDigest(password, UtilMD5.MD5);
			User usuarioLogin = new UserDAOImpl().buscarUsuarioAgregado(loginUsuario, claveMD5);
			if(usuarioLogin != null)
			{
				
				String estado = usuarioLogin.getActive().trim();
				if(estado.equals("I"))
				{
					errorIndex = "Usuario bloqueado";

				}
			}
			else
			{
				errorIndex ="Usuario incorrecto";	
			}
	
		}
		return errorIndex;
	}

	public void setErrorIndex(String errorIndex) {
		this.errorIndex = errorIndex;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}

	public void resetearVariables()
	{
		loginUsuario = null;
		usuario = null;
		password = null;
	}

	public int getIntentosAdministrador() {
		return intentosAdministrador;
	}

	public void setIntentosAdministrador(int intentosAdministrador) {
		this.intentosAdministrador = intentosAdministrador;
	}
	

	public String getIdUsuarioAutenticado() {
		return idUsuarioAutenticado;
	}

	public void setIdUsuarioAutenticado(String idUsuarioAutenticado) {
		this.idUsuarioAutenticado = idUsuarioAutenticado;
	}

	
	
	public String getConfirmarPassword() {
		return confirmarPassword;
	}

	public void setConfirmarPassword(String confirmarClave)
	{
		String claveMD5 = UtilMD5.getStringMessageDigest(confirmarClave, UtilMD5.MD5);
		this.confirmarPassword = claveMD5;
	}
	
	
	
	
	
	
	
	
	
	
	
	//-----------------------------------------------------------------------
	
	/**
	 *  Usuario Funcional
	 */
	//-----------------------------------------------------------------------
	
	
	

	

	/**
	 * Inicio de sesion
	 */
	
	
	
	
	
	public void login(ActionEvent actionEvent)
	{
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		boolean loged = false;
		
		if(userNameLogin != null && userNameLogin.equals("admin")  && passwordLogin != null && passwordLogin.equals("admin"))
		{
			loged = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Bienvenido",usuario.getUserName());
		}
		
		else
		{
			
			loged = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Bienvenido","Usuario inexistente");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("LoggedIn", loged);
		
	}


	public String getUserNameLogin() {
		return userNameLogin;
	}


	public void setUserNameLogin(String userNameLogin) {
		this.userNameLogin = userNameLogin;
	}


	public String getPasswordLogin() {
		return passwordLogin;
	}


	public void setPasswordLogin(String passwordLogin) {
		this.passwordLogin = passwordLogin;
	}
	
	
}
