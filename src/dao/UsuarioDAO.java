package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public Usuario buscarUsuarioPorLogin(String login) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, login, senha, nome, nascimento, genero, email from usuarios where login like ?");
			st.setString(1, login);
			
			rs = st.executeQuery();
			
			if (rs.next()) {

				Usuario usuario = new Usuario();
				
				usuario.setId(rs.getInt(1));
				usuario.setUsername(rs.getString(2));
				usuario.setSenhaCriptografada(rs.getString(3));
				usuario.setNomeUsuario(rs.getString(4));
				usuario.setDataNascimento(rs.getDate(5));
				usuario.setGenero(rs.getString(6));
				usuario.setEmail(rs.getString(7));

				return usuario;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Usuario buscarUsuarioPorId(int id) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select id, login, senha, nome, nascimento, genero, email from usuarios where id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()) {

				Usuario usuario = new Usuario();
				
				usuario.setId(rs.getInt(1));
				usuario.setUsername(rs.getString(2));
				usuario.setSenhaCriptografada(rs.getString(3));
				usuario.setNomeUsuario(rs.getString(4));
				usuario.setDataNascimento(rs.getDate(5));
				usuario.setGenero(rs.getString(6));
				usuario.setEmail(rs.getString(7));

				return usuario;
			}
			
			return null;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Usuario> listarOutrosUsuario(Usuario usuarioLogado) throws SQLException{
		List<Usuario> outrosUsuarios = new ArrayList<Usuario>();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select id, login, senha, nome, nascimento, genero, email from usuarios where id <> ?");
			st.setInt(1, usuarioLogado.getId());
			
			rs = st.executeQuery();
			
			while (rs.next()) {

				Usuario usuario = new Usuario();
				
				usuario.setId(rs.getInt(1));
				usuario.setUsername(rs.getString(2));
				usuario.setSenhaCriptografada(rs.getString(3));
				usuario.setNomeUsuario(rs.getString(4));
				usuario.setDataNascimento(rs.getDate(5));
				usuario.setGenero(rs.getString(6));
				usuario.setEmail(rs.getString(7));

				outrosUsuarios.add(usuario);
			}
			
			return outrosUsuarios;
			
		}  finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
