package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Agenda;
import entities.Convite;
import entities.StatusConvite;
import entities.Usuario;
import service.AgendaService;
import service.ConviteService;
import service.NotificacaoService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class UsuarioWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private AgendaService agendaService;
	private Usuario usuarioLogado;
	private JTable tableAgendas;
	private JButton btnAtualizar;
	private JButton btnExcluir;
	private JTextField txtNomeAgenda;
	private JTextArea txtDescricao;
	private NotificacaoService notificacaoService;
	
	private void buscarAgendas() {
		
		DefaultTableModel modelo = (DefaultTableModel)this.tableAgendas.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);
		
		List<Agenda> agendas = this.agendaService.buscarAgendasUsuario(usuarioLogado);
		
		for(Agenda agenda : agendas) {
			
			modelo.addRow(new Object[] {
					agenda.getIdAgenda(),
					agenda.getNomeAgenda(),
					agenda.getDescricao()
			});
		}
	}
	
	private boolean possuiSelecaoAgendaValida() {
		
		if(tableAgendas.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(this, "Selecione apenas UMA agenda!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(tableAgendas.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Por favor seleciona uma agenda!");
			return false;
		}
		return true;
	}
	
	private void excluirAgenda() {
		
		if(!possuiSelecaoAgendaValida()) return;
		
		int res = JOptionPane.showConfirmDialog(this, "Deseja mesmo excluir a agenda " + tableAgendas.getValueAt(tableAgendas.getSelectedRow(), 1) + "?\nO conteúdo dela sera PERMENENTEMENTE excluído.", "AVISO!", JOptionPane.YES_NO_CANCEL_OPTION);
		
		if(res == JOptionPane.YES_OPTION) {
			
			if(agendaService.excluirAgenda((int) tableAgendas.getValueAt(tableAgendas.getSelectedRow(), 0)) == 1) {
				
				JOptionPane.showMessageDialog(this, "Exclusão feita com sucesso!");
				buscarAgendas();
			} else {
				
				JOptionPane.showMessageDialog(this, "Ocorreu um erro durante a exclusão da agenda.\nTente Novamente.", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(this, "Exclusão Cancelada!");
		}
		
	}
	
	private void atualizarAgenda() {
		
		if(!possuiSelecaoAgendaValida()) return;
		
		if((this.txtNomeAgenda.getText().isBlank()) && (this.txtDescricao.getText().isBlank())) {
			JOptionPane.showMessageDialog(this, "Nenhum campo foi informado para alteração.");
			return;
		}
		
		//Pede confirmacao do usuario
		int res = JOptionPane.showConfirmDialog(this, "Deseja mesmo atualizar a agenda " + tableAgendas.getValueAt(tableAgendas.getSelectedRow(), 1) + "?", "AVISO!", JOptionPane.YES_NO_CANCEL_OPTION);
		
		//Valida a resposta
		if(res == JOptionPane.YES_OPTION) {
			
			//Busca a agenda no banco
			Agenda agendaSelecionada = agendaService.buscarAgendaPorId((int) tableAgendas.getValueAt(tableAgendas.getSelectedRow(), 0));
			
			//Substitui os campos preenchidos
			if(!this.txtNomeAgenda.getText().isBlank()) agendaSelecionada.setNomeAgenda(this.txtNomeAgenda.getText());
			if(!this.txtDescricao.getText().isBlank()) agendaSelecionada.setDescricao(this.txtDescricao.getText());
			
			if(agendaService.atualizarAgenda(agendaSelecionada) == 1) {
				
				limparCampos();
				buscarAgendas();
				JOptionPane.showMessageDialog(this, "Atualização concluída com sucesso!");
			}
		}else {
			JOptionPane.showMessageDialog(this, "Atualização Cancelada!");
		}
	}
	
	private void cadastrarAgenda() {
		
		if(possuiCampoVazio()) {
			JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		Agenda agenda = new Agenda();
		
		agenda.setNomeAgenda(this.txtNomeAgenda.getText());
		agenda.setDescricao(this.txtDescricao.getText());
		agenda.setUsuario(this.usuarioLogado);
		agendaService.cadastrarAgenda(agenda);
		limparCampos();
		buscarAgendas();
		JOptionPane.showMessageDialog(this, "Agenda cadastrada com Sucesso!");
	}
	
	private boolean possuiCampoVazio() {
		
		if(this.txtNomeAgenda.getText().isBlank()) return true;
		if(this.txtDescricao.getText().isBlank()) return true;
		
		return false;
	}
	
	private void limparCampos() {
		
		this.txtNomeAgenda.setText("");
		this.txtDescricao.setText("");
	}
	
	private void abrirJanelaAgenda(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			
			Agenda agendaSelecionada = agendaService.buscarAgendaPorId((int) this.tableAgendas.getValueAt(this.tableAgendas.getSelectedRow(), 0));
			new AgendaWindow(this, agendaSelecionada).setVisible(true);
			setVisible(false);
		}
		return;
	}
	
	private void abrirJanelaConvites() {
		
		new ConvitesWindow(this, this.usuarioLogado).setVisible(true);
		setVisible(false);
	}
	
	private void deslogar() {
		
		int res = JOptionPane.showConfirmDialog(this, "Deseja mesmo sair da sua conta?");
		
		if(res == JOptionPane.YES_OPTION) {
			
			new LoginWindow().setVisible(true);
			setVisible(false);
			this.notificacaoService.interrupt();
			dispose();
		}
	}
	
	private void checarConvites() {
		
		List<Convite> convites;
		
		try {
			convites = new ConviteService().buscarConvitesPorIdConvidado(this.usuarioLogado.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Não foi possível carregar os convites do Usuario!\nPor favor reinicie o aplicativo", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		
		if(convites == null) return;
		
		for(Convite convite : convites) {
			
			if(convite.getStatusConvite().equals(StatusConvite.PENDENTE)) {
				JOptionPane.showMessageDialog(this, "Você possui convites pendentes!\nPara vê-los clique no botão \"Ver Convites\"");
				return;
			}
		}
	}
	
	private void abrirJanelaPerfil() {
		
		new PerfilWindow(this, usuarioLogado).setVisible(true);
		setVisible(false);
	}
	
	private void iniciarThread() {
		
		try {
			this.notificacaoService = new NotificacaoService(usuarioLogado);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UsuarioWindow(Usuario usuario) {
		
		this.usuarioLogado = usuario;
		initComponents();
		this.agendaService = new AgendaService();
		
		iniciarThread();
		
		buscarAgendas();
		checarConvites();
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
		
		JLabel lblBemVindo = new JLabel("Bem vindo, " + this.usuarioLogado.getNomeUsuario() + "!");
		lblBemVindo.setVerticalAlignment(SwingConstants.TOP);
		lblBemVindo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBemVindo.setBounds(360, 35, 412, 27);
		contentPane.add(lblBemVindo);
		
		JPanel panelAgendas = new JPanel();
		panelAgendas.setBorder(new TitledBorder(null, "Suas Agendas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAgendas.setBounds(10, 73, 928, 250);
		contentPane.add(panelAgendas);
		panelAgendas.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 908, 147);
		panelAgendas.add(scrollPane);
		
		tableAgendas = new JTable() {
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableAgendas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirJanelaAgenda(e);
			}
		});
		scrollPane.setViewportView(tableAgendas);
		tableAgendas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome da Agenda", "Descri\u00E7\u00E3o"
			}
		));
		tableAgendas.getTableHeader().setReorderingAllowed(false);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarAgenda();
			}
		});
		btnAtualizar.setFocusable(false);
		btnAtualizar.setBounds(642, 187, 123, 40);
		panelAgendas.add(btnAtualizar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirAgenda();
			}
		});
		btnExcluir.setFocusable(false);
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setBackground(new Color(150, 50, 50));
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(795, 187, 123, 40);
		panelAgendas.add(btnExcluir);
		
		JLabel lblAviso = new JLabel("<html>\r\n<p>\r\nINSTRUÇÕES: <br>\r\nPara VER SEUS COMPROMISSOS, clique duas vezes na agenda desejada.<br>\r\nPara EXCLUSÃO, selecione apenas UMA agenda da tabela.<br>\r\nPara ATUALIZAÇÃO, seleciona UMA linha da tabela e preencha abaixo APENAS OS CAMPOS QUE DEVEM SER ATUALIZADOS.\r\n</p>\r\n</html>");
		lblAviso.setVerticalAlignment(SwingConstants.TOP);
		lblAviso.setBounds(10, 173, 600, 66);
		panelAgendas.add(lblAviso);
		
		JLabel lblInstrucaoCadastro = new JLabel("Para Cadastrar uma nova agenda, preencha todos os campos e clique no botão CADASTRAR.");
		lblInstrucaoCadastro.setBounds(206, 334, 552, 27);
		contentPane.add(lblInstrucaoCadastro);
		
		JLabel lblNomeAgenda = new JLabel("Nome da Agenda:");
		lblNomeAgenda.setBounds(218, 377, 109, 21);
		contentPane.add(lblNomeAgenda);
		
		txtNomeAgenda = new JTextField();
		txtNomeAgenda.setBounds(327, 377, 307, 21);
		contentPane.add(txtNomeAgenda);
		txtNomeAgenda.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descrição da Agenda:");
		lblDescricao.setBounds(194, 411, 126, 21);
		contentPane.add(lblDescricao);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(328, 409, 307, 107);
		contentPane.add(scrollPane_1);
		
		txtDescricao = new JTextArea();
		scrollPane_1.setViewportView(txtDescricao);
		txtDescricao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrarAgenda();
			}
		});
		btnCadastrar.setFocusable(false);
		btnCadastrar.setBounds(676, 518, 123, 40);
		contentPane.add(btnCadastrar);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				deslogar();
			}
		});
		btnSair.setForeground(new Color(255, 255, 255));
		btnSair.setBounds(10, 11, 58, 27);
		btnSair.setFocusable(false);
		btnSair.setBackground(new Color(150, 50, 50));
		btnSair.setBorderPainted(false);
		contentPane.add(btnSair);
		
		JButton btnEditarPerfil = new JButton("Editar Perfil");
		btnEditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaPerfil();
			}
		});
		btnEditarPerfil.setBounds(815, 11, 123, 27);
		contentPane.add(btnEditarPerfil);
		
		JButton btnVerConvites = new JButton("Ver Convites");
		btnVerConvites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaConvites();
			}
		});
		btnVerConvites.setBounds(665, 11, 123, 27);
		contentPane.add(btnVerConvites);
	}
}
