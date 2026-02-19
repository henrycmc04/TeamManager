/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.Giocatore;

/**
 *
 * @author enric
 */
public interface GiocatoreRepository extends CrudRepository<Giocatore, Long>{
    
    Optional<Giocatore> findByCodiceFiscale(String codiceFiscale);
    
}
