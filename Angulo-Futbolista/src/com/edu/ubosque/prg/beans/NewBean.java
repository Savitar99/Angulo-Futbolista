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
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.New;
import com.edu.ubosque.prg.entity.User;


@ManagedBean
@SessionScoped
public class NewBean implements Serializable
{

	private New news;
	private DataModel listaNew;
	Audit auditoria;
	User user;
	private static final Logger logger = Logger.getLogger(NewBean.class);


	
	public NewBean()
	{
		news = new New();
		news.setDateNews(new GregorianCalendar().getTime());
		news.setIdUser(0);
		news.setState("A");
		
		user = new User();
		auditoria = new Audit();
		
	}

	public String adicionarNew(New news) {
		NewDAO dao = new NewDAOImpl();
		dao.save(news);
		return "noticias";
	}

	public DataModel getListarNew() {
		List<New> lista = new NewDAOImpl().list();
		listaNew = new ListDataModel(lista);
		return listaNew;
	}
	
	
	
	
	
	
	public String prepararModificarNoticia()
	{
		news = (New) (listaNew.getRowData());
		return "/funcional/noticiaRegistrada?faces-redirect=true";
		
		
	}
	
	
	
	
	
	
	
	public String modificarNoticia() throws UnknownHostException
	{
        NewDAO dao = new NewDAOImpl();
			
		news.setDateNews(new GregorianCalendar().getTime());
		news.setIdUser(1);
		news.setState("A");
		

		dao.update(news);
		logger.info("Se modificó una noticia");
		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
				
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional modificó el contenido de: "+news.getShortDescription());
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Noticia");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
				
		auditDao.save(auditoria);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/funcional/index-funcional.xhtml?faces-redirect=true";
	}

	
	public String adicionarNoticia() throws UnknownHostException
	{
        NewDAO dao = new NewDAOImpl();
        
       
	    news.setDateNews(new GregorianCalendar().getTime());
	    news.setIdUser(0);
	    news.setState("A");
		
		dao.save(news);
		logger.info("Se agregó una noticia");


//		//Auditoria
		AuditDAO auditDao = new AuditDAOImpl();
		
		auditoria.setCreateDate(new GregorianCalendar().getTime());
		auditoria.setOperation("El usuario funcional registró una nueva noticia: "+ news.getShortDescription() );
		auditoria.setTableId((long) 1);
		auditoria.setTableName("Noticia");
		auditoria.setUserId((long) 0);
		auditoria.setUserName("Funcional");
		
		auditDao.save(auditoria);
		
		return "/funcional/index-funcional.xhtml?faces-redirect=true";
	}

	public New getNews() {
		return news;
	}

	public void setNews(New news) {
		this.news = news;
	}
	
	
	
	
	

}
