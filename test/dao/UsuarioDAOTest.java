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
	
	public static void atualizarUsuarioDAOTest(Usuario usuario) throws SQLException, IOException, ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		
//		Usuario usuario = new Usuario();
//		usuario.setId(1);
		usuario.setUsername("Usuario-atualizado");
		usuario.setSenha("senhaforte123");
		usuario.setNomeUsuario("Novo Usuario");
		usuario.setDataNascimento(new java.sql.Date(sdf.parse("19/02/1999").getTime()));
		usuario.setGenero("MASCULINO");
		usuario.setEmail("email@email.com");
		

		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).atualizar(usuario);

		System.out.println("Usuario " + usuario.getNomeUsuario() + " atualizado com sucesso.");
	}
	
	public static void excluirUsuarioDAOTest(Usuario usuario) throws SQLException, IOException, ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
//		Usuario usuario = new Usuario();
//		usuario.setId(1);
//		usuario.setUsername("Usuario-atualizado");
//		usuario.setSenha("senhaforte123");
//		usuario.setNomeUsuario("Novo Usuario");
//		usuario.setDataNascimento(new java.sql.Date(sdf.parse("19/02/1999").getTime()));
//		usuario.setGenero("MASCULINO");
//		usuario.setEmail("email@email.com");
		
		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).excluir(usuario);

		System.out.println("Usuario " + usuario.getNomeUsuario() + " excluido com sucesso.");
	}
	
	public static Usuario buscarUsuarioPorLoginDAOTest(String login) throws SQLException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = BancoDados.conectar();
		Usuario usuario = new UsuarioDAO(conn).buscarUsuarioPorLogin(login);
		
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Usuário teste de busca:");
		System.out.println("Id: " + usuario.getId());
		System.out.println("Login: " + usuario.getUsername());
		System.out.println("Senha (criptografada): "+ usuario.getSenhaCriptografada());
		System.out.println("Nome: " + usuario.getNomeUsuario());
		System.out.println("Data de nascimento: " + usuario.getDataNascimento());
		System.out.println("Gênero: " + usuario.getGenero());
		System.out.println("E-mail: " + usuario.getEmail());
		System.out.println("======================================================");
		System.out.println();
		
		return usuario;
	}
	
	public static void main(String[] args) {

		try {
			UsuarioDAOTest.cadastrarUsuarioDAOTest();
			
			Usuario usuario1 = UsuarioDAOTest.buscarUsuarioPorLoginDAOTest("Usuario");
			UsuarioDAOTest.atualizarUsuarioDAOTest(usuario1);
			
			Usuario usuario2 = UsuarioDAOTest.buscarUsuarioPorLoginDAOTest("Usuario-atualizado");
			UsuarioDAOTest.excluirUsuarioDAOTest(usuario2);
		} catch (SQLException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
