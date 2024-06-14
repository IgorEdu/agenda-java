package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Agenda;
import entities.Usuario;
import service.AgendaService;

import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class UsuarioWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private AgendaService agendaService;
	private Usuario usuarioLogado;
	private JTable tableAgendas;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioWindow frame = new UsuarioWindow(new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	
	private void buscarAgendas() {
		
		DefaultTableModel modelo = (DefaultTableModel)this.tableAgendas.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);
		
		List<Agenda> agendas = this.agendaService.pesquisarAgendasUsuario(usuarioLogado);
		
		for(Agenda agenda : agendas) {
			
			modelo.addRow(new Object[] {
					agenda.getIdAgenda(),
					agenda.getNomeAgenda(),
					agenda.getDescricao()
			});
		}
	}
	
	public UsuarioWindow(Usuario usuario) {
		
		this.usuarioLogado = usuario;
		initComponents();
		this.agendaService = new AgendaService();
		
		buscarAgendas();
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 580);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBemVindo = new JLabel("Bem vindo, " + this.usuarioLogado.getNomeUsuario() + "!");
		lblBemVindo.setVerticalAlignment(SwingConstants.TOP);
		lblBemVindo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBemVindo.setBounds(360, 11, 412, 27);
		contentPane.add(lblBemVindo);
		
		JPanel panelAgendas = new JPanel();
		panelAgendas.setBorder(new TitledBorder(null, "Suas Agendas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAgendas.setBounds(10, 32, 928, 250);
		contentPane.add(panelAgendas);
		panelAgendas.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 908, 147);
		panelAgendas.add(scrollPane);
		
		tableAgendas = new JTable();
		scrollPane.setViewportView(tableAgendas);
		tableAgendas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome da Agenda", "Descri\u00E7\u00E3o"
			}
		));
	}
}
