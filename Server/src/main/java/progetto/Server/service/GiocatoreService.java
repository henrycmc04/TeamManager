/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progetto.Server.beans.Categoria;
import progetto.Server.beans.CertificatoMedico;
import progetto.Server.beans.Giocatore;
import progetto.Server.beans.IscrizioneStagionale;
import progetto.Server.dto.request.CreaGiocatoreRequestDTO;
import progetto.Server.dto.response.GiocatoreResponseDTO;
import progetto.Server.repository.CategoriaRepository;
import progetto.Server.repository.CertificatoMedicoRepository;
import progetto.Server.repository.GiocatoreRepository;
import progetto.Server.repository.IscrizioneStagionaleRepository;
import progetto.Server.repository.UtenteRepository;
import progetto.Server.repository.UtentiSocietaRepository;

/**
 *
 * @author enric
 */
@Service
public class GiocatoreService {
    
    private final GiocatoreRepository giocatoreRepository;
    private final IscrizioneStagionaleRepository iscrizioneRepository;
    private final UtentiSocietaRepository utentiSocietaRepository;
    private final CategoriaRepository categoriaRepository;
    private final TokenService tokenService;
    private final UtenteRepository utenteRepository;
    private final CertificatoMedicoRepository certificatoRepository;

    public GiocatoreService(GiocatoreRepository giocatoreRepository, IscrizioneStagionaleRepository iscrizioneRepository, UtentiSocietaRepository utentiSocietaRepository, CategoriaRepository categoriaRepository, TokenService tokenService, UtenteRepository utenteRepository, CertificatoMedicoRepository certificatoRepository) {
        this.giocatoreRepository = giocatoreRepository;
        this.iscrizioneRepository = iscrizioneRepository;
        this.utentiSocietaRepository = utentiSocietaRepository;
        this.categoriaRepository = categoriaRepository;
        this.tokenService = tokenService;
        this.utenteRepository = utenteRepository;
        this.certificatoRepository = certificatoRepository;
    }

    @Transactional(readOnly = true)
    public List<GiocatoreResponseDTO> getGiocatoriPerSocieta(String token) {
        Long idSocieta = getSocietaIdFromToken(token);
        String stagione = calcolaStagione(LocalDate.now());
        
        return categoriaRepository.findByIdSocieta(idSocieta).stream()
                .flatMap(c -> iscrizioneRepository.findByIdCategoria(c.getIdCategoria()).stream())
                .filter(i -> i.getStagione().equals(stagione))
                .map(i -> {
                    Giocatore g = i.getGiocatore();
                    LocalDate scadenza = certificatoRepository.findFirstByGiocatoreOrderByDataScadenzaDesc(g).map(CertificatoMedico::getDataScadenza).orElse(null);
                    
                    // Conversione da Stringa DB a Enum per il DTO
                    progetto.Server.enumeration.Categoria catEnum = progetto.Server.enumeration.Categoria.valueOf(i.getCategoria().getNomeCategoria());

                    return new GiocatoreResponseDTO(g.getIdGiocatore(), g.getNome(), g.getCognome(),g.getCodiceFiscale(), catEnum, scadenza);
                }).collect(Collectors.toList());
    }
    
    @Transactional
    public void aggiungiGiocatore(CreaGiocatoreRequestDTO req, String token) {
        Long idSocieta = getSocietaIdFromToken(token);
        String stagione = calcolaStagione(LocalDate.now());

        // Trovo o crea la categoria
        String nomeCatDaCercare = req.getCategoria().name(); 
        
        Categoria categoria = categoriaRepository.findByIdSocietaAndNomeCategoria(idSocieta, nomeCatDaCercare).orElseGet(() -> {
                    Categoria nuova = new Categoria();
                    nuova.setNomeCategoria(nomeCatDaCercare);
                    nuova.setIdSocieta(idSocieta);
                    return categoriaRepository.save(nuova);
                });
        
        /*
        Giocatore g = new Giocatore(req.getNome(), req.getCognome(), req.getDataNascita(), req.getCodiceFiscale(), req.getSesso());
        g = giocatoreRepository.save(g);
        */
        
        Giocatore g = giocatoreRepository.findByCodiceFiscale(req.getCodiceFiscale())
                .orElseGet(() -> {
                    Giocatore nuovo = new Giocatore(req.getNome(), req.getCognome(), req.getDataNascita(), req.getCodiceFiscale(), req.getSesso());
                    return giocatoreRepository.save(nuovo);
                });

        boolean giaIscrittoInStagione = iscrizioneRepository.existsByIdGiocatoreAndStagione(
                g.getIdGiocatore(), 
                stagione
        );

        if (giaIscrittoInStagione) {
            throw new RuntimeException("Il giocatore è già stato iscritto a una categoria per la stagione " + stagione);
        }
    
        IscrizioneStagionale iscr = new IscrizioneStagionale(g.getIdGiocatore(), categoria.getIdCategoria(), stagione);
        iscrizioneRepository.save(iscr);

        if (req.getScadenzaCertificato() != null) {
            CertificatoMedico cert = new CertificatoMedico();
            cert.setGiocatore(g);
            cert.setIdGiocatore(g.getIdGiocatore());
            cert.setDataScadenza(req.getScadenzaCertificato());
            cert.setDataInserimento(LocalDate.now());
            certificatoRepository.save(cert);
        }
    }
    
    @Transactional
    public void eliminaGiocatore(Long id, String token) {
        Long idSocieta = getSocietaIdFromToken(token);
        boolean appartiene = categoriaRepository.findByIdSocieta(idSocieta).stream()
                .anyMatch(c -> iscrizioneRepository.findByIdCategoria(c.getIdCategoria()).stream()
                        .anyMatch(i -> i.getGiocatore().getIdGiocatore().equals(id)));
        if (!appartiene) throw new RuntimeException("Accesso negato");
        giocatoreRepository.deleteById(id);
    }
    
    private Long getSocietaIdFromToken(String token) {
        if (!tokenService.isValid(token)) throw new RuntimeException("Token non valido");
        String email = tokenService.getEmailByToken(token);
        Long idU = utenteRepository.findByEmail(email).orElseThrow().getIdUtente();
        return utentiSocietaRepository.findByIdUtente(idU).stream().findFirst().orElseThrow().getIdSocieta();
    }
    
    protected String calcolaStagione(LocalDate oggi) {
        int annoCorrente = oggi.getYear();
        int meseCorrente = oggi.getMonthValue();

        if (meseCorrente >= 8) {
            return annoCorrente + "/" + (annoCorrente + 1);
        } else {
            return (annoCorrente - 1) + "/" + annoCorrente;
        }
    }
    
    @Transactional
    public void aggiornaCertificato(Long idGiocatore, LocalDate nuovaScadenza, String token) {
        Long idSocieta = getSocietaIdFromToken(token);

        Giocatore g = giocatoreRepository.findById(idGiocatore).orElseThrow(() -> new RuntimeException("Giocatore non trovato"));

        boolean appartiene = categoriaRepository.findByIdSocieta(idSocieta).stream()
                .anyMatch(c -> iscrizioneRepository.findByIdCategoria(c.getIdCategoria()).stream()
                        .anyMatch(i -> i.getGiocatore().getIdGiocatore().equals(idGiocatore)));

        if (!appartiene) throw new RuntimeException("Accesso negato");

        CertificatoMedico nuovoCertificato = new CertificatoMedico(
            g.getIdGiocatore(), 
            LocalDate.now(),
            nuovaScadenza
        );

        certificatoRepository.save(nuovoCertificato);
    }
    
}
