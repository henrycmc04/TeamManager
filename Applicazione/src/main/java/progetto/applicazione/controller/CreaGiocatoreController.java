/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package progetto.applicazione.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import progetto.applicazione.api.ApiClient;
import progetto.applicazione.enumeration.Categoria;
import progetto.applicazione.enumeration.Sesso;

/**
 * FXML Controller class
 *
 * @author enric
 */
public class CreaGiocatoreController{

    @FXML private TextField nomeField, cognomeField, cfField;
    @FXML private DatePicker nascitaPicker, scadenzaPicker;
    
    @FXML private ComboBox<Sesso> sessoComboBox; 
    @FXML private ComboBox<Categoria> categoriaComboBox;

    private boolean salvato = false;

    @FXML
    public void initialize() {
        sessoComboBox.getItems().setAll(Sesso.values());
        categoriaComboBox.getItems().setAll(Categoria.values());

        sessoComboBox.setPromptText("Seleziona...");
        categoriaComboBox.setPromptText("Seleziona...");

        //nascitaPicker.setEditable(true);
    }

    @FXML
    private void handleSalva() {
        try {
            if (nascitaPicker.getEditor().getText() != null && !nascitaPicker.getEditor().getText().isBlank()) {
                nascitaPicker.setValue(nascitaPicker.getConverter().fromString(nascitaPicker.getEditor().getText()));
            }
            if (scadenzaPicker.getEditor().getText() != null && !scadenzaPicker.getEditor().getText().isBlank()) {
                scadenzaPicker.setValue(scadenzaPicker.getConverter().fromString(scadenzaPicker.getEditor().getText()));
            }

            if (isInputValido()) {
                Sesso sesso = sessoComboBox.getValue();
                Categoria cat = categoriaComboBox.getValue();

                ApiClient.creaGiocatore(
                    nomeField.getText().trim(),
                    cognomeField.getText().trim(),
                    cfField.getText().toUpperCase().trim(),
                    nascitaPicker.getValue(),
                    sesso,
                    cat,
                    scadenzaPicker.getValue()
                );

                salvato = true;
                chiudi();
            }
        } catch (java.time.format.DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Il formato della data non è corretto. Usa gg/mm/aaaa").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Errore durante il salvataggio: " + e.getMessage()).show();
        }
    }

    private boolean isInputValido() {
        StringBuilder errori = new StringBuilder();

        if (nomeField.getText().isBlank()) errori.append("- Nome mancante\n");
        if (cognomeField.getText().isBlank()) errori.append("- Cognome mancante\n");
        if (cfField.getText().trim().length() != 16) errori.append("- CF non valido\n");
        if (nascitaPicker.getValue() == null) errori.append("- Data nascita mancante\n");
        if (sessoComboBox.getValue() == null) errori.append("- Seleziona il sesso\n");
        if (categoriaComboBox.getValue() == null) errori.append("- Seleziona la categoria\n");
        if (scadenzaPicker.getValue() == null) errori.append("- Data scadenza mancante\n");
        if (errori.length() > 0) {
            new Alert(Alert.AlertType.WARNING, "Errori:\n" + errori.toString()).show();
            return false;
        }
        return true;
    }

    private void chiudi() {
        ((Stage) nomeField.getScene().getWindow()).close();
    }
    public boolean isSalvato() {
        return salvato;
    }
    
    @FXML
    private void handleAnnulla() {
        chiudi();
    }
    
}
