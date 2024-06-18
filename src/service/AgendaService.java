package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.AgendaDAO;
import dao.BancoDados;
import entities.Agenda;
import entities.Usuario;

public class AgendaService {

//	private List<Agenda> agendas;
//	private AgendaDAO agendaDAO;
	
	public AgendaService() {
		
//		this.agendas = new ArrayList<Agenda>();
//		
//		//int idAgenda, Usuario usuario, String nomeAgenda, String descricao, List<Compromisso> compromissos
//		cadastrarAgenda(new Agenda(0, new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"), "Agenda1", "agenda 1", null));
//		cadastrarAgenda(new Agenda(1, new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"), "Agenda2", "agenda 2", null));
//		cadastrarAgenda(new Agenda(2, new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"), "Agenda3", "agenda 3", null));
//		cadastrarAgenda(new Agenda(3, new Usuario("joaozinho", "bambam", "Joaozinho Santos", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "joaozinho@outlook.com", "joao.png"), "Agenda4", "agenda 4", null));
	
	}
	
	public void cadastrarAgenda(Agenda agenda) {
		
//		this.agendas.add(agenda);
		try {
			Connection conn = BancoDados.conectar();
			AgendaDAO agendaDAO = new AgendaDAO(conn);
			agendaDAO.cadastrar(agenda);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Agenda buscarAgendaPorId(int idAgenda) {
		try {
			Connection conn = BancoDados.conectar();
			AgendaDAO agendaDAO = new AgendaDAO(conn);
			return agendaDAO.buscarAgendaPorIdAgenda(idAgenda);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for(Agenda agenda : agendas) {
//			
//			if(agenda.getIdAgenda() == idAgenda) return agenda;
//		}
		return null;
	}
	
	public List<Agenda> buscarAgendasUsuario(Usuario usuario){
		List<Agenda> resultado = new ArrayList<Agenda>();

		try {
			Connection conn = BancoDados.conectar();
			AgendaDAO agendaDAO = new AgendaDAO(conn);
			resultado = agendaDAO.buscarAgendaPorIdUsuario(usuario.getId());
//			for(Agenda agenda : agendas) {
//				
//				if(agenda.getUsuario().getUsername().equalsIgnoreCase(usuario.getUsername())) {
//					resultado.add(agenda);
//				}
//			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	}
   
	public int atualizarAgenda(Agenda agendaNova) {
		try {
			Connection conn = BancoDados.conectar();
			AgendaDAO agendaDAO = new AgendaDAO(conn);
			agendaDAO.atualizar(agendaNova);
			return 1;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for(Agenda agenda : agendas) {
//			
//			if(agenda.getIdAgenda() == agendaNova.getIdAgenda()) {
//				
//				agendas.add(agendaNova);
//				agendas.remove(agenda);
//				return 1;
//			}
//		}
		
		return 0;
	}
	
	public int excluirAgenda(int idAgenda) {
		try {
			Connection conn = BancoDados.conectar();
			AgendaDAO agendaDAO = new AgendaDAO(conn);
			agendaDAO.excluir(idAgenda);
			return 1;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
//		for(Agenda agenda : agendas) {
//			
//			if(agenda.getIdAgenda() == idAgenda) {
//				
//				agendas.remove(agenda);
//				return 1;
//			}
//		}
//		return 0;
	}
}
