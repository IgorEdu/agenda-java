package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Usuario;
import service.ConviteService;
import entities.Agenda;
import entities.Compromisso;
import entities.Convite;
import entities.StatusConvite;
import service.CompromissoService;
import service.AgendaService;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class ConvitesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private UsuarioWindow usuarioWindow;
	private Usuario usuario;
	private List<Convite> convitesUsuario;
	private ConviteService conviteService;
	private JButton btnPendentes;
	private JButton btnTodos;
	private JTable tableConvites;
	private JComboBox cbAgendas;
	private JButton btnAceitar;
	private JButton btnRecusar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioWindow userWindow = new UsuarioWindow(new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"));
					Usuario murilo = new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png");
					ConvitesWindow frame = new ConvitesWindow(userWindow, murilo);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void voltarUsuarioWindow() {
		
		this.usuarioWindow.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private void mostrarConvitesPendentes() {
		
		this.btnPendentes.setEnabled(false);
		this.btnTodos.setEnabled(true);
		this.btnAceitar.setEnabled(true);
		this.btnRecusar.setEnabled(true);
		
		DefaultTableModel modelo = (DefaultTableModel) this.tableConvites.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);
		
		for(Convite convite : this.convitesUsuario) {
			
			if(!convite.getStatusConvite().equals(StatusConvite.PENDENTE)) continue;
			
			//TODO buscar compromisso no BD
			
			Compromisso compromisso;
			try {
				compromisso = new CompromissoService().buscarCompromissoPorId(convite.getCompromisso().getIdCompromisso());
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Um erro ocorreu ao buscar por seus convites!", "ERRO",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu, 'as' HH:mm", Locale.of("pt", "BR"));
			
			//Data de Inicio com tempo incluso
			LocalDateTime dataInicio = LocalDate.parse(compromisso.getDataInicio().toString())
												.atTime(Integer.parseInt(compromisso.getHorarioInicio().substring(0, 2)), Integer.parseInt(compromisso.getHorarioInicio().substring(3, 5)));
					
			//Data de fim com tempo incluso
			LocalDateTime dataFim = LocalDate.parse(compromisso.getDataTermino().toString())
											 .atTime(Integer.parseInt(compromisso.getHorarioTermino().substring(0, 2)), Integer.parseInt(compromisso.getHorarioTermino().substring(3, 5)));
			
			String dataNotificacao;
			if(compromisso.getDataNotificacao() == null) {
				
				dataNotificacao = "Não Possui";
			}else {
				
				//Data de notificacao com tempo incluso
				LocalDateTime diaHoraNotificacao = LocalDate.parse(compromisso.getDataNotificacao().toString())
						.atTime(Integer.parseInt(compromisso.getHorarioNotificacao().substring(0, 2)), Integer.parseInt(compromisso.getHorarioNotificacao().substring(3, 5)));
				dataNotificacao = formato.format(diaHoraNotificacao);
			}
			
			
			modelo.addRow(new Object[] {
					
					convite.getId(),
					compromisso.getTitulo(),
					compromisso.getDescricao(),
					compromisso.getLocal(),
					formato.format(dataInicio),
					formato.format(dataFim),
					dataNotificacao,
					convite.getStatusConvite()
			});
		}
	}
	
	private void mostrarTodosConvites() {
		
		this.btnPendentes.setEnabled(true);
		this.btnTodos.setEnabled(false);
		this.btnAceitar.setEnabled(false);
		this.btnRecusar.setEnabled(false);
		
		DefaultTableModel modelo = (DefaultTableModel) this.tableConvites.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);
		
		for(Convite convite : this.convitesUsuario) {
			
			Compromisso compromisso;
			try {
				compromisso = new CompromissoService().buscarCompromissoPorId(convite.getCompromisso().getIdCompromisso());
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Um erro ocorreu ao buscar por seus convites!", "ERRO",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Data de Inicio com tempo incluso
			LocalDateTime dataInicio = LocalDate.parse(compromisso.getDataInicio().toString())
												.atTime(Integer.parseInt(compromisso.getHorarioInicio().substring(0, 2)), Integer.parseInt(compromisso.getHorarioInicio().substring(3, 5)));
					
			//Data de fim com tempo incluso
			LocalDateTime dataFim = LocalDate.parse(compromisso.getDataTermino().toString())
											 .atTime(Integer.parseInt(compromisso.getHorarioTermino().substring(0, 2)), Integer.parseInt(compromisso.getHorarioTermino().substring(3, 5)));
					
			//Data de notificacao com tempo incluso
			LocalDateTime dataNotificacao = LocalDate.parse(compromisso.getDataNotificacao().toString())
													 .atTime(Integer.parseInt(compromisso.getHorarioNotificacao().substring(0, 2)), Integer.parseInt(compromisso.getHorarioNotificacao().substring(3, 5)));
			
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu, 'as' HH:mm", Locale.of("pt", "BR"));
			
			modelo.addRow(new Object[] {
					
					convite.getId(),
					compromisso.getTitulo(),
					compromisso.getDescricao(),
					compromisso.getLocal(),
					formato.format(dataInicio),
					formato.format(dataFim),
					formato.format(dataNotificacao),
					convite.getStatusConvite()
			});
		}
	}
	
	private void buscarConvitesUsuario() {
		
		try {
			this.convitesUsuario = conviteService.buscarConvitesPorIdConvidado(this.usuario.getId());
			if(this.convitesUsuario == null) {
				JOptionPane.showMessageDialog(this, "Nenhum convite encontrado!");
				return;
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Um erro ocorreu ao buscar por seus convites!\nPor favor reinicie o aplicativo.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void popularComboBox() {
		
		List<Agenda> agendas = null;
		
		try {
			
			agendas = new AgendaService().buscarAgendasUsuario(usuario);
			if(agendas.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhuma agenda encontrada.\nCadastre uma nova agenda ou reinicie o aplicativo se ja possui!", "AVISO", JOptionPane.WARNING_MESSAGE);
				this.btnAceitar.setEnabled(false);
				return;
			}
			
			this.cbAgendas.addItem(null);
			
			for(Agenda agenda : agendas) {
				
				this.cbAgendas.addItem(agenda);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Um erro ocorreu ao buscar por seus convites!\nPor favor reinicie o aplicativo.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private boolean possuiSelecaoAgendaValida() {
		
		if(tableConvites.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(this, "Selecione apenas UM convite!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(tableConvites.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Por favor seleciona um convite!");
			return false;
		}
		return true;
	}
	
	private void aceitarConvite() {
		
		if(!possuiSelecaoAgendaValida()) return;
		if(this.cbAgendas.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "Por favor selecione uma agenda", "AVISO", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int idConvite = (int) this.tableConvites.getValueAt(this.tableConvites.getSelectedRow(), 0);
		
		try {
			Convite convite = new ConviteService().buscarConvitePorIdConvite(idConvite);
			convite.aceitarConvite();
			if(new ConviteService().atualizar(convite) == 1) {
				JOptionPane.showMessageDialog(this, "Convite aceito com sucesso!");
				
				Compromisso compromisso = new CompromissoService().buscarCompromissoPorId(convite.getCompromisso().getIdCompromisso());
				
				new CompromissoService().cadastrar(compromisso, (Agenda) this.cbAgendas.getSelectedItem());
				
				return;
			}else {
				JOptionPane.showMessageDialog(this, "Não foi possível aceitar o convite!\nTente novamente.", "ERRO", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (SQLException | IOException e) {
			
			JOptionPane.showMessageDialog(this, "Não foi possível aceitar o convite!\nTente novamente.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void recusarConvite() {
		
		if(!possuiSelecaoAgendaValida()) return;
		
		int idConvite = (int) this.tableConvites.getValueAt(this.tableConvites.getSelectedRow(), 0);
		
		try {
			Convite convite = new ConviteService().buscarConvitePorIdConvite(idConvite);
			convite.recusarConvite();
			if(new ConviteService().atualizar(convite) == 1) {
				JOptionPane.showMessageDialog(this, "Convite recusado!");
				new ConviteService().atualizar(convite);
				return;
			}else {
				JOptionPane.showMessageDialog(this, "Um erro ocorreu ao recusar o convite!", "ERRO", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch(SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Não foi possível recusar o convite!\nTente novamente.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * Create the frame.
	 */
	
	public ConvitesWindow(UsuarioWindow usuarioWindow, Usuario usuario) {
		
		this.usuarioWindow = usuarioWindow;
		this.usuario = usuario;
		
		initComponents();
		
		this.conviteService = new ConviteService();
		
		popularComboBox();
		buscarConvitesUsuario();
		mostrarConvitesPendentes();
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
				voltarUsuarioWindow();
			}
		});
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFocusable(false);
		btnVoltar.setBorderPainted(false);
		btnVoltar.setBackground(new Color(150, 50, 50));
		btnVoltar.setBounds(10, 11, 84, 27);
		contentPane.add(btnVoltar);
		
		JPanel panelConvites = new JPanel();
		panelConvites.setBorder(new TitledBorder(null, "Convites", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConvites.setBounds(20, 58, 918, 316);
		contentPane.add(panelConvites);
		panelConvites.setLayout(null);
		
		btnPendentes = new JButton("Pendentes");
		btnPendentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarConvitesPendentes();
			}
		});
		btnPendentes.setFocusable(false);
		btnPendentes.setEnabled(false);
		btnPendentes.setBounds(20, 20, 100, 27);
		panelConvites.add(btnPendentes);
		
		btnTodos = new JButton("Todos");
		btnTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarTodosConvites();
			}
		});
		btnTodos.setFocusable(false);
		btnTodos.setBounds(141, 20, 100, 27);
		panelConvites.add(btnTodos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 58, 877, 247);
		panelConvites.add(scrollPane);
		
		tableConvites = new JTable(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableConvites.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Convite", "Titulo do Compromisso", "Descricao", "Local", "Data de Inicio", "Data de Termino", "Data de Notificacao", "Status do convite"
			}
		));
		tableConvites.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tableConvites);
		
		cbAgendas = new JComboBox();
		cbAgendas.setBounds(366, 454, 185, 27);
		contentPane.add(cbAgendas);
		
		JLabel lblAgenda = new JLabel("Agenda:");
		lblAgenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAgenda.setBounds(278, 456, 78, 21);
		contentPane.add(lblAgenda);
		
		btnAceitar = new JButton("Aceitar");
		btnAceitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceitarConvite();
			}
		});
		btnAceitar.setFocusable(false);
		btnAceitar.setBounds(590, 447, 123, 40);
		contentPane.add(btnAceitar);
		
		btnRecusar = new JButton("Recusar");
		btnRecusar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recusarConvite();
			}
		});
		btnRecusar.setForeground(Color.WHITE);
		btnRecusar.setFocusable(false);
		btnRecusar.setBorderPainted(false);
		btnRecusar.setBackground(new Color(150, 50, 50));
		btnRecusar.setBounds(747, 447, 123, 40);
		contentPane.add(btnRecusar);
		
		JLabel lblInstrucoes = new JLabel("<html>\r\n<p>\r\nInstruções:<br>\r\nPara aceitar um convite, selecione o convite, selecione uma agenda e clique no botão ACEITAR. <br>\r\nPara recusar, selecione o convite e clique no botão RECUSAR.\r\n</p>\r\n</html>");
		lblInstrucoes.setBounds(30, 380, 726, 58);
		contentPane.add(lblInstrucoes);
	}
}
