package com.edu.ubosque.prg.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String country;

	private String flag;

	private int goalsAgainst;

	private int goalsFavor;

	@Column(name="group_")
	private String group;

	private int lostMatches;

	private int playedGames;

	private String state;

	private int tiedMatches;

	private int wonMatches;
	
	private int points;
	
	

	public Team() {
	}

	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getGoalsAgainst() {
		return this.goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getGoalsFavor() {
		return this.goalsFavor;
	}

	public void setGoalsFavor(int goalsFavor) {
		this.goalsFavor = goalsFavor;
	}

	
	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getLostMatches() {
		return this.lostMatches;
	}

	public void setLostMatches(int lostMatches) {
		this.lostMatches = lostMatches;
	}

	public int getPlayedGames() {
		return this.playedGames;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTiedMatches() {
		return this.tiedMatches;
	}

	public void setTiedMatches(int tiedMatches) {
		this.tiedMatches = tiedMatches;
	}

	public int getWonMatches() {
		return this.wonMatches;
	}

	public void setWonMatches(int wonMatches) {
		this.wonMatches = wonMatches;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}




}