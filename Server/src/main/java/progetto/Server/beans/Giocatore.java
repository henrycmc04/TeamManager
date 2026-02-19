/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.beans;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import progetto.Server.enumeration.Sesso;

/**
 *
 * @author enric
 */
@Entity
@Table(name = "giocatori")
public class Giocatore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_giocatore")
    private Long idGiocatore;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "codice_fiscale", unique = true, length = 16)
    private String codiceFiscale;

    @Column(name = "sesso", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sesso sesso;
    
    @OneToMany(mappedBy = "giocatore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IscrizioneStagionale> iscrizioni;

    @OneToMany(mappedBy = "giocatore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificatoMedico> certificati;

    public Giocatore() {
    }

    public Giocatore(String nome, String cognome, LocalDate dataNascita, String codiceFiscale, Sesso sesso) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
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

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public void setIdGiocatore(Long idGiocatore) {
        this.idGiocatore = idGiocatore;
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
    
}
