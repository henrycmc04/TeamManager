/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.Utente;

/**
 *
 * @author enric
 */
public interface UtenteRepository extends CrudRepository<Utente, Long>{
    
    Optional<Utente> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByCodiceFiscale(String codiceFiscale);    
    
}
