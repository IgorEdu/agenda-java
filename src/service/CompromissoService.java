package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.BancoDados;
import dao.CompromissoAgendaDAO;
import dao.CompromissoDAO;
import entities.Agenda;
import entities.Compromisso;

public class CompromissoService {
	public CompromissoService() {
		
	}
	
	public void cadastrar(Compromisso compromisso, Agenda agenda) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).cadastrar(compromisso);
			
			Connection conn2 = BancoDados.conectar();
			compromisso = new CompromissoDAO(conn2).buscarCompromissoSemId(compromisso);
			
			Connection conn3 = BancoDados.conectar();
			new CompromissoAgendaDAO(conn3).cadastrar(compromisso.getIdCompromisso(), agenda.getIdAgenda());
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void atualizar(Compromisso compromisso) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).atualizar(compromisso);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void excluir(Compromisso compromisso) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).excluir(compromisso);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
