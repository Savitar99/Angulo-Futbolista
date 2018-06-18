package com.edu.ubosque.prg.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.User;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private User user;
	private boolean autenticado = false;
	private static final Logger logger = Logger.getLogger(LoginBean.class);


	public String verifica()
	{
		user = new User();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		UserDAO dao = new UserDAOImpl();
		User us = dao.iniciarSesion(user);
		
		if(us != null)
		{
			setAutenticado(true);
			logger.info("Se inicia una sesión");
			return "/administrador/index-administrador.xhtml?faces-redirect=true";
		}
		
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Invalid Login!","Try again"));
		}
		return "login";	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	
	
	
	
	
}
