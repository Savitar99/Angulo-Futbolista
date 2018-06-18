package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Match;

public interface MatchDAO {

	public void save(Match match);

	public List<Match> list();
	
	public void update(Match match);
}
