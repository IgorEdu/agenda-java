package service;

import java.util.ArrayList;
import java.util.List;

import entities.Agenda;
import entities.Usuario;

public class AgendaService {

	private List<Agenda> agendas;
	
	public AgendaService() {
		
		this.agendas = new ArrayList<Agenda>();
		
		//int idAgenda, Usuario usuario, String nomeAgenda, String descricao, List<Compromisso> compromissos
		cadastrarAgenda(new Agenda(0, new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"), "Agenda1", "agenda 1", null));
		cadastrarAgenda(new Agenda(1, new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"), "Agenda2", "agenda 2", null));
		cadastrarAgenda(new Agenda(2, new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"), "Agenda3", "agenda 3", null));
		cadastrarAgenda(new Agenda(3, new Usuario("joaozinho", "bambam", "Joaozinho Santos", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "joaozinho@outlook.com", "joao.png"), "Agenda4", "agenda 4", null));
	
	}
	
	public List<Agenda> pesquisarAgendasUsuario(Usuario usuario){
		
		List<Agenda> resultado = new ArrayList<Agenda>();
		
		for(Agenda agenda : agendas) {
			
			if(agenda.getUsuario().getUsername().equalsIgnoreCase(usuario.getUsername())) {
				resultado.add(agenda);
			}
		}
		
		return resultado;
	}
	
	public void cadastrarAgenda(Agenda agenda) {
		this.agendas.add(agenda);
	}
	
	public void excluirAgenda(Agenda agenda) {
		this.agendas.remove(agenda);
	}
}
