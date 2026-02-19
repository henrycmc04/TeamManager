/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 *
 * @author enric
 */
@Entity
@Table(name = "certificati_medici")
public class CertificatoMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificato")
    private Long idCertificato;

    @Column(name = "id_giocatore", nullable = false)
    private Long idGiocatore;

    @Column(name = "data_inserimento", nullable = false)
    private LocalDate dataInserimento;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @ManyToOne
    @JoinColumn(name = "id_giocatore", insertable = false, updatable = false)
    private Giocatore giocatore;

    public CertificatoMedico() {
    }

    public CertificatoMedico(Long idGiocatore, LocalDate dataInserimento, LocalDate dataScadenza) {
        this.idGiocatore = idGiocatore;
        this.dataInserimento = dataInserimento;
        this.dataScadenza = dataScadenza;
    }

    public Long getIdCertificato() {
        return idCertificato;
    }

    public Long getIdGiocatore() {
        return idGiocatore;
    }

    public LocalDate getDataInserimento() {
        return dataInserimento;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setIdCertificato(Long idCertificato) {
        this.idCertificato = idCertificato;
    }

    public void setIdGiocatore(Long idGiocatore) {
        this.idGiocatore = idGiocatore;
    }

    public void setDataInserimento(LocalDate dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }
    
}
