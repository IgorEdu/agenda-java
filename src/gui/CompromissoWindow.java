package gui;

import java.awt.EventQueue;
import java.sql.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompromissoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private AgendaWindow agendaWindow;
	private Compromisso compromisso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioWindow usuarioWindow = new UsuarioWindow(new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png"));
					Agenda agenda = new Agenda();
					agenda.setNomeAgenda("Agenda 1");
					AgendaWindow agendaWindow = new AgendaWindow(usuarioWindow, agenda);
					Compromisso comp1 = new Compromisso(1, "Comp 1", "Compromisso 1", Date.valueOf("2024-06-30"),"22:22",Date.valueOf("2024-06-30"),"23:00", "UTFPR",Date.valueOf("2024-06-30"),"22:00");
					CompromissoWindow frame = new CompromissoWindow(agendaWindow, comp1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void retornarAgendaWindow() {
		
		this.agendaWindow.setVisible(true);
		setVisible(false);
		dispose();
	}

	/**
	 * Create the frame.
	 */
	public CompromissoWindow(AgendaWindow agendaWindow, Compromisso compromisso) {
		
		this.agendaWindow = agendaWindow;
		this.compromisso = compromisso;
		
		initComponents();
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
	}
}
