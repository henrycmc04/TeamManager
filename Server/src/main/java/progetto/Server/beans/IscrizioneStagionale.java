/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.beans;

import jakarta.persistence.*;

/**
 *
 * @author enric
 */
@Entity
@Table(name = "iscrizioni_stagionali", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_giocatore", "id_categoria", "stagione"})
})
public class IscrizioneStagionale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iscrizione")
    private Long idIscrizione;

    @Column(name = "id_giocatore", nullable = false)
    private Long idGiocatore;

    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @Column(name = "stagione", nullable = false, length = 9)
    private String stagione;

    @ManyToOne
    @JoinColumn(name = "id_giocatore", insertable = false, updatable = false)
    private Giocatore giocatore;

    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    public IscrizioneStagionale() {
    }

    public IscrizioneStagionale(Long idGiocatore, Long idCategoria, String stagione) {
        this.idGiocatore = idGiocatore;
        this.idCategoria = idCategoria;
        this.stagione = stagione;
    }

    public Long getIdIscrizione() {
        return idIscrizione;
    }

    public Long getIdGiocatore() {
        return idGiocatore;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public String getStagione() {
        return stagione;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setIdIscrizione(Long idIscrizione) {
        this.idIscrizione = idIscrizione;
    }

    public void setIdGiocatore(Long idGiocatore) {
        this.idGiocatore = idGiocatore;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
}
