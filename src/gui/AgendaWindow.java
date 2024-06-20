package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;
import service.CompromissoService;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import com.toedter.calendar.JDateChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;

public class AgendaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private MaskFormatter mascaraHora;
	private Agenda agenda;
	private CompromissoService compromissoService;
	private UsuarioWindow usuarioWindow;
	private JTable tableCompromissos;
	private JTextField txtTitulo;
	private JTextField txtLocal;
	private JTextArea txtDescricao;
	private JFormattedTextField txtHoraInicio;
	private JDateChooser dateChooserInicio;
	private JDateChooser dateChooserFim;
	private JFormattedTextField txtHoraFim;
	private JDateChooser dateChooserNotificacao;
	private JFormattedTextField txtHoraNotificacao;
	private JCheckBox chckbxSemDataNotificacao;

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
					AgendaWindow frame = new AgendaWindow(usuarioWindow, agenda);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void limparCampos() {
		
		this.txtTitulo.setText("");
		this.txtDescricao.setText("");
		this.txtLocal.setText("");
		
		this.dateChooserInicio.setDate(null);
		this.txtHoraInicio.setValue(null);
		
		this.dateChooserFim.setDate(null);
		this.txtHoraFim.setValue(null);
		
		this.dateChooserNotificacao.setDate(null);
		this.txtHoraNotificacao.setValue(null);
	}
	
	private void buscarCompromissos() {
		
		try {
			this.agenda.setCompromissos(compromissoService.buscarCompromissosAgenda(agenda.getIdAgenda()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Um erro ocorreu durante a busca dos compromissos.\nPor favor reinicie o app.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		
		DefaultTableModel modelo = (DefaultTableModel) this.tableCompromissos.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);
		
		if(this.agenda.getCompromissos() == null) {
			JOptionPane.showMessageDialog(this, "Nenhum Compromisso encontrado!");
			return;
		}
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu, 'as' HH:mm", Locale.of("pt", "BR"));
		
		for(Compromisso c : this.agenda.getCompromissos()) {
			
			
			//Data de Inicio com tempo incluso
			LocalDateTime dataInicio = LocalDate.parse(c.getDataInicio().toString())
												.atTime(Integer.parseInt(c.getHorarioInicio().substring(0, 2)), Integer.parseInt(c.getHorarioInicio().substring(3, 5)));
					
			//Data de fim com tempo incluso
			LocalDateTime dataFim = LocalDate.parse(c.getDataTermino().toString())
											 .atTime(Integer.parseInt(c.getHorarioTermino().substring(0, 2)), Integer.parseInt(c.getHorarioTermino().substring(3, 5)));
			
			String dataNotificacao;
			if(c.getDataNotificacao() == null) {
				
				dataNotificacao = "Não Possui";
			}else {
				
				//Data de notificacao com tempo incluso
				LocalDateTime diaHoraNotificacao = LocalDate.parse(c.getDataNotificacao().toString())
						.atTime(Integer.parseInt(c.getHorarioNotificacao().substring(0, 2)), Integer.parseInt(c.getHorarioNotificacao().substring(3, 5)));
				dataNotificacao = formato.format(diaHoraNotificacao);
			}
			
			modelo.addRow(new Object[] {
					c.getIdCompromisso(),
					c.getTitulo(),
					c.getLocal(),
					formato.format(dataInicio),
					formato.format(dataFim),
					dataNotificacao
			});
		}
	}
	
	private boolean possuiSelecaoValida() {
		
		if(tableCompromissos.getSelectedRowCount() > 1) {
			JOptionPane.showMessageDialog(this, "Selecione apenas UM compromisso!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(tableCompromissos.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Por favor selecione um compromisso!");
			return false;
		}
		return true;
	}
	
	private void retornarUsuarioWindow() {
		
		usuarioWindow.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private void criarMascaraHora() {
		
		try {
			
			this.mascaraHora = new MaskFormatter("##:##");
			
		} catch(ParseException e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private void abrirJanelaCompromisso(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			
			for(Compromisso c : this.agenda.getCompromissos()) {
				
				if(c.getIdCompromisso() == (int) this.tableCompromissos.getValueAt(tableCompromissos.getSelectedRow(), 0)) {
					
					new CompromissoWindow(this, c, agenda.getUsuario()).setVisible(true);
					setVisible(false);
					return;
				}
			}
			
			JOptionPane.showMessageDialog(this, "Compromisso não Encontrado!\nPor favor feche o aplicativo e o abra novamente.", "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean possuiCampoVazio() {
		
		if(this.txtTitulo.getText().isBlank()) return true;
		if(this.txtDescricao.getText().isBlank()) return true;
		if(this.txtLocal.getText().isBlank()) return true;
		
		if(this.dateChooserInicio.getDate() == null) return true;
		if(this.txtHoraInicio.getText().replace(":", "").isBlank()) return true;
		
		if(this.dateChooserFim.getDate() == null) return true;
		if(this.txtHoraFim.getText().replace(":", "").isBlank()) return true;
		
		if(!this.chckbxSemDataNotificacao.isEnabled()) {
			
			if(this.dateChooserNotificacao.getDate() == null) return true;
			if(this.txtHoraNotificacao.getText().replace(":", "").isBlank()) return true;
		}
		
		return false;
	}
	
	private boolean possuiDataInvalida() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//Data de Inicio com tempo incluso
		LocalDateTime dataInicio = LocalDate.parse(sdf.format(this.dateChooserInicio.getDate()))
				.atTime(Integer.parseInt(this.txtHoraInicio.getText().substring(0, 2)), Integer.parseInt(this.txtHoraInicio.getText().substring(3, 5)));
		
		//Data de fim com tempo incluso
		LocalDateTime dataFim = LocalDate.parse(sdf.format(this.dateChooserFim.getDate()))
				.atTime(Integer.parseInt(this.txtHoraFim.getText().substring(0, 2)), Integer.parseInt(this.txtHoraFim.getText().substring(3, 5)));
		
		//Por hora comentado, pois nao sei se pode ser criado um compromisso cuja data inicial ja passou!
		/*if(dataInicio.isBefore(LocalDateTime.now())) {
			JOptionPane.showMessageDialog(this, "Date e horario de inicio NÃO podem ser uma data que ja passou!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			this.dateChooserInicio.setDate(null);
			this.txtHoraInicio.setValue(null);
			return true;
		}*/
		
		if(dataFim.isBefore(LocalDateTime.now())) {
			JOptionPane.showMessageDialog(this, "Date e horario de termino NÃO podem ser uma data que ja passou!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			this.dateChooserFim.setDate(null);
			this.txtHoraFim.setValue(null);
			return true;
		}
		
		if(dataInicio.isAfter(dataFim)) {
			
			JOptionPane.showMessageDialog(this, "Data e horario de inicio não podem se passar depois da data de termino!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			this.dateChooserInicio.setDate(null);
			this.txtHoraInicio.setValue(null);
			return true;
		}
		
		if(!this.chckbxSemDataNotificacao.isSelected()) {
			
			//Data de notificacao com tempo incluso
			LocalDateTime dataNotificacao = LocalDate.parse(sdf.format(this.dateChooserNotificacao.getDate()))
					.atTime(Integer.parseInt(this.txtHoraNotificacao.getText().substring(0, 2)), Integer.parseInt(this.txtHoraNotificacao.getText().substring(3, 5)));
			
			if(dataNotificacao.isBefore(LocalDateTime.now())) {
				JOptionPane.showMessageDialog(this, "Date e horario de notificação NÃO podem ser uma data que ja passou!", "AVISO!", JOptionPane.WARNING_MESSAGE);
				this.dateChooserFim.setDate(null);
				this.txtHoraFim.setValue(null);
				return true;
			}
		}
		return false;
	}
	
	private boolean horaValida(String horario, String source) {
		
		String[] split = horario.split(":");
		int hora = Integer.parseInt(split[0]);
		int minuto = Integer.parseInt(split[1]);
		
		if(hora >= 24 || hora < 0) {
			
			JOptionPane.showMessageDialog(this, "Horario de " + source + " possui Hora invalida!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(minuto >= 60 || minuto < 0) {
			JOptionPane.showMessageDialog(this, "Horario de " + source + " possui Minuto invalida!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	private void cadastrarCompromisso() {
				
		if(possuiCampoVazio()) {
			JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "AVISO!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(!horaValida(this.txtHoraInicio.getText(),"Inicio")) return;
		if(!horaValida(this.txtHoraFim.getText(),"Termino")) return;
		if(!horaValida(this.txtHoraNotificacao.getText(),"Notificação")) return;
		if(possuiDataInvalida()) return;
		
		
		Compromisso c = new Compromisso();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		c.setTitulo(this.txtTitulo.getText());
		c.setDescricao(this.txtDescricao.getText());
		c.setLocal(this.txtLocal.getText());
		
		c.setDataInicio(Date.valueOf(sdf.format(this.dateChooserInicio.getDate())));
		c.setHorarioInicio(this.txtHoraInicio.getText());
		
		c.setDataTermino(Date.valueOf(sdf.format(this.dateChooserFim.getDate())));
		c.setHorarioTermino(this.txtHoraFim.getText());
		
		c.setDataNotificacao(Date.valueOf(sdf.format(this.dateChooserNotificacao.getDate())));
		c.setHorarioNotificacao(this.txtHoraNotificacao.getText());
		
		try {
			
			this.compromissoService.cadastrar(c, agenda);
			limparCampos();
			buscarCompromissos();
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(this, "Não foi possível realizar o cadastro.", "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void excluirCompromisso() {
		
		if(!possuiSelecaoValida()) return;
		
		int res = JOptionPane.showConfirmDialog(this, "Deseja mesmo excluir \""+ this.tableCompromissos.getValueAt(tableCompromissos.getSelectedRow(), 1) + "\"?\nEle sera excluido PERMANENTEMENTE!");
		
		if(res == JOptionPane.YES_OPTION) {
			
			try {
				compromissoService.excluirCompromisso((int)tableCompromissos.getValueAt(tableCompromissos.getSelectedRow(), 0));
				JOptionPane.showMessageDialog(this, "Compromisso excluido com sucesso!");
				buscarCompromissos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Um erro ocorreu durante a exclusão!", "ERRO", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
	private void atualizarCompromisso() {
		
		Compromisso antigo = null;
		
		if(this.agenda.getCompromissos() == null) {
			JOptionPane.showMessageDialog(this, "Você não possui nenhum compromisso cadastrado!");
			return;
		}
		
		for(Compromisso c : this.agenda.getCompromissos()) {
			if(c.getIdCompromisso() == (int) tableCompromissos.getValueAt(tableCompromissos.getSelectedRow(), 0)) {
				antigo = c;
			}
		}
		
		Compromisso novo = new Compromisso();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		novo.setIdCompromisso(antigo.getIdCompromisso());
		novo.setTitulo(this.txtTitulo.getText().isBlank() ? antigo.getTitulo() : this.txtTitulo.getText());
		novo.setDescricao(this.txtDescricao.getText().isBlank() ? antigo.getDescricao() : this.txtDescricao.getText());
		novo.setLocal(this.txtLocal.getText().isBlank() ? antigo.getLocal() : this.txtLocal.getText());
		
		if(!this.txtHoraInicio.getText().isBlank() && !horaValida(this.txtHoraInicio.getText(), "Inicio")) return;
		if(!this.txtHoraFim.getText().isBlank() && !horaValida(this.txtHoraFim.getText(), "Termino")) return;
		if(!this.txtHoraNotificacao.getText().isBlank() && !horaValida(this.txtHoraNotificacao.getText(), "Notificação")) return;
		
		if((this.dateChooserInicio.getDate() != null) && (this.dateChooserFim.getDate() != null) && (this.dateChooserNotificacao.getDate() != null)) {
			if(this.possuiDataInvalida()) return;
			
			novo.setDataInicio(Date.valueOf(sdf.format(this.dateChooserInicio.getDate())));
			novo.setHorarioInicio(this.txtHoraInicio.getText());
			
			novo.setDataTermino(Date.valueOf(sdf.format(this.dateChooserFim.getDate())));
			novo.setHorarioTermino(this.txtHoraFim.getText());
			
			novo.setDataNotificacao(Date.valueOf(sdf.format(this.dateChooserNotificacao.getDate())));
			novo.setHorarioNotificacao(this.txtHoraNotificacao.getText());
		}else {
		
			//Valida se a data de inicio ja passou
			if(this.dateChooserInicio.getDate() == null) {
				novo.setDataInicio(antigo.getDataInicio());
				novo.setHorarioInicio(antigo.getHorarioInicio());
			}
			else {
				if(this.txtHoraInicio.getText().replace(":", "").isBlank()) {
					JOptionPane.showMessageDialog(this, "Por favor informe a hora de inicio!");
					return;
				}
				LocalDateTime dataInicio = LocalDate.parse(sdf.format(this.dateChooserInicio.getDate()))
						.atTime(Integer.parseInt(this.txtHoraInicio.getText().substring(0, 2)), Integer.parseInt(this.txtHoraInicio.getText().substring(3, 5)));
				
				//Por hora comentado, pois nao sei se pode ser criado um compromisso cuja data inicial ja passou!
				if(dataInicio.isBefore(LocalDateTime.now())) {
					JOptionPane.showMessageDialog(this, "Date e horario de inicio NÃO podem ser uma data que ja passou!", "AVISO!", JOptionPane.WARNING_MESSAGE);
					this.dateChooserInicio.setDate(null);
					this.txtHoraInicio.setValue(null);
					return;
				} else{
					novo.setDataInicio(Date.valueOf(sdf.format(this.dateChooserInicio.getDate())));
					novo.setHorarioInicio(this.txtHoraInicio.getText());
				}
			}
			
			//Valida se a Data de Termino ja Passou
			if(this.dateChooserFim.getDate() == null) {
				novo.setDataTermino(antigo.getDataTermino());
				novo.setHorarioTermino(antigo.getHorarioTermino());
			}
			else {
				if(this.txtHoraFim.getText().replace(":", "").isBlank()) {
					JOptionPane.showMessageDialog(this, "Por favor informe a hora de termino!");
					return;
				}
				LocalDateTime dataFim = LocalDate.parse(sdf.format(this.dateChooserFim.getDate()))
						.atTime(Integer.parseInt(this.txtHoraFim.getText().substring(0, 2)), Integer.parseInt(this.txtHoraFim.getText().substring(3, 5)));
				
				if(dataFim.isBefore(LocalDateTime.now())) {
					JOptionPane.showMessageDialog(this, "Date e horario de termino NÃO podem ser uma data que ja passou!", "AVISO!", JOptionPane.WARNING_MESSAGE);
					this.dateChooserFim.setDate(null);
					this.txtHoraFim.setValue(null);
					return;
				} else {
					
					novo.setDataTermino(Date.valueOf(sdf.format(this.dateChooserFim.getDate())));
					novo.setHorarioTermino(this.txtHoraFim.getText());
				}
			}
			
			//Valida se a data de inicio vem depois da data de termino
			if((this.dateChooserInicio.getDate() != null) && this.dateChooserFim.getDate() != null) {
				
				LocalDateTime dataFim = LocalDate.parse(sdf.format(this.dateChooserFim.getDate()))
						.atTime(Integer.parseInt(this.txtHoraFim.getText().substring(0, 2)), Integer.parseInt(this.txtHoraFim.getText().substring(3, 5)));
				
				LocalDateTime dataInicio = LocalDate.parse(sdf.format(this.dateChooserInicio.getDate()))
						.atTime(Integer.parseInt(this.txtHoraInicio.getText().substring(0, 2)), Integer.parseInt(this.txtHoraInicio.getText().substring(3, 5)));
				
				if(dataInicio.isAfter(dataFim)) {
					
					JOptionPane.showMessageDialog(this, "Data e horario de inicio não podem se passar depois da data de termino!", "AVISO!", JOptionPane.WARNING_MESSAGE);
					this.dateChooserInicio.setDate(null);
					this.txtHoraInicio.setValue(null);
					return;
				} else {
					
					novo.setDataInicio(Date.valueOf(sdf.format(this.dateChooserInicio.getDate())));
					novo.setHorarioInicio(this.txtHoraInicio.getText());
				}
			}
			
			//Valida se a data de Notificacao ja passou
			if(this.dateChooserNotificacao.getDate() == null) {
				novo.setDataNotificacao(antigo.getDataNotificacao());
				novo.setHorarioNotificacao(antigo.getHorarioNotificacao());
			}
			else {
				
				if(this.chckbxSemDataNotificacao.isSelected()) {
					
					novo.setDataNotificacao(null);
					novo.setHorarioNotificacao("");
				}else {
					
					if(this.txtHoraNotificacao.getText().replace(":", "").isBlank()) {
						JOptionPane.showMessageDialog(this, "Por favor informe a hora de Notificacao!");
						return;
					}
					LocalDateTime dataNotificacao = LocalDate.parse(sdf.format(this.dateChooserNotificacao.getDate()))
							.atTime(Integer.parseInt(this.txtHoraNotificacao.getText().substring(0, 2)), Integer.parseInt(this.txtHoraNotificacao.getText().substring(3, 5)));
					
					if(dataNotificacao.isBefore(LocalDateTime.now())) {
						JOptionPane.showMessageDialog(this, "Date e horario de Notificacao NÃO podem ser uma data que ja passou!", "AVISO!", JOptionPane.WARNING_MESSAGE);
						this.dateChooserNotificacao.setDate(null);
						this.txtHoraNotificacao.setValue(null);
						return;
					} else {
						
						novo.setDataNotificacao(Date.valueOf(sdf.format(this.dateChooserNotificacao.getDate())));
						novo.setHorarioNotificacao(this.txtHoraNotificacao.getText());
					}
				}
				
			}
		}
		try {
			if(compromissoService.atualizarCompromisso(novo) == 1) {
				
				JOptionPane.showMessageDialog(this, "Compromisso atualizado com sucesso!");
				limparCampos();
				buscarCompromissos();
			}else {
				
				JOptionPane.showMessageDialog(this, "OCORREU UM ERRO!", "ERRO FATAL", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(this, "Um erro ocorreu durante a atualização!", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void exportarCSV() {
		
		if(this.agenda.getCompromissos() == null) {
			JOptionPane.showMessageDialog(this, "Não é possível exportar pois a agenda não possue nenhum compromisso!");
			return;
		}
		
		JFileChooser chooser = new JFileChooser();
		
		FileNameExtensionFilter filtroCsv = new FileNameExtensionFilter("Arquivo CSV (.csv)", ".csv");
		chooser.setFileFilter(filtroCsv);
		
		int res = chooser.showSaveDialog(this);
		
		if(res == JFileChooser.APPROVE_OPTION) {
			
			String caminho = chooser.getSelectedFile().getAbsolutePath();
			
			this.compromissoService.exportarArquivoCSV(caminho, this.agenda);
			
			JOptionPane.showMessageDialog(this, "Arquivo exportado com sucesso!");
		} else if(res == JFileChooser.ERROR_OPTION) {
			
			JOptionPane.showMessageDialog(this, "Um erro ocorreu ao salvar o arquivo!\nTente novamente.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void importarCSV() {
		
		JFileChooser chooser = new JFileChooser();
		
		FileNameExtensionFilter filtroCsv = new FileNameExtensionFilter("Arquivo CSV (.csv)", ".csv");
		chooser.setFileFilter(filtroCsv);
		
		int res = chooser.showOpenDialog(this);
		
		if(res == JFileChooser.APPROVE_OPTION) {
			
			String caminho = chooser.getSelectedFile().getAbsolutePath();
			
			if(!caminho.endsWith(".csv")) {
				JOptionPane.showMessageDialog(this, "Só é possível realizar a importação de arquivos CSV (.csv)!", "ERRO", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			compromissoService.importarArquivoCSV(caminho, agenda);
			buscarCompromissos();
		}else if (res == JFileChooser.ERROR_OPTION) {
			
			JOptionPane.showMessageDialog(this, "Um erro durante a importação do arquivo!\nTente novamente.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void tirarDataNotificacao() {
		
		Boolean ativado;
		
		if(this.chckbxSemDataNotificacao.isSelected()) {
			
			ativado = false;
		}else {
			
			ativado = true;
		}
		
		this.dateChooserNotificacao.setEnabled(ativado);
		this.txtHoraNotificacao.setEnabled(ativado);
	}

	/**
	 * Create the frame.
	 */
	public AgendaWindow(UsuarioWindow usuarioWindow, Agenda agenda) {
		
		this.usuarioWindow = usuarioWindow;
		this.agenda = agenda;
		this.compromissoService = new CompromissoService();
		criarMascaraHora();
		initComponents();
		
		buscarCompromissos();
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
		
		JPanel panelCompromissos = new JPanel();
		panelCompromissos.setBounds(10, 49, 928, 266);
		contentPane.add(panelCompromissos);
		panelCompromissos.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 908, 161);
		panelCompromissos.add(scrollPane);
		
		tableCompromissos = new JTable(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(tableCompromissos);
		tableCompromissos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Titulo", "Local", "Data Inicio", "Data Termino", "Data Notifica\u00E7\u00E3o"
			}
		));
		tableCompromissos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirJanelaCompromisso(e);
			}
		});
		tableCompromissos.getTableHeader().setReorderingAllowed(false);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCompromisso();
			}
		});
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFocusable(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBackground(new Color(150, 50, 50));
		btnExcluir.setBounds(795, 183, 123, 40);
		panelCompromissos.add(btnExcluir);
		
		JLabel lblInstrucoes = new JLabel("<HTML>\r\n<p>\r\nInstruções:<br>\r\nPara visualizar todas as informações do compromisso, clique duas vezes no compromisso.<br>\r\nPara excluir um compromisso, selecione o compromisso e clique no bota EXCLUIR.<br>\r\nPara atualizar, preencha apenas os campos que deverão ser atualizados, selecione um compromisso e pressione o botao ATUALIZAR.\r\n</p>\r\n</HTML>");
		lblInstrucoes.setVerticalAlignment(SwingConstants.TOP);
		lblInstrucoes.setBounds(10, 183, 641, 83);
		panelCompromissos.add(lblInstrucoes);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarCompromisso();
			}
		});
		btnAtualizar.setFocusable(false);
		btnAtualizar.setBounds(661, 183, 123, 40);
		panelCompromissos.add(btnAtualizar);
		
		JLabel lblNomeAgenda = new JLabel(this.agenda.getNomeAgenda());
		lblNomeAgenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeAgenda.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNomeAgenda.setBounds(309, 17, 308, 21);
		contentPane.add(lblNomeAgenda);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarCompromisso();
			}
		});
		btnCadastrar.setFocusable(false);
		btnCadastrar.setBounds(815, 560, 123, 40);
		contentPane.add(btnCadastrar);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTitulo.setBounds(10, 380, 68, 20);
		contentPane.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(95, 380, 175, 21);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescricao.setBounds(10, 411, 68, 20);
		contentPane.add(lblDescricao);
		
		JScrollPane scrollPaneDescricao = new JScrollPane();
		scrollPaneDescricao.setBounds(95, 408, 175, 83);
		contentPane.add(scrollPaneDescricao);
		
		txtDescricao = new JTextArea();
		scrollPaneDescricao.setViewportView(txtDescricao);
		
		JLabel lblLocal = new JLabel("Local:");
		lblLocal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocal.setBounds(10, 520, 68, 20);
		contentPane.add(lblLocal);
		
		txtLocal = new JTextField();
		txtLocal.setColumns(10);
		txtLocal.setBounds(95, 520, 175, 21);
		contentPane.add(txtLocal);
		
		dateChooserInicio = new JDateChooser();
		dateChooserInicio.setBounds(336, 405, 147, 20);
		dateChooserInicio.setDateFormatString("dd/MM/yyyy");
		contentPane.add(dateChooserInicio);
		
		dateChooserFim = new JDateChooser();
		dateChooserFim.setBounds(527, 411, 147, 20);
		dateChooserFim.setDateFormatString("dd/MM/yyyy");
		contentPane.add(dateChooserFim);
		
		dateChooserNotificacao = new JDateChooser();
		dateChooserNotificacao.setBounds(737, 411, 147, 20);
		dateChooserNotificacao.setDateFormatString("dd/MM/yyyy");
		contentPane.add(dateChooserNotificacao);
		
		txtHoraInicio = new JFormattedTextField(this.mascaraHora);
		txtHoraInicio.setBounds(336, 471, 110, 20);
		contentPane.add(txtHoraInicio);
		
		txtHoraFim = new JFormattedTextField(this.mascaraHora);
		txtHoraFim.setBounds(527, 471, 110, 20);
		contentPane.add(txtHoraFim);
		
		txtHoraNotificacao = new JFormattedTextField(this.mascaraHora);
		txtHoraNotificacao.setBounds(737, 471, 110, 20);
		contentPane.add(txtHoraNotificacao);
		
		JLabel lblHoraInicio = new JLabel("Hora de Inicio");
		lblHoraInicio.setBounds(336, 440, 91, 20);
		contentPane.add(lblHoraInicio);
		
		JLabel lblDataInicio = new JLabel("Data de Inicio");
		lblDataInicio.setBounds(336, 380, 91, 20);
		contentPane.add(lblDataInicio);
		
		JLabel lblDataFim = new JLabel("Data de término");
		lblDataFim.setBounds(527, 380, 91, 20);
		contentPane.add(lblDataFim);
		
		JLabel lblHoraFim = new JLabel("Data de término");
		lblHoraFim.setBounds(527, 440, 91, 20);
		contentPane.add(lblHoraFim);
		
		JLabel lblDataNotificacao = new JLabel("Data da Notificação");
		lblDataNotificacao.setBounds(737, 380, 123, 20);
		contentPane.add(lblDataNotificacao);
		
		JLabel lblHoraNotificacao = new JLabel("Hora de Notificação");
		lblHoraNotificacao.setBounds(737, 442, 123, 20);
		contentPane.add(lblHoraNotificacao);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(20, 326, 918, 2);
		contentPane.add(separator);
		
		JLabel lblCadastrar = new JLabel("Cadastrar");
		lblCadastrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCadastrar.setBounds(309, 339, 308, 21);
		contentPane.add(lblCadastrar);
		
		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimparCampos.setFocusable(false);
		btnLimparCampos.setBounds(10, 573, 117, 27);
		contentPane.add(btnLimparCampos);
		
		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportarCSV();
			}
		});
		btnExportar.setFocusable(false);
		btnExportar.setBounds(828, 13, 110, 27);
		contentPane.add(btnExportar);
		
		JButton btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importarCSV();
			}
		});
		btnImportar.setFocusable(false);
		btnImportar.setBounds(703, 13, 104, 27);
		contentPane.add(btnImportar);
		
		chckbxSemDataNotificacao = new JCheckBox("Não quero notificações");
		chckbxSemDataNotificacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tirarDataNotificacao();
			}
		});
		chckbxSemDataNotificacao.setBounds(737, 502, 157, 23);
		contentPane.add(chckbxSemDataNotificacao);
	}
}
