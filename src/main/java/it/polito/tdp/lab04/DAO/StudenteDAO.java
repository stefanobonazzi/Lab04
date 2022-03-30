package it.polito.tdp.lab04.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Studente getStudenteDaMatricola(Integer matricola) {
		String sql = "select s.matricola, s.nome, s.cognome, s.CDS "
				+ "from studente s "
				+ "where s.matricola = ?";
		
		Studente studente = null;
		
		try {
			Connection connection = ConnectDB.getConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
			}
			
			rs.close();
			st.close();
			connection.close();
			return studente;
			
		} catch(SQLException e) {
			System.out.println("ERRORE nel DAO Studente!");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Corso> getCorsiPerStudente(Integer matricola) {
		String sql = "select c.codins, c.crediti, c.nome, c.pd "
				+ "from corso c, iscrizione i "
				+ "where c.codins = i.codins and i.matricola = ?";
		
		List<Corso> ris = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				ris.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return ris;
		} catch(SQLException e) {
			System.out.println("ERRORE nel DAO Studente nel getCorsiPerStudente!");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean cercaStudenteInCorso(Studente studente, Corso corso) {	
		String sql = "select i.matricola, i.codins "
				+ "from iscrizione i "
				+ "where i.matricola = ? and i.codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) 
				return true;
			
			rs.close();
			st.close();
			conn.close();
			return false;
		} catch (SQLException e) {
			System.out.println("ERRORE in iscriviStudenteACorso!");
			e.printStackTrace();
			return false;
		}
		
	}

	
}
