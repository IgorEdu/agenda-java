package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;

public class CadastroWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private LoginWindow login;
	private JTextField txtUsername;
	private JPasswordField txtSenha;
	private JPasswordField txtConfirmarSenha;
	private JTextField txtNomeUsuario;
	private JTextField txtEmail;
	private JComboBox cbGenero;
	private JFormattedTextField txtDataNascimento;
	private JLabel lblArquivoSelecionado;

	/**
	 * Create the frame.
	 */
	
	private void abrirJanelaLogin() {
		
		this.login.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private void cadastrarUsuario() {
		
		if(!senhasConferem()) {
		
			JOptionPane.showMessageDialog(this, "Senhas não conferem!");
			this.txtConfirmarSenha.setText("");
			return;
		}
		System.out.println("Cadastradando...");
		return;
	}
	
	private boolean senhasConferem() {
		
		String senha = String.valueOf(this.txtSenha.getPassword());
		String confirmarSenha = String.valueOf(this.txtConfirmarSenha.getPassword());
		
		return senha.equals(confirmarSenha);
	}
	
	public CadastroWindow(LoginWindow login) {
		setResizable(false);
		
		this.login = login;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 537);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogar = new JLabel("Já tem uma conta?");
		lblLogar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogar.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogar.setBounds(120, 429, 137, 21);
		contentPane.add(lblLogar);
		
		JButton btnLogar = new JButton("Entrar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaLogin();
			}
		});
		btnLogar.setForeground(Color.RED);
		btnLogar.setBackground(Color.WHITE);
		btnLogar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogar.setBounds(248, 426, 110, 26);
		contentPane.add(btnLogar);
		
		JLabel lblCadastrarText = new JLabel("CADASTRAR");
		lblCadastrarText.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrarText.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCadastrarText.setBounds(158, 11, 172, 46);
		contentPane.add(lblCadastrarText);
		
		JLabel lblUsername = new JLabel("Nome de Usuário:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(69, 68, 116, 23);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(174, 70, 175, 21);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSenha.setBounds(123, 107, 50, 23);
		contentPane.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(174, 109, 175, 21);
		contentPane.add(txtSenha);
		
		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha:");
		lblConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmarSenha.setBounds(69, 141, 104, 23);
		contentPane.add(lblConfirmarSenha);
		
		txtConfirmarSenha = new JPasswordField();
		txtConfirmarSenha.setBounds(174, 143, 175, 21);
		contentPane.add(txtConfirmarSenha);
		
		JLabel lblNomeUsuario = new JLabel("Nome Completo:");
		lblNomeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeUsuario.setBounds(69, 175, 116, 23);
		contentPane.add(lblNomeUsuario);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setColumns(10);
		txtNomeUsuario.setBounds(174, 177, 175, 21);
		contentPane.add(txtNomeUsuario);
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataNascimento.setBounds(51, 209, 116, 23);
		contentPane.add(lblDataNascimento);
		
		txtDataNascimento = new JFormattedTextField();
		txtDataNascimento.setBounds(174, 211, 116, 21);
		contentPane.add(txtDataNascimento);
		
		JLabel lblGenero = new JLabel("Gênero:");
		lblGenero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGenero.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGenero.setBounds(99, 245, 68, 19);
		contentPane.add(lblGenero);
		
		cbGenero = new JComboBox();
		cbGenero.setBounds(174, 243, 175, 22);
		contentPane.add(cbGenero);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(108, 276, 59, 19);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(174, 276, 175, 21);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblFotoPessoal = new JLabel("Foto Pessoal:");
		lblFotoPessoal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFotoPessoal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFotoPessoal.setBounds(63, 312, 104, 19);
		contentPane.add(lblFotoPessoal);
		
		JButton btnSelecinarFoto = new JButton("Procurar");
		btnSelecinarFoto.setBounds(174, 311, 89, 23);
		contentPane.add(btnSelecinarFoto);
		
		lblArquivoSelecionado = new JLabel("Nenhum arquivo selecionado.");
		lblArquivoSelecionado.setBounds(273, 315, 149, 19);
		contentPane.add(lblArquivoSelecionado);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrarUsuario();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(282, 360, 110, 31);
		contentPane.add(btnNewButton);
	}
}
