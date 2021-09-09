import java.util.ArrayList;
import java.awt.Shape;
// Fuer FarbFigur
import java.awt.Color;
import java.util.Hashtable;
import java.util.ConcurrentModificationException;
//Fuer Zeichenflaeche
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * Zeichnung - Klasse für das Grafikprojekt,
 * das die auf der Zeichenfläche unterzubringenden
 * Grafikobjekte (Farbfiguren) verwaltet.
 * 
 * Singleton: genau eine Zeichnung [mit genau einer Zeichenflaeche]
 * Enthaelt die internen Klassen Zeichenflaeche und FarbFigur
 *
 * @author Claus Albowski, basiert auf dem Figuren-Projekt von
 * Bruce Quig, Michael Kölling, Axel Schmolitzky
 * 
 * @version (5) Jan 2015
 */
public class Zeichnung
{
    private ArrayList<FarbFigur> figuren;
    private static Zeichnung zeichnung;
    private static Zeichenflaeche zeichenflaeche;
    private static JFrame gui;
    private static int frameWidth = 400;
    private static int frameHeight = 400;

    /**
     * privater Konstruktor
     */
    private Zeichnung() {
        figuren = new ArrayList();
        zeichenflaeche = new Zeichenflaeche();
        zeichenflaeche.setPreferredSize(new Dimension(frameWidth, frameHeight));
        gui.setVisible(true);
    }

    /**
     * Fabrikmethode fuer das Zeichnungsobjekt  mit eigener Gui
     * @return zeichnung      das Zeichnungs-Singleton
     */
    public static Zeichnung gibZeichnung() {
        if (zeichnung==null) {
            if (gui==null) {
                gui = new JFrame("Grafikfenster");
                gui.setBounds(50, 50, frameWidth, frameHeight);
                gui.getContentPane().setLayout(null);
                gui.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent evt) {
                            gui.dispose();
                            System.exit(0);
                        }
                    });
            }
            zeichnung = new Zeichnung();
            gui.setContentPane(zeichenflaeche);
        }
        return zeichnung;
    }

    /**
     * Fabrikmethode fuer das Zeichnungsobjekt
     * mit fremdem JFrame-Objekt.
     * @param dieGui    das externe JFRame-Objekt
     * @return zeichnung      das Zeichnungs-Singleton
     */
    public static Zeichnung gibZeichnung(JFrame dieGui) {
        if (gui==null) gui = dieGui;
        if (zeichnung==null) zeichnung = new Zeichnung();
        return zeichnung;
    }

    /**
     * Fabrikmethode fuer das Zeichnungsobjekt mit uebergebener Fenstergroesse
     * @param breite     in Pixeln
     * @param hoehe     in Pixeln
     * @return zeichnung      das Zeichnungs-Singleton
     */
    public static Zeichnung gibZeichnung(int breite, int hoehe) {
        frameWidth = breite;
        frameHeight = hoehe;
        return gibZeichnung();
    }

    /**
     * Fabrikmethode fuer das Zeichnungsobjekt
     * mit uebergebenen fremdem JFrame-Objekt und Fenstergroesse
     * @param dieGui    das externe JFRame-Objekt
     * @param breite     in Pixeln
     * @param hoehe     in Pixeln
     * @return zeichnung      das Zeichnungs-Singleton
     */
    public static Zeichnung gibZeichnung(JFrame dieGui, int breite, int hoehe) {
        frameWidth = breite;
        frameHeight = hoehe;
        return gibZeichnung(dieGui);
    }

    /**
     * gibZeichenflaeche
     * ermöglicht den Zugriff auf die Zeichenflaeche [JPanel] durch die fremde Gui
     * @return zeichenflaeche    das Zeichenflaeche-Singleton
     */
    public Zeichenflaeche gibZeichenflaeche() {
        return zeichenflaeche;
    }

    /**
     * zeichne
     * nimmt ein neues Grafikobjekt auf und 
     * setzt es an eine Standardposition am Ende der Liste
     * @param form     implementiert Shape
     * @param farbname Farbe ["rot" usw.]
     * @param voll     true, wenn gefuellt
     * @return grafikobjekt    das Objekt [FarbFigur]
     */
    public Object zeichne(Shape form, String farbname, boolean voll){
        FarbFigur grafikobjekt =
            new FarbFigur(form, farbname, voll);
        figuren.add(grafikobjekt);
        update();
        return grafikobjekt;
    }

    /**
     * loesche
     * entfernt ein Grafikobjekt aus der Darstellung.
     * macht nichts, wenn es nicht enthalten war.
     * @param grafikobjekt     das zu entfernende Objekt
     */
    public void loesche(Object grafikobjekt){
        figuren.remove(grafikobjekt);
        update();
    }

    /**
     * gibFigur
     * gibt das Objekt von der Position in der Liste zurück
     * @param i   Position in der Liste; 0...figuren.size()-1
     * @return grafikobjekt    
     *         das Objekt an der Position in der Liste
     */
    public Object gibFigur(int i) {
        if (i >= 0)
            if (i < figuren.size())
                return figuren.get(i);
        return null;
    }

    /**
     * aendereFuellung
     * wechselt den Zustand der Fuellung vom Objekt an der Position in der Liste
     * @param i       zulaessige Position in der Liste; 0...figuren.size()-1
     */
    public void aendereFuellung(int i) {
        if (i >= 0)
            if (i < figuren.size())
                figuren.get(i).setzeFuellung(!figuren.get(i).getVoll());
        update();
    }

    /**
     * tauscheReihenfolge
     * tauscht zwei Grafikobjekte in der Reihenfolge aus und
     * ermoeglicht dadurch gezieltes Decken von gefuellten Figuren.
     * @param objekt1       das erste der zu tauschenden Grafikobjekte
     * @param objekt2       das zweite der zu tauschenden Grafikobjekte
     */
    public void tauscheReihenfolge(Object objekt1, Object objekt2) {
        if (figuren.contains(objekt1))
            if (figuren.contains(objekt2)) {
                int i = figuren.indexOf(objekt1);
                int j = figuren.indexOf(objekt2);
                figuren.set(j, (FarbFigur)objekt1);
                figuren.set(i, (FarbFigur)objekt2);
        }
        update();
    }

    /**
     * tauscheReihenfolge
     * tauscht zwei Grafikobjekte in der Reihenfolge aus und
     * ermoeglicht dadurch gezieltes Decken von gefuellten Figuren.
     * @param pos1             die erste der beiden Positionen
     * @param pos2             die zweite der beiden Positionen
     */
    public void tauscheReihenfolge(int pos1, int pos2) {
        if (pos1 >= 0)
            if (pos2 >= 0)
                if (pos1 < figuren.size())
                    if (pos2 < figuren.size()) {
                        FarbFigur temp = figuren.get(pos1);
                        figuren.set(pos1, figuren.get(pos2));
                        figuren.set(pos2, temp);
        }
        update();
    }

    /**
     * gibt die zu zeichnenden Figuren in der gegebenen
     * Reihenfolge zurück.
     * @return figuren   die Liste der zu zeichnenden Figuren
     */
    public ArrayList<FarbFigur> gibFiguren(){
        return figuren;
    }

    /**
     * update
     * loest erneutes Zeichnen aus
     */
    private void update(){
        zeichenflaeche.repaint();
    }

    /**
     * warte
     * ermoeglicht schrittweise Darstellungen
     * @param zeit      in Millisekunden
     */
    public void warte(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch(Exception e){};
    }

    /**
     * zeigt alle zulaessigen Farbnamen von FarbFigur in einem String an
     * @return alleNamen     in einem String
     */
    public String zeigeZulaessigeFarbnamen() {
        String alleNamen="rot, "+"gruen, "+"blau, "+"gelb, "+"weiss, "+"braun, "
            +"grau, "+"dunkelgrau, "+"hellgrau, "+"hellblau, "+"lila, "+"orange, "
            +"rosa, "+"alternativ wird "+"schwarz"+" verwendet";
        return alleNamen;
    }

    // ********** Zeichenflaeche ********************

    /** 
     * Interne Klasse zum Erzeugen einer Zeichenflaeche
     * mit der Moeglichkeit, Umriss oder Fuellung zu zeichnen
     * 
     * Die Zeichenflaeche greift auf das Singleton zeichnung
     * mit der Methode gibFiguren() zu.
     * Es wird davon ausgegangen, dass diese eine Liste mit
     * Objekten vom Typ FarbFigur liefert.
     * 
     * @author claus albowski
     * @version august 2014
     */

    public class Zeichenflaeche
    extends JPanel
    {
        private Graphics2D graphic;
        private Image image;

        /**
         * privater Konstruktor
         */
        private Zeichenflaeche() {                
            setBounds(0, 0, frameWidth, frameHeight);
            gui.getContentPane().add(this);
        }

        /**
         * spezielle paint - Methode für die Grafikobjekte
         * benoetigt die Methode gibFiguren() vom Zeichnungsobjekt.
         * @param g    das Graphics-Objekt
         */
        public void paint(Graphics g)
        {
            Dimension size = getSize();
            if (graphic == null) {
                image = createImage(size.width, size.height);
                graphic = (Graphics2D) image.getGraphics();
            }
            try {
                // Hintergrund zeichnen
                graphic.setColor(Color.white);
                graphic.fill(new Rectangle(0, 0, frameWidth, frameHeight));
                //*****ArrayList<FarbFigur> figuren = zeichnung.gibFiguren();
                // Figuren zeichnen
                for (FarbFigur figur: figuren) figur.draw(graphic);
                g.drawImage(image, 0, 0, null); }
            catch (ConcurrentModificationException e) {
                //System.out.println("ConcurrentModificationException abgefangen");
                warte(50);
                repaint();
            }
        }
    }    

    //********** FarbFigur ********************

    /**
     * Die Klasse FarbFigur kapselt die zum Zeichnen notwendigen
     * Attribute Figur (Shape), Farbe und Zustand Fuellung/Umriss
     * in einer internen Klasse.
     * 
     * Alternativ waere ebenfalls sinnvoll, eine externe Klasse
     * FarbigeFigur zu verwenden. Dann koennte Zeichnung auf deren
     * Objekte direkt zugreifen und braeuchte nur die zu verwalten.
     * 
     * @author claus albowski
     * @version Dezember 2014
     */
    public class FarbFigur
    {
        private Shape shape;
        private Color color;
        private boolean voll;

        /**
         * Konstruktor fuer Objekte der Klasse FarbFigur
         * @param shape     die grafische Form
         * @param farbname  Name der zu verwendenden Farbe
         * @param voll      Umschalter fuer gefuellt/Umriss
         */
        public FarbFigur(Shape shape, String farbname, boolean voll)
        {
            this.shape = shape;
            setzeFarbe(farbname);
            this.voll = voll;
        }

        /**
         * draw
         * die eigentliche Zeichenmethode greift auf die Attribute zu
         * und verwendet ggf. fill
         * @param graphic        das Graphics2D-Objekt
         */
        public void draw(Graphics2D graphic)  {
            graphic.setColor(color);
            if (voll) graphic.fill(shape);
            else graphic.draw(shape);
        }

        /**
         * get-Methoden
         */
        public Shape getShape(){ return shape; }

        public Color getColor(){ return color; }

        public boolean getVoll(){ return voll; }

        /**
         * setzeForm
         * @param shape       ein Objekt, das Shape implementiert
         */
        private void setzeForm(Shape shape) {
            this.shape = shape;
        }

        /**
         * setzeFarbe
         * Uebersetzt den Farbnamen in ein Color-Objekt
         * zulaessig: "rot","gruen","blau","gelb","weiss","braun", "grau", 
         *   "dunkelgrau","hellgrau","hellblau","lila","orange","rosa"
         *   alternativ wird "schwarz" verwendet.
         * @param String farbname
         */
        private void setzeFarbe(String farbname) {
            if (farbname=="rot") color = Color.red;
            else if (farbname=="gruen") color = Color.green;
            else if (farbname=="blau") color = Color.blue;
            else if (farbname=="gelb") color = Color.yellow;
            else if (farbname=="weiss") color = Color.white;
            else if (farbname=="braun") color = new Color(165,42,42);
            else if (farbname=="grau") color = Color.gray;
            else if (farbname=="dunkelgrau") color = Color.darkGray;
            else if (farbname=="hellgrau") color = Color.lightGray;
            else if (farbname=="hellblau") color = Color.cyan;
            else if (farbname=="lila") color = Color.magenta;
            else if (farbname=="orange") color = Color.orange;
            else if (farbname=="rosa") color = Color.pink;
            else color = Color.black;
        }

        /**
         * setzeFuellung
         * @param gefuellt setzt das Attribut
         */
        private void setzeFuellung(boolean gefuellt) {
            voll = gefuellt;
        }
    }    

}
