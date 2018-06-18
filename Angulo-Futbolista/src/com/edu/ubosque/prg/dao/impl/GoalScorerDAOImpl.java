package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.GoalScorerDAO;
import com.edu.ubosque.prg.entity.GoalScorer;
import com.edu.ubosque.prg.util.HibernateUtil;

public class GoalScorerDAOImpl implements GoalScorerDAO{

	@Override
	public void save(GoalScorer goalScorer) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(goalScorer);
		t.commit();
	}

	@Override
	public List<GoalScorer> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from GoalScorer").list();
		t.commit();
		return lista;
	}

	@Override
	public void update(GoalScorer goalScorer) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(goalScorer);
		t.commit();
	}

}
