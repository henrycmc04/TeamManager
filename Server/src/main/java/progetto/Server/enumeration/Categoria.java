/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package progetto.Server.enumeration;

/**
 *
 * @author enric
 */
public enum Categoria {
    
    PRIMA_SQUADRA("Prima Squadra"),
    U19("U19"),
    U17("U17"),
    U15("U15"),
    U14("U14"),
    U13("U13"),
    ESORDIENTI("Esordienti");

    private final String label;
    
    Categoria(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return label;
    }
    
    @com.fasterxml.jackson.annotation.JsonCreator
    public static Categoria fromValue(String value) {
        for (Categoria c : Categoria.values()) {
            // Questo accetta sia "PRIMA_SQUADRA" che "Prima Squadra"
            if (c.name().equalsIgnoreCase(value) || c.label.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoria non valida: " + value);
    }
    
}
