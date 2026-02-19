/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.UtentiCategorie;
import progetto.Server.chiaviprimariecomposte.UtentiCategorieId;

/**
 *
 * @author enric
 */
public interface UtentiCategorieRepository extends CrudRepository<UtentiCategorie, UtentiCategorieId>{
    
    List<UtentiCategorie> findByIdUtente(Long idUtente);
    
}
