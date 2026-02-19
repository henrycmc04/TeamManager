/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package progetto.applicazione.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class CreaSocietaController{

    private static final Logger logger= LogManager.getLogger(CreaSocietaController.class); 
    
    
    
    @FXML private BorderPane root;
    @FXML private TextField nomeSocieta;
    @FXML private TextField partitaIva;
    @FXML private Button creaSocietaButton;
    @FXML private Label errorLabel;
    
    @FXML
    public void initialize() {
        
        root.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
    }
    
    private void setLoadingState(boolean loading){
        nomeSocieta.setDisable(loading);
        partitaIva.setDisable(loading);
        creaSocietaButton.setDisable(loading);
    }
    
    @FXML
    private void handleCreaSocieta() {
        String nome = nomeSocieta.getText().trim();
        String iva = partitaIva.getText().trim();

        
        if (nome.isEmpty() || iva.isEmpty()) {
            showError("Inserisci nome società e partita IVA");
            return;
        }

        setLoadingState(true);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    ApiClient.creaSocieta(nome, iva);

                    Platform.runLater(() -> showSuccess("Società creata con successo!"));
                    App.setRoot("home");
                } catch (Exception e) {
                    logger.error("Errore creazione società", e);
                    Platform.runLater(() -> showError("Errore durante la creazione della società"));
                } finally {
                    Platform.runLater(() -> setLoadingState(false));
                }
                return null;
            }
        };

        
        new Thread(task).start();
    }

    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.getStyleClass().remove("success");
        if (!errorLabel.getStyleClass().contains("error")) {
            errorLabel.getStyleClass().add("error");
        }
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }


    private void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.getStyleClass().remove("error");
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }


    
}
