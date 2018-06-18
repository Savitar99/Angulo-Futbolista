package com.edu.ubosque.prg.util;

import java.io.IOException;
import java.io.Serializable;


import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.edu.ubosque.prg.entity.User;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable{

	public void verificarSesion(ComponentSystemEvent event) throws IOException
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if (!"admin".equals(fc.getExternalContext().getSessionMap().get("role"))){

			ConfigurableNavigationHandler nav 
			   = (ConfigurableNavigationHandler) 
				fc.getApplication().getNavigationHandler();
			
			nav.performNavigation("/administrador/bloqueado.xhtml");
			
		
	}
		
	}
}


