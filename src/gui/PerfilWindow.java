package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Usuario;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PerfilWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UsuarioWindow usuarioWindow;
	private Usuario usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario murilo = new Usuario("murilinho", "123", "Murilo Vozniaki", new java.sql.Date(Long.valueOf("1718384913992")), "Masculino", "murilo@gmail.com", "murilo.png");
					UsuarioWindow userFrame = new UsuarioWindow(murilo);
					PerfilWindow frame = new PerfilWindow(userFrame, murilo);
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
	
	private void retornarUsuarioWindow() {
		
		usuarioWindow.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public PerfilWindow(UsuarioWindow usuarioWindow, Usuario usuario) {
		
		this.usuarioWindow = usuarioWindow;
		this.usuario = usuario;
		initComponents();
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 650);
		setLocationRelativeTo(null);
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
	}

}
