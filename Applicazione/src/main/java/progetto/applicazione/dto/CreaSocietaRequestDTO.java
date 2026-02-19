/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.applicazione.dto;

/**
 *
 * @author enric
 */
public class CreaSocietaRequestDTO {
    
    private String nome;
    private String partitaIva;

    public CreaSocietaRequestDTO() {
    }

    public CreaSocietaRequestDTO(String nome, String partitaIva) {
        this.nome = nome;
        this.partitaIva = partitaIva;
    }

    public String getNome() {
        return nome;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
    
    
    
}
