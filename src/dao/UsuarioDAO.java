package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Usuario;

public class UsuarioDAO {

	private Connection conn;

	public UsuarioDAO(Connection conn) {

		this.conn = conn;
	}
	
	public void cadastrar(Usuario usuario) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"insert into usuarios (login, senha, nome, nascimento, genero, email) values (?, ?, ?, ?, ?, ?)");

			st.setString(1, usuario.getUsername());
			st.setString(2, usuario.getSenhaCriptografada());
			st.setString(3, usuario.getNomeUsuario());
			st.setDate(4, usuario.getDataNascimento());
			st.setString(5, usuario.getGenero());
			st.setString(6, usuario.getEmail());

			st.executeUpdate();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizar(Usuario usuario) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update usuarios set login = ?, senha = ?, nome = ?, nascimento = ?, genero = ?, email = ? where id = ?"
					);
			
			st.setString(1, usuario.getUsername());
			st.setString(2, usuario.getSenhaCriptografada());
			st.setString(3, usuario.getNomeUsuario());
			st.setDate(4, usuario.getDataNascimento());
			st.setString(5, usuario.getGenero());
			st.setString(6, usuario.getEmail());
			st.setInt(7, usuario.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public void excluir(Usuario usuario) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from usuarios where id = ?");
			st.setInt(1, usuario.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
}
