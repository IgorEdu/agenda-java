package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import entities.Usuario;

public class UsuarioDAOTest {
	
	public static void cadastrarUsuarioDAOTest() throws SQLException, IOException, ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Usuario usuario = new Usuario();
		usuario.setUsername("Usuario");
		usuario.setSenha("senhaforte123");
		usuario.setNomeUsuario("Novo Usuario");
		usuario.setDataNascimento(new java.sql.Date(sdf.parse("19/02/1999").getTime()));
		usuario.setGenero("MASCULINO");
		usuario.setEmail("emailfulano@email.com");
		

		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).cadastrar(usuario);

		System.out.println("Cadastro efetuado com sucesso.");
	}
	
	public static void atualizarUsuarioDAOTest() throws SQLException, IOException, ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setUsername("Usuario-atualizado");
		usuario.setSenha("senhaforte123");
		usuario.setNomeUsuario("Novo Usuario");
		usuario.setDataNascimento(new java.sql.Date(sdf.parse("19/02/1999").getTime()));
		usuario.setGenero("MASCULINO");
		usuario.setEmail("email@email.com");
		

		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).atualizar(usuario);

		System.out.println("Cadastro atualizado com sucesso.");
	}
	
	public static void excluirUsuarioDAOTest() throws SQLException, IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setUsername("Usuario-atualizado");
		usuario.setSenha("senhaforte123");
		usuario.setNomeUsuario("Novo Usuario");
		usuario.setDataNascimento(new java.sql.Date(sdf.parse("19/02/1999").getTime()));
		usuario.setGenero("MASCULINO");
		usuario.setEmail("email@email.com");
		
		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).excluir(usuario);

		System.out.println("Cadastro excluido com sucesso.");
	}
	
	public static void main(String[] args) {

		try {
//			UsuarioDAOTest.cadastrarUsuarioDAOTest();
//			UsuarioDAOTest.atualizarUsuarioDAOTest();
			UsuarioDAOTest.excluirUsuarioDAOTest();
		} catch (SQLException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
