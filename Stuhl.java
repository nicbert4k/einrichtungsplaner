import figuren.*;


/**
 * Ein Stuhl, der im Grafikfenster angezeigt und
 * dessen Eigenschaften veraendert werden koennen.
 * 
 * Erweiterung um eine Methode bewege
 * 
 * @author Claus Albowski
 * @version (okt 2014)
 */
public class Stuhl
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
     * Erzeuge einen neuen Stuhl mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Stuhl() {
        xPosition = 160;
        yPosition = 80;
        breite = 40;
        tiefe  = 40;
        farbe = "blau";
        orientierung = 0;
        istSichtbar = false;
        setzeGrundform();
    }
    
    /**
     * definiert die verwendete Grundform
     */
    private void setzeGrundform() {
        ZusammengesetzteFigur figur = new ZusammengesetzteFigur();
        figur.fuegeHinzu(0, 0, new Linie(0,0,breite,0), 0);
        figur.fuegeHinzu(0, 0, new Linie(0,0,-(breite/20+1),tiefe), 0);
        figur.fuegeHinzu(breite, 0, new Linie(breite,0,breite+breite/20+1,tiefe), 0);
        figur.fuegeHinzu(-(breite/20+1), tiefe, new Linie(-(breite/20+1),tiefe,breite+breite/20+1, tiefe), 0);
        figur.fuegeHinzu(0, breite/10+1, new Linie(0, tiefe/10+1, breite, tiefe/10+1), 0 );
        this.figur = figur;
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
    
    // Loesung fuer den Arbeitsschritt in 1.2 >>>>>
    /**
     * Bewege dies Objekt horizontal um 'dx' und vertikal um 'dy'
     * Bildschirmpunkte.
     */
    public void bewege(int dx, int dy) {
        loesche();
        xPosition += dx;
        yPosition += dy;
        zeichne();
    }
    // Loesung fuer den Arbeitsschritt in 1.2 <<<<<
    
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
              false);          // keine Fuellung
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
