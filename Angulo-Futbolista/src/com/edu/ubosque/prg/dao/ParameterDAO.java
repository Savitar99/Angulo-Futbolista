package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Parameter;

public interface ParameterDAO {

	
	public void save(Parameter parameter);

	public List<Parameter> list();
	
	public void update(Parameter parameter);
}
