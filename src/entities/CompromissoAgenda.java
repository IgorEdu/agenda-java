package entities;

public class CompromissoAgenda {
	private int id;
	private int idCompromisso;
	private int idAgenda;
	
	public CompromissoAgenda(int id, int idCompromisso, int idAgenda) {
		super();
		this.id = id;
		this.idCompromisso = idCompromisso;
		this.idAgenda = idAgenda;
	}
	
	public CompromissoAgenda() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCompromisso() {
		return idCompromisso;
	}

	public void setIdCompromisso(int idCompromisso) {
		this.idCompromisso = idCompromisso;
	}

	public int getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(int idAgenda) {
		this.idAgenda = idAgenda;
	}
	
	
}
