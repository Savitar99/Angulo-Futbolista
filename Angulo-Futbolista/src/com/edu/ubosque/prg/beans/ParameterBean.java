package com.edu.ubosque.prg.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

import com.edu.ubosque.prg.dao.ParameterDAO;
import com.edu.ubosque.prg.dao.RefereeDAO;
import com.edu.ubosque.prg.dao.impl.ParameterDAOImpl;
import com.edu.ubosque.prg.dao.impl.RefereeDAOImpl;
import com.edu.ubosque.prg.entity.Parameter;

@ManagedBean
@SessionScoped
public class ParameterBean implements Serializable{
	
	private Parameter parameter;
	private DataModel listaParameter;
	private static final Logger logger = Logger.getLogger(ParameterBean.class);

	public ParameterBean()
	{
		parameter = new Parameter();
		
	}

	public String adicionarParameter(Parameter parameter) {
		ParameterDAO dao = new ParameterDAOImpl();
		dao.save(parameter);
        logger.info("Se agregó un parametro");

		return "parametros";
	}

	public DataModel getListarParameter() {
		List<Parameter> lista = new ParameterDAOImpl().list();
		listaParameter = new ListDataModel(lista);
		logger.info("Se conectó a la base de datos");

		return listaParameter;
	}

}
