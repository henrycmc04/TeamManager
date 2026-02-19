/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.chiaviprimariecomposte;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author enric
 */
public class UtentiCategorieId implements Serializable{
    
    private Long idUtente;
    private Long idCategoria;

    public UtentiCategorieId() {}

    public UtentiCategorieId(Long idUtente, Long idCategoria) {
        this.idUtente = idUtente;
        this.idCategoria = idCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtentiCategorieId that = (UtentiCategorieId) o;
        return Objects.equals(idUtente, that.idUtente) && Objects.equals(idCategoria, that.idCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtente, idCategoria);
    }
    
}
