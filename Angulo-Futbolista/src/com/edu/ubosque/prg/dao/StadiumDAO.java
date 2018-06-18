package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Stadium;

public interface StadiumDAO {

	
	public void save(Stadium stadium);

	public List<Stadium> list();
	
	public void update(Stadium stadium);
}
