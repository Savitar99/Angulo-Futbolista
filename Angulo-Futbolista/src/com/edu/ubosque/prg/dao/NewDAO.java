package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.New;

public interface NewDAO {

	public void save(New news);

	public List<New> list();
	
	public void update(New news);
}
