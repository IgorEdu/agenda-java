package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import entities.Compromisso;
import entities.Convite;

public class CompromissoDAOTest {
	public static void cadastrarCompromissoDAOTest(Compromisso compromisso) throws SQLException, IOException, ParseException {
		Connection conn = BancoDados.conectar();

		new CompromissoDAO(conn).cadastrar(compromisso);

		System.out.println("Compromisso cadastrado com sucesso.");
	}
	
	public static void atualizarCompromissoDAOTest(Compromisso compromisso) throws SQLException, IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		compromisso.setTitulo("Compromisso teste");
		compromisso.setDescricao("Compromisso usado para testar a classe DAO");
		compromisso.setDataInicio(new java.sql.Date(sdf.parse("05/05/2024").getTime()));
		compromisso.setHorarioInicio("13:30:59");
		compromisso.setDataTermino(new java.sql.Date(sdf.parse("05/05/2024").getTime()));
		compromisso.setHorarioTermino("16:30:59");
		compromisso.setLocal("Sala de reuniões");
		compromisso.setConvites(new ArrayList<Convite>());
		compromisso.setDataTermino(new java.sql.Date(sdf.parse("05/05/2024").getTime()));
		compromisso.setHorarioTermino("12:30:00");

		Connection conn = BancoDados.conectar();
		new CompromissoDAO(conn).atualizar(compromisso);

		System.out.println("Compromisso atualizado com sucesso");
	}
	
	public static void excluirCompromissoDAOTest(Compromisso compromisso) throws SQLException, IOException, ParseException {

		
		Connection conn = BancoDados.conectar();
		new CompromissoDAO(conn).excluir(compromisso.getIdCompromisso());

		System.out.println("Compromisso " + compromisso.getIdCompromisso() + " excluido com sucesso.");
	}
	
	public static Compromisso buscarCompromissoPorIdCompromissoDAOTest(int idCompromisso) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		Compromisso compromisso = new CompromissoDAO(conn).buscarCompromissoPorIdCompromisso(idCompromisso);
		
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Compromisso teste de busca:");
		System.out.println("IdCompromisso: " + compromisso.getIdCompromisso());
		System.out.println("Titulo: " + compromisso.getTitulo());
		System.out.println("Descricao: "+ compromisso.getDescricao());
		System.out.println("DataInicio: " + compromisso.getDataInicio());
		System.out.println("HorarioInicio: " + compromisso.getHorarioInicio());
		System.out.println("DataTermino: " + compromisso.getDataTermino());
		System.out.println("HorarioTermino: " + compromisso.getHorarioTermino());
		System.out.println("Local: " + compromisso.getLocal());
		//Só verificar se traz o objeto
		System.out.println("Convites: " + compromisso.getConvites());
		System.out.println("DataNotificacao: " + compromisso.getDataNotificacao());
		System.out.println("HorarioNotificacao: " + compromisso.getHorarioNotificacao());
		System.out.println("======================================================");
		System.out.println();
		
		return compromisso;
	}
	
	public static Compromisso buscarCompromissoSemIdCompromissoDAOTest(Compromisso compromissoBuscado) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		CompromissoDAO compromissoDAO = new CompromissoDAO(conn);
		Compromisso compromisso = compromissoDAO.buscarCompromissoSemId(compromissoBuscado);
		
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Compromisso teste de busca:");
		System.out.println("IdCompromisso: " + compromisso.getIdCompromisso());
		System.out.println("Titulo: " + compromisso.getTitulo());
		System.out.println("Descricao: "+ compromisso.getDescricao());
		System.out.println("DataInicio: " + compromisso.getDataInicio());
		System.out.println("HorarioInicio: " + compromisso.getHorarioInicio());
		System.out.println("DataTermino: " + compromisso.getDataTermino());
		System.out.println("HorarioTermino: " + compromisso.getHorarioTermino());
		System.out.println("Local: " + compromisso.getLocal());
		//Só verificar se traz o objeto
		System.out.println("Convites: " + compromisso.getConvites());
		System.out.println("DataNotificacao: " + compromisso.getDataNotificacao());
		System.out.println("HorarioNotificacao: " + compromisso.getHorarioNotificacao());
		System.out.println("======================================================");
		System.out.println();
		
		return compromisso;
	}
	
	public static void main(String[] args) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Compromisso compromisso = new Compromisso();
			
			compromisso.setTitulo("Compromisso teste");
			compromisso.setDescricao("Compromisso usado para testar a classe DAO");
			compromisso.setDataInicio(new java.sql.Date(sdf.parse("05/05/2024").getTime()));
			compromisso.setHorarioInicio("12:30:59");
			compromisso.setDataTermino(new java.sql.Date(sdf.parse("05/05/2024").getTime()));
			compromisso.setHorarioTermino("15:30:59");
			compromisso.setLocal("Sala de reuniões");
			compromisso.setConvites(new ArrayList<Convite>());
			compromisso.setDataTermino(new java.sql.Date(sdf.parse("05/05/2024").getTime()));
			compromisso.setHorarioTermino("11:30:00");
			
			CompromissoDAOTest.cadastrarCompromissoDAOTest(compromisso);
			
			Compromisso compromisso1 = CompromissoDAOTest.buscarCompromissoSemIdCompromissoDAOTest(compromisso);
			
			CompromissoDAOTest.atualizarCompromissoDAOTest(compromisso1);
			
			Compromisso compromisso2 = CompromissoDAOTest.buscarCompromissoPorIdCompromissoDAOTest(2);
			
			CompromissoDAOTest.excluirCompromissoDAOTest(compromisso2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (SQLException | IOException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}
}
