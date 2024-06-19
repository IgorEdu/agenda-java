package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Compromisso> buscarCompromissosAgenda(int idAgenda) throws SQLException, IOException{
		Connection conn = BancoDados.conectar();
		CompromissoAgendaDAO compromissoAgendaDAO = new CompromissoAgendaDAO(conn);
		
		List<Integer> listaIdCompromissos = new ArrayList<Integer>();
		listaIdCompromissos = compromissoAgendaDAO.listarIdCompromissosPorAgenda(idAgenda);
		
		List<Compromisso> resultado = new ArrayList<Compromisso>();
		
		
		for(Integer idCompromisso : listaIdCompromissos) {
			Connection conn2 = BancoDados.conectar();
			resultado.add(new CompromissoDAO(conn2).buscarCompromissoPorIdCompromisso(idCompromisso));
		}
		
		return resultado.isEmpty() ? null : resultado;
	}
	
	public int atualizarCompromisso(Compromisso compromisso) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).atualizar(compromisso);
			return 1;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	
	public int excluirCompromisso(int idCompromisso) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).excluir(idCompromisso);
			return 1;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
}
