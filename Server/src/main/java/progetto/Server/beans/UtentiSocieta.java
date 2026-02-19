/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.beans;

import jakarta.persistence.*;
import progetto.Server.chiaviprimariecomposte.UtentiSocietaId;
import progetto.Server.enumeration.Ruolo;

/**
 *
 * @author enric
 */
@Entity
@Table(name = "utenti_societa")
@IdClass(UtentiSocietaId.class)
public class UtentiSocieta {
    
    @Id
    @Column(name = "id_utente")
    private Long idUtente;

    @Id
    @Column(name = "id_societa")
    private Long idSocieta;

    @Column(name = "ruolo", nullable = false)
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;
    
    @ManyToOne
    @JoinColumn(name = "id_utente", insertable = false, updatable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_societa", insertable = false, updatable = false)
    private Societa societa;

    public UtentiSocieta() {
    }

    public UtentiSocieta(Long idUtente, Long idSocieta, Ruolo ruolo) {
        this.idUtente = idUtente;
        this.idSocieta = idSocieta;
        this.ruolo = ruolo;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public Long getIdSocieta() {
        return idSocieta;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public Societa getSocieta() {
        return societa;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public void setIdSocieta(Long idSocieta) {
        this.idSocieta = idSocieta;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setSocieta(Societa societa) {
        this.societa = societa;
    }
    
}
