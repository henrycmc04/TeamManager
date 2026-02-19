/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import progetto.Server.exception.InitDBException;
import progetto.Server.service.InitService;

/**
 *
 * @author enric
 */
@Controller
@RequestMapping(path = "/api/inizializza")
public class InitController {
    
    @Autowired
    private InitService initService;
    
    @PostMapping
    public @ResponseBody String inizializzaDatabase(){
        
        try {
            initService.inizializzaDatabse();
        } catch (InitDBException e) {
            return "Failed initialization of DB, " + e;
        }
        
        return "Database inizializzato";
    }
    
}
