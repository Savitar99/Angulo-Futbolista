package com.edu.ubosque.prg.beans;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.StadiumDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.StadiumDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.Stadium;

@ManagedBean
@SessionScoped
public class StadiumBean implements Serializable{

	
	private Stadium stadium;
	private DataModel listaStadium;
	Audit auditoria;
	private static final Logger logger = Logger.getLogger(StadiumBean.class);

	
	public StadiumBean()
	{
		stadium = new Stadium();
		auditoria = new Audit();
		
	}

	public String adicionarStadium(Stadium stadium) {
		StadiumDAO dao = new StadiumDAOImpl();
		dao.save(stadium);
		return "estadios";
	}

	public DataModel getListarStadium() {
		List<Stadium> lista = new StadiumDAOImpl().list();
		listaStadium = new ListDataModel(lista);
		return listaStadium;
	}
	
	public String prepararModificarStadium()
	{
		stadium = (Stadium) (listaStadium.getRowData());
		return "/funcional/estadioRegistrado?faces-redirect=true";
		
	}
	
	
	
	
	public String modificarStadium() throws UnknownHostException
	{
        StadiumDAO dao = new StadiumDAOImpl();
			
		stadium.setPhoto("N.A");
		stadium.setPopulation("0");
		
		

		dao.update(stadium);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Se modificó un estadio");
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional modificó el contenido de un estadio :"+stadium.getName() );
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Estadio");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
				
		auditDao.save(auditoria);

		return "/funcional/estadios.xhtml?faces-redirect=true";
	}

	
	public String adicionarStadium() throws UnknownHostException
	{
		 StadiumDAO dao = new StadiumDAOImpl();
		
		 stadium.setPhoto("N.A");
		 stadium.setPopulation("0");
		
		dao.save(stadium);
		logger.info("Se agregó un estadio");
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("Se registró un nuevo estadio: "+stadium.getName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Estadio");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
		
		auditDao.save(auditoria);
		
		return "/funcional/estadios.xhtml?faces-redirect=true";
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
	
	
	
	
	
	
	
	
}
