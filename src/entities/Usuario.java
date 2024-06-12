package entities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

public class Usuario {

	private int id;
	private String username;
	private String senha;
	private String nomeUsuario;
	private Date dataNascimento; //mudar o tipo para Date se precisar
	private String genero;
	private String email;
	//Falta adicinar a foto pessoal
	
	public Usuario(String username, String senha, String nomeUsuario, Date dataNascimento, String genero, String email) {
		this.username = username;
		try {
			this.senha = criptografarSenha(senha);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.nomeUsuario = nomeUsuario;
		this.dataNascimento = dataNascimento;
		this.genero = genero;
		this.email = email;
	}
	
	public Usuario() {
		
	}
	
	public boolean validarSenha(String senha) {
		boolean validado = false;
		try {
			validado = this.senha.equalsIgnoreCase(criptografarSenha(senha));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return validado;
	}
	
	public String criptografarSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
          hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhahex = hexString.toString();

        return senhahex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenhaCriptografada() {
		return senha;        //Não sei se precisa de getSenha então vou deixar comentado
	}

	public void setSenha(String senha) {
		try {
			this.senha = criptografarSenha(senha);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
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
