package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	String ruolo = boxRuolo.getValue();
    	if (ruolo != null) {
    		for (Arco a : model.artistiConnessi(ruolo) ) {
    			txtResult.appendText(a.getA1() + " --> " + a.getA2() + "; Peso: " + a.getPeso() + "\n");
    		}
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Integer id;
    	try {
    		id = Integer.parseInt( txtArtista.getText() );
    	} catch (NumberFormatException nfe) {
    		txtResult.appendText("Inserire id nel formato valido\n");
    		return;
    		
    	}
    	
    	if ( !model.grafoContiene(id) ) {
    		txtResult.appendText("Artista non presente nel grafo\n");
    		return;
    	} 
    	
    	List<Artist> percorso = model.trovaPercorso( id );
    	txtResult.appendText( "Percorso più lungo: " + percorso.size()+"\n");
    	
    	for (Artist a : percorso) {
    		txtResult.appendText(a.toString() + "\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String ruolo = boxRuolo.getValue();
    	if (ruolo != null) {
    		model.creaGrafo(ruolo);
    		txtResult.clear();
        	txtResult.appendText(model.getInfo());
        	this.btnArtistiConnessi.setDisable(false);
    		this.btnCalcolaPercorso.setDisable(false);
    	} else {
    		txtResult.clear();
        	txtResult.appendText("Seleziona  un ruolo\n");
    	}
    	
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.btnArtistiConnessi.setDisable(true);
		this.btnCalcolaPercorso.setDisable(true);
		boxRuolo.getItems().addAll( model.getRuoli() );
	}
}

