package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Usuario;
import service.UsuarioService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtSenha;
	private UsuarioService usuarioService;

	/**
	 * Create the frame.
	 */
	private void validarLogin() {
		
		Usuario usuario = usuarioService.buscarUsuarioPorLogin(this.txtUsername.getText());
		
		if(usuario == null) {
			JOptionPane.showMessageDialog(this, "Usuario não encontrado", "AVISO!", JOptionPane.WARNING_MESSAGE);
		} else {
			
			if(usuario.validarSenha(String.valueOf(this.txtSenha.getPassword()))) {
				
				new UsuarioWindow(usuario).setVisible(true);
				this.setVisible(false);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Senha Inválida!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			}
		}
		this.txtSenha.setText("");
	}
	
	private void abrirJanelaCadastro() {
		
		new CadastroWindow(this).setVisible(true);
		setVisible(false);
	}
	
	public LoginWindow() {
		
		initComponents();
		
		this.usuarioService = new UsuarioService();
	}
	
	public void initComponents() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 537);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginText = new JLabel("LOGIN");
		lblLoginText.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginText.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblLoginText.setBounds(185, 50, 92, 46);
		contentPane.add(lblLoginText);
		
		JLabel lblUsername = new JLabel("Nome de Usuario");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(104, 143, 110, 14);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(104, 168, 265, 31);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(104, 210, 110, 14);
		contentPane.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(104, 235, 265, 31);
		contentPane.add(txtSenha);
		
		JButton btnLogin = new JButton("Entrar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				validarLogin();
			}
		});
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(277, 277, 92, 31);
		contentPane.add(btnLogin);
		
		JLabel lblCadastro = new JLabel("Não possue uma conta?");
		lblCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCadastro.setBounds(104, 432, 156, 14);
		contentPane.add(lblCadastro);
		
		JButton btnCadastro = new JButton("Cadastre-se");
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaCadastro();
			}
		});
		btnCadastro.setForeground(Color.RED);
		btnCadastro.setBackground(Color.WHITE);
		btnCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastro.setBounds(248, 426, 110, 26);
		contentPane.add(btnCadastro);
	}
}
