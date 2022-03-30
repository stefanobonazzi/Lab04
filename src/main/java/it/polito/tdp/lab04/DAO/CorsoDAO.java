package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public Corso getCorso(String codins) {
		String sql = "select c.codins, c.crediti, c.nome, c.pd "
				+ "from corso c "
				+ "where c.codins = ?";
		
		Corso ris = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String codinss = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				ris = new Corso(codinss, numeroCrediti, nome, periodoDidattico);
			}

			rs.close();
			st.close();
			conn.close();
			return ris;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}

	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "select s.matricola, s.nome, s.cognome, s.CDS "
				+ "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola and i.codins = ?";
		
		List<Studente> ris = new ArrayList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				ris.add(new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS")));
			}
	
			rs.close();
			st.close();
			conn.close();
			return ris;
		} catch(SQLException e) {
			System.out.println("ERRORE nel CORSODAO, getStudentuIscrittiAlCorso!");
			e.printStackTrace();
			return null;
		}
	}

	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {	
		String sql = "insert into iscrizione "
				+ "value (?, ?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
					
			rs.close();
			st.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			System.out.println("ERRORE in iscriviStudenteACorso!");
			e.printStackTrace();
			return false;
		}
		
	}

}
