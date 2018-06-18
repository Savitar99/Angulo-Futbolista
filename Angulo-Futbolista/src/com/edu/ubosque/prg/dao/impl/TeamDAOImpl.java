package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.TeamDAO;
import com.edu.ubosque.prg.entity.Team;
import com.edu.ubosque.prg.util.HibernateUtil;

public class TeamDAOImpl implements TeamDAO{

	@Override
	public void save(Team team) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(team);
		t.commit();
		
	}

	@Override
	public List<Team> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Team").list();
		t.commit();
		return lista;
	}

	@Override
	public void update(Team team) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(team);
		t.commit();
	}

}
