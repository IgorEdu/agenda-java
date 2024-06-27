package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Agenda;
import entities.CompromissoAgenda;
import entities.Usuario;
import service.AgendaService;
import service.UsuarioService;


public class CompromissoAgendaDAO {
private Connection conn;
	
	public CompromissoAgendaDAO(Connection conn) {	
		this.conn = conn;
	}
	
	public void cadastrar(int idCompromisso, int idAgenda) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"insert into compromissos_agendas(id_compromissos, id_agendas) values (?, ?)");

			st.setInt(1, idCompromisso);
			st.setInt(2, idAgenda);

			st.executeUpdate();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizar(CompromissoAgenda compromissoAgenda) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update compromissos_agendas set id_compromissos = ?, id_agendas = ? where id = ?"
					);
			
			st.setInt(1, compromissoAgenda.getIdCompromisso());
			st.setInt(2, compromissoAgenda.getIdAgenda());
			st.setInt(3, compromissoAgenda.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public void excluir(int id) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from compromissos_agendas where id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public CompromissoAgenda buscarCompromissoAgendaPorAgendaECompromisso(int idAgenda, int idCompromisso) throws SQLException, IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_compromissos, id_agendas from compromissos_agendas where id_compromissos = ? and id_agendas = ?");
			st.setInt(1, idCompromisso);
			st.setInt(2, idAgenda);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				CompromissoAgenda compromissoAgenda = new CompromissoAgenda();
				compromissoAgenda.setId(rs.getInt(1));
				compromissoAgenda.setIdCompromisso(rs.getInt(2));
				compromissoAgenda.setIdAgenda(rs.getInt(3));

				return compromissoAgenda;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public CompromissoAgenda buscarCompromissoAgendaPorId(int idCompromissoAgenda) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_compromissos, id_agendas from compromissos_agendas where id = ?");
			st.setInt(1, idCompromissoAgenda);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				CompromissoAgenda compromissoAgenda = new CompromissoAgenda();
				compromissoAgenda.setId(rs.getInt(1));
				compromissoAgenda.setIdCompromisso(rs.getInt(2));
				compromissoAgenda.setIdAgenda(rs.getInt(3));

				return compromissoAgenda;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Integer> listarIdCompromissosPorAgenda(int idAgenda) throws SQLException{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<Integer> idsCompromissos = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("select id, id_compromissos, id_agendas from compromissos_agendas where id_agendas = ?");
			st.setInt(1, idAgenda);
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				
				CompromissoAgenda compromissoAgenda = new CompromissoAgenda();
				compromissoAgenda.setId(rs.getInt(1));
				compromissoAgenda.setIdCompromisso(rs.getInt(2));
				compromissoAgenda.setIdAgenda(rs.getInt(3));

				idsCompromissos.add(compromissoAgenda.getIdCompromisso());
			}
			
			return idsCompromissos;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public List<Integer> listarIdCompromissosPorUsuario(int idUsuario) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		AgendaService agendaService = new AgendaService();
		UsuarioService usuarioService = new UsuarioService();
		
		Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
		
		List<Integer> idsCompromissos = new ArrayList<>();
		
		List<Agenda> agendasUsuario = new ArrayList<Agenda>();
		List<Integer> idsAgendasUsuario = new ArrayList<Integer>();
		
		agendasUsuario = agendaService.buscarAgendasUsuario(usuario);
		for(Agenda agenda : agendasUsuario) {
			idsAgendasUsuario.add(agenda.getIdAgenda());
		}

		try {
			for(Integer idAgenda : idsAgendasUsuario) {
				st = conn.prepareStatement("select id, id_compromissos, id_agendas from compromissos_agendas where id_agendas in ?");
				st.setInt(1, idAgenda);
				
				rs = st.executeQuery();
				
				while (rs.next()) {
					
					CompromissoAgenda compromissoAgenda = new CompromissoAgenda();
					compromissoAgenda.setId(rs.getInt(1));
					compromissoAgenda.setIdCompromisso(rs.getInt(2));
					compromissoAgenda.setIdAgenda(rs.getInt(3));

					idsCompromissos.add(compromissoAgenda.getIdCompromisso());
				}
			}
			
			
			return idsCompromissos;
			
		}
		finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
