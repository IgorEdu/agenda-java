package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Compromisso;
import entities.Usuario;

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
					"update compromissos set titulo = ?, descricao = ?, data_inicio = ?, horario_inicio = ?, "
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
	
	public void excluir(int idCompromisso) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from compromissos where id = ?");
			st.setInt(1, idCompromisso);
			
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
			st = conn.prepareStatement("select id, titulo, descricao, data_inicio, horario_inicio, "
					+ "data_termino, horario_termino, local, "
					+ "data_notificacao, horario_notificacao "
					+ "from compromissos "
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
	
	public Compromisso buscarCompromissoSemId(Compromisso compromisso) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, titulo, descricao, data_inicio, horario_inicio, "
					+ "data_termino, horario_termino, local, "
					+ "data_notificacao, horario_notificacao "
					+ "from compromissos "
					+ "where titulo like ? and descricao like ? and data_inicio = ? and horario_inicio = ?"
					+ " and data_termino = ? and horario_termino = ? and local like ?");
			st.setString(1, compromisso.getTitulo());
			st.setString(2, compromisso.getDescricao());
			st.setDate(3, compromisso.getDataInicio());
			st.setString(4, compromisso.getHorarioInicio());
			st.setDate(5, compromisso.getDataTermino());
			st.setString(6, compromisso.getHorarioTermino());
			st.setString(7, compromisso.getLocal());
//			st.setDate(8, compromisso.getDataNotificacao());
//			st.setString(9, compromisso.getHorarioNotificacao());
			
			rs = st.executeQuery();
			
			if (rs.next()) {
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
	
	public List<Compromisso> buscarCompromissosPorUsuario(int idUsuario) throws SQLException, IOException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			
			st = conn.prepareStatement("select t1.id, t1.titulo, t1.descricao, t1.data_inicio, t1.horario_inicio, "
					+ "t1.data_termino, t1.horario_termino, t1.local, "
					+ "t1.data_notificacao, t1.horario_notificacao "
					+ "from compromissos as t1, compromissos_agendas, agendas, usuarios "
					+ "where usuarios.id = ? "
					+ "and usuarios.id = agendas.id_usuario "
					+ "and agendas.id = compromissos_agendas.id_agendas "
					+ "and compromissos_agendas.id_compromissos = t1.id");
			st.setInt(1, idUsuario);
			
			List<Compromisso> compromissos = new ArrayList<Compromisso>();
			rs = st.executeQuery();
			
			while(rs.next()) {
				
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
				
				compromissos.add(compromisso);
			}
			
			return compromissos;
			
		}finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
