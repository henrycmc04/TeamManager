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
@Table(name = "societa")
public class Societa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_societa")
    private Long idSocieta;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "partita_iva", nullable = false, unique = true)
    private String partitaIva;

    public Societa() {
    }

    public Societa(String nome, String partitaIva) {
        this.nome = nome;
        this.partitaIva = partitaIva;
    }

    public Long getIdSocieta() {
        return idSocieta;
    }

    public String getNome() {
        return nome;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setIdSocieta(Long idSocieta) {
        this.idSocieta = idSocieta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
    
    
}
