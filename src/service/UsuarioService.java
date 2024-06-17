package service;

import java.util.ArrayList;
import java.util.List;

import entities.Usuario;

public class UsuarioService {

	private List<Usuario> usuarios;
	
	public UsuarioService() {
		
		this.usuarios = new ArrayList<Usuario>();
		//(String username, String senha, String nomeUsuario, Date dataNascimento, String genero, String email, String fotoPessoal
		usuarios.add(new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"));
		usuarios.add(new Usuario("joaozinho", "bambam", "Joaozinho Santos", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "joaozinho@outlook.com", "joao.png"));
	}
	
	public void cadastrar(Usuario usuario) {
		
		this.usuarios.add(usuario);
	}
	
	public void atualizarUsuario(Usuario usuarioNovo) {
		
		for(Usuario usuario : usuarios) {
			
			if(usuario.getId() == usuarioNovo.getId()) {
				
				usuarios.add(usuarioNovo);
				usuarios.remove(usuario);
				return;
			}
		}
	}
	
	public void excluirUsuario(Usuario usuario) {
		
		this.usuarios.remove(usuario);
	}
	
	public Usuario buscarUsuarioPorLogin(String login) {
		
		for(Usuario user : usuarios) {
			
			if(user.getUsername().equalsIgnoreCase(login)) return user;
		}
		return null;
	}
	
	public void listarUsuarios() {
		
		for(Usuario user : this.usuarios) {
			
			System.out.println(user.getNomeUsuario());
		}
	}
}
