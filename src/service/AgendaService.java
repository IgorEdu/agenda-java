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
	
	public AgendaService() {

	}
	
	public void cadastrarAgenda(Agenda agenda) {
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
		return null;
	}
	
	public List<Agenda> buscarAgendasUsuario(Usuario usuario){
		List<Agenda> resultado = new ArrayList<Agenda>();

		try {
			Connection conn = BancoDados.conectar();
			AgendaDAO agendaDAO = new AgendaDAO(conn);
			resultado = agendaDAO.buscarAgendaPorIdUsuario(usuario.getId());
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
	}
}
