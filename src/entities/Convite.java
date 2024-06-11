package entities;

public class Convite {

	private Usuario usuario;
	private StatusConvite statusConvite;
	private Compromisso compromisso;
	
	public Convite() {
		
	}
	
	public Convite(Usuario usuario, StatusConvite statusConvite, Compromisso compromisso) {
		super();
		this.usuario = usuario;
		this.statusConvite = StatusConvite.PENDENTE;
		this.compromisso = compromisso;
	}
	
	public boolean aceitarConvite() {
		if(this.statusConvite != StatusConvite.PENDENTE) {
			statusConvite = StatusConvite.ACEITO;
			return true;
		}
		return false;
	}
	
	public boolean recustarConvite() {
		if(this.statusConvite != StatusConvite.PENDENTE) {
			statusConvite = StatusConvite.REJEITADO;
			return true;
		}
		return false;
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
