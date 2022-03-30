package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.*;

public class Model {
	
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;

	public Model() {
		this.corsoDAO = new CorsoDAO();
		this.studenteDAO = new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi() {
		return corsoDAO.getTuttiICorsi();
	}
	
	public Studente getStudenteDaMatricola(Integer matricola) {
		return studenteDAO.getStudenteDaMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return corsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiPerStudente(Integer matricola) {
		return studenteDAO.getCorsiPerStudente(matricola);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return corsoDAO.inscriviStudenteACorso(studente, corso);
	}
	
	public boolean cercaStudenteInCorso(Studente studente, Corso corso) {
		return studenteDAO.cercaStudenteInCorso(studente, corso);
	}
	
}
