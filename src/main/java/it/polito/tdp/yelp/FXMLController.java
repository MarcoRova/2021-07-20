/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
import it.polito.tdp.yelp.model.UserSimili;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	int N = 0;
    	
    	Integer anno = this.cmbAnno.getValue();
    	
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    		
    		if(N<=0.0){
    			this.txtResult.setText("Numeri negativi non ammessi.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		txtResult.setText("Formato non corretto per il numero di recensioni.");
    		return;
    	}
    	
    	if(anno == null) {
    		this.txtResult.appendText("Seleionare un anno per creare il grafo!");
    		return;
    	}
    	
    	this.model.creaGrafo(N, anno);
    	
    	this.txtResult.appendText(this.model.infoGrafo());
    	
    	List<User> users = this.model.getVertici(N);
    	
    	Collections.sort(users);
    	
    	this.cmbUtente.getItems().addAll(users);
    	
    	this.btnUtenteSimile.setDisable(false);	
    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(this.model.getGrafo() == null) {
    		this.txtResult.clear();
    		this.txtResult.setText("Creare prima il grafo!");
    	}
    	
    	User u = this.cmbUtente.getValue();
    	
    	if(u == null) {
    		this.txtResult.appendText("Devi selezionare un utente!");
    		return;
    	}
    	
    	List<UserSimili> simili = this.model.userSimili(u);
    	
    	if(simili.isEmpty()) {
    		this.txtResult.appendText("\nNessun utente simile!");
    	}
    	
    	this.txtResult.appendText("Lista utenti simili a "+u+":");
    	
    	for(UserSimili us : simili) {
    		this.txtResult.appendText("\n"+us.getU()+"	Grado: "+us.getGrado());
    	}
    	
    }
    
    @FXML
    void doSimula(ActionEvent event) {

    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(int a = 2005; a<=2013; a++) {
    		this.cmbAnno.getItems().add(a);
    	}
    	
    	this.btnUtenteSimile.setDisable(true);
    }
}
