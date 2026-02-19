/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package progetto.Server;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author enric
 */
@SpringBootTest
public class DBConnectionTest {
    
    @Autowired
    private DataSource dataSource;    
    
    public DBConnectionTest() {
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
    public void testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "La connessione al database non deve essere null");
            
            assertFalse(connection.isClosed(), "La connessione dovrebbe essere aperta");
            
            System.out.println("Connessione riuscita con successo al database: " + connection.getMetaData().getURL());
            
        } catch (SQLException e) {
            fail("Errore durante il tentativo di connessione al database: " + e.getMessage());
        }
    }
    
    
}
