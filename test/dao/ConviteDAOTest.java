package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import entities.Convite;
import entities.StatusConvite;
import entities.Usuario;

public class ConviteDAOTest {
	public static void cadastrarConviteDAOTest() throws SQLException, IOException, ParseException {
		Connection conn = BancoDados.conectar();
		
		Convite convite = new Convite();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
		Usuario usuario = usuarioDAO.buscarUsuarioPorLogin("teste 1");
		
//		CompromissoDAO compromissoDAO = new CompromissoDAO(conn);
//		Compromisso compromisso = compromissoDAO.
		
		convite.setUsuario(usuario);
		convite.setStatusConvite(StatusConvite.PENDENTE);
		convite.setCompromisso(null);

		new ConviteDAO(conn).cadastrar(convite);

		System.out.println("Convite cadastrado com sucesso.");
	}
	
	public static void atualizarConviteDAOTest(Convite convite) throws SQLException, IOException, ParseException {
		convite.setStatusConvite(StatusConvite.ACEITO);

		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).atualizar(convite);

		System.out.println("Status do convite atualizado para: " + convite.getStatusConvite().name());
	}
	
	public static void excluirConviteDAOTest(Convite convite) throws SQLException, IOException, ParseException {

		
		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).excluir(convite);

		System.out.println("Convite " + convite.getId() + " excluido com sucesso.");
	}
	
	public static Convite buscarConvitePorIdConviteDAOTest(int idConvite) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		Convite convite = new ConviteDAO(conn).buscarConvitePorIdConvite(idConvite);
		
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Convite teste de busca:");
		System.out.println("Id: " + convite.getId());
		System.out.println("IdUsuario: " + convite.getUsuario().getId());
		System.out.println("StatusConvite: "+ convite.getStatusConvite().name());
		System.out.println("IdCompromisso: " + convite.getCompromisso().getIdCompromisso());
		System.out.println("======================================================");
		System.out.println();
		
		return convite;
	}
	
	public static List<Convite> buscarConvitePorIdUsuarioDAOTest(int idConvidado) throws SQLException, IOException{
		Connection conn = BancoDados.conectar();
		List<Convite> convites = new ConviteDAO(conn).buscarConvitesPorIdConvidado(idConvidado);
		
		for(Convite convite : convites) {			
			System.out.println();
			System.out.println("=====================================================");
			System.out.println("Convite teste de busca:");
			System.out.println("Id: " + convite.getId());
			System.out.println("IdUsuario: " + convite.getUsuario().getId());
			System.out.println("StatusConvite: "+ convite.getStatusConvite().name());
			System.out.println("IdCompromisso: " + convite.getCompromisso().getIdCompromisso());
			System.out.println("======================================================");
			System.out.println();
		}
		
		return convites;
	}
	
	public static List<Convite> buscarConvitePorIdCompromissoDAOTest(int idCompromisso) throws SQLException, IOException{
		Connection conn = BancoDados.conectar();
		List<Convite> convites = new ConviteDAO(conn).buscarConvitesPorIdCompromisso(idCompromisso);
		
		for(Convite convite : convites) {			
			System.out.println();
			System.out.println("=====================================================");
			System.out.println("Convite teste de busca:");
			System.out.println("Id: " + convite.getId());
			System.out.println("IdUsuario: " + convite.getUsuario().getId());
			System.out.println("StatusConvite: "+ convite.getStatusConvite().name());
			System.out.println("IdCompromisso: " + convite.getCompromisso().getIdCompromisso());
			System.out.println("======================================================");
			System.out.println();
		}
		
		return convites;
	}
	
	public static void main(String[] args) {

		try {
//			AgendaDAOTest.cadastrarAgendaDAOTest();
//			
//			Agenda agenda1 = AgendaDAOTest.buscarAgendaPorIdAgendaDAOTest(1);
//			AgendaDAOTest.atualizarAgendaDAOTest(agenda1);
//			
//			Agenda agenda2 = AgendaDAOTest.buscarAgendaPorIdAgendaDAOTest(2);
//			AgendaDAOTest.excluirAgendaDAOTest(agenda2);
//			
//			AgendaDAOTest.buscarAgendaPorIdUsuarioDAOTest(4);
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
