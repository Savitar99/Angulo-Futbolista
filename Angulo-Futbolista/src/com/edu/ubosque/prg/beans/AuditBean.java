package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.User;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

@ManagedBean
@SessionScoped
public class AuditBean implements Serializable {

	//Atributos
	private Audit auditoria;
	private DataModel listaAuditoria;
	private static final Logger logger = Logger.getLogger(AuditBean.class);

	
	public AuditBean()
	{
		auditoria = new Audit();
	}

	public String adicionarAuditoria(Audit auditoria) {
		AuditDAO dao = new AuditDAOImpl();
		dao.save(auditoria);
        logger.info("Se agregó una auditoria");
		return "auditoria";
	}

	public DataModel getListarAuditoria() {
		List<Audit> lista = new AuditDAOImpl().list();
		listaAuditoria = new ListDataModel(lista);
		logger.info("Se conectó a la base de datos");
		return listaAuditoria;
	}
	
	
	

}