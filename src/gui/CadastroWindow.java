package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CadastroWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int height = 400;
	private static final int width = 550;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public CadastroWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
