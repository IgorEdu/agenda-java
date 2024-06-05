package entities;

public class Usuario {

	private String username;
	private String senha;
	private String nomeUsuario;
	private String dataNascimento; //mudar o tipo para Date se precisar
	private String genero;
	private String email;
	//Falta adicinar a foto pessoal
	
	public Usuario(String username, String senha, String nomeUsuario, String dataNascimento, String genero, String email) {
		
		this.username = username;
		this.senha = senha;
		this.nomeUsuario = nomeUsuario;
		this.dataNascimento = dataNascimento;
		this.genero = genero;
		this.email = email;
	}
	
	public Usuario() {
		
	}
	
	public boolean validarSenha(String senha) {
		
		return this.senha.equalsIgnoreCase(senha);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*public String getSenha() {
		return senha;        //Não sei se precisa de getSenha então vou deixar comentado
	}*/

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
