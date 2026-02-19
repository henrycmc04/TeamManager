/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progetto.Server.dto.request.LoginRequestDTO;
import progetto.Server.dto.request.RegistrazioneRequestDTO;
import progetto.Server.dto.response.LoginResponseDTO;
import progetto.Server.service.AuthService;

/**
 *
 * @author enric
 */
@RestController
@RequestMapping(path = "/api/autenticazione")
public class AuthController {
    
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/registrazione")
    public LoginResponseDTO registrazione(@RequestBody RegistrazioneRequestDTO req){
        LoginResponseDTO res = authService.registrazione(req);
        return res;
    }
    
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO req){
        LoginResponseDTO res = authService.login(req);
        return res;
    }
    
    @PostMapping("/logout")
    public void logout(@RequestHeader("X-Token") String token){
        authService.logout(token);
    }
    
    @GetMapping("/check")
    public String checkToken(@RequestHeader("X-Token") String token){
        if (authService.isValidToken(token)) {
            return authService.getEmailByToken(token);
        } else{
            throw new RuntimeException("Token non valido");
        }
    }
    
}
