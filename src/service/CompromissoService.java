package service;

//<<<<<<< HEAD
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import dao.BancoDados;
//import dao.CompromissoAgendaDAO;
//import dao.CompromissoDAO;
//import entities.Agenda;
//import entities.Compromisso;
//
//public class CompromissoService {
//	public CompromissoService() {
//		
//	}
//	
//	public void cadastrar(Compromisso compromisso, Agenda agenda) throws SQLException {
//		try {
//			Connection conn = BancoDados.conectar();
//			new CompromissoDAO(conn).cadastrar(compromisso);
//			
//			Connection conn2 = BancoDados.conectar();
//			compromisso = new CompromissoDAO(conn2).buscarCompromissoSemId(compromisso);
//			
//			Connection conn3 = BancoDados.conectar();
//			new CompromissoAgendaDAO(conn3).cadastrar(compromisso.getIdCompromisso(), agenda.getIdAgenda());
//			
//		} catch (SQLException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public void atualizar(Compromisso compromisso) throws SQLException {
//		try {
//			Connection conn = BancoDados.conectar();
//			new CompromissoDAO(conn).atualizar(compromisso);
//		} catch (SQLException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void excluir(Compromisso compromisso) throws SQLException {
//		try {
//			Connection conn = BancoDados.conectar();
//			new CompromissoDAO(conn).excluir(compromisso);
//		} catch (SQLException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//=======
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entities.Compromisso;

public class CompromissoService {

	List<Compromisso> compromissos;
	
	public CompromissoService() {
		
		this.compromissos = new ArrayList<Compromisso>();
		
		Compromisso comp1 = new Compromisso(1, "Comp 1", "Compromisso 1", Date.valueOf("2024-06-30"),"22:22",Date.valueOf("2024-06-30"),"23:00", "UTFPR",Date.valueOf("2024-06-30"),"22:00");
		this.compromissos.add(comp1);
	}
	
	public List<Compromisso> buscarCompromissosAgenda(int idAgenda){
		
		//O ID vai ser usado só quando integrar com o DAO, pq os compromissos são guardados dentro da agenda
		
		List<Compromisso> resultado = new ArrayList<Compromisso>();
		
		for(Compromisso c : compromissos) {
			
			resultado.add(c);
		}
		
		return resultado.isEmpty() ? null : resultado;
	}
	
	public int atualizarCompromisso(int idCompromisso, Compromisso novo) {
		
		for(Compromisso c : compromissos) {
			
			if(c.getIdCompromisso() == idCompromisso) {
				
				compromissos.add(novo);
				compromissos.remove(c);
				return 1;
			}
		}
		
		return 0;
//>>>>>>> 3bc438fbffbf85992b4b316887c2ee9472e368d7
	}
}
