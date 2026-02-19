/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package progetto.applicazione.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import progetto.applicazione.App;
import progetto.applicazione.api.ApiClient;

/**
 * FXML Controller class
 *
 * @author enric
 */
public class RegistrazioneController{

    private static final Logger logger = LogManager.getLogger(RegistrazioneController.class);
    
    
    @FXML private BorderPane root;
    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField codiceFiscale;
    @FXML private TextField email;
    @FXML private PasswordField pwd;
    @FXML private PasswordField confPwd;
    @FXML private Button registratiButton;
    @FXML private Button backButton;
    @FXML private Label errorLabel;
    
    @FXML
    public void initialize(){
        
        root.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
    }
    
    @FXML
    public void back() throws IOException{
        
        logger.debug("Premuto tasto indietro");
        
        String testoOriginale = registratiButton.getText();
        setLoadingState(true);
        
        registratiButton.setText("Waiting...");
        
        try {
            App.setRoot("main");
        } catch (IOException ioe) {
            
            logger.error("Errore cambio schermata da registrazione a main", ioe);
            
            setLoadingState(false);
            registratiButton.setText(testoOriginale);
        }
        
    }
    
    private void setLoadingState(boolean loading){
        nome.setDisable(loading);
        cognome.setDisable(loading);
        codiceFiscale.setDisable(loading);
        email.setDisable(loading);
        pwd.setDisable(loading);
        confPwd.setDisable(loading);
        registratiButton.setDisable(loading);
        backButton.setDisable(loading);
    }
    
    @FXML
    private void handleRegistrazione() {

        logger.debug("Tentativo di registrazione");

        resetFeedback();

        String nomeVal = nome.getText().trim();
        String cognomeVal = cognome.getText().trim();
        String cfVal = codiceFiscale.getText().trim().toUpperCase();
        String emailVal = email.getText().trim();
        String pwdVal = pwd.getText();
        String confPwdVal = confPwd.getText();

        
        if (nomeVal.isEmpty() || cognomeVal.isEmpty() || cfVal.isEmpty()
                || emailVal.isEmpty() || pwdVal.isEmpty() || confPwdVal.isEmpty()) {
            mostraErrore("Compila tutti i campi");
            return;
        }

        if (!emailVal.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            mostraErrore("Email non valida");
            return;
        }

        if (!cfVal.matches("^[A-Z0-9]{16}$")) {
            mostraErrore("Codice fiscale non valido");
            return;
        }

        if (!pwdVal.equals(confPwdVal)) {
            mostraErrore("Le password non coincidono");
            return;
        }

        String testoOriginale = registratiButton.getText();
        setLoadingState(true);
        registratiButton.setText("Registrazione...");

        
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    ApiClient.registrazione(nomeVal, cognomeVal, cfVal, emailVal, pwdVal);

                    Platform.runLater(() -> {
                        mostraSuccesso("Registrazione completata con successo!");
                        logger.info("Registrazione effettuata per {}", emailVal);
                        try {
                            App.setRoot("creaSocieta");
                        } catch (IOException ioe) {
                            mostraErrore("Errore cambio schermata");
                        }
                        registratiButton.setText(testoOriginale);
                        setLoadingState(false);
                    });
                } catch (Exception e) {
                    logger.error("Errore durante la registrazione", e);
                    Platform.runLater(() -> {
                        mostraErrore("Errore durante la registrazione");
                        registratiButton.setText(testoOriginale);
                        setLoadingState(false);
                    });
                }
                return null;
            }
        };

        new Thread(task).start();

    }

    
    private void resetFeedback() {
        errorLabel.setText("");
        errorLabel.getStyleClass().remove("error");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    private void mostraErrore(String messaggio) {
        errorLabel.setText(messaggio);
        if (!errorLabel.getStyleClass().contains("error")) {
            errorLabel.getStyleClass().add("error");
        }
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void mostraSuccesso(String messaggio) {
        errorLabel.setText(messaggio);
        errorLabel.getStyleClass().remove("error");
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }


    
}
