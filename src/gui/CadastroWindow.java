package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Usuario;
import service.UsuarioService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;

public class CadastroWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private LoginWindow login;
	private JTextField txtUsername;
	private JPasswordField txtSenha;
	private JPasswordField txtConfirmarSenha;
	private JTextField txtNomeUsuario;
	private JTextField txtEmail;
	private JFormattedTextField txtDataNascimento;
	private JLabel lblArquivoSelecionado;
	private ButtonGroup btnGroupSexo;
	private JRadioButton rbMasculino;
	private JRadioButton rbFeminino;
	private JRadioButton rbNaoInformar;
	private String fotoPessoal;
	private MaskFormatter mascaraData;
	private UsuarioService usuarioService;

	/**
	 * Create the frame.
	 */
	
	private void abrirJanelaLogin() {
		
		this.login.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private void cadastrarUsuario() {
		
		/*if(temCamposVazios()) {
			
			JOptionPane.showMessageDialog(this,"Preencha todos os campos!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(!senhasConferem()) {
		
			JOptionPane.showMessageDialog(this, "Senhas não conferem!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			this.txtConfirmarSenha.setText("");
			return;
		}
		
		if(usuarioService.buscarUsuarioPorLogin(this.txtUsername.getText()) != null) {
			JOptionPane.showMessageDialog(this, "Nome de Usuario escolhido ja esta sendo usado!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(!isValidDate()) return;*/
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			Usuario user = new Usuario();
			user.setUsername(this.txtUsername.getText());
			user.setSenha(String.valueOf(this.txtSenha.getPassword()));
			user.setNomeUsuario(this.txtNomeUsuario.getText());
			user.setDataNascimento(new java.sql.Date(sdf.parse(this.txtDataNascimento.getText()).getTime()));
			user.setGenero(generoSelecionado());
			user.setEmail(this.txtEmail.getText());
			user.setFotoPessoal(this.fotoPessoal);
			JOptionPane.showMessageDialog(this, "Usuario cadastrado com sucesso!", "SUCESSO!", JOptionPane.INFORMATION_MESSAGE);
			usuarioService.listarUsuarios();
			limparCampos();
			return;
		} catch(ParseException e) {
			
			JOptionPane.showMessageDialog(this, "Um erro inesperado ocorreu. \nTente Novamente", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String generoSelecionado() {
		
		if(this.rbMasculino.isSelected()) return "Masculino";
		if(this.rbFeminino.isSelected()) return "Feminino";
		return "Não Informar";
	}
	
	private boolean senhasConferem() {
		
		String senha = String.valueOf(this.txtSenha.getPassword());
		String confirmarSenha = String.valueOf(this.txtConfirmarSenha.getPassword());
		
		return senha.equals(confirmarSenha);
	}
	
	private boolean temCamposVazios() {
		
		
		/*if(this.txtUsername.getText().isBlank()) return true;
		if(String.valueOf(this.txtSenha.getPassword()).isBlank()) return true;
		if(String.valueOf(this.txtConfirmarSenha.getPassword()).isBlank()) return true;
		if(this.txtNomeUsuario.getText().isBlank()) return true;
		if(this.txtDataNascimento.getText().replaceAll(" ", "").length() < 10)  return true; //checa campo de data vazio
		if(getGenero().isBlank()) return true;
		if(this.txtEmail.getText().isBlank()) return true;
		if(this.fotoPessoal.isBlank()) return true;*/
		
		return false;
	}
	
	private String getGenero() {
		
		if(this.rbMasculino.isSelected()) return "Masculino";
		else if(this.rbFeminino.isSelected()) return "Feminino";
		else if(this.rbNaoInformar.isSelected()) return "Não informar";
		return "";
	}
	
	private void selecionarFotoPessoal() {
		
		JFileChooser chooser = new JFileChooser();
		
		int res = chooser.showOpenDialog(this);
		
		if(res == JFileChooser.APPROVE_OPTION) {
			
			this.fotoPessoal = chooser.getSelectedFile().getAbsolutePath();
			this.lblArquivoSelecionado.setText(chooser.getSelectedFile().getName());
			this.lblArquivoSelecionado.setToolTipText(chooser.getSelectedFile().getName());
			return;
		} else if(res == JFileChooser.ERROR_OPTION) {			
			JOptionPane.showMessageDialog(this, "Não foi possivel encontrar o arquivo. Tente Novamente", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		return;
	}
	
	private void criarMascara() {
		
		try {
			
			this.mascaraData = new MaskFormatter("##/##/####");
			
		} catch(ParseException e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean isValidDate() {
		
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/uuuu")
				.withResolverStyle(ResolverStyle.STRICT);
		try {
			LocalDate data = LocalDate.parse(this.txtDataNascimento.getText(), formater);
			if(Integer.parseInt(this.txtDataNascimento.getText().substring(6, 10)) > 2024) throw new Exception();
			if(data.isAfter(LocalDate.now())) throw new Exception();
			return true;
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(this, "Data Inválida! Digite Novamente", "ERROR", JOptionPane.ERROR_MESSAGE);
			this.txtDataNascimento.setValue(null);
			return false;
		}
	}
	
	private void limparCampos() {
		
		this.txtUsername.setText("");
		this.txtSenha.setText("");
		this.txtConfirmarSenha.setText("");
		this.txtNomeUsuario.setText("");
		this.txtDataNascimento.setValue(null);
		this.rbMasculino.setSelected(false);
		this.rbFeminino.setSelected(false);
		this.rbNaoInformar.setSelected(false);
		this.txtEmail.setText("");
		this.fotoPessoal = "";
		this.lblArquivoSelecionado.setText("Nenhum arquivo selecionado.");
		this.lblArquivoSelecionado.setToolTipText("");
	}
	
	public CadastroWindow(LoginWindow login) {
		
		this.login = login;
		this.criarMascara();
		this.initComponents();
		
		this.usuarioService = new UsuarioService();
	}
	
	public void initComponents() {
		setResizable(false);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 537);
		setLocationRelativeTo(null);
		setResizable(false);
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
		lblUsername.setBounds(61, 69, 116, 23);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(166, 71, 175, 21);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSenha.setBounds(115, 108, 50, 23);
		contentPane.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(166, 110, 175, 21);
		contentPane.add(txtSenha);
		
		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha:");
		lblConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmarSenha.setBounds(61, 142, 104, 23);
		contentPane.add(lblConfirmarSenha);
		
		txtConfirmarSenha = new JPasswordField();
		txtConfirmarSenha.setBounds(166, 144, 175, 21);
		contentPane.add(txtConfirmarSenha);
		
		JLabel lblNomeUsuario = new JLabel("Nome Completo:");
		lblNomeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeUsuario.setBounds(61, 176, 116, 23);
		contentPane.add(lblNomeUsuario);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setColumns(10);
		txtNomeUsuario.setBounds(166, 178, 175, 21);
		contentPane.add(txtNomeUsuario);
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataNascimento.setBounds(40, 210, 116, 23);
		contentPane.add(lblDataNascimento);
		
		txtDataNascimento = new JFormattedTextField(this.mascaraData);
		txtDataNascimento.setBounds(166, 212, 116, 21);
		contentPane.add(txtDataNascimento);
		
		JLabel lblGenero = new JLabel("Gênero:");
		lblGenero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGenero.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGenero.setBounds(88, 244, 68, 19);
		contentPane.add(lblGenero);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(97, 277, 59, 19);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(166, 277, 229, 21);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblFotoPessoal = new JLabel("Foto Pessoal:");
		lblFotoPessoal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFotoPessoal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFotoPessoal.setBounds(52, 313, 104, 19);
		contentPane.add(lblFotoPessoal);
		
		JButton btnSelecinarFoto = new JButton("Procurar");
		btnSelecinarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selecionarFotoPessoal();
			}
		});
		btnSelecinarFoto.setBounds(166, 312, 89, 23);
		contentPane.add(btnSelecinarFoto);
		
		lblArquivoSelecionado = new JLabel("Nenhum arquivo selecionado.");
		lblArquivoSelecionado.setToolTipText("Nenhum arquivo selecionado.");
		lblArquivoSelecionado.setBounds(265, 316, 149, 19);
		contentPane.add(lblArquivoSelecionado);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrarUsuario();
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCadastrar.setBounds(282, 360, 110, 31);
		contentPane.add(btnCadastrar);
		
		rbMasculino = new JRadioButton("Masculino");
		rbMasculino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbMasculino.setBounds(166, 243, 79, 23);
		contentPane.add(rbMasculino);
		
		rbFeminino = new JRadioButton("Feminino");
		rbFeminino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbFeminino.setBounds(247, 243, 79, 23);
		contentPane.add(rbFeminino);
		
		rbNaoInformar = new JRadioButton("Não Informar");
		rbNaoInformar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbNaoInformar.setBounds(323, 243, 104, 23);
		contentPane.add(rbNaoInformar);
		
		this.btnGroupSexo = new ButtonGroup();
		btnGroupSexo.add(rbMasculino);
		btnGroupSexo.add(rbFeminino);
		btnGroupSexo.add(rbNaoInformar);
		
		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(btnLimparCampos, "Tem certeza que deseja limpar os campos?") == JOptionPane.YES_OPTION) {
					limparCampos();
				}
			}
		});
		btnLimparCampos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLimparCampos.setBounds(52, 366, 125, 21);
		contentPane.add(btnLimparCampos);
		
		this.fotoPessoal = "";
	}
}
