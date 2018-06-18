package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Team;

public interface TeamDAO {

	
	public void save(Team team);

	public List<Team> list();
	
	public void update(Team team);
}
