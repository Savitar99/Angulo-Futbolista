package com.edu.ubosque.prg.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the referee database table.
 * 
 */
@Entity
public class Referee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String fullName;

	private String nationality;

	private String state;

	public Referee() {
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

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}