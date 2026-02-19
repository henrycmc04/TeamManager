/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.IscrizioneStagionale;

/**
 *
 * @author enric
 */
public interface IscrizioneStagionaleRepository extends CrudRepository<IscrizioneStagionale, Long>{
    
    List<IscrizioneStagionale> findByIdCategoria(Long idCategoria);
    List<IscrizioneStagionale> findByIdGiocatore(Long idGiocatore);
    boolean existsByIdGiocatoreAndStagione(Long idGiocatore, String stagione);
    
}


