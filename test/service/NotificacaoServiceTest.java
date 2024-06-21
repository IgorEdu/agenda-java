package service;

import java.io.IOException;
import java.sql.SQLException;

import entities.Usuario;

public class NotificacaoServiceTest {
	public static void main(String[] args) {
		UsuarioService usuarioService = new UsuarioService();
		
		Usuario usuario = usuarioService.buscarUsuarioPorLogin("novo");
		
		try {
			NotificacaoService notificacaoService = new NotificacaoService(usuario);
			notificacaoService.run();
			notificacaoService.start();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
