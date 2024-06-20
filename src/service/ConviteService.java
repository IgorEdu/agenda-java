package service;

import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ConviteDAO;
import entities.Convite;

public class ConviteService {

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
}
