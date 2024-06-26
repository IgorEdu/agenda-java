package service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;

public class NotificacaoService extends Thread {
	Usuario usuario;
	
	public NotificacaoService(Usuario usuario) throws SQLException, IOException {
		this.usuario = usuario;
//		notificarUsuario(usuario);
	}
	
	private void notificarUsuario(Usuario usuario) throws SQLException, IOException {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		
//		AgendaService agendaService = new AgendaService();
		CompromissoService compromissoService = new CompromissoService();
//		List<Agenda> agendasUsuario = new ArrayList<Agenda>();
		
//		agendasUsuario = agendaService.buscarAgendasUsuario(usuario);
		
//		List<Compromisso> compromissosUsuario = new ArrayList<Compromisso>();
		
		List<Compromisso> compromissosUsuario = compromissoService.buscarCompromissosPorUsuario(usuario.getId());
		
		if(compromissosUsuario == null) return;
//		for(Agenda agenda : agendasUsuario) {
//			compromissosUsuario.addAll(compromissoService.buscarCompromissosAgenda(agenda.getIdAgenda()));
//		}
		
		for(Compromisso compromisso : compromissosUsuario) {
			if(compromisso.getDataNotificacao() != null) {
				LocalDateTime dataNotificacao = LocalDate.parse(data.format(compromisso.getDataNotificacao()))
						.atTime(Integer.parseInt(compromisso.getHorarioNotificacao().substring(0, 2)), Integer.parseInt(compromisso.getHorarioNotificacao().substring(3, 5)));
				
	            LocalDateTime agora = LocalDateTime.now();
	                
	            if(dataNotificacao.isAfter(agora.minusMinutes(1)) && dataNotificacao.isBefore(agora.plusMinutes(1))) {	            	
	            	// Lógica para notificar o usuário
	            	
	            	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' uuuu, 'as' HH:mm", Locale.of("pt", "BR"));
	            	
	            	LocalDateTime dataInicio = LocalDate.parse(data.format(compromisso.getDataInicio()))
							.atTime(Integer.parseInt(compromisso.getHorarioInicio().substring(0, 2)), Integer.parseInt(compromisso.getHorarioInicio().substring(3, 5)));
	            	
	            	JOptionPane.showMessageDialog(null, "Notificação para o compromisso " + compromisso.getTitulo()
	            										 + "\nCuja data de inicio é " + formato.format(dataInicio));
	            }
			}
		}
 
    }
	
	@Override
    public void run() {
        
        try {
            
            while(!isInterrupted()) {

                try {
					notificarUsuario(this.usuario);
					Thread.sleep(60000);
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
            
        } catch (InterruptedException ex) {
        
            Thread.currentThread().interrupt();
        }
    }
}
