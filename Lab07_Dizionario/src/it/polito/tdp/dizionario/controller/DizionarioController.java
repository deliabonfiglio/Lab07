package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.*;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DizionarioController {

	private Model model;
	
	@FXML
	private ResourceBundle resources;
	@FXML // fx:id="testo"
	private Text testo; // Value injected by FXMLLoader
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTrovaGradoMax;

	@FXML
	void doReset(ActionEvent event) {
		txtResult.clear();
		inputParola.clear();
		inputNumeroLettere.clear();
		tuttoVisibile(false);
	}
	
	void tuttoVisibile( boolean valore){
		inputParola.setVisible(valore);
		btnTrovaVicini.setVisible(valore);
		btnTrovaGradoMax.setVisible(valore);
		testo.setVisible(valore);
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		try {
			int dim = Integer.parseInt(inputNumeroLettere.getText());
				
			if(dim < 7){		
				List<String> grafo = new ArrayList<String>(model.createGraph(dim));
				this.tuttoVisibile(true);
					
				txtResult.setText("Grafo creato con successo!\n"+grafo);	
			} else {
				txtResult.setText("Numero di lettere troppo grande, il tuo PC potrebbe bloccarsi.\nInserire un numero minore di 7!");
			}
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
		
	}
//per ottenere il vertice del grafo con il grado massimo.
	@FXML
	void doTrovaGradoMax(ActionEvent event) {	
		try {
			String maxDegree= model.findMaxDegree();
			
			txtResult.setText("Parola di grado massimo: "+maxDegree.toString());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}
//per visualizzare tutte le parole direttamente connesse a quella inserita
	@FXML
	void doTrovaVicini(ActionEvent event) {
		
		try {
			String parola = inputParola.getText();
			List<String> vicini = new ArrayList<String>(model.displayNeighbours(parola));
			
			txtResult.setText("Vicini della parola inserita: \n"+ vicini.toString());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaGradoMax\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert testo != null : "fx:id=\"testo\" was not injected: check your FXML file 'Dizionario.fxml'.";

	}

	public void setModel(Model model) {
		this.model=model;		
	}
}