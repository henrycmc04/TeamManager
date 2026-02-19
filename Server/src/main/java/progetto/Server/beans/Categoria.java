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
@Table(name = "categorie")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "id_societa", nullable = false)
    private Long idSocieta;

    @Column(name = "nome_categoria", length = 50, nullable = false)
    private String nomeCategoria;

    @ManyToOne
    @JoinColumn(name = "id_societa", insertable = false, updatable = false)
    private Societa societa;

    public Categoria() {
    }

    public Categoria(Long idSocieta, String nomeCategoria) {
        this.idSocieta = idSocieta;
        this.nomeCategoria = nomeCategoria;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public Long getIdSocieta() {
        return idSocieta;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public Societa getSocieta() {
        return societa;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdSocieta(Long idSocieta) {
        this.idSocieta = idSocieta;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public void setSocieta(Societa societa) {
        this.societa = societa;
    }
    
    
    
}
