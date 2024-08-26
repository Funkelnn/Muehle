import MuehleController.ControllerMuehle;
import MuehleView.ViewMuehle;
import processing.core.PApplet;

import MuehleModel.MuehleModel;



/**
 * Die Hauptklasse zum Starten des Muehle-Spiels.
 * Sie initialisiert die Model-, Controller- und View-Komponenten,
 *  setzt ihre Verbindungen und startet den Processing-Sketch.
 * <p>
 * Diese Klasse folgt dem Entwurfsmuster Model-View-Controller (MVC),
 * wobei {@link MuehleModel} die Daten und Regeln des Spiels repräsentiert,
 * {@link ControllerMuehle} als Schnittstelle zwischen Model und View vermittelt,
 * und {@link ViewMuehle} die grafische Benutzeroberfläche darstellt.
 * </p>
 *
 * @author Angel Tise
 * @version 1.0
 * @see MuehleModel
 * @see ControllerMuehle
 * @see ViewMuehle

 */
public class Main {
    /**
     * Die Hauptmethode zum Starten des Spiels.
     *
     * @param args Standard Parameter in der Main.
     */
    public static void main(String[] args) {
        MuehleModel muehleModel = new MuehleModel();


        ControllerMuehle controllerMuehle = new ControllerMuehle();
        ViewMuehle viewMuehle = new ViewMuehle();

        viewMuehle.setController(controllerMuehle);
        controllerMuehle.setMuehleModel(muehleModel);
        controllerMuehle.setViewMuehle(viewMuehle);

        PApplet.runSketch(new String[]{"Muehle"}, viewMuehle);





    }


}

