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
	
	public int atualizar(Convite convite) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		int res = new ConviteDAO(conn).atualizar(convite);
		return res;
	}
	
	public int excluir(Convite convite) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new ConviteDAO(conn).excluir(convite);
	}
	
	public Convite buscarConvitePorIdConvite(int idConvite) throws SQLException, IOException{
		
		Convite convite = null;
		
		Connection conn = BancoDados.conectar();
		convite = new ConviteDAO(conn).buscarConvitePorIdConvite(idConvite);
		
		return convite;
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
	
	public List<Convite> buscarConvitesPorIdCompromissoEIdUsuario(int idCompromisso, int idUsuario) throws SQLException, IOException{
		
		List<Convite> convites = null;
		
		Connection conn = BancoDados.conectar();
		convites = new ConviteDAO(conn).buscarConvitesPorIdCompromissoEIdUsuario(idCompromisso, idUsuario);
		
		return convites;
	}
}
