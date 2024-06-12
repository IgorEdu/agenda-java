package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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
	private static final int height = 400;
	private static final int width = 550;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtSenha;

	/**
	 * Create the frame.
	 */
	private void validarLogin() {
		
		System.out.println("Validando...");
	}
	
	private void abrirJanelaCadastro() {
		
		new CadastroWindow().setVisible(true);;
		setVisible(false);
		dispose();
	}
	
	public LoginWindow() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginText = new JLabel("LOGIN");
		lblLoginText.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginText.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblLoginText.setBounds(223, 24, 92, 46);
		contentPane.add(lblLoginText);
		
		JLabel lblUsername = new JLabel("Nome de Usuario");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(130, 81, 110, 14);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(130, 106, 265, 31);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(130, 148, 110, 14);
		contentPane.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(130, 173, 265, 31);
		contentPane.add(txtSenha);
		
		JButton btnLogin = new JButton("Entrar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				validarLogin();
			}
		});
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(303, 215, 92, 31);
		contentPane.add(btnLogin);
		
		JLabel lblCadastro = new JLabel("Não possue uma conta?");
		lblCadastro.setBounds(130, 288, 156, 14);
		contentPane.add(lblCadastro);
		
		JButton btnCadastro = new JButton("Cadastre-se");
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaCadastro();
			}
		});
		btnCadastro.setForeground(Color.RED);
		btnCadastro.setBackground(Color.WHITE);
		btnCadastro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCadastro.setBounds(294, 283, 101, 23);
		contentPane.add(btnCadastro);
	}
}
