# TeamManager – Sistema di gestione società sportive

**TeamManager** è una soluzione software progettata per semplificare la gestione amministrativa delle società sportive, con un focus particolare sulla **scadenza dei certificati di idoneità agonistica**. L'obiettivo è prevenire l'esclusione di atleti dalle competizioni dovuta a dimenticanze burocratiche o difficoltà nella gestione delle scadenze.

Sviluppata in **Java 21** con **JavaFX**, l'applicazione fornisce supporto alle società per la registrazione, la gestione dei tesserati e il monitoraggio costante delle idoneità mediche.

## Architettura del Sistema

Il sistema è strutturato secondo una configurazione **Client-Server**:

- **TeamManager Server**: Backend sviluppato con **Spring Boot**, che gestisce la logica di business, la sicurezza (Spring Security) e la persistenza dei dati su database **MySQL**. La comunicazione avviene tramite API REST (formato JSON).
- **TeamManager Client**: Applicazione desktop sviluppata in **Java 21** con **JavaFX**, che fornisce l'interfaccia grafica per l'interazione degli utenti e comunica con il server tramite un `ApiClient` dedicato.

## Funzionalità Principali

### Per la Società Sportiva
- **Autenticazione Sicura**: Sistema di registrazione e login con password protette da crittografia hash e gestione degli accessi tramite **X-Token** personalizzati.
- **Gestione Tesserati**: Inserimento, modifica ed esclusione dei giocatori appartenenti alla società.
- **Monitoraggio Medico**: Registrazione delle date di scadenza dei certificati di idoneità agonistica e verifica dell'approssimarsi delle scadenze.
- **Organizzazione Squadre**: Suddivisione dei tesserati per categorie e possibilità di filtrare i dati in tabelle interattive.

### Caratteristiche Tecniche
- **Inizializzazione DB**: Funzionalità integrata per il caricamento automatico di tabelle e dati di test (`init.sql` e `data.json`).
- **Validazione Dati**: Controllo lato client su formati email e codici fiscali prima dell'invio al server.
- **Modularità**: Logica divisa in livelli (Controller, Service, Repository) con uso di DTO per lo scambio dati.

## Prerequisiti

- **Java 21** o superiore
- **MySQL 8.0** o superiore
- **Maven** (per la gestione delle dipendenze)
- **JavaFX Runtime** (incluso nelle dipendenze del progetto)

## Test e Account di Prova

Il sistema include test automatici per la connessione al database e la logica delle stagioni sportive. È possibile testare l'applicazione utilizzando le seguenti credenziali pre-configurate:

- **Email**: `test@test.it`
- **Password**: `test`

## Struttura del Repository

- `/src/main/java`: Contiene il codice sorgente diviso tra logica Server (Spring) e Client (JavaFX).
- `/src/main/resources`: Contiene i file FXML per la UI e gli script SQL di inizializzazione.
- `LICENSE`: Termini della licenza d'uso.

## Licenza

Questo progetto è distribuito sotto licenza **GNU GPL v3**. Consulta il file [LICENSE](LICENSE) per i dettagli completi.
