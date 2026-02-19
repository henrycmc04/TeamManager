/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package progetto.applicazione.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import progetto.applicazione.App;
import progetto.applicazione.api.ApiClient;
import progetto.applicazione.beans.Giocatore;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import progetto.applicazione.enumeration.Categoria;

/**
 * FXML Controller class
 *
 * @author enric
 */
public class HomeController{

    @FXML private BorderPane root;
    
    // Top bar
    @FXML private Label societaLabel;
    @FXML private Button logoutButton;
    
    // Squadra e Tabella
    @FXML private ComboBox<Object> squadraComboBox; // Object per contenere String "Tutte" e Categoria Enum
    @FXML private TableView<Giocatore> giocatoriTable;
    @FXML private TableColumn<Giocatore, String> nomeColumn;
    @FXML private TableColumn<Giocatore, String> cognomeColumn;
    @FXML private TableColumn<Giocatore, String> codiceFiscaleColumn;
    @FXML private TableColumn<Giocatore, Categoria> categoriaColumn; // Tipo Enum
    @FXML private TableColumn<Giocatore, LocalDate> scadenzaColumn;
    @FXML private TableColumn<Giocatore, Long> giorniColumn;
    
    private ObservableList<Giocatore> giocatoriList = FXCollections.observableArrayList();
    
    @FXML
    private void initialize() {
        giocatoriTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        codiceFiscaleColumn.setCellValueFactory(new PropertyValueFactory<>("codiceFiscale"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        scadenzaColumn.setCellValueFactory(new PropertyValueFactory<>("scadenzaCertificato"));
        giorniColumn.setCellValueFactory(new PropertyValueFactory<>("giorniRimanenti"));
        
        giorniColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    if (item < 0) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else if (item < 30) {
                        setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: green;");
                    }
                }
            }
        });
        
        giocatoriTable.setItems(giocatoriList);
        
        squadraComboBox.setOnAction(event -> filtraGiocatoriPerCategoria());
        
        caricaDatiUtente();
        setupContextMenu();
    }
    
    private void filtraGiocatoriPerCategoria() {
        Object selected = squadraComboBox.getSelectionModel().getSelectedItem();
        
        if (selected == null || "Tutte".equals(selected)) {
            giocatoriTable.setItems(giocatoriList);
        } else {
            ObservableList<Giocatore> filtered = FXCollections.observableArrayList(
                giocatoriList.stream()
                    .filter(g -> selected.equals(g.getCategoria()))
                    .collect(Collectors.toList())
            );
            giocatoriTable.setItems(filtered);
        }
    }
    
    private void caricaDatiUtente() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                List<Giocatore> giocatori = ApiClient.getGiocatoriPerSocieta();
                String nomeSocieta = ApiClient.getSocietaUtente();

                Platform.runLater(() -> {
                    societaLabel.setText(nomeSocieta);
                    
                    giocatoriList.clear();
                    giocatoriList.addAll(giocatori);

                    squadraComboBox.getItems().clear();
                    squadraComboBox.getItems().add("Tutte");
                    squadraComboBox.getItems().addAll(Categoria.values());
                    squadraComboBox.getSelectionModel().select(0);
                    
                    FXCollections.sort(giocatoriList, (g1, g2) -> 
                        Long.compare(g1.getGiorniRimanenti(), g2.getGiorniRimanenti())
                    );
                    
                    filtraGiocatoriPerCategoria();
                });
                return null;
            }
        };

        task.setOnFailed(e -> mostraErrore("Errore caricamento dati: " + task.getException().getMessage()));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    public void handleAggiungiGiocatore() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("creaGiocatore.fxml"));
            Parent rootPane = loader.load();
            
            CreaGiocatoreController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setTitle("Nuovo Atleta");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(rootPane));
            stage.showAndWait();
            
            if (controller.isSalvato()) {
                caricaDatiUtente();
            }
        } catch (IOException e) {
            mostraErrore("Errore apertura finestra: " + e.getMessage());
        }
    }
    
    @FXML
    public void handleLogout() {
        try {
            ApiClient.logout();
            App.setRoot("main");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem modificaItem = new MenuItem("Aggiorna Scadenza Certificato");
        MenuItem eliminaItem = new MenuItem("Elimina Atleta");

        modificaItem.setOnAction(event -> handleModificaScadenza());
        eliminaItem.setOnAction(event -> handleEliminaGiocatore());

        contextMenu.getItems().addAll(modificaItem, eliminaItem);

        giocatoriTable.setRowFactory(tv -> {
            TableRow<Giocatore> row = new TableRow<>();
            row.contextMenuProperty().bind(
                javafx.beans.binding.Bindings.when(row.emptyProperty())
                    .then((ContextMenu) null)
                    .otherwise(contextMenu)
            );
            return row;
        });
    }

    private void handleEliminaGiocatore() {
        Giocatore selezionato = giocatoriTable.getSelectionModel().getSelectedItem();
        if (selezionato == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Vuoi davvero eliminare " + selezionato.getNome() + "?", 
            ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    ApiClient.eliminaGiocatore(selezionato.getIdGiocatore());
                    giocatoriList.remove(selezionato);
                    filtraGiocatoriPerCategoria();
                } catch (Exception e) {
                    mostraErrore("Errore eliminazione: " + e.getMessage());
                }
            }
        });
    }
    
    private void handleModificaScadenza() {
        Giocatore selezionato = giocatoriTable.getSelectionModel().getSelectedItem();
        if (selezionato == null) return;

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Modifica Scadenza");
        dialog.setHeaderText("Nuova data per: " + selezionato.getNome());

        ButtonType salvaBtn = new ButtonType("Aggiorna", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(salvaBtn, ButtonType.CANCEL);

        DatePicker picker = new DatePicker(selezionato.getScadenzaCertificato());
        dialog.getDialogPane().setContent(picker);

        dialog.setResultConverter(btn -> {
            if (btn == salvaBtn) {
                try {
                    String testo = picker.getEditor().getText();
                    if (testo != null && !testo.isBlank()) {
                        picker.setValue(picker.getConverter().fromString(testo));
                    }
                    return picker.getValue();
                } catch (java.time.format.DateTimeParseException e) {
                    Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Formato data non valido!").show());
                    return null; 
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(nuovaData -> {
            if (nuovaData != null && !nuovaData.isBefore(selezionato.getScadenzaCertificato())) {
                if (nuovaData.equals(selezionato.getScadenzaCertificato())) return;

                try {
                    ApiClient.aggiornaScadenza(selezionato.getIdGiocatore(), nuovaData);
                    selezionato.setScadenzaCertificato(nuovaData);
                    giocatoriTable.refresh(); // Aggiorna la riga nella tabella
                } catch (Exception e) {
                    mostraErrore("Errore aggiornamento: " + e.getMessage());
                }
            } else if (nuovaData != null) {
                new Alert(Alert.AlertType.WARNING, "La nuova scadenza non può essere precedente a quella attuale!").show();
            }
        });
    }

    private void mostraErrore(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, msg);
            alert.show();
        });
    }
    
    
}
