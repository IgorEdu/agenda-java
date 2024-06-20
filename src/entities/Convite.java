package entities;

public class Convite {

	private int id;
	private Usuario usuario;
	private StatusConvite statusConvite;
	private Compromisso compromisso;
	
	public Convite() {
		this.usuario = new Usuario();
		this.compromisso = new Compromisso();
	}
	
	public Convite(int id, Usuario usuario, StatusConvite statusConvite, Compromisso compromisso) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.statusConvite = StatusConvite.PENDENTE;
		this.compromisso = compromisso;
	}
	
	public Convite(Usuario usuario, StatusConvite statusConvite, Compromisso compromisso) {
		super();
		this.usuario = usuario;
		this.statusConvite = StatusConvite.PENDENTE;
		this.compromisso = compromisso;
	}
	
	public boolean aceitarConvite() {
		if(this.statusConvite == StatusConvite.PENDENTE) {
			statusConvite = StatusConvite.ACEITO;
			return true;
		}
		return false;
	}
	
	public boolean recusarConvite() {
		if(this.statusConvite == StatusConvite.PENDENTE) {
			statusConvite = StatusConvite.REJEITADO;
			return true;
		}
		return false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public StatusConvite getStatusConvite() {
		return statusConvite;
	}
	public void setStatusConvite(StatusConvite statusConvite) {
		this.statusConvite = statusConvite;
	}
	public Compromisso getCompromisso() {
		return compromisso;
	}
	public void setCompromisso(Compromisso compromisso) {
		this.compromisso = compromisso;
	}
	
	
}
