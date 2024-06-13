package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Agenda;
import entities.Usuario;

public class AgendaDAO {
	private Connection conn;
	
	public AgendaDAO(Connection conn) {

		this.conn = conn;
	}
	
	public void cadastrar(Agenda agenda) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"insert into agendas (id_usuario, nome, descricao) values (?, ?, ?)");

			st.setInt(1, agenda.getUsuario().getId());
			st.setString(2, agenda.getNomeAgenda());
			st.setString(3, agenda.getDescricao());

			st.executeUpdate();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizar(Agenda agenda) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update agendas set nome = ?, descricao = ? where id = ?"
					);
			
			st.setString(1, agenda.getNomeAgenda());
			st.setString(2, agenda.getDescricao());
			st.setInt(3, agenda.getIdAgenda());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public void excluir(Agenda agenda) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from agendas where id = ?");
			st.setInt(1, agenda.getIdAgenda());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public Agenda buscarAgendaPorIdAgenda(int idAgenda) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_usuario, nome, descricao from agendas where id = ?");
			st.setInt(1, idAgenda);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				Agenda agenda = new Agenda();
				agenda.setIdAgenda(rs.getInt(1));
				agenda.getUsuario().setId(rs.getInt(2));
				agenda.setNomeAgenda(rs.getString(3));
				agenda.setDescricao(rs.getString(4));

				return agenda;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Agenda> buscarAgendaPorIdUsuario(int idUsuario) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_usuario, nome, descricao from agendas where id_usuario = ?");
			st.setInt(1, idUsuario);
			
			rs = st.executeQuery();
			
			List<Agenda> listaAgendas = new ArrayList<>();
			
			while (rs.next()) {
				Agenda agenda = new Agenda();
				agenda.setIdAgenda(rs.getInt(1));
				agenda.getUsuario().setId(rs.getInt(2));
				agenda.setNomeAgenda(rs.getString(3));
				agenda.setDescricao(rs.getString(4));			

				listaAgendas.add(agenda);
			}
			
			return listaAgendas;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
