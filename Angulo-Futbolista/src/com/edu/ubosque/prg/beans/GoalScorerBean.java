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
import com.edu.ubosque.prg.dao.GoalScorerDAO;
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.GoalScorerDAOImpl;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.GoalScorer;
import com.edu.ubosque.prg.entity.New;

@ManagedBean
@SessionScoped
public class GoalScorerBean implements Serializable {
	private GoalScorer goalScorer;
	private DataModel listaGoalScorer;
	Audit auditoria;
	private static final Logger logger = Logger.getLogger(GoalScorerBean.class);

	
	
	public GoalScorerBean()
	{
		goalScorer = new GoalScorer();
		goalScorer.setGoals(0);
		auditoria = new Audit();
		
	}
	
	

	


	public String adicionarGoalScorer(GoalScorer goalScorer) {
		GoalScorerDAO dao = new GoalScorerDAOImpl();
		dao.save(goalScorer);
		return "goleadores";
	}

	public DataModel getListarGoalScorer() {
		List<GoalScorer> lista = new GoalScorerDAOImpl().list();
		listaGoalScorer = new ListDataModel(lista);
		return listaGoalScorer;
	}
	
	
	public String prepararModificarGoalScorer()
	{
		goalScorer = (GoalScorer) (listaGoalScorer.getRowData());
		return "/funcional/goleadorRegistrado?faces-redirect=true";
		
	}
	
	
	
	
	public String modificarGoalScorer() throws UnknownHostException
	{
       GoalScorerDAO dao = new GoalScorerDAOImpl();
			
	   goalScorer.setPhoto("N.A");
	   goalScorer.setGoals(0);
		

		dao.update(goalScorer);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Se modificó un goleador");
		
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional modificó el contenido de un goleador: "+goalScorer.getFullName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Goleador");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
				
		auditDao.save(auditoria);

		return "/funcional/goleadores.xhtml?faces-redirect=true";
	}

	
	public String adicionarGoalScorer() throws UnknownHostException
	{
		GoalScorerDAO dao = new GoalScorerDAOImpl();
		
		goalScorer.setPhoto("N.A");
		goalScorer.setGoals(0);

		dao.save(goalScorer);
		logger.info("Se agregó un goleador");


		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("Se registró un nuevo goleador: "+goalScorer.getFullName());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Goleador");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
		
		auditDao.save(auditoria);
		
		return "/funcional/goleadores.xhtml?faces-redirect=true";
	}

	
	public GoalScorer getGoalScorer() {
		return goalScorer;
	}



	public void setGoalScorer(GoalScorer goalScorer) {
		this.goalScorer = goalScorer;
	}


}
