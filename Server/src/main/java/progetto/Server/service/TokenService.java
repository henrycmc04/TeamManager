/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author enric
 */
@Service
public class TokenService {
    
    private Map<String, String> tokenMap = new ConcurrentHashMap<>();
    
    public String generaToken(String email){
        String token = UUID.randomUUID().toString();
        tokenMap.put(token, email);
        return token;
    }
    
    public String getEmailByToken(String token){
        return tokenMap.get(token);
    }
    
    public void invalidateToken(String token){
        tokenMap.remove(token);
    }
    
    public boolean isValid(String token){
        return tokenMap.containsKey(token);
    }
    
}
