package service;

import java.util.ArrayList;
import java.util.List;

import entities.Usuario;

public class UsuarioServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UsuarioService userService = new UsuarioService();
		
		Usuario usuario = new Usuario("igorzinho", "bumbum", "Igor Eduardo", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "igor@gmail.com", "igor.png");
		if(userService.cadastrar(usuario)
		) {
			System.out.println("Usuario cadastrado com sucesso!");
		}
		
		userService.atualizarUsuario(usuario);
		
		Usuario usuarioBusca = userService.buscarUsuarioPorLogin(usuario.getUsername());
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Usuário teste de busca:");
		System.out.println("Id: " + usuarioBusca.getId());
		System.out.println("Login: " + usuarioBusca.getUsername());
		System.out.println("Senha (criptografada): "+ usuarioBusca.getSenhaCriptografada());
		System.out.println("Nome: " + usuarioBusca.getNomeUsuario());
		System.out.println("Data de nascimento: " + usuarioBusca.getDataNascimento());
		System.out.println("Gênero: " + usuarioBusca.getGenero());
		System.out.println("E-mail: " + usuarioBusca.getEmail());
		System.out.println("======================================================");
		System.out.println();
		
		List<Usuario> outrosUsuarios = new ArrayList<Usuario>();
		outrosUsuarios = userService.listarOutrosUsuario(usuario);
		
		for(Usuario outroUsuario : outrosUsuarios) {
			System.out.println();
			System.out.println("=====================================================");
			System.out.println("Usuário teste de busca da listagem:");
			System.out.println("Id: " + outroUsuario.getId());
			System.out.println("Login: " + outroUsuario.getUsername());
			System.out.println("Senha (criptografada): "+ outroUsuario.getSenhaCriptografada());
			System.out.println("Nome: " + outroUsuario.getNomeUsuario());
			System.out.println("Data de nascimento: " + outroUsuario.getDataNascimento());
			System.out.println("Gênero: " + outroUsuario.getGenero());
			System.out.println("E-mail: " + outroUsuario.getEmail());
			System.out.println("======================================================");
			System.out.println();
		}
		
		userService.excluirUsuario(usuario);
		
	}

}
