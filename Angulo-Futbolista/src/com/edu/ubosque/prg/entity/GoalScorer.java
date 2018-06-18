package com.edu.ubosque.prg.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goalscorer database table.
 * 
 */
@Entity
public class GoalScorer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String fullName;

	private int goals;

	private String photo;

	private String team;

	public GoalScorer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGoals() {
		return this.goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}