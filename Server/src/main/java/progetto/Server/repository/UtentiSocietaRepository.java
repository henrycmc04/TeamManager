/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.UtentiSocieta;
import progetto.Server.chiaviprimariecomposte.UtentiSocietaId;

/**
 *
 * @author enric
 */
public interface UtentiSocietaRepository extends CrudRepository<UtentiSocieta, UtentiSocietaId>{
    
    List<UtentiSocieta> findByIdUtente(Long idUtente);

    boolean existsByIdUtente(Long idUtente);

    List<UtentiSocieta> findByIdSocieta(Long idSocieta);
    
}
