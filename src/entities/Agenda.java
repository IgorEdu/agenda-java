package entities;

import java.util.List;

public class Agenda {

	private String idAgenda;
	private String nomeAgenda;
	private String descricao;
	private List<Compromisso> compromissos;

	Agenda(){
		
	}

	public String getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(String idAgenda) {
		this.idAgenda = idAgenda;
	}

	public String getNomeAgenda() {
		return nomeAgenda;
	}

	public void setNomeAgenda(String nomeAgenda) {
		this.nomeAgenda = nomeAgenda;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Compromisso> getCompromissos() {
		return compromissos;
	}

	public void setCompromissos(List<Compromisso> compromissos) {
		this.compromissos = compromissos;
	}
}
