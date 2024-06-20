package service;

import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ConviteDAO;
import entities.Convite;

public class ConviteService {

	public void cadastrar(Convite convite) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).cadastrar(convite);
	}
	
	public void atualizar(Convite convite) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).atualizar(convite);
	}
	
	public void excluir(Convite convite) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).excluir(convite);
	}
	
	public List<Convite> buscarConvitesPorIdConvidado(int idUsuario) throws SQLException, IOException{
		
		List<Convite> convites = null;
		
		try {
			Connection conn = BancoDados.conectar();
			convites = new ConviteDAO(conn).buscarConvitesPorIdConvidado(idUsuario);
			
		} finally {
			BancoDados.desconectar();
		}
		return convites;
	}
	
	public List<Convite> buscarConvitesPorIdCompromisso(int idCompromisso) throws SQLException, IOException {
		
		List<Convite> convites = null;
		
		Connection conn = BancoDados.conectar();
		convites = new ConviteDAO(conn).buscarConvitesPorIdCompromisso(idCompromisso);
		
		return convites;
	}
}
