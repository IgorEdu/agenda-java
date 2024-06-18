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

	private List<Usuario> usuarios;
	
	public UsuarioService() {
	
		this.usuarios = new ArrayList<Usuario>();
		//(String username, String senha, String nomeUsuario, Date dataNascimento, String genero, String email, String fotoPessoal
		usuarios.add(new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"));
		usuarios.add(new Usuario("joaozinho", "bambam", "Joaozinho Santos", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "joaozinho@outlook.com", "joao.png"));
	}
	
	public boolean cadastrar(Usuario usuario) {
		String login = usuario.getUsername();
		
		try {		
			if(buscarUsuarioPorLogin(login) == null) {
				Connection conn = BancoDados.conectar();
				new UsuarioDAO(conn).cadastrar(usuario);

				
	//			this.usuarios.add(usuario);
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
		
//		for(Usuario usuario : usuarios) {
//			
//			if(usuario.getId() == usuarioNovo.getId()) {
//				
//				usuarios.add(usuarioNovo);
//				usuarios.remove(usuario);
//				return;
//			}
//		}
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
		
//		this.usuarios.remove(usuario);
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
//		
//		for(Usuario user : usuarios) {
//			
//			if(user.getUsername().equalsIgnoreCase(login)) return user;
//		}
//		return null;
	}
	
//	public void listarUsuarios() {
//		
//		for(Usuario user : this.usuarios) {
//			
//			System.out.println(user.getNomeUsuario());
//		}
//	}
}
