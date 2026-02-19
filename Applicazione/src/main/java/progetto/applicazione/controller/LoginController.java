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
public class LoginController{
    
    private static final Logger logger= LogManager.getLogger(LoginController.class);
    
    @FXML private BorderPane root;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button backButton;
    @FXML private Label errorLabel;
    
    @FXML
    public void initialize(){
        
        root.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
    }
    
    @FXML
    public void back(){
        
        logger.debug("Premuto tasto indietro");
        
        setLoadingState(true);
        
        String testoOriginale = loginButton.getText();
        setLoadingState(true);
        
        loginButton.setText("Waiting");

        
        try {
            App.setRoot("main");
        } catch (IOException ioe) {
            
            logger.error("Errore cambio schermata da login a main", ioe);
            
            setLoadingState(false);
            loginButton.setText(testoOriginale);
        }
        
    }
    
    private void setLoadingState(boolean loading){
        emailField.setDisable(loading);
        passwordField.setDisable(loading);
        loginButton.setDisable(loading);
        backButton.setDisable(loading);
    }
    
    
    @FXML
    private void handleLogin() {

        String email = emailField.getText().trim();
        String password = passwordField.getText();

        
        if (email.isEmpty() || password.isEmpty()) {
            showError("Inserisci email e password");
            return;
        }

        setLoadingState(true);
        String testoOriginale = loginButton.getText();
        loginButton.setText("Waiting");

        
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    ApiClient.login(email, password);

                    Platform.runLater(() -> {
                        if (ApiClient.isLogged()) {
                            showSuccess("Login effettuato con successo!");

                            try {
                                if (ApiClient.getHaSocieta()) {
                                    App.setRoot("home");
                                } else {
                                    App.setRoot("creaSocieta");
                                }
                            } catch (IOException ioe) {
                                showError("Errore cambio schermata");
                            }
                        } else {
                            showError("Credenziali non valide");
                        }

                        loginButton.setText(testoOriginale);
                        setLoadingState(false);
                    });

                } catch (Exception e) {
                    //e.printStackTrace();
                    logger.error("Errore durante il login",e);
                    Platform.runLater(() -> {
                        showError("Errore nel login");
                        loginButton.setText(testoOriginale);
                        setLoadingState(false);
                    });
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
