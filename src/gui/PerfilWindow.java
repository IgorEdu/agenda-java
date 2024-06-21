package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Usuario;
import service.UsuarioService;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import java.awt.Font;

public class PerfilWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UsuarioWindow usuarioWindow;
	private Usuario usuario;
	private UsuarioService usuarioService;
	private JTextField txtUsername;
	private JPasswordField txtSenhaAtual;
	private JPasswordField txtSenhaNova;
	private JPasswordField txtConfirmarSenha;
	private JTextField txtNomeCompleto;
	private JTextField txtEmail;
	private MaskFormatter mascaraData;
	private JCheckBox chckbxMostrarSenha_1;
	private JCheckBox chckbxMostrarSenha_2;
	private JCheckBox chckbxMostrarSenha;
	private JFormattedTextField txtDataNascimento;
	private JRadioButton rbMasculino;
	private JRadioButton rbFeminino;
	private JRadioButton rbNaoInformar;
	private ButtonGroup grupoGenero;
	private JLabel lblFotoPessoal;
	private JLabel lblSenhaNova;
	private JLabel lblConfirmarSenha;
	private JLabel lblimgFotoPessoal;
	private JButton btnEditarPerfil;
	private JButton btnSalvarAlteracoes;
	private JButton btnCancelar;
	private String fotoPessoal;
	private JButton btnSelecinarFoto;
	private JLabel lblArquivoSelecionado;

	/**
	 * Create the frame.
	 */
	
	private void criarMascara() {
		
		try {
			
			this.mascaraData = new MaskFormatter("##/##/####");
			
		} catch(ParseException e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void visibilidadeCamposSenhaNova(boolean estado) {
		
		this.chckbxMostrarSenha_1.setVisible(estado);
		
		this.lblSenhaNova.setVisible(estado);
		this.txtSenhaNova.setVisible(estado);
		this.chckbxMostrarSenha.setVisible(estado);
		
		this.lblConfirmarSenha.setVisible(estado);
		this.txtConfirmarSenha.setVisible(estado);
		this.chckbxMostrarSenha_2.setVisible(estado);
	}
	
	private void mudarEstadoCampos(boolean estado) {
		
		this.txtUsername.setEnabled(estado);
		this.txtSenhaAtual.setEnabled(estado);
		this.txtNomeCompleto.setEnabled(estado);
		this.txtDataNascimento.setEnabled(estado);
		this.txtEmail.setEnabled(estado);
		this.rbMasculino.setEnabled(estado);
		this.rbFeminino.setEnabled(estado);
		this.rbNaoInformar.setEnabled(estado);
	}
	
	private void preencherInformacoes() {
		
		mudarEstadoCampos(false);
		
		this.txtUsername.setText(usuario.getUsername());
		
		this.txtNomeCompleto.setText(usuario.getNomeUsuario());
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		this.txtDataNascimento.setValue(formato.format(usuario.getDataNascimento()));
		
		this.txtEmail.setText(usuario.getEmail());

		switch(usuario.getGenero()) {
			case "Masculino":
				this.rbMasculino.setSelected(true);
				break;
			case "Feminino":
				this.rbFeminino.setSelected(true);
				break;
			default:
				this.rbNaoInformar.setSelected(true);
		}
		
		ImageIcon pfp = new ImageIcon(usuario.getFotoPessoal());
		
		lblimgFotoPessoal.setIcon(pfp);
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
	
	private void mostrarSenha(JCheckBox source, JPasswordField txt) {
		
		if(source.isSelected()) {
			txt.setEchoChar((char) 0);
		} else {
			txt.setEchoChar('●');
		}
	}
	
	private void retornarUsuarioWindow() {
		
		usuarioWindow.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private void excluirConta() {
		
		int res = JOptionPane.showConfirmDialog(this, "Deseja mesmo excluir sua conta?\nTodos os seus compromissos serão perdido para SEMPRE.");
		
		if(res == JOptionPane.YES_OPTION) {
			
			usuarioService.excluirUsuario(usuario);
			JOptionPane.showMessageDialog(this, "Exclusão Concluída com Sucesso!");
			new LoginWindow().setVisible(true);
			setVisible(false);
			usuarioWindow.dispose();
			dispose();
		}
	}
	
	private void editarPerfil() {
		
		visibilidadeCamposSenhaNova(true);
		
		this.mudarEstadoCampos(true);
		
		this.btnSelecinarFoto.setVisible(true);
		
		this.lblArquivoSelecionado.setVisible(true);
		
		this.btnEditarPerfil.setEnabled(false);
		
		this.btnSalvarAlteracoes.setEnabled(true);
		
		this.btnCancelar.setEnabled(true);
		
		this.txtSenhaAtual.setText("");
	}
	
	private void cancelarEdicao() {
		
		this.visibilidadeCamposSenhaNova(false);
		
		this.preencherInformacoes();
		
		this.btnSelecinarFoto.setVisible(false);
		
		this.lblArquivoSelecionado.setText("Nenhum arquivo Selecionado.");
		this.lblArquivoSelecionado.setToolTipText("Nenhum arquivo Selecionado.");
		this.lblArquivoSelecionado.setVisible(false);
		
		this.btnEditarPerfil.setEnabled(true);
		
		this.btnSalvarAlteracoes.setEnabled(false);
		
		this.btnCancelar.setEnabled(false);
		
		this.txtSenhaAtual.setText("bimbim");
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
	
	private String getGeneroSelecionado() {
		
		if(this.rbMasculino.isSelected()) return "Masculino";
		if(this.rbFeminino.isSelected()) return "Feminino";
		return "Não Informar";
	}
	
	private boolean possuiSenhaValida() {
		
		String senhaAtual = String.valueOf(this.txtSenhaAtual.getPassword());
		String senhaNova = String.valueOf(this.txtSenhaNova.getPassword());
		String confirmarSenha = String.valueOf(this.txtConfirmarSenha.getPassword());
		
		if(senhaAtual.isBlank() && !senhaNova.isBlank()) {
			JOptionPane.showMessageDialog(this, "Informe a sua senha atual para modificar a senha!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(!usuario.validarSenha(senhaAtual) && !senhaAtual.isBlank()) {
			JOptionPane.showMessageDialog(this, "Senha Atual INCORRETA!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(!senhaNova.equals(confirmarSenha)) {
			JOptionPane.showMessageDialog(this, "Senha confirmada não equivale a Senha Nova digitada!", "AVISO", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private boolean possuiCamposVazios() {
		
		
		if(this.txtUsername.getText().isBlank()) return true;
		if(this.txtNomeCompleto.getText().isBlank()) return true;
		if(this.txtDataNascimento.getText().replaceAll(" ", "").length() < 10)  return true; //checa campo de data vazio
		if(getGeneroSelecionado().isBlank()) return true;
		if(this.txtEmail.getText().isBlank()) return true;
		if(this.fotoPessoal.isBlank()) return true;
		
		return false;
	}
	
	
	private void salvarAlteracoes() {
		
		if(!possuiSenhaValida()) return;
		
		if(possuiCamposVazios()) {
			JOptionPane.showMessageDialog(this, "Não são aceitos campos vazios!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		//checa se o nome de usuario é diferente do utilizado atualmente e checa se o novo nome esta disponivel
		if((!this.txtUsername.getText().equalsIgnoreCase(usuario.getUsername()) && (usuarioService.buscarUsuarioPorLogin(this.txtUsername.getText()) != null))) {
			JOptionPane.showMessageDialog(this, "Nome de Usuario escolhido ja esta sendo usado!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if(!isValidDate()) {
				
			JOptionPane.showMessageDialog(this, "Por favor insira uma data valida!");
			this.txtDataNascimento.setValue(sdf.format(usuario.getDataNascimento()));
			return;
		}
		
		int res = JOptionPane.showConfirmDialog(this, "Deseja mesmo alterar suas informações?\n\nATENÇÃO, CAMPOS NÃO PREENCHIDOS NÃO SERÃO ATUALIZADOS");
		
		if(res == JOptionPane.YES_OPTION) {
			
			try {
				
				
				Usuario user = new Usuario();
				user.setId(usuario.getId());
				
				//Se o campo estiver vazio, ele reseta o valor para o que ja estava
				
				user.setUsername(this.txtUsername.getText().isBlank() ? usuario.getUsername() : this.txtUsername.getText());
				
				if(String.valueOf(this.txtSenhaAtual.getPassword()).isBlank()) {
					user.setSenhaCriptografada(usuario.getSenhaCriptografada());
				} else {
					user.setSenha(String.valueOf(this.txtSenhaNova.getPassword()));
				}
				
				user.setNomeUsuario(this.txtNomeCompleto.getText()
														.isBlank() ? usuario.getNomeUsuario(): this.txtNomeCompleto.getText());
				
				user.setDataNascimento(this.txtDataNascimento.getText().replaceAll("/", "")
																	   .isBlank() ? usuario.getDataNascimento() 
																				   : new java.sql.Date(sdf.parse(this.txtDataNascimento.getText()).getTime()));

				user.setGenero(getGeneroSelecionado());
				
				user.setEmail(this.txtEmail.getText().isBlank()
									? usuario.getEmail() : this.txtEmail.getText());
				
				user.setFotoPessoal(this.fotoPessoal);
				
				usuarioService.atualizarUsuario(user);
				
				this.usuario = user;
				this.cancelarEdicao();
			} catch(Exception e) {
				
				System.out.println(e);
				JOptionPane.showMessageDialog(this, "Um erro inesperado ocorreu. \nTente Novamente", "ERRO", JOptionPane.ERROR_MESSAGE);
				this.cancelarEdicao();
			}
		}else {
			JOptionPane.showMessageDialog(this, "Alteração cancelada!");
		}
	}
	
	public PerfilWindow(UsuarioWindow usuarioWindow, Usuario usuario) {
		
		this.usuarioWindow = usuarioWindow;
		this.usuario = usuario;
		this.fotoPessoal = usuario.getFotoPessoal();
		criarMascara();
		initComponents();
		preencherInformacoes();
		visibilidadeCamposSenhaNova(false);
		
		this.usuarioService = new UsuarioService();
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 650);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retornarUsuarioWindow();
			}
		});
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFocusable(false);
		btnVoltar.setBorderPainted(false);
		btnVoltar.setBackground(new Color(150, 50, 50));
		btnVoltar.setBounds(10, 11, 84, 27);
		contentPane.add(btnVoltar);
		
		JPanel panelInformacoesLogin = new JPanel();
		panelInformacoesLogin.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Acesso", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInformacoesLogin.setBounds(23, 90, 904, 143);
		contentPane.add(panelInformacoesLogin);
		panelInformacoesLogin.setLayout(null);
		
		JLabel lblUsername = new JLabel("Nome de Usuario:");
		lblUsername.setBounds(45, 24, 109, 20);
		panelInformacoesLogin.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(154, 24, 175, 21);
		panelInformacoesLogin.add(txtUsername);
		
		JLabel lblSenhaAtual = new JLabel("Senha Atual:");
		lblSenhaAtual.setBounds(66, 78, 78, 20);
		panelInformacoesLogin.add(lblSenhaAtual);
		
		txtSenhaAtual = new JPasswordField();
		txtSenhaAtual.setText("bimbim");
		txtSenhaAtual.setBounds(154, 78, 175, 21);
		panelInformacoesLogin.add(txtSenhaAtual);
		
		lblSenhaNova = new JLabel("Senha Nova:");
		lblSenhaNova.setBounds(447, 24, 109, 20);
		panelInformacoesLogin.add(lblSenhaNova);
		
		txtSenhaNova = new JPasswordField();
		txtSenhaNova.setBounds(537, 24, 175, 21);
		panelInformacoesLogin.add(txtSenhaNova);
		
		txtConfirmarSenha = new JPasswordField();
		txtConfirmarSenha.setBounds(537, 78, 175, 21);
		panelInformacoesLogin.add(txtConfirmarSenha);
		
		lblConfirmarSenha = new JLabel("Confirmar Senha Nova:");
		lblConfirmarSenha.setBounds(399, 78, 138, 20);
		panelInformacoesLogin.add(lblConfirmarSenha);
		
		chckbxMostrarSenha = new JCheckBox("Mostrar Senha");
		chckbxMostrarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarSenha(chckbxMostrarSenha, txtSenhaNova);
			}
		});
		chckbxMostrarSenha.setBounds(547, 48, 109, 23);
		panelInformacoesLogin.add(chckbxMostrarSenha);
		
		chckbxMostrarSenha_1 = new JCheckBox("Mostrar Senha");
		chckbxMostrarSenha_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarSenha(chckbxMostrarSenha_1, txtSenhaAtual);
			}
		});
		chckbxMostrarSenha_1.setBounds(164, 106, 109, 23);
		panelInformacoesLogin.add(chckbxMostrarSenha_1);
		
		chckbxMostrarSenha_2 = new JCheckBox("Mostrar Senha");
		chckbxMostrarSenha_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarSenha(chckbxMostrarSenha_2, txtConfirmarSenha);
			}
		});
		chckbxMostrarSenha_2.setBounds(547, 106, 109, 23);
		panelInformacoesLogin.add(chckbxMostrarSenha_2);
		
		JPanel panelInformacoesPessoais = new JPanel();
		panelInformacoesPessoais.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es Pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInformacoesPessoais.setBounds(23, 254, 904, 270);
		contentPane.add(panelInformacoesPessoais);
		panelInformacoesPessoais.setLayout(null);
		
		JLabel lblNomeUsuario = new JLabel("Nome Completo:");
		lblNomeUsuario.setBounds(35, 23, 109, 20);
		panelInformacoesPessoais.add(lblNomeUsuario);
		
		txtNomeCompleto = new JTextField();
		txtNomeCompleto.setColumns(10);
		txtNomeCompleto.setBounds(154, 23, 175, 21);
		panelInformacoesPessoais.add(txtNomeCompleto);
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(10, 70, 134, 20);
		panelInformacoesPessoais.add(lblDataNascimento);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(94, 114, 43, 20);
		panelInformacoesPessoais.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(154, 114, 175, 21);
		panelInformacoesPessoais.add(txtEmail);
		
		txtDataNascimento = new JFormattedTextField(this.mascaraData);
		txtDataNascimento.setBounds(154, 70, 175, 20);
		panelInformacoesPessoais.add(txtDataNascimento);
		
		JLabel lblGenero = new JLabel("Gênero:");
		lblGenero.setBounds(74, 145, 70, 24);
		panelInformacoesPessoais.add(lblGenero);
		
		this.grupoGenero = new ButtonGroup();
		
		rbMasculino = new JRadioButton("Masculino");
		rbMasculino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbMasculino.setBounds(97, 176, 79, 23);
		panelInformacoesPessoais.add(rbMasculino);
		
		rbFeminino = new JRadioButton("Feminino");
		rbFeminino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbFeminino.setBounds(178, 176, 79, 23);
		panelInformacoesPessoais.add(rbFeminino);
		
		rbNaoInformar = new JRadioButton("Não Informar");
		rbNaoInformar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbNaoInformar.setBounds(254, 176, 104, 23);
		panelInformacoesPessoais.add(rbNaoInformar);
		
		this.grupoGenero.add(rbMasculino);
		this.grupoGenero.add(rbFeminino);
		this.grupoGenero.add(rbNaoInformar);
		
		lblFotoPessoal = new JLabel("Foto Pessoal:");
		lblFotoPessoal.setBounds(437, 26, 92, 17);
		panelInformacoesPessoais.add(lblFotoPessoal);
		
		ImageIcon pfp = new ImageIcon(usuario.getFotoPessoal());
		
		lblimgFotoPessoal = new JLabel("New label");
		lblimgFotoPessoal.setIcon(pfp);
		lblimgFotoPessoal.setBounds(525, 23, 200, 200);
		panelInformacoesPessoais.add(lblimgFotoPessoal);
		
		btnSelecinarFoto = new JButton("Procurar");
		btnSelecinarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarFotoPessoal();
			}
		});
		btnSelecinarFoto.setBounds(524, 234, 89, 23);
		btnSelecinarFoto.setVisible(false);
		panelInformacoesPessoais.add(btnSelecinarFoto);
		
		lblArquivoSelecionado = new JLabel("Nenhum arquivo selecionado.");
		lblArquivoSelecionado.setToolTipText("Nenhum arquivo selecionado.");
		lblArquivoSelecionado.setVisible(false);
		lblArquivoSelecionado.setBounds(623, 238, 149, 19);
		panelInformacoesPessoais.add(lblArquivoSelecionado);
		
		JButton btnExcluirConta = new JButton("Excluir Conta");
		btnExcluirConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirConta();
			}
		});
		btnExcluirConta.setForeground(new Color(255, 255, 255));
		btnExcluirConta.setFocusable(false);
		btnExcluirConta.setBorderPainted(false);
		btnExcluirConta.setBounds(10, 560, 123, 40);
		btnExcluirConta.setBackground(new Color(150, 50, 50));
		contentPane.add(btnExcluirConta);
		
		btnEditarPerfil = new JButton("Editar Perfil");
		btnEditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarPerfil();
			}
		});
		btnEditarPerfil.setFocusable(false);
		btnEditarPerfil.setBounds(791, 48, 123, 40);
		contentPane.add(btnEditarPerfil);
		
		btnSalvarAlteracoes = new JButton("Salvar Alterações");
		btnSalvarAlteracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarAlteracoes();
			}
		});
		btnSalvarAlteracoes.setFocusable(false);
		btnSalvarAlteracoes.setEnabled(false);
		btnSalvarAlteracoes.setBounds(791, 560, 136, 40);
		contentPane.add(btnSalvarAlteracoes);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarEdicao();
			}
		});
		btnCancelar.setFocusable(false);
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(672, 560, 102, 40);
		contentPane.add(btnCancelar);
	}
}
