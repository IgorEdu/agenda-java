package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;

public class UsuarioService {
	
	public UsuarioService() {
	}
	
	public boolean cadastrar(Usuario usuario) {
		String login = usuario.getUsername();
		
		try {		
			if(buscarUsuarioPorLogin(login) == null) {
				Connection conn = BancoDados.conectar();
				new UsuarioDAO(conn).cadastrar(usuario);
				
				return true;
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.err.println("Usuario " + login + " já cadastrado!");
		return false;
	}
	
	public void atualizarUsuario(Usuario usuarioNovo) {
		try {
			Connection conn = BancoDados.conectar();
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			usuarioDAO.atualizar(usuarioNovo);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void excluirUsuario(Usuario usuario) {
		try {
			Connection conn = BancoDados.conectar();
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			usuarioDAO.excluir(usuario);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Usuario buscarUsuarioPorLogin(String login) {
		try {
			Connection conn = BancoDados.conectar();
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			return usuarioDAO.buscarUsuarioPorLogin(login);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Usuario> listarOutrosUsuario(Usuario usuario) {
		List<Usuario> outrosUsuarios = new ArrayList<Usuario>();
		try {
			Connection conn = BancoDados.conectar();
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			outrosUsuarios = usuarioDAO.listarOutrosUsuario(usuario);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outrosUsuarios;
	}
}
