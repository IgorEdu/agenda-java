package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.BancoDados;
import dao.CompromissoAgendaDAO;
import dao.CompromissoDAO;
import entities.Agenda;
import entities.Compromisso;

public class CompromissoService {
	private static final String SEPARADOR_CSV = ";";
	
	public CompromissoService() {
	}
	
	public void cadastrar(Compromisso compromisso, Agenda agenda) throws SQLException {
	try {
		Connection conn = BancoDados.conectar();
		new CompromissoDAO(conn).cadastrar(compromisso);
		
		Connection conn2 = BancoDados.conectar();
		compromisso = new CompromissoDAO(conn2).buscarCompromissoSemId(compromisso);
		
		Connection conn3 = BancoDados.conectar();
		new CompromissoAgendaDAO(conn3).cadastrar(compromisso.getIdCompromisso(), agenda.getIdAgenda());
		
	} catch (SQLException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public Compromisso buscarCompromissoPorId(int idCompromisso) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		Compromisso compromisso = new CompromissoDAO(conn).buscarCompromissoPorIdCompromisso(idCompromisso);
		
		return compromisso;
	}
	
	public List<Compromisso> buscarCompromissosAgenda(int idAgenda) throws SQLException, IOException{
		Connection conn = BancoDados.conectar();
		CompromissoAgendaDAO compromissoAgendaDAO = new CompromissoAgendaDAO(conn);
		
		List<Integer> listaIdCompromissos = new ArrayList<Integer>();
		listaIdCompromissos = compromissoAgendaDAO.listarIdCompromissosPorAgenda(idAgenda);
		
		List<Compromisso> resultado = new ArrayList<Compromisso>();
		
		
		for(Integer idCompromisso : listaIdCompromissos) {
			Connection conn2 = BancoDados.conectar();
			resultado.add(new CompromissoDAO(conn2).buscarCompromissoPorIdCompromisso(idCompromisso));
		}
		
		return resultado.isEmpty() ? null : resultado;
	}
	
	public int atualizarCompromisso(Compromisso compromisso) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).atualizar(compromisso);
			return 1;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	
	public int excluirCompromisso(int idCompromisso) throws SQLException {
		try {
			Connection conn = BancoDados.conectar();
			new CompromissoDAO(conn).excluir(idCompromisso);
			return 1;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	public void importarArquivoCSV(String path, Agenda agenda) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] vetor = line.split(SEPARADOR_CSV);
				
				
				Compromisso compromisso = new Compromisso();
				compromisso.setTitulo(vetor[0]);
				compromisso.setDescricao(vetor[1]);
				
				if(!String.valueOf(vetor[2]).isEmpty() && !String.valueOf(vetor[2]).equals("null")) {
					compromisso.setDataInicio(new java.sql.Date(sdf.parse(vetor[2]).getTime()));
				}
				
				compromisso.setHorarioInicio(vetor[3]);
				
				if(!String.valueOf(vetor[4]).isEmpty() && !String.valueOf(vetor[4]).equals("null")) {
					compromisso.setDataTermino(new java.sql.Date(sdf.parse(vetor[4]).getTime()));
				}
				
				if(!String.valueOf(vetor[8]).isEmpty() && !String.valueOf(vetor[8]).equals("null")) {
					compromisso.setHorarioNotificacao(vetor[8]);
				}
				
				compromisso.setHorarioTermino(vetor[5]);
				compromisso.setLocal(vetor[6]);
				
				if(!String.valueOf(vetor[7]).isEmpty() && !String.valueOf(vetor[7]).equals("null")) {
					compromisso.setDataNotificacao(new java.sql.Date(sdf.parse(vetor[7]).getTime()));
				}
				
				if(!String.valueOf(vetor[8]).isEmpty() && !String.valueOf(vetor[8]).equals("null")) {
					compromisso.setHorarioNotificacao(vetor[8]);
				}
					
				System.out.println();
				System.out.println("==============================");
				System.out.println(compromisso.getTitulo());
				System.out.println(compromisso.getDescricao());
				System.out.println(compromisso.getDataInicio());
				System.out.println(compromisso.getHorarioInicio());
				System.out.println(compromisso.getDataTermino());
				System.out.println(compromisso.getHorarioTermino());
				System.out.println(compromisso.getLocal());
				System.out.println(compromisso.getDataNotificacao());
				System.out.println(compromisso.getHorarioNotificacao());
				System.out.println("==============================");
				
				cadastrar(compromisso, agenda);
				
				line = br.readLine();
				
			}
		}
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void exportarArquivoCSV(String path, Agenda agenda) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String idAgenda = String.valueOf(agenda.getIdAgenda());
		
		String nomeArquivo;
		
		if(!path.endsWith(".csv")) nomeArquivo = path.concat(".csv");
		else nomeArquivo = path;
		
		List<Compromisso> compromissos = new ArrayList<Compromisso>();
//		boolean arquivoExiste = new File(path).exists();
//		if(new File(path))
		try {
			FileWriter writer = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1);
			
			writer.write("Titulo" + SEPARADOR_CSV + "Descricao" + SEPARADOR_CSV + "DataInicio" + SEPARADOR_CSV 
					+ "HorarioInicio"+ SEPARADOR_CSV + "DataTermino" + SEPARADOR_CSV + "HorarioTermino" + SEPARADOR_CSV 
					+ "Local" + SEPARADOR_CSV + "DataNotificacao" + SEPARADOR_CSV + "HorarioNotificacao\n");
			
			compromissos = buscarCompromissosAgenda(agenda.getIdAgenda());
			
			for(Compromisso compromisso : compromissos) {
				String dataInicioFormatada;
				String dataTerminoFormatada;
				String dataNotificacaoFormatada;
				
				dataInicioFormatada = (String.valueOf(compromisso.getDataInicio()).isEmpty() || String.valueOf(compromisso.getDataInicio()).equals("null")) ? null : String.valueOf(sdf.format(compromisso.getDataInicio()));
				dataTerminoFormatada = (String.valueOf(compromisso.getDataTermino()).isEmpty() || String.valueOf(compromisso.getDataTermino()).equals("null")) ? null : String.valueOf(sdf.format(compromisso.getDataTermino()));
				dataNotificacaoFormatada = (String.valueOf(compromisso.getDataNotificacao()).isEmpty() || String.valueOf(compromisso.getDataNotificacao()).equals("null")) ? null : String.valueOf(sdf.format(compromisso.getDataNotificacao()));
				
				String titulo = compromisso.getTitulo().contains(";") ? compromisso.getTitulo().replaceAll(";", "|"): compromisso.getTitulo();
				String descricao = compromisso.getDescricao().contains(";") ? compromisso.getDescricao().replaceAll(";", "|") : compromisso.getDescricao();
				String local = compromisso.getLocal().contains(";") ? compromisso.getLocal().replaceAll(";", "|") : compromisso.getLocal();
				
				writer.write(titulo + SEPARADOR_CSV + descricao 
					+ SEPARADOR_CSV + dataInicioFormatada + SEPARADOR_CSV + compromisso.getHorarioInicio() 
					+ SEPARADOR_CSV + dataTerminoFormatada + SEPARADOR_CSV + compromisso.getHorarioTermino() + SEPARADOR_CSV + local 
					+ SEPARADOR_CSV + dataNotificacaoFormatada + SEPARADOR_CSV + compromisso.getHorarioNotificacao() + "\n"
				);
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
