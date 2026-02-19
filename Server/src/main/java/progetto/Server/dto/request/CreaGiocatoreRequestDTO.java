/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.dto.request;

import java.time.LocalDate;
import progetto.Server.enumeration.Categoria;
import progetto.Server.enumeration.Sesso;

/**
 *
 * @author enric
 */
public class CreaGiocatoreRequestDTO {
    
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String codiceFiscale;
    private Sesso sesso;
    private Categoria categoria;
    private LocalDate scadenzaCertificato;

    public CreaGiocatoreRequestDTO() {
    }

    public CreaGiocatoreRequestDTO(String nome, String cognome, LocalDate dataNascita, String codiceFiscale, Sesso sesso, Categoria categoria, LocalDate scadenzaCertificato) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.categoria = categoria;
        this.scadenzaCertificato = scadenzaCertificato;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDate getScadenzaCertificato() {
        return scadenzaCertificato;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setScadenzaCertificato(LocalDate scadenzaCertificato) {
        this.scadenzaCertificato = scadenzaCertificato;
    }
    
}
