/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.controller;

import org.springframework.web.bind.annotation.*;
import progetto.Server.dto.request.CreaSocietaRequestDTO;
import progetto.Server.dto.response.SocietaResponseDTO;
import progetto.Server.service.SocietaService;

/**
 *
 * @author enric
 */
@RestController
@RequestMapping(path = "/api/societa")
public class SocietaController {
    
    private final SocietaService societaService;

    public SocietaController(SocietaService societaService) {
        this.societaService = societaService;
    }
    
    @PostMapping("/crea")
    public void creaSocieta(@RequestBody CreaSocietaRequestDTO req, @RequestHeader("X-Token") String token){
        societaService.creaSocieta(req, token);
    }
    
    @GetMapping("/utente")
    public @ResponseBody SocietaResponseDTO getSocietaUtente(@RequestHeader("X-Token") String token){
        return societaService.getSocietaByUtente(token);
    }
    
    
}
