import figuren.*;

/**
 * Ein Tisch, der im Grafikfenster angezeigt und
 * dessen Eigenschaften veraendert werden koennen.
 * 
 * @author Claus Albowski
 * @version (sept 2014)
 */
public class Tisch 
{
    private int xPosition;
    private int yPosition;
    private int orientierung;
    private String farbe;
    private boolean istSichtbar;
    private int breite;
    private int tiefe;
    private Object grafikObjekt;
    private Figur figur;
    
    
    /**
     * Erzeuge einen neuen Tisch mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Tisch()  {
        xPosition = 60;
        yPosition = 60;
        breite = 120;
        tiefe  = 60;
        orientierung = 0;
        farbe = "rot";
        istSichtbar = false;
        setzeGrundform();
    }
    
    /**
     * definiert die verwendete Grundform
     */
    private void setzeGrundform() {
        figur = new Rechteck(breite, tiefe);        
    }
    
    /**
     * Mache dies Objekt sichtbar.
     * Wenn es bereits sichtbar ist, tue nichts.
     */
    public void zeige() {
        if (!istSichtbar) {
            istSichtbar = true;
            zeichne();
        }
    }
    
    /**
     * Mache dies Objekt unsichtbar.
     * Wenn es bereits unsichtbar ist, tue nichts.
     */
    public void verberge() {
        loesche(); // "tue nichts" wird in loesche() abgefangen.
        istSichtbar = false;
    }
    
    /**
     * Drehe dies Objekt auf den angegebenen Winkel
     */
    public void dreheAuf(int neuerWinkel) {
        loesche();
        orientierung = neuerWinkel;
        zeichne();
    }
    
    /**
     * Bewege dies Objekt horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void bewegeHorizontal(int entfernung) {
        loesche();
        xPosition = xPosition + entfernung;
        zeichne();
    }
    
    /**
     * Bewege dies Objekt vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void bewegeVertikal(int entfernung) {
        loesche();
        yPosition = yPosition + entfernung;
        zeichne();
    }
    
    
    /**
     * Aendere die Farbe dieses Objektes in 'neueFarbe'.
     * Muster "rot", "gelb" usw.
     */
    public void aendereFarbe(String neueFarbe) {
        loesche();
        farbe = neueFarbe;
        zeichne();
    }
    
    /**
     * Zeichne das Grafikobjekt mit seinen aktuellen Werten auf den Bildschirm.
     */
    private void zeichne() {
        if (istSichtbar) {
            Zeichnung zeichnung = Zeichnung.gibZeichnung();
            grafikObjekt =
            zeichnung.zeichne (   // gibt das Objekt zurueck
              figur.gibForm(xPosition, yPosition, orientierung),    // definiert seinen grafischen Aspekt    
              farbe,              // definiert seine Zeichenfarbe
              false);             // keine Fuellung
            zeichnung.warte(10);
        }
    }
    
    /**
     * Loesche das Grafikobjekt vom Bildschirm.
     */
    private void loesche() {
        if (istSichtbar){
            Zeichnung zeichnung = Zeichnung.gibZeichnung();
            zeichnung.loesche(grafikObjekt);
        }
    }
}
