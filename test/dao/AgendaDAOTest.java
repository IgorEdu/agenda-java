package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import entities.Agenda;
import entities.Usuario;

public class AgendaDAOTest {
	public static void cadastrarAgendaDAOTest() throws SQLException, IOException, ParseException {
		Connection conn = BancoDados.conectar();
		
		Agenda agenda = new Agenda();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
		Usuario usuario = usuarioDAO.buscarUsuarioPorLogin("teste 1");
		
		conn = BancoDados.conectar();
		agenda.setNomeAgenda("Agenda de teste");
		agenda.setDescricao("Descrição da agenda usada para teste do método");
		agenda.setUsuario(usuario);

		new AgendaDAO(conn).cadastrar(agenda);

		System.out.println("Agenda cadastrada com sucesso.");
	}
	
	public static void atualizarAgendaDAOTest(Agenda agenda) throws SQLException, IOException, ParseException {

		agenda.setNomeAgenda("Atualizando agenda de teste");
		agenda.setDescricao("Descrição atualizada da agenda usada para teste do método");
		

		Connection conn = BancoDados.conectar();
		new AgendaDAO(conn).atualizar(agenda);

		System.out.println("Agenda " + agenda.getNomeAgenda() + " atualizada com sucesso.");
	}
	
	public static void excluirAgendaDAOTest(Agenda agenda) throws SQLException, IOException, ParseException {

		
		Connection conn = BancoDados.conectar();
		new AgendaDAO(conn).excluir(agenda.getIdAgenda());

		System.out.println("Agenda " + agenda.getNomeAgenda() + " excluida com sucesso.");
	}
	
	public static Agenda buscarAgendaPorIdAgendaDAOTest(int idAgenda) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		Agenda agenda = new AgendaDAO(conn).buscarAgendaPorIdAgenda(idAgenda);
		
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Agenda teste de busca:");
		System.out.println("Id: " + agenda.getIdAgenda());
		System.out.println("IdUsuario: " + agenda.getUsuario().getId());
		System.out.println("Nome: "+ agenda.getNomeAgenda());
		System.out.println("Descrição: " + agenda.getDescricao());
		System.out.println("======================================================");
		System.out.println();
		
		return agenda;
	}
	
	public static List<Agenda> buscarAgendaPorIdUsuarioDAOTest(int idUsuario) throws SQLException, IOException{
		Connection conn = BancoDados.conectar();
		List<Agenda> agendas = new AgendaDAO(conn).buscarAgendaPorIdUsuario(idUsuario);
		
		for(Agenda agenda : agendas) {			
			System.out.println();
			System.out.println("=====================================================");
			System.out.println("Agenda teste de busca:");
			System.out.println("Id: " + agenda.getIdAgenda());
			System.out.println("IdUsuario: " + agenda.getUsuario().getId());
			System.out.println("Nome: "+ agenda.getNomeAgenda());
			System.out.println("Descrição: " + agenda.getDescricao());
			System.out.println("======================================================");
			System.out.println();
		}
		
		return agendas;
	}
	
	public static void main(String[] args) {

		try {
			AgendaDAOTest.cadastrarAgendaDAOTest();
			
			Agenda agenda1 = AgendaDAOTest.buscarAgendaPorIdAgendaDAOTest(1);
			AgendaDAOTest.atualizarAgendaDAOTest(agenda1);
			
			Agenda agenda2 = AgendaDAOTest.buscarAgendaPorIdAgendaDAOTest(2);
			AgendaDAOTest.excluirAgendaDAOTest(agenda2);
			
			AgendaDAOTest.buscarAgendaPorIdUsuarioDAOTest(4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (SQLException | IOException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}
}
