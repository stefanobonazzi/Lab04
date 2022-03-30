/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;
	
	public void setModel(Model model) {
		this.model = model;
		
		cmbCorsi.getItems().clear();
		List<Corso> l = model.getTuttiICorsi();
		Collections.sort(l);
		cmbCorsi.getItems().addAll(l);
		cmbCorsi.getItems().add(null);
	}
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbCorsi"
    private ComboBox<Corso> cmbCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader
   
    @FXML
    void doCercaCorsi(ActionEvent event) {
    	Integer matricola;
    	txtRisultato.clear();
    	
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserire una matricola!");
    		return;
    	}
    	
    	if(model.getStudenteDaMatricola(matricola) == null) {
    		txtRisultato.setText("Studente non presente...");
    		return;
    	}
    	
    	List<Corso> l = model.getCorsiPerStudente(matricola);
    	
    	if(l.isEmpty()) {
    		txtRisultato.setText("Studente iscritto a NESSUN corso.");
    		return;
    	}
    	
    	for(Corso c: l) {
    		txtRisultato.appendText(c + "\t" + "\n");
    	}
    	
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	Corso corso = cmbCorsi.getValue();
    	txtRisultato.clear();
    	
    	if(corso == null) {
    		txtRisultato.setText("Selezionare un corso di studi!");
    		return;
    	}
    	
    	List<Studente> l = model.getStudentiIscrittiAlCorso(corso);
    	
    	if(l.isEmpty()) {
    		txtRisultato.setText("Nessun iscritto a questo corso.");
    		return;
    	}
    	
    	for(Studente s: l) {
    		txtRisultato.appendText(s + "\n");
    	}
    	
    }

    @FXML
    void doFindByMatricola(ActionEvent event) {
    	Integer matricola;
    	txtRisultato.clear();
    	
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserire una matricola!");
    		return;
    	}
    	
    	Studente s = model.getStudenteDaMatricola(matricola);
    	
    	if(s == null) {
    		txtRisultato.setText("Studente non presente...");
    		return;
    	}
    	
    	txtNome.setText(s.getNome());
    	txtCognome.setText(s.getCognome());
    	
    }

	@FXML
    void doIscrivi(ActionEvent event) {
		Integer matricola;
		txtRisultato.clear();
    	
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserire una matricola!");
    		return;
    	}
    	
    	Studente studente = this.model.getStudenteDaMatricola(matricola);
    	Corso corso = cmbCorsi.getValue();
    	
    	if(corso == null) {
    		txtRisultato.setText("Selezionare un corso di studi!");
    		return;
    	}
    	
    	boolean presente = this.model.cercaStudenteInCorso(studente, corso);
    	
    	if(presente)
    		txtRisultato.setText("Studente già iscritto al corso di studi");
    	else {
    		boolean iscritto = this.model.inscriviStudenteACorso(studente, corso);
    		
    		if(iscritto)
    			txtRisultato.setText("Studente iscritto al corso di studi!");
    		else
    			txtRisultato.setText("Studente non iscritto correttamente");
    	}
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisultato.clear();
    	txtMatricola.clear();
    	cmbCorsi.setValue(null);
    }

    @FXML
    void doScegliCorso(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
    }
	
}