package service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;

public class CompromissoServiceTest {
	
	public static void cadastrarTest(Compromisso compromisso, Agenda agenda) throws SQLException {
		CompromissoService compromissoService = new CompromissoService();
		
		compromissoService.cadastrar(compromisso, agenda);
	}
	
	public static List<Compromisso> buscarCompromissosAgendaTest(int idAgenda) throws SQLException, IOException{
		CompromissoService compromissoService = new CompromissoService();
		
		List<Compromisso> resultado = new ArrayList<Compromisso>();
		resultado = compromissoService.buscarCompromissosAgenda(idAgenda);
		
		return resultado.isEmpty() ? null : resultado;
	}
	
	public static void atualizarCompromissoTest(Compromisso compromisso) throws SQLException {
		CompromissoService compromissoService = new CompromissoService();
		
		try {
			int retorno =  compromissoService.atualizarCompromisso(compromisso);
			
			if(retorno == 1) {
				System.out.println("Compromisso: " + compromisso.getIdCompromisso() + " atualizado!");
			} else {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void excluirCompromissoTest(int idCompromisso) {
		CompromissoService compromissoService = new CompromissoService();
		
		try {
			int retorno =  compromissoService.excluirCompromisso(idCompromisso);
			
			if(retorno == 1) {
				System.out.println("Compromisso: " + idCompromisso + " excluido!");
			} else {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void importarArquivoCSVTest(String path, Agenda agenda) {
		CompromissoService compromissoService = new CompromissoService();
		
		compromissoService.importarArquivoCSV(path, agenda);
	}
	
	public static void exportarArquivoCSV(String path, Agenda agenda) {
		CompromissoService compromissoService = new CompromissoService();
		
		compromissoService.exportarArquivoCSV(path, agenda);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			UsuarioService usuarioService = new UsuarioService();
			AgendaService agendaService = new AgendaService();
			
			Usuario usuario = usuarioService.buscarUsuarioPorLogin("novo");
			
			List<Agenda> agendas = agendaService.buscarAgendasUsuario(usuario);
			Agenda agenda1 = agendas.get(0);
			
			Agenda agenda2 = agendas.get(1);

			
			Compromisso compromisso = new Compromisso();
			compromisso.setTitulo("Teste service");
			compromisso.setDescricao("Compromisso de teste na service");
			compromisso.setDataInicio(new java.sql.Date(sdf.parse("01/01/2024").getTime()));
			compromisso.setHorarioInicio("10:01:30");
			compromisso.setDataTermino(new java.sql.Date(sdf.parse("01/01/2024").getTime()));
			compromisso.setHorarioTermino("10:50:30");
			compromisso.setLocal("Sala");
			
			
			cadastrarTest(compromisso, agenda1);
			
			exportarArquivoCSV("./assets", agenda1);
			
			importarArquivoCSVTest("./assets/compromissos-idagenda-11.csv", agenda2);
		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
