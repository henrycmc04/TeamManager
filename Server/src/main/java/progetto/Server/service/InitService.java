/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progetto.Server.dto.request.CreaGiocatoreRequestDTO;
import progetto.Server.dto.request.CreaSocietaRequestDTO;
import progetto.Server.dto.request.RegistrazioneRequestDTO;
import progetto.Server.dto.response.LoginResponseDTO;
import progetto.Server.exception.InitDBException;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author enric
 */
@Service
public class InitService {
    
    @Autowired
    private DataSource dataSource;
    
    private final AuthService authService;
    private final SocietaService societaService;
    private final ObjectMapper objectMapper;
    private final GiocatoreService giocatoreService;

    public InitService(DataSource dataSource, AuthService authService, SocietaService societaService, ObjectMapper objectMapper, GiocatoreService giocatoreService) {
        this.dataSource = dataSource;
        this.authService = authService;
        this.societaService = societaService;
        this.objectMapper = objectMapper;
        this.giocatoreService = giocatoreService;
    }
    
    private static final String dataFileName = "/data.json";
    private static final String sqlFileName = "/init.sql";
    

    public void inizializzaDatabse() throws InitDBException{
        eseguiScriptSQL();
        caricaDati();
    }
    
    private void eseguiScriptSQL() throws InitDBException{
        
        String sqlFile;
        try {
            sqlFile = InitService.class.getResource(sqlFileName).getPath();
        } catch (Exception e) {
            throw new InitDBException("init.sql file not found");
        }
        
        try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                BufferedReader reader =  new BufferedReader(new FileReader(sqlFile))){
            
            String sql = reader.lines().collect(Collectors.joining("\n"));
            String[] statements = sql.split(";");
            
            for (String s : statements) {
                if (s.trim().isEmpty()) {
                    continue;
                }
                stmt.execute(s.trim());
            }
            
            
        } catch (Exception e) {
            throw new InitDBException("Error during sql file execution: " + e.getMessage());
        }
        
        
        
    }
    
    private void caricaDati() throws InitDBException{
        
        String dataFile;
        try {
            dataFile = InitService.class.getResource(dataFileName).getPath();
        } catch (Exception e) {
            throw new InitDBException("data.json file not found");
        }
        
        try {
            
            RegistrazioneRequestDTO user = new RegistrazioneRequestDTO("test", "test", "ABCDEF00G00H000I", "test@test.it", "test");

            LoginResponseDTO login = authService.registrazione(user);

            CreaSocietaRequestDTO societa = new CreaSocietaRequestDTO("Test team", "01234567890");

            societaService.creaSocieta(societa, login.getToken());
            
            InputStream is = getClass().getResourceAsStream(dataFileName);

            CreaGiocatoreRequestDTO[] giocatori = objectMapper.readValue(is, CreaGiocatoreRequestDTO[].class);

            for (CreaGiocatoreRequestDTO g : giocatori) {
                
                if (g.getCodiceFiscale() != null) {
                    g.setCodiceFiscale(g.getCodiceFiscale().toUpperCase());
                }
                
                giocatoreService.aggiungiGiocatore(g, login.getToken());
            }
            
            authService.logout(login.getToken());

        } catch (Exception e) {
            throw new InitDBException("Errore lettura file JSON: " + e.getMessage());
        }
        
        
        
    }
}
