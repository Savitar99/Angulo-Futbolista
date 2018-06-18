package com.edu.ubosque.prg.util;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


@ManagedBean(name="textManager", eager=true)
public class TextManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String text = "Iniciar sesión";

	public String getText() {
		
		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User") != null) {
			return "Cerrar sesión";
		}
		
		return text;
	}

	public void setText(String text) {
		this.text = text;
	
	}
}
