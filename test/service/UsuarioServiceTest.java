package service;

import entities.Usuario;

public class UsuarioServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UsuarioService userService = new UsuarioService();
		userService.cadastrar(new Usuario("igorzinho", "bumbum", "Igor Eduardo", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "igor@gmail.com", "igor.png"));
		userService.listarUsuarios();
	}

}
