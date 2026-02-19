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
public class UtentiSocietaId implements Serializable{
    
    private Long idUtente;
    private Long idSocieta;

    public UtentiSocietaId() {}

    public UtentiSocietaId(Long idUtente, Long idSocieta) {
        this.idUtente = idUtente;
        this.idSocieta = idSocieta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UtentiSocietaId)) return false;
        UtentiSocietaId that = (UtentiSocietaId) o;
        return Objects.equals(idUtente, that.idUtente) && Objects.equals(idSocieta, that.idSocieta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtente, idSocieta);
    }
    
}
