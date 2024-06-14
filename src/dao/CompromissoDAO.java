package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Compromisso;

public class CompromissoDAO {
	private Connection conn;
	
	public CompromissoDAO(Connection conn) {	
		this.conn = conn;
	}
	
	public void cadastrar(Compromisso compromisso) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"insert into compromissos (titulo, descricao, data_inicio, horario_inicio, data_termino, horario_termino, local, data_notificacao, horario_notificacao) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			st.setString(1, compromisso.getTitulo());
			st.setString(2, compromisso.getDescricao());
			st.setDate(3, compromisso.getDataInicio());
			st.setString(4, compromisso.getHorarioInicio());
			st.setDate(5, compromisso.getDataTermino());
			st.setString(6, compromisso.getHorarioTermino());
			st.setString(7, compromisso.getLocal());
			st.setDate(8, compromisso.getDataNotificacao());
			st.setString(9, compromisso.getHorarioNotificacao());

			st.executeUpdate();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizar(Compromisso compromisso) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update compromissos titulo = ?, descricao = ?, data_inicio = ?, horario_inicio = ?, "
					+ "data_termino = ?, horario_termino = ?, local = ?, data_notificacao = ?, horario_notificacao = ?"
					+ " where id = ?"
					);
			
			st.setString(1, compromisso.getTitulo());
			st.setString(2, compromisso.getDescricao());
			st.setDate(3, compromisso.getDataInicio());
			st.setString(4, compromisso.getHorarioInicio());
			st.setDate(5, compromisso.getDataTermino());
			st.setString(6, compromisso.getHorarioTermino());
			st.setString(7, compromisso.getLocal());
			st.setDate(8, compromisso.getDataNotificacao());
			st.setString(9, compromisso.getHorarioNotificacao());
			st.setInt(10, compromisso.getIdCompromisso());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public void excluir(Compromisso compromisso) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from compromissos where id = ?");
			st.setInt(1, compromisso.getIdCompromisso());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public Compromisso buscarCompromissoPorIdCompromisso(int idCompromisso) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("id, titulo, descricao, data_inicio, horario_inicio, "
					+ "data_termino, horario_termino, local, "
					+ "data_notificacao, horario_notificacao "
					+ "from convites "
					+ "where id = ?");
			st.setInt(1, idCompromisso);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				Compromisso compromisso = new Compromisso();
				compromisso.setIdCompromisso(rs.getInt(1));
				compromisso.setTitulo(rs.getString(2));
				compromisso.setDescricao(rs.getString(3));
				compromisso.setDataInicio(rs.getDate(4));
				compromisso.setHorarioInicio(rs.getString(5));
				compromisso.setDataTermino(rs.getDate(6));
				compromisso.setHorarioTermino(rs.getString(7));
				compromisso.setLocal(rs.getString(8));
				compromisso.setDataNotificacao(rs.getDate(9));
				compromisso.setHorarioNotificacao(rs.getString(10));

				return compromisso;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
