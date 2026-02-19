/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package progetto.Server.service;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author enric
 */
public class GiocatoreServiceTest {
    
    public GiocatoreServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCalcolaStagione() {
        GiocatoreService service = new GiocatoreService(null, null, null, null, null, null, null);

        LocalDate dataTest1 = LocalDate.of(2025, 8, 1);
        String risultato1 = service.calcolaStagione(dataTest1);
        
        LocalDate dataTest2 = LocalDate.of(2025, 7, 31);
        String risultato2 = service.calcolaStagione(dataTest2);
        
        LocalDate dataTest3 = LocalDate.of(2026, 1, 15);
        String risultato3 = service.calcolaStagione(dataTest3);

        if (!"2025/2026".equals(risultato1)) {
            fail("La stagione calcolata non è corretta! Atteso: 2025/2026, Ricevuto: " + risultato1);
        }
        
        if (!"2024/2025".equals(risultato2)) {
            fail("La stagione calcolata non è corretta! Atteso: 2025/2026, Ricevuto: " + risultato2);
        }
        
        if (!"2025/2026".equals(risultato3)) {
            fail("La stagione calcolata non è corretta! Atteso: 2025/2026, Ricevuto: " + risultato2);
        }
    }
    
}
