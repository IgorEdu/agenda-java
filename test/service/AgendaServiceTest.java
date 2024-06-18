package service;

import java.util.List;

import entities.Agenda;
import entities.Usuario;

public class AgendaServiceTest {
	public static void main(String[] args) {		
		AgendaService agendaService = new AgendaService();
		
		Usuario usuario = new Usuario("joaozinho", "bambam", "Joaozinho Santos", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "joaozinho@outlook.com", "joao.png");
		Agenda agenda = new Agenda(5, usuario, "Agenda4", "agenda 4");
		
		agendaService.cadastrarAgenda(agenda);
		List<Agenda> agendasUsuario = agendaService.buscarAgendasUsuario(usuario);
		
		for(Agenda agendaUsuario : agendasUsuario) {
			System.out.println("=================================");
			System.out.println("IdAgenda: " + agendaUsuario.getIdAgenda());
			System.out.println("Username: " + agendaUsuario.getUsuario().getUsername());
			System.out.println("NomeAgenda: " + agendaUsuario.getNomeAgenda());
			System.out.println("Descricao: " + agendaUsuario.getDescricao());
			System.out.println("Compromissos: " + agendaUsuario.getCompromissos());
			System.out.println("=================================");
			System.out.println();
		}
		
		agendaService.excluirAgenda(agenda.getIdAgenda());
		agendasUsuario = agendaService.buscarAgendasUsuario(usuario);
		
		for(Agenda agendaUsuario : agendasUsuario) {
			System.out.println("=================================");
			System.out.println("IdAgenda: " + agendaUsuario.getIdAgenda());
			System.out.println("Username: " + agendaUsuario.getUsuario().getUsername());
			System.out.println("NomeAgenda: " + agendaUsuario.getNomeAgenda());
			System.out.println("Descricao: " + agendaUsuario.getDescricao());
			System.out.println("Compromissos: " + agendaUsuario.getCompromissos());
			System.out.println("=================================");
			System.out.println();
		}
	}
}
