/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progetto.Server.beans.Societa;
import progetto.Server.beans.UtentiSocieta;
import progetto.Server.dto.request.CreaSocietaRequestDTO;
import progetto.Server.dto.response.SocietaResponseDTO;
import progetto.Server.enumeration.Ruolo;
import progetto.Server.repository.SocietaRepository;
import progetto.Server.repository.UtenteRepository;
import progetto.Server.repository.UtentiSocietaRepository;

/**
 *
 * @author enric
 */
@Service
public class SocietaService {
    
    private final SocietaRepository societaRepository;
    private final UtentiSocietaRepository utentiSocietaRepository;
    private final UtenteRepository utenteRepository;
    private final TokenService tokenService;

    public SocietaService(SocietaRepository societaRepository, UtentiSocietaRepository utentiSocietaRepository, UtenteRepository utenteRepository, TokenService tokenService) {
        this.societaRepository = societaRepository;
        this.utentiSocietaRepository = utentiSocietaRepository;
        this.utenteRepository = utenteRepository;
        this.tokenService = tokenService;
    }
    
    @Transactional
    public void creaSocieta(CreaSocietaRequestDTO req, String token){
        
        if (!tokenService.isValid(token)) {
            throw new RuntimeException("Token non valido");
        }
        
        String email = tokenService.getEmailByToken(token);
        
        Long idUtente = utenteRepository.findByEmail(email).orElseThrow(() -> new  RuntimeException("Utente non trovato")).getIdUtente();
        
        if (utentiSocietaRepository.existsByIdUtente(idUtente)) {
             throw new RuntimeException("Utente ha già una società");
        }
        
        if (societaRepository.existsByPartitaIva(req.getPartitaIva())) {
            throw new RuntimeException("Partita IVA già esistente");
        }
        
        Societa societa = new Societa(req.getNome(), req.getPartitaIva());
        societa = societaRepository.save(societa);
        
        UtentiSocieta utenteSocieta = new UtentiSocieta(idUtente, societa.getIdSocieta(), Ruolo.ADMIN);
        utentiSocietaRepository.save(utenteSocieta);
        
    }
    
    public SocietaResponseDTO getSocietaByUtente(String token){
        
        if (!tokenService.isValid(token)) {
            throw new RuntimeException("Token non valido");
        }
        
        String email = tokenService.getEmailByToken(token);
        
        Long idUtente = utenteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Utente non trovato")).getIdUtente();
        
        List<UtentiSocieta> usList = utentiSocietaRepository.findByIdUtente(idUtente);
        if (usList.isEmpty()) {
            throw new RuntimeException("Utente non associato a nessuna società");
        }
        UtentiSocieta us = usList.get(0);
        
        Societa societa = societaRepository.findById(us.getIdSocieta()).orElseThrow(() -> new RuntimeException("Società non trovata"));
        
        return new SocietaResponseDTO(societa.getNome(), societa.getPartitaIva());
        
    }
        
    
}
