/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.applicazione.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import progetto.applicazione.beans.Giocatore;
import progetto.applicazione.dto.CreaGiocatoreRequestDTO;
import progetto.applicazione.dto.CreaSocietaRequestDTO;
import progetto.applicazione.dto.GiocatoreResponseDTO;
import progetto.applicazione.dto.LoginRequestDTO;
import progetto.applicazione.dto.LoginResponseDTO;
import progetto.applicazione.dto.RegistrazioneRequestDTO;
import progetto.applicazione.dto.SocietaResponseDTO;
import progetto.applicazione.enumeration.Categoria;
import progetto.applicazione.enumeration.Sesso;

/**
 *
 * @author enric
 */
public class ApiClient {
    
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        // 1. Forza Gson a ignorare i campi interni delle Enum (risolve l'errore del modulo)
        .excludeFieldsWithModifiers(java.lang.reflect.Modifier.STATIC, java.lang.reflect.Modifier.TRANSIENT)
        // 2. Assicura che la serializzazione sia pulita
        .enableComplexMapKeySerialization()
        .create();
    
    private static String token;
    private static String email;
    private static boolean haSocieta;
    
    public static boolean isLogged() { return token != null && !token.isBlank(); }
    public static String getEmail() { return email; }
    public static String getToken() { return token; }
    public static boolean getHaSocieta() { return haSocieta; }
    
    private static void requireToken() {
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Utente non autenticato");
        }
    }
    
    private static String sendGet(String path, boolean withToken) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(BASE_URL + path)).GET();
        if (withToken) {
            requireToken();
            builder.header("X-Token", token);
        }
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new RuntimeException(response.body());
        return response.body();
    }
    
    private static String sendPost(String path, Object body, boolean withToken) throws Exception {
        String json = gson.toJson(body);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (withToken) {
            requireToken();
            builder.header("X-Token", token);
        }
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new RuntimeException(response.body());
        return response.body();
    }
    
    private static void sendPostNoBody(String path, boolean withToken) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .POST(HttpRequest.BodyPublishers.noBody());
        if (withToken) {
            requireToken();
            builder.header("X-Token", token);
        }
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new RuntimeException(response.body());
    }
    
    private static void sendDelete(String path, boolean withToken) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .DELETE();
        if (withToken) {
            requireToken();
            builder.header("X-Token", token);
        }
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new RuntimeException(response.body());
    }
    
    public static void registrazione(String nome, String cognome, String cf, String emailInput, String password) throws Exception {
        RegistrazioneRequestDTO req = new RegistrazioneRequestDTO(nome, cognome, cf, emailInput, password);
        String body = sendPost("/autenticazione/registrazione", req, false);
        LoginResponseDTO res = gson.fromJson(body, LoginResponseDTO.class);
        token = res.getToken();
        email = res.getEmail();
        haSocieta = res.isHaSocieta();
    }
    
    public static void login(String emailInput, String password) throws Exception {
        LoginRequestDTO req = new LoginRequestDTO(emailInput, password);
        String body = sendPost("/autenticazione/login", req, false);
        LoginResponseDTO res = gson.fromJson(body, LoginResponseDTO.class);
        token = res.getToken();
        email = res.getEmail();
        haSocieta = res.isHaSocieta();
    }
    
    public static void logout() throws Exception {
        sendPostNoBody("/autenticazione/logout", true);
        token = null; email = null; haSocieta = false;
    }
    
    public static void creaSocieta(String nome, String partitaIva) throws Exception {
        CreaSocietaRequestDTO req = new CreaSocietaRequestDTO(nome, partitaIva);
        sendPost("/societa/crea", req, true);
        haSocieta = true;
    }

    public static String getSocietaUtente() throws Exception {
        String json = sendGet("/societa/utente", true);
        SocietaResponseDTO dto = gson.fromJson(json, SocietaResponseDTO.class);
        return dto.getNome();
    }
    
    public static List<Giocatore> getGiocatoriPerSocieta() throws Exception {
        String json = sendGet("/societa/utente/giocatori/lista", true);
        
        Type listType = new TypeToken<List<GiocatoreResponseDTO>>(){}.getType();
        List<GiocatoreResponseDTO> dtoList = gson.fromJson(json, listType);

        return dtoList.stream().map(dto -> new Giocatore(
            dto.getIdGiocatore(),
            dto.getNome(),
            dto.getCognome(),
            dto.getCodiceFiscale(),
            dto.getCategoria(),
            dto.getScadenzaCertificato()
        )).collect(Collectors.toList());
    }
    
    public static void creaGiocatore(String nome, String cognome, String cf, LocalDate nascita, Sesso sesso, Categoria cat, LocalDate scadenza) throws Exception {
        
        CreaGiocatoreRequestDTO dto = new CreaGiocatoreRequestDTO(
            nome, cognome, nascita, cf, sesso, cat, scadenza
        );
        sendPost("/societa/utente/giocatori/aggiungi", dto, true);
    }

    public static void eliminaGiocatore(Long id) throws Exception {
        sendDelete("/societa/utente/giocatori/elimina/" + id, true);
    }

    public static void aggiornaScadenza(Long id, LocalDate nuovaData) throws Exception {
        sendPostNoBody("/societa/utente/giocatori/aggiorna-scadenza/" + id + "?data=" + nuovaData.toString(), true);
    }
    
    public static void inizializzaDatabase() throws Exception {
        sendPostNoBody("/inizializza", false);
    }
    
    
}
