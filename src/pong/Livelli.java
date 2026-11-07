// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 3: i livelli. Scadenza: domenica 8 novembre 2026.
package pong;

/**
 * Il gioco a livelli contro il giocatore automatico: a che livello si è e quante vite restano.
 * Si vince un livello vincendo una partita; perdendo una partita si perde una vita.
 * La Partita gli dice com'è finita ogni partita; il giocatore automatico e la velocità della palla
 * dipendono dal livello.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi qui sotto (e i campi che vi servono).
 * Ogni metodo ha il suo contratto, nel commento sopra. I test ufficiali (ConsegnaTreTest) lo controllano.
 */
public class Livelli {

    // scrivete qui i campi che vi servono
    private int viteAttuali;
    private int viteTot;
    private int numeroLivelli;
    private int livelloAttuale;
    private Lato umano;
    private boolean completato;

    /**
     * Un gioco nuovo.
     * Pretende: niente.
     * Promette: livello 1, con tutte le vite; il gioco non è completato né perso.
     * Lancia IllegalArgumentException se numeroLivelli o vite è minore di 1, o se umano è null.
     */
    public Livelli(int numeroLivelli, int vite, Lato umano) {
        // Rifiuta valori non validi per la configurazione iniziale della partita
        if(numeroLivelli < 1 || vite < 1 || umano == null) throw new IllegalArgumentException();
        this.numeroLivelli = numeroLivelli;
        // Memorizza il numero totale di vite per poterle ripristinare in seguito
        this.viteTot = vite;
        this.viteAttuali = vite;
        this.umano = umano;
        // Inizializza lo stato della partita al primo livello e come non completata
        livelloAttuale = 1;
        completato = false;
    }

    /** Il livello a cui si gioca, fra 1 e getNumeroLivelli(). */
    public int getLivello() {
        return livelloAttuale; // scrivete qui
    }

    /** Quanti livelli ha il gioco. */
    public int getNumeroLivelli() {
        return numeroLivelli; // scrivete qui
    }

    /** Le vite che restano, fra 0 e quelle iniziali. */
    public int getVite() {
        return viteAttuali; // scrivete qui
    }

    /** Il lato su cui gioca la persona (l'altro è il giocatore automatico). */
    public Lato getUmano() {
        return umano; // scrivete qui
    }

    /** Vero se è stato vinto l'ultimo livello. */
    public boolean isCompletato() {
        if(completato) return true;
        return false; // scrivete qui
    }

    /** Vero se le vite sono finite. */
    public boolean isPerso() {
        if(viteAttuali < 1) return true;
        return false; // scrivete qui
    }

    /** Vero se il gioco è completato o perso. */
    public boolean isFinito() {
        if(isCompletato() || isPerso()) return true;
        return false; // scrivete qui
    }

    /**
     * Registra com'è finita una partita.
     * Pretende: niente.
     * Promette: se il gioco è finito (completato o perso), non cambia niente. Altrimenti:
     *           se ha vinto umano, si passa al livello dopo; se era l'ultimo, il gioco è completato
     *           e il livello resta l'ultimo; le vite non cambiano.
     *           Se ha vinto l'altro lato, una vita in meno e il livello non cambia;
     *           con l'ultima vita il gioco è perso.
     * Lancia IllegalArgumentException se vincitore è null, e non cambia niente.
     */
    public void partitaFinita(Lato vincitore) {
        // scrivete qui
        // Verifica che il vincitore sia specificato prima di aggiornare lo stato
        if(vincitore == null) throw new IllegalArgumentException("Il vincitore non puo' essere null");
        // Esegue l'aggiornamento solo se la partita è ancora in corso
        if(!isFinito()){
            // Se vince il giocatore umano, avanza di livello o completa il gioco
            if (vincitore == umano) {
                if (livelloAttuale < numeroLivelli) {
                    livelloAttuale++;
                } else {
                    completato = true; // Se è l'ultimo livello e vince l'umano, il gioco è completato.
                }
            } else {
                // Se vince il computer, il giocatore perde una vita
                viteAttuali--;
            }
        }
    }

    /**
     * Si ricomincia da capo.
     * Pretende: niente.
     * Promette: livello 1, con tutte le vite; il gioco non è completato né perso.
     */
    public void ricomincia() {
        // Ripristina lo stato iniziale recuperando le vite totali memorizzate nel costruttore
        livelloAttuale = 1;
        viteAttuali = viteTot;
        completato = false;
    }
}
