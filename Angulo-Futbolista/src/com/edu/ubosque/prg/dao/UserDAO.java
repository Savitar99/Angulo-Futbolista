package com.edu.ubosque.prg.dao;

import com.edu.ubosque.prg.entity.User;
import java.util.List;

public interface UserDAO {

	public void save(User usuario);

	public User getUsuario(long id);

	public List<User> list();

	public void remove(User usuario);

	public void update(User usuario);
	
	public User buscarUsuarioAgregado(String userName, String password);
	
	public User buscarUsuario(String userName, String password);
	
	public User buscarUsuarioCorriente(String userName, String password);
	
	public User buscarUsuarioLogin(String userName);
	
	public User buscarUsuarioRepetidoLogin(String userName);
	
	public User buscarUsuarioRepetidoCorreo(String correo);
	
	public User iniciarSesion(User user);
	
	public User passwordLogin(String password);
	
	public User verificarSesion(String usu, String pass);

}
