module progetto.applicazione {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.apache.logging.log4j;
    requires java.net.http;
    requires com.google.gson;
    
    
    opens progetto.applicazione.beans to javafx.base, com.google.gson, javafx.fxml;
    opens progetto.applicazione.enumeration to com.google.gson;
    opens progetto.applicazione.dto to com.google.gson;
    opens progetto.applicazione.controller to javafx.fxml;
    exports progetto.applicazione;
}
