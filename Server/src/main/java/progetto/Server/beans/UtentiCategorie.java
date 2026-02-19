/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.beans;

import jakarta.persistence.*;
import progetto.Server.chiaviprimariecomposte.UtentiCategorieId;

/**
 *
 * @author enric
 */
@Entity
@Table(name = "utenti_categorie")
@IdClass(UtentiCategorieId.class)
public class UtentiCategorie {
    
    @Id
    @Column(name = "id_utente")
    private Long idUtente;

    @Id
    @Column(name = "id_categoria")
    private Long idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_utente", insertable = false, updatable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    public UtentiCategorie() {
    }

    public UtentiCategorie(Long idUtente, Long idCategoria) {
        this.idUtente = idUtente;
        this.idCategoria = idCategoria;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public Utente getUtente() {
        return utente;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
}
