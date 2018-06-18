package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.GoalScorer;


public interface GoalScorerDAO {

	
	public void save(GoalScorer goalScorer);

	public List<GoalScorer> list();
	
	public void update(GoalScorer goalScorer);
}
