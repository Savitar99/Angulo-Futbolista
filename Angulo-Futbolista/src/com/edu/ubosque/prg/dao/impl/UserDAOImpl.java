package com.edu.ubosque.prg.dao.impl;

import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.dao.UserDAO;
import java.util.List;

import javax.persistence.EntityManager;
//import javax.persistence.Query;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.edu.ubosque.prg.util.HibernateUtil;


public class UserDAOImpl implements UserDAO {



	public void save(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(usuario);
		t.commit();
	}

	public User getUsuario(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (User) session.load(User.class, id);
	}

	public void update(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(usuario);
		t.commit();
	}

	public void remove(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(usuario);
		t.commit();
	}

	public List<User> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from User").list();
		t.commit();
		return lista;
	}
	
	public User buscarUsuarioAgregado(String userName, String password)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName AND u.password= :password");
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	public User buscarUsuario(String userName, String password)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName AND u.password= :password AND u.active = 'A'");
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	public User buscarUsuarioCorriente(String userName, String password)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName AND u.password= :password AND u.active = 'A'");
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	public User buscarUsuarioLogin(String userName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName");
		query.setParameter("userName", userName);
		
		
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	
	public User passwordLogin(String password)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.password= :password");
		query.setParameter("password", password);
		
		
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	
	
	public User buscarUsuarioRepetidoLogin(String userName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName ");
		query.setParameter("userName", userName);
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	public User buscarUsuarioRepetidoCorreo(String correo)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.emailAddress= :correo");
		query.setParameter("correo", correo);
		
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
	}
	
	
//	public User iniciarSesion(User user)
//	{
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Transaction t = session.beginTransaction();
//		Query query = session.createQuery("FROM User u WHERE u.userName = ?0 and u.password = ?1");
//		query.setParameter(0, user.getUserName());
//		query.setParameter(1, user.getPassword());
//		
//		
//		User usuario = (User) query.uniqueResult();
//	
//		t.commit();
//		session.close();
//		return usuario;
//		
//	}
	
	public User iniciarSesion(User user)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName AND u.password= :password");
		query.setParameter("userName", user.getUserName());
		query.setParameter("password", user.getPassword());
	
	
		User usuario = (User) query.uniqueResult();
	
		t.commit();
		session.close();
		return usuario;
		
	}
	
	public User verificarSesion(String usu, String pass)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM User u where u.userName= :userName AND u.password= :password");
		query.setParameter("userName", usu);
		query.setParameter("password", pass);
	
		return (User) query.uniqueResult();
		
	}

}
