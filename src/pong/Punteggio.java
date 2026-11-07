// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 1: il Punteggio. Scadenza: domenica 25 ottobre 2026.
package pong;

import java.util.ArrayList;
import java.util.List;

/**
 * I conti di una partita: chi ha segnato, quanti punti ha ciascun lato, chi ha vinto.
 * La Partita gli dice chi ha fatto punto; lo Schermo chiede i punti alla Partita, che li chiede a lui.
 *
 * La regola per vincere: un lato vince quando ha almeno {@code puntiPerVincere} punti e almeno
 * {@code scartoMinimo} punti più dell'altro. Esempi, con 11 punti e scarto 2: 11 a 9 vince;
 * 11 a 10 si continua; 12 a 10 vince. Con scarto 1 vince chi arriva per primo a 11.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi qui sotto (e i campi che vi servono).
 * Ogni metodo ha il suo contratto, nel commento sopra: cosa pretende, cosa promette.
 * I test ufficiali (cartella test) controllano i contratti.
 */
public class Punteggio {

    // scrivete qui i campi che vi servono
    private int puntiPerVincere; 
    private int scartoMinimo;
    private int puntiDestra; 
    private int puntiSinistra;
    private List<Lato> storia = new ArrayList<>();
    private Lato vincitore = null;
    
    

    /**
     * Una partita nuova, 0 a 0, con la sua regola per vincere.
     * Pretende: puntiPerVincere è almeno 1; scartoMinimo è almeno 1.
     * Promette: una partita 0 a 0, non finita, senza vincitore e con la storia vuota.
     */
    public Punteggio(int puntiPerVincere, int scartoMinimo) {
        if (puntiPerVincere < 1 || scartoMinimo < 1) {
            throw new IllegalArgumentException("I punti per vincere e lo scarto minimo devono essere almeno 1");
        }
        this.puntiPerVincere = puntiPerVincere;
        this.scartoMinimo = scartoMinimo; 
        puntiDestra = 0;
        puntiSinistra = 0; 
        vincitore = null; 
    }

    /**
     * Il lato chi ha fatto un punto.
     * Pretende: chi non è null.
     * Promette: se la partita non era finita, il lato chi ha un punto in più, l'altro lato non
     *           cambia e il punto si aggiunge in fondo alla storia; se con questo punto il lato chi
     *           vince (regola in cima alla classe), la partita è finita e il vincitore è chi.
     *           Se la partita era già finita, non cambia niente.
     */
    public void segnaPunto(Lato chi) {
        if(chi == null) throw new IllegalArgumentException("Il lato non può essere null");

        if(isFinita()) return;

        if(chi == Lato.SINISTRA){
            puntiSinistra ++ ;
            storia.add(chi);
        }else{
            puntiDestra ++;
            storia.add(chi);  
        }
        if((puntiDestra >= puntiPerVincere && (puntiDestra - puntiSinistra) >= scartoMinimo) || (puntiSinistra >= puntiPerVincere && (puntiSinistra - puntiDestra) >= scartoMinimo )){
                vincitore = chi; 
        }

    }

    /**
     * Pretende: chi non è null.
     * Promette: i punti del lato chi. Non cambia niente.
     */
    public int getPunti(Lato chi) {
        if(chi != null){
            if(chi == Lato.SINISTRA){
                return puntiSinistra;
            }else{
                return puntiDestra; 
            }
        } else throw new IllegalArgumentException(); 
    }

    /**
     * Promette: vero se la partita è finita, cioè se un lato ha vinto. Non cambia niente.
     */
    public boolean isFinita() {
        if( vincitore == null){
            return false; 
        }else{
            return true; 
        }
    }

    /**
     * Promette: il lato che ha vinto; null se la partita non è finita. Non cambia niente.
     */
    public Lato getVincitore() {
        if(isFinita() && vincitore != null){
            return vincitore; 
        }else{
            return null; 
        }
    }

    /**
     * L'arbitro ci ripensa: toglie l'ultimo punto della storia (l'ultimo segnato e non già annullato).
     * Pretende: niente.
     * Promette: se nella storia c'è almeno un punto, l'ultimo punto viene tolto, dai conti e dalla
     *           storia, come se non fosse mai stato segnato. Se era il punto che aveva chiuso la
     *           partita, la partita torna in corso, senza vincitore. Se la storia è vuota, non cambia
     *           niente.
     */
    public void annullaUltimoPunto() {
        if(storia.size() >= 1){
            vincitore = null;
            if(storia.get(storia.size()-1) == Lato.SINISTRA){
                puntiSinistra -- ; 
            }else{
                puntiDestra -- ; 
            }
            storia.remove(storia.size() - 1);
        }
    }

    /**
     * La storia dei punti, dal primo all'ultimo: per ogni punto segnato (e non annullato), il lato
     * che l'ha segnato.
     * Pretende: niente.
     * Promette: una lista con i punti in ordine. Chi riceve la lista non può cambiare il Punteggio
     *           attraverso di lei: aggiungere, togliere o cambiare elementi della lista ricevuta
     *           (se la lista lo permette) non cambia i punti, il vincitore né la storia del Punteggio.
     *           Non promette altro: la lista ricevuta può restare com'era o mostrare anche i punti
     *           segnati dopo; chi la usa non deve contare né sull'una né sull'altra cosa.
     */
    public List<Lato> getStoria() {
        return List.copyOf(storia); 
    }

    /**
     * Una partita nuova con la stessa regola.
     * Pretende: niente.
     * Promette: 0 a 0, non finita, senza vincitore, storia vuota; la regola per vincere non cambia.
     */
    public void azzera() {
        puntiDestra = 0;
        puntiSinistra = 0; 
        vincitore = null;
        storia = new ArrayList<>();
    
    }

    /**
     * Proprietà che valgono sempre (Invariante di Punteggio):
     * 1. La lista "storia" non contiene mai elementi null.
     * 2. Lo scarto minimo e i punti per vincere impostati per la vittoria non sono mai minori di 1.
     * 3. I punti di ciascun giocatore (calcolati scorrendo la storia) corrispondono sempre 
     *    esattamente a quanto restituito da getPunti().
     * 4. Se è stato dichiarato un vincitore, questo deve aver raggiunto la soglia dei 
     *    puntiPerVincere e avere un vantaggio sull'avversario di almeno scartoMinimo punti.
     */

    public boolean repOK() {
        // 1. Controllo base
        if (storia.contains(null)) return false;

        // 2. Controllo scarto minimo e punti per vincere
        if (scartoMinimo < 1 || puntiPerVincere < 1) return false;
        
        // 3. Controllo di coerenza: i punti dichiarati corrispondono alla storia?
        int d = 0, s = 0;
        for (int i = 0; i < storia.size(); i++) {
            if(storia.get(i) == Lato.DESTRA) d++;
            else s++;
        }
        if(getPunti(Lato.DESTRA) != d || getPunti(Lato.SINISTRA) != s) return false;

        // 4. Controllo regole di vittoria
        Lato vincitore = getVincitore();
        if (vincitore != null) {
            Lato perdente = (vincitore == Lato.DESTRA) ? Lato.SINISTRA : Lato.DESTRA;
            if (getPunti(vincitore) < puntiPerVincere) return false;
            if (getPunti(vincitore) - getPunti(perdente) < scartoMinimo) return false;
        }
        
        return true;
    }
}
