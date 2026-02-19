/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package progetto.Server.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import progetto.Server.beans.Categoria;

/**
 *
 * @author enric
 */
public interface CategoriaRepository extends CrudRepository<Categoria, Long>{
    
    List<Categoria> findByIdSocieta(Long idSocieta);
    Optional<Categoria> findByIdSocietaAndNomeCategoria(Long idSocieta, String nomeCategoria);
    
}
