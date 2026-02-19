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
public class MainController{

    private static final Logger logger = LogManager.getLogger(MainController.class);
    
    @FXML private BorderPane root;
    @FXML private Button accediButton;
    @FXML private Button registratiButton;
    @FXML private Button initDbButton;
    @FXML private Label initDbResultLabel;
    
    @FXML
    public void initialize(){
        
        root.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
    }
    
    @FXML
    public void login(){
        
        logger.debug("Premuto tasto login");
        
        String testoOriginale = accediButton.getText();
        
        setLoadingState(true);
        accediButton.setText("Waiting...");
        
        try {
            App.setRoot("login");
        } catch (IOException ioe) {
            
            logger.error("Errore cambio schermata da main a login", ioe);
            
            setLoadingState(false);
            accediButton.setText(testoOriginale);
            
        }
        
    }
    
    @FXML
    public void registrazione(){
        
        logger.debug("Premuto tasto registrazione");
        
        String testoOriginale = registratiButton.getText();
        setLoadingState(true);
        
        registratiButton.setText("Waiting...");
        
        try {
            App.setRoot("registrazione");
        } catch (IOException ioe) {
            
            logger.error("Errore cambio schermata da main a registrazione", ioe);
            
            setLoadingState(false);
            registratiButton.setText(testoOriginale);
        }
        
    }
    
    private void setLoadingState(boolean loading){
        accediButton.setDisable(loading);
        registratiButton.setDisable(loading);
        initDbButton.setDisable(loading);
    }
    
    @FXML
    private void handleInitDatabase() {
        setLoadingState(true);
        initDbResultLabel.setVisible(false);
        initDbResultLabel.setManaged(false);
        initDbResultLabel.getStyleClass().remove("error");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    ApiClient.inizializzaDatabase();

                    Platform.runLater(() -> {
                        showSuccess("Database inizializzato correttamente");
                        setLoadingState(false);
                    });

                } catch (Exception e) {
                    logger.error("Errore inizializzazione database", e);
                    Platform.runLater(() -> {
                        showError("Errore durante l'inizializzazione del database");
                        setLoadingState(false);
                    });
                }
                return null;
            }
        };

        new Thread(task).start();
    }
    

    private void showError(String message) {
        initDbResultLabel.setText(message);
        initDbResultLabel.getStyleClass().remove("success");
        if (!initDbResultLabel.getStyleClass().contains("error")) {
            initDbResultLabel.getStyleClass().add("error");
        }
        initDbResultLabel.setVisible(true);
        initDbResultLabel.setManaged(true);
    }

    
    private void showSuccess(String message) {
        initDbResultLabel.setText(message);
        initDbResultLabel.getStyleClass().remove("error");
        initDbResultLabel.setVisible(true);
        initDbResultLabel.setManaged(true);
    }

    
    
}
