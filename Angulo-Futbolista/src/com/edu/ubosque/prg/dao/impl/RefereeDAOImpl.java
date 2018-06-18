package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.RefereeDAO;
import com.edu.ubosque.prg.entity.New;
import com.edu.ubosque.prg.entity.Referee;
import com.edu.ubosque.prg.util.HibernateUtil;

public class RefereeDAOImpl implements RefereeDAO {

	public void save(Referee referee) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(referee);
		t.commit();
	}

	public List<Referee> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Referee").list();
		t.commit();
		return lista;
	}
	
	public void update(Referee referee) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(referee);
		t.commit();
	}

}
