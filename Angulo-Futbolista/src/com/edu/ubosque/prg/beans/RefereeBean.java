package com.edu.ubosque.prg.beans;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.RefereeDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.dao.impl.RefereeDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.New;
import com.edu.ubosque.prg.entity.Referee;
import com.edu.ubosque.prg.entity.User;

@ManagedBean
@SessionScoped
public class RefereeBean implements Serializable
{
	
	private Referee referee;
	private DataModel listaReferee;
	Audit auditoria;
	private static final Logger logger = Logger.getLogger(RefereeBean.class);

	
	public RefereeBean()
	{
		referee = new Referee();
		auditoria = new Audit();
		
	}

	public String adicionarReferee(Referee referee) {
		RefereeDAO dao = new RefereeDAOImpl();
		dao.save(referee);
		return "arbitros";
	}

	public DataModel getListarReferee() {
		List<Referee> lista = new RefereeDAOImpl().list();
		listaReferee = new ListDataModel(lista);
		return listaReferee;
	}
	

	public String prepararModificarReferee()
	{
		referee = (Referee) (listaReferee.getRowData());
		return "/funcional/arbitroRegistrado?faces-redirect=true";
		
	}
	
	
	
	
	public String modificarReferee() throws UnknownHostException
	{
        RefereeDAO dao = new RefereeDAOImpl();
			
		referee.setState("A");
		

		dao.update(referee);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Se modificó un arbitro");
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional modificó el contenido de un arbitro: "+referee.getFullName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Arbitro");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
				
		auditDao.save(auditoria);

		return "/funcional/arbitros.xhtml?faces-redirect=true";
	}

	
	public String adicionarReferee() throws UnknownHostException
	{
		 RefereeDAO dao = new RefereeDAOImpl();
		
		 referee.setState("A");
		
		dao.save(referee);
        logger.info("Se agregó un arbitro");

		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("Se registró un nuevo arbitro: "+referee.getFullName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Arbitro");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
		
		auditDao.save(auditoria);
		
		return "/funcional/arbitros.xhtml?faces-redirect=true";
	}

	public Referee getReferee() {
		return referee;
	}

	public void setReferee(Referee referee) {
		this.referee = referee;
	}
	
	
	public String bloquearReferee() throws UnknownHostException {
		Referee refereeTemp = (Referee) (listaReferee.getRowData());
		RefereeDAO dao = new RefereeDAOImpl();		
		refereeTemp.setState("I");
		
		dao.update(refereeTemp);
		
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El Funcional desactivó a "+refereeTemp.getFullName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Usuario");
		auditoria.setUserId((long) refereeTemp.getId());
		auditoria.setUserName(refereeTemp.getFullName());
				
		auditDao.save(auditoria);
		
		
		
		return "/funcional/arbitros.xhtml?faces-redirect=true";
	}
	
	
	

}
