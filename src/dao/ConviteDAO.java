package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Convite;
import entities.StatusConvite;

public class ConviteDAO {
	private Connection conn;
		
	public ConviteDAO(Connection conn) {	
		this.conn = conn;
	}
	
	public void cadastrar(Convite convite) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"insert into convites (id_convidado, status, id_compromisso) values (?, ?, ?)");

			st.setInt(1, convite.getUsuario().getId());
			st.setString(2, convite.getStatusConvite().name());
			st.setInt(3, convite.getCompromisso().getIdCompromisso());

			st.executeUpdate();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizar(Convite convite) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update convites set id_convidado = ?, status = ?, id_compromisso = ? where id = ?"
					);
			
			st.setInt(1, convite.getUsuario().getId());
			st.setString(2, convite.getStatusConvite().name());
			st.setInt(3, convite.getCompromisso().getIdCompromisso());
			st.setInt(4, convite.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public void excluir(Convite convite) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from convites where id = ?");
			st.setInt(1, convite.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public Convite buscarConvitePorIdConvite(int idConvite) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_convidado, status, id_compromisso from convites where id = ?");
			st.setInt(1, idConvite);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				Convite convite = new Convite();
				convite.setId(rs.getInt(1));
				convite.getUsuario().setId(rs.getInt(2));
				convite.setStatusConvite(StatusConvite.valueOf(rs.getString(3)));
				convite.getCompromisso().setIdCompromisso(rs.getInt(4));

				return convite;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Convite> buscarConvitesPorIdConvidado(int idConvidado) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_convidado, status, id_compromisso from convites where id_convidado = ?");
			st.setInt(1, idConvidado);
			
			rs = st.executeQuery();
			
			List<Convite> listaConvites = new ArrayList<>();
			
			while (rs.next()) {
				Convite convite = new Convite();
				convite.setId(rs.getInt(1));
				convite.getUsuario().setId(rs.getInt(2));
				convite.setStatusConvite(StatusConvite.valueOf(rs.getString(3)));
				convite.getCompromisso().setIdCompromisso(rs.getInt(4));			

				listaConvites.add(convite);
			}
			
			return listaConvites;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Convite> buscarConvitesPorIdCompromisso(int idCompromisso) throws SQLException, IOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, id_convidado, status, id_compromisso from convites where id_compromisso = ?");
			st.setInt(1, idCompromisso);
			
			rs = st.executeQuery();
			
			List<Convite> listaConvites = new ArrayList<>();
			
			while (rs.next()) {
				Convite convite = new Convite();
				convite.setId(rs.getInt(1));
				convite.getUsuario().setId(rs.getInt(2));
				convite.setStatusConvite(StatusConvite.valueOf(rs.getString(3)));
				convite.getCompromisso().setIdCompromisso(rs.getInt(4));			

				listaConvites.add(convite);
			}
			
			return listaConvites;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
