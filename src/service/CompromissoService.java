package service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entities.Compromisso;

public class CompromissoService {

	List<Compromisso> compromissos;
	
	public CompromissoService() {
		
		this.compromissos = new ArrayList<Compromisso>();
		
		Compromisso comp1 = new Compromisso(1, "Comp 1", "Compromisso 1", Date.valueOf("2024-06-30"),"22:22",Date.valueOf("2024-06-30"),"23:00", "UTFPR",Date.valueOf("2024-06-30"),"22:00");
		this.compromissos.add(comp1);
	}
	
	public List<Compromisso> buscarCompromissosAgenda(int idAgenda){
		
		//O ID vai ser usado só quando integrar com o DAO, pq os compromissos são guardados dentro da agenda
		
		List<Compromisso> resultado = new ArrayList<Compromisso>();
		
		for(Compromisso c : compromissos) {
			
			resultado.add(c);
		}
		
		return resultado.isEmpty() ? null : resultado;
	}
	
	public int atualizarCompromisso(int idCompromisso, Compromisso novo) {
		
		for(Compromisso c : compromissos) {
			
			if(c.getIdCompromisso() == idCompromisso) {
				
				compromissos.add(novo);
				compromissos.remove(c);
				return 1;
			}
		}
		
		return 0;
	}
	
	public int excluirCompromisso(int idCompromisso) {
		
		for(Compromisso c : compromissos) {
			
			if(c.getIdCompromisso() == idCompromisso) {
				
				compromissos.remove(c);
				return 1;
			}
		}
		
		return 0;
	}
}
