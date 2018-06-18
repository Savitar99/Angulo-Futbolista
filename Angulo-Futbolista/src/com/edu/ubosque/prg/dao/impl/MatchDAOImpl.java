package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.MatchDAO;
import com.edu.ubosque.prg.entity.Match;
import com.edu.ubosque.prg.util.HibernateUtil;

public class MatchDAOImpl implements MatchDAO{

	@Override
	public void save(Match match) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(match);
		t.commit();		
	}

	@Override
	public List<Match> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Match").list();
		t.commit();
		return lista;
	}

	@Override
	public void update(Match match) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(match);
		t.commit();
	}

}
