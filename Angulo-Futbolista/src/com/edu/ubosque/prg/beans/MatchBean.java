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
import com.edu.ubosque.prg.dao.MatchDAO;
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.MatchDAOImpl;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.Match;
import com.edu.ubosque.prg.entity.New;

@ManagedBean
@SessionScoped
public class MatchBean implements Serializable
{
	private Match match;
	private DataModel listaMatch;
	private Audit auditoria;
	private static final Logger logger = Logger.getLogger(MatchBean.class);

	
	public MatchBean()
	{
		match = new Match();
		auditoria = new Audit();
	}

	
	
	public Match getMatch() {
		return match;
	}



	public void setMatch(Match match) {
		this.match = match;
	}



	public String adicionarMatch(Match m) {
		MatchDAO dao = new MatchDAOImpl();
		dao.save(m);
		return "equipos";
	}

	public DataModel getListarMatch() {
		List<Match> lista = new MatchDAOImpl().list();
		listaMatch = new ListDataModel(lista);
		return listaMatch;
	}
	
	
	
	public String prepararModificarMatch()
	{
		match = (Match) (listaMatch.getRowData());
		return "/funcional/partidoRegistrado?faces-redirect=true";
		
	}
	
	
	public String modificarMatch() throws UnknownHostException
	{
        MatchDAO dao = new MatchDAOImpl();
			
		
		

		dao.update(match);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Se modificó un partido");
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional modificó el contenido de: "+match.getPartido());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Partido");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
				
		auditDao.save(auditoria);

		return "/funcional/calendario.xhtml?faces-redirect=true";
	}
	
	
	public String adicionarMatch()
	{
		
        MatchDAO dao = new MatchDAOImpl();
        match.setGoles1(0);
        match.setGoles2(0);
        match.setPartido(match.getEquipo1()+" vs "+match.getEquipo2());
        match.setResultado(match.getGoles1()+" - "+match.getGoles2());
        
        
		
	   
		
		dao.save(match);

		logger.info("Se agregó un partido");

		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("Se registró un partido: "+match.getResultado());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Partido");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
		
		auditDao.save(auditoria);
		
		return "/funcional/calendario.xhtml?faces-redirect=true";
	}

}
