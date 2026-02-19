/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.applicazione.beans;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import progetto.applicazione.enumeration.Categoria;

/**
 *
 * @author enric
 */
public class Giocatore {
    
    private Long idGiocatore;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private Categoria categoria;
    private LocalDate scadenzaCertificato;
    private Long giorniRimanenti;

    public Giocatore() {
    }

    public Giocatore(Long idGiocatore, String nome, String cognome, String codiceFiscale, Categoria categoria, LocalDate scadenzaCertificato) {
        this.idGiocatore = idGiocatore;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.categoria = categoria;
        this.scadenzaCertificato = scadenzaCertificato;
        this.giorniRimanenti = calcolaGiorni(scadenzaCertificato);
    }
    
    private Long calcolaGiorni(LocalDate scadenza) {
        if (scadenza == null) return null;
        return ChronoUnit.DAYS.between(LocalDate.now(), scadenza);
    }

    public Long getIdGiocatore() {
        return idGiocatore;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDate getScadenzaCertificato() {
        return scadenzaCertificato;
    }

    public Long getGiorniRimanenti() {
        return giorniRimanenti;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setScadenzaCertificato(LocalDate scadenzaCertificato) {
        this.scadenzaCertificato = scadenzaCertificato;
        this.giorniRimanenti = calcolaGiorni(scadenzaCertificato);
    }
    
    
}
