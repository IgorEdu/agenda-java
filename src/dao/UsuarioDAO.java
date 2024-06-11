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
}
