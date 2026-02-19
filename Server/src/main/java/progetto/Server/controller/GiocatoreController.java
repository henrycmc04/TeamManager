/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import progetto.Server.dto.request.CreaGiocatoreRequestDTO;
import progetto.Server.dto.response.GiocatoreResponseDTO;
import progetto.Server.service.GiocatoreService;

/**
 *
 * @author enric
 */
@Controller
@RequestMapping("/api/societa/utente/giocatori")
public class GiocatoreController {
    
    private final GiocatoreService giocatoreService;

    public GiocatoreController(GiocatoreService giocatoreService) {
        this.giocatoreService = giocatoreService;
    }
    
    @GetMapping("/lista")
    public @ResponseBody List<GiocatoreResponseDTO> getGiocatori(@RequestHeader("X-Token") String token) {
        return giocatoreService.getGiocatoriPerSocieta(token);
    }
    
    @PostMapping("/aggiungi")
    public @ResponseBody void aggiungiGiocatore(@RequestBody CreaGiocatoreRequestDTO req, @RequestHeader("X-Token") String token) {
        giocatoreService.aggiungiGiocatore(req, token);
    }
    
    @DeleteMapping("/elimina/{id}")
    public @ResponseBody void eliminaGiocatore(@PathVariable Long id, @RequestHeader("X-Token") String token) {
        giocatoreService.eliminaGiocatore(id, token);
    }
    
    @PostMapping("/aggiorna-scadenza/{id}")
    public @ResponseBody void aggiornaScadenza(
            @PathVariable Long id, 
            @RequestParam("data") String dataStr, 
            @RequestHeader("X-Token") String token) {
        
        LocalDate nuovaScadenza = LocalDate.parse(dataStr);
        giocatoreService.aggiornaCertificato(id, nuovaScadenza, token);
    }
    
}
