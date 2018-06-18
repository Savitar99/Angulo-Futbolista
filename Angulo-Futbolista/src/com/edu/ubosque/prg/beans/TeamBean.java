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
import com.edu.ubosque.prg.dao.TeamDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.TeamDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.Team;

@ManagedBean
@SessionScoped
public class TeamBean implements Serializable
{
	private Team team;
	private DataModel listaTeam;
	Audit auditoria;
	private static final Logger logger = Logger.getLogger(TeamBean.class);


	
	public TeamBean()
	{
		team = new Team();
		auditoria = new Audit();
		
	}
	
	
	public String adicionarTeam(Team team) {
		TeamDAO dao = new TeamDAOImpl();
		dao.save(team);
		return "equipos";
	}
	
	public DataModel getListarTeam() {
		List<Team> lista = new TeamDAOImpl().list();
		listaTeam = new ListDataModel(lista);
		return listaTeam;
	}
	
	
	public String prepararModificarTeam()
	{
		team = (Team) (listaTeam.getRowData());
		return "/funcional/equipoRegistrado?faces-redirect=true";
		
	}
	
	
	
	
	public String modificarTeam() throws UnknownHostException
	{
       TeamDAO dao = new TeamDAOImpl();
			
		team.setFlag("N.A");
		team.setState("A");
		

		dao.update(team);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Se modificó un equipo");
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional modificó el contenido de un equipo :"+team.getCountry());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Equipo");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
				
		auditDao.save(auditoria);

		return "/funcional/equipos.xhtml?faces-redirect=true";
	}

	
	public String adicionarTeam() throws UnknownHostException
	{
		TeamDAO dao = new TeamDAOImpl();
		
		team.setFlag("N.A");
		team.setState("A");
		

		dao.save(team);
		logger.info("Se agregó un equipo");


		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("Se registró un nuevo equipo: "+team.getCountry());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Equipo");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
		
		auditDao.save(auditoria);
		
		return "/funcional/equipos.xhtml?faces-redirect=true";
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	

}
