/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.Societa;

/**
 *
 * @author enric
 */
public interface SocietaRepository extends CrudRepository<Societa, Long>{
    
    Optional<Societa> findByPartitaIva(String partitaIva);

    boolean existsByNome(String nome);

    boolean existsByPartitaIva(String partitaIva);
    
}
