package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Referee;

public interface RefereeDAO {
	
	public void save(Referee referee);

	public List<Referee> list();
	
	public void update(Referee referee);

}
