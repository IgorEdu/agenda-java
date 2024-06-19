package gui;

import java.awt.EventQueue;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;
import service.UsuarioService;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class CompromissoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private MaskFormatter mascaraHora;
	private AgendaWindow agendaWindow;
	private Compromisso compromisso;
	private Usuario usuarioLogado;
	private JLabel lblDataInicio;
	private JLabel lblDataDeTermino;
	private JLabel lblDataDaNotificao;
	private JTable tableUsuariosConvidados;
	private JComboBox cbUsuariosConvidaveis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioWindow usuarioWindow = new UsuarioWindow(new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"));
					Agenda agenda = new Agenda();
					Usuario murilo = new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png");
					agenda.setNomeAgenda("Agenda 1");
					AgendaWindow agendaWindow = new AgendaWindow(usuarioWindow, agenda);
					Compromisso comp1 = new Compromisso(1, "Comp 1", "Compromisso 1", Date.valueOf("2024-06-30"),"22:22",Date.valueOf("2024-06-30"),"23:00", "UTFPR",Date.valueOf("2024-06-30"),"22:00");
					CompromissoWindow frame = new CompromissoWindow(agendaWindow,comp1, murilo);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void criarMascaraHora() {
		
		try {
			
			this.mascaraHora = new MaskFormatter("##:##");
			
		} catch(ParseException e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private void retornarAgendaWindow() {
		
		this.agendaWindow.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private void preencherDatas() {
		
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		
		//Data de Inicio com tempo incluso
		LocalDateTime dataInicio = LocalDate.parse(data.format(compromisso.getDataInicio()))
											.atTime(Integer.parseInt(this.compromisso.getHorarioInicio().substring(0, 2)), Integer.parseInt(this.compromisso.getHorarioInicio().substring(3, 5)));
				
		//Data de fim com tempo incluso
		LocalDateTime dataFim = LocalDate.parse(data.format(compromisso.getDataTermino()))
										 .atTime(Integer.parseInt(this.compromisso.getHorarioTermino().substring(0, 2)), Integer.parseInt(this.compromisso.getHorarioTermino().substring(3, 5)));
				
		//Data de notificacao com tempo incluso
		LocalDateTime dataNotificacao = LocalDate.parse(data.format(compromisso.getDataNotificacao()))
												 .atTime(Integer.parseInt(this.compromisso.getHorarioNotificacao().substring(0, 2)), Integer.parseInt(this.compromisso.getHorarioNotificacao().substring(3, 5)));
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu, 'as' HH:mm", Locale.of("pt", "BR"));
		
		this.lblDataInicio.setText("Data de Início: " + formato.format(dataInicio));
		
		this.lblDataDeTermino.setText("Data de Término: " + formato.format(dataFim));
		
		this.lblDataDaNotificao.setText("Data de Notificação: " + formato.format(dataNotificacao));
	}
	
	private void popularComboBox() {
		
		List<Usuario> usuarios = new UsuarioService().listarOutrosUsuario(usuarioLogado);
		
		cbUsuariosConvidaveis.addItem(null);
		
		for(Usuario user : usuarios) {
			
			cbUsuariosConvidaveis.addItem(user);
		}
	}
	
	private void convidarUsuario() {
		
		Usuario usuario = (Usuario) this.cbUsuariosConvidaveis.getSelectedItem();
		
		if(usuario == null) {
			
			JOptionPane.showMessageDialog(this, "Selecione um usuario para poder convida-lo");
			return;
		}
		
	}

	/**
	 * Create the frame.
	 */
//	public CompromissoWindow(Compromisso compromisso) {
//		
//		this.compromisso = compromisso;
//		
//		criarMascaraHora();
//		initComponents();
//		preencherDatas();
//	}
	
	public CompromissoWindow(AgendaWindow agendaWindow, Compromisso compromisso, Usuario usuarioLogado) {
		
		this.agendaWindow = agendaWindow;
		this.compromisso = compromisso;
		this.usuarioLogado = usuarioLogado;
		
		criarMascaraHora();
		initComponents();
		preencherDatas();
		popularComboBox();
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
				retornarAgendaWindow();
			}
		});
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFocusable(false);
		btnVoltar.setBorderPainted(false);
		btnVoltar.setBackground(new Color(150, 50, 50));
		btnVoltar.setBounds(10, 11, 84, 27);
		contentPane.add(btnVoltar);
		
		JLabel lblTitulo = new JLabel(this.compromisso.getTitulo());
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(386, 23, 172, 41);
		contentPane.add(lblTitulo);
		
		JLabel lblDescricao = new JLabel("<html>\r\n<p>\r\n" + this.compromisso.getDescricao() +"\r\n</p>\r\n</html>");
		lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescricao.setBounds(184, 61, 593, 65);
		contentPane.add(lblDescricao);
		
		JLabel lblLocal = new JLabel("Local: " + this.compromisso.getLocal());
		lblLocal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLocal.setBounds(114, 137, 621, 27);
		contentPane.add(lblLocal);
		
		lblDataInicio = new JLabel("Data de Inicio: ");
		lblDataInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataInicio.setBounds(67, 173, 621, 27);
		contentPane.add(lblDataInicio);
		
		lblDataDeTermino = new JLabel("Data de Término: ");
		lblDataDeTermino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataDeTermino.setBounds(50, 211, 621, 27);
		contentPane.add(lblDataDeTermino);
		
		lblDataDaNotificao = new JLabel("Data da Notificação");
		lblDataDaNotificao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataDaNotificao.setBounds(36, 248, 621, 27);
		contentPane.add(lblDataDaNotificao);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(36, 286, 875, 2);
		contentPane.add(separator);
		
		JPanel panelConvites = new JPanel();
		panelConvites.setBorder(new TitledBorder(null, "Convites", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConvites.setBounds(36, 299, 875, 279);
		contentPane.add(panelConvites);
		panelConvites.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(104, 22, 675, 193);
		panelConvites.add(scrollPane);
		
		tableUsuariosConvidados = new JTable();
		scrollPane.setViewportView(tableUsuariosConvidados);
		tableUsuariosConvidados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Usuario Convidado", "Status do Convite"
			}
		));
		tableUsuariosConvidados.getTableHeader().setReorderingAllowed(false);
		
		cbUsuariosConvidaveis = new JComboBox();
		cbUsuariosConvidaveis.setBounds(398, 237, 275, 22);
		panelConvites.add(cbUsuariosConvidaveis);
		
		JButton btnConvidar = new JButton("Convidar");
		btnConvidar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convidarUsuario();
			}
		});
		btnConvidar.setFocusable(false);
		btnConvidar.setBounds(706, 228, 123, 40);
		panelConvites.add(btnConvidar);
	}
}
