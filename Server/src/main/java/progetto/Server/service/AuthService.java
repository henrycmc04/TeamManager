/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progetto.Server.beans.Utente;
import progetto.Server.dto.request.LoginRequestDTO;
import progetto.Server.dto.request.RegistrazioneRequestDTO;
import progetto.Server.dto.response.LoginResponseDTO;
import progetto.Server.repository.UtenteRepository;
import progetto.Server.repository.UtentiSocietaRepository;

/**
 *
 * @author enric
 */
@Service
public class AuthService {
    
    private final UtenteRepository utenteRepository;
    private final UtentiSocietaRepository utenteSocietaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UtenteRepository utenteRepository, UtentiSocietaRepository utenteSocietaRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.utenteRepository = utenteRepository;
        this.utenteSocietaRepository = utenteSocietaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }
    
    public LoginResponseDTO login(LoginRequestDTO req){
        
        Utente utente = utenteRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        if (!passwordEncoder.matches(req.getPassword(), utente.getPasswordHash())) {
            throw new RuntimeException("Password errata");
        }
        
        String token = tokenService.generaToken(utente.getEmail());
        
        boolean haSocieta = utenteSocietaRepository.existsByIdUtente(utente.getIdUtente());
        
        return new LoginResponseDTO(token, utente.getEmail(), haSocieta);
        
    }
    
    public LoginResponseDTO registrazione(RegistrazioneRequestDTO req){
        
        if (utenteRepository.existsByEmail((req.getEmail()))) {
            throw new RuntimeException("Email già registrata");
        }
        
        if (utenteRepository.existsByCodiceFiscale(req.getCodiceFiscale())) {
            throw new RuntimeException("Codice fiscale già registrato");
        }
        
        Utente utente = new Utente();
        utente.setNome(req.getNome());
        utente.setCognome(req.getCognome());
        utente.setEmail(req.getEmail());
        utente.setCodiceFiscale(req.getCodiceFiscale());
        utente.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        
        utenteRepository.save(utente);
        
        String token = tokenService.generaToken(utente.getEmail());
        
        return new LoginResponseDTO(token, utente.getEmail(), false);
        
    }
    
    public void logout(String token){
        tokenService.invalidateToken(token);
    }
    
    public boolean isValidToken(String token){
        return tokenService.isValid(token);
    }
    
    public String getEmailByToken(String token){
        return tokenService.getEmailByToken(token);
    }
    
}
