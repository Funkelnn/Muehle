package MuehleView;

import MuehleController.IControllerMuehle;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * Die Klasse ViewMuehle stellt die grafische Benutzeroberfläche für das Mühle-Spiel dar.
 * Sie verwendet die Processing-Bibliothek und implementiert das IViewMuehle-Interface.
 * Hier werden Grafiken geladen, Benutzereingaben verarbeitet und das Spielgeschehen auf dem Bildschirm dargestellt.
 * Die Klasse arbeitet interagiert  mit dem zugehörigen Controller (IControllerMuehle) zusammen.
 */
public class ViewMuehle extends PApplet implements IViewMuehle {
    /**
     * Die Methode main(String[] args) ist der Einstiegspunkt für die Ausführung der Anwendung.
     * Sie startet die Verarbeitung durch die Processing-Bibliothek.
     */
    public static void main(String[] args) {
        PApplet.main(ViewMuehle.class);
    }

    private PImage playerOne;
    private PImage playerTwo;
    private PImage startScreen;
    private PImage playerOneWon;
    private PImage playerTwoWon;
    private PImage backGround;
    private double circleRadius = 32.5;
    private IControllerMuehle iControllerMuehle;
    private int progressBarWidth = 200;
    private int progressBarHeight = 20;
    private int progress = 0;
    private int totalProgress = 100;

    /**
     * Setzt den Controller für die Kommunikation zwischen der MuehleView und dem MuehleController.
     *
     * @param iControllerMuehle Der Controller, der mit dieser View verbunden werden soll.
     *
     * Methode wird in der Main Klasse aufgerufen
     */
    public void setController(IControllerMuehle iControllerMuehle) {
        this.iControllerMuehle = iControllerMuehle;
    }
    /**
     * Legt die Größe des Fensters und die Pixeldichte für die Processing-Anwendung fest.
     * Diese Methode wird nur einmal am Anfang der Ausführung aufgerufen
     */
    @Override
    public void settings() {
        setSize(1150, 750);
        pixelDensity(2);
    }
    /**
     * Initialisiert die der Bilder/Grafik für Spieler, den Startbildschirm und den Hintergrund.
     * Wird nur einmalig zu Beginn des Programms aufgerufen.
     */
    @Override
    public void setup() {
        playerOne = loadImage("images/025.png");
        playerTwo = loadImage("images/052.png");
        startScreen = loadImage("images/StartScreen.jpg");
        playerOneWon = loadImage("images/PlayerOneWin.png");
        playerTwoWon = loadImage("images/PlayerTwoWin.png");
        backGround = loadImage("images/background.jpg");
    }

    @Override
    public void draw() {
        imageMode(CENTER);
        strokeWeight(5);
        iControllerMuehle.drawNextFrame();


    }
    /**
     * Zeichnet das nächste Frame des Spiels. Diese Methode wird in einer Endlosschleife wiederholt aufgerufen, um kontinuierlich die Grafik zu aktualisieren.
     * Hierbei wird der Spielbildschirm durch den Aufruf von {@link IControllerMuehle#drawNextFrame()} aktualisiert.
     */

    @Override
    public void mouseClicked() {

        iControllerMuehle.userInput(mouseX, mouseY, height, width, circleRadius);

    }

    /**
     * Zeichnet den Startbildschirm des Spiels. Enthält eine Hintergrundgrafik und einen Ladebalken, der den Fortschritt des Spielstarts in Prozent anzeigt.
     * Diese Methode wird aufgerufen, wenn das Spiel gestartet wird.
     * Für den Ladebalken wird die Methode {@link #drawProgressBar(float, float, float, float, int, int)} aufgerufen.
     */

    public void drawStartScreen() {
        image(startScreen, width / 2, height / 2, width, height);
        fill(255);

        textSize(30);
        //textMode(CENTER);
        text("Artificial intelligence is loading. Please wait...", width / 2, height / 6);


        drawProgressBar(width / 2 - progressBarWidth / 2, height / 2, progressBarWidth, progressBarHeight, progress, totalProgress);


        if (progress < totalProgress) {
            progress += 1;
        }
    }
    /**
     * Zeichnet einen Ladebalken mit bestimmten Abmessungen und Fortschritt.
     * Wird von der Methode {@link #drawStartScreen()} aufgerufen, um den Ladebalken während des Spielstarts anzuzeigen.
     *
     * @param x        Die X-Position, an der der Ladebalken gezeichnet wird.
     * @param y        Die Y-Position, an der der Ladebalken gezeichnet wird.
     * @param width    Die Breite des Ladebalkens.
     * @param height   Die Höhe des Ladebalkens.
     * @param current  Der aktuelle Fortschritt des Ladebalkens.
     * @param total    Der Gesamtforschritt, den der Ladebalken erreichen soll.
     */
    void drawProgressBar(float x, float y, float width, float height, int current, int total) {
        float percentage = (float) current / total;
        float barWidth = percentage * width;


        fill(150);
        rect(x, y, width, height);


        fill(128, 186, 36);
        rect(x, y, barWidth, height);


        fill(255);
        textSize(16);
        textAlign(CENTER,CENTER);
        text("Loading: " + (int)(percentage * 100) + "%", x + width / 2, y + height / 2);
    }

    /**
     * Die Methode gibt ein Bild (playerOneWon) auf dem Bildschirm aus, um anzuzeigen, dass Spieler 1 gewonnen hat.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */
    public void playerOneHasWon() {
        image(playerOneWon, width / 2, height / 2, width, height);
    }

    /**
     * Die Methode gibt ein Bild (playerTwoWon) auf dem Bildschirm aus, um anzuzeigen, dass Spieler s gewonnen hat.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */

    public void playerTwoHasWon() {
        image(playerTwoWon, width / 2, height / 2, width, height);
    }
    /**
     * Zeichnet die Spielfiguren (X oder O) an den spezifizierten Positionen auf dem Spielbrett.
     * Wird vom GameController (IControllerMuehle) aufgerufen, um die aktuelle Spielsituation zu visualisieren.
     * Bekommt die Daten vom {@link #drawPlayer()} und verarbeitet sie hier.
     *
     * @param arrayIndex Der Index im Spielfeld-Array, der den Zustand eines bestimmten Feldes repräsentiert.
     * @param x          Die X-Position, an der die Spielfigur gezeichnet werden soll.
     * @param y          Die Y-Position, an der die Spielfigur gezeichnet werden soll.
     */
    public void drawPlayerMethod(int arrayIndex, int x, int y) {
        if (iControllerMuehle.getGameBoard()[arrayIndex] == 'X') {
            image(playerOne, x, y, 75, 75);
        } else if (iControllerMuehle.getGameBoard()[arrayIndex] == 'O') {
            image(playerTwo, x, y, 75, 75);
        }
    }

    /**
     * Wird aufgerufen, wenn eine Taste auf der Tastatur gedrückt wird.
     * Überprüft, ob die gedrückte Taste 'r' oder 'R' ist, und ruft dann die Methode {@link IControllerMuehle#handleInput(char)}
     * des Controllers (IControllerMuehle) auf, um ein neues Spiel zu starten.
     *
     * @see IControllerMuehle#handleInput(char)
     */
    @Override
    public void keyPressed() {

        if (key == 'r' || key == 'R') {

            iControllerMuehle.handleInput('r');
        }
    }

    /**
     * Zeichnet eine Informationsbox auf dem Bildschirm, die den aktuellen Spieler, die durchgeführte Aktion und eine Beschreibung anzeigt.
     * Diese Methode wird vom Controller (IControllerMuehle) aufgerufen, um während des Spiels wichtige Informationen darzustellen.
     *
     * @param player      Der aktuelle Spieler ('X' oder 'O'), dessen Symbol in der Informationsbox angezeigt wird.
     * @param action      Die durchgeführte Aktion, die in der Informationsbox angezeigt wird.
     * @param description Eine detaillierte Beschreibung der Aktion, wie sie durchgeführt wird, die in der Informationsbox angezeigt wird.
     */
    public void drawBoxPlayer(char player, String action, String description){
        fill(0);
        textSize(20);

        text("Action",110,300);
        line(75,315,140,315);

        text("Current Player",110,150);
        text(action,30,190,150,320);

        text("How ?!",100,415 );
        line(65,430,130,430);
        text(description,30,275,150,500);
        if (player == 'X'){
            image(playerOne,110,200,75,75);
        }else {
            image(playerTwo,110,200,75,75);
        }

    }


    /**
     * Zeichnet das Spielbrett und den Hintergrund auf den Bildschirm.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */
    public void gameBoard() {
        image(backGround,width / 2, height / 2, width, height);
        noTint();

        fill(255,60);
        rect(20,125,175, 500);

        // Setze die allgemeine Transparenz für die Formen
        fill(255, 30);
        square((float) height / 3, (float) height / 10, height - (((float) height / 10) * 2));
        square((float) height / 30 * 14, (float) height / 30 * 7, (float) height / 30 * 16);
        square((float) height / 30 * 18, (float) height / 30 * 11, (float) height / 30 * 8);

        line(height / 30 * 22, height / 10, height / 30 * 22, height / 30 * 11);
        line(height / 30 * 22, height / 30 * 19, height / 30 * 22, height / 10 * 9);

        line(height / 3, height / 10 * 5, height / 30 * 18, height / 10 * 5);
        line(height / 30 * 26, height / 10 * 5, height / 30 * 34, height / 10 * 5);

        fill(128, 186, 36);

        circle(height / 3, height / 10, height / 10);
        circle(height / 30 * 22, height / 10, height / 10);
        circle(height / 30 * 34, height / 10, height / 10);
        circle(height / 30 * 34, (height / 10) * 5, height / 10);
        circle(height / 30 * 34, height / 10 * 9, height / 10);
        circle(height / 30 * 22, height / 10 * 9, height / 10);
        circle(height / 3, height / 10 * 9, height / 10);
        circle(height / 3, height / 10 * 5, height / 10);

        circle(height / 30 * 14, height / 30 * 7, height / 10);
        circle(height / 30 * 22, height / 30 * 7, height / 10);
        circle(height, height / 30 * 7, height / 10);
        circle(height, height / 10 * 5, height / 10);
        circle(height, height / 30 * 23, height / 10);
        circle(height / 30 * 22, height / 30 * 23, height / 10);
        circle(height / 30 * 14, height / 30 * 23, height / 10);
        circle(height / 30 * 14, height / 10 * 5, height / 10);

        circle(height / 30 * 18, height / 30 * 11, height / 10);
        circle(height / 30 * 22, height / 30 * 11, height / 10);
        circle(height / 30 * 26, height / 30 * 11, height / 10);
        circle(height / 30 * 26, height / 10 * 5, height / 10);
        circle(height / 30 * 26, height / 30 * 19, height / 10);
        circle(height / 30 * 22, height / 30 * 19, height / 10);
        circle(height / 30 * 18, height / 30 * 19, height / 10);
        circle(height / 30 * 18, height / 10 * 5, height / 10);


    }
    /**
     * Zeichnet die Spielfiguren (X oder O) an vordefinierten Positionen auf dem Spielbrett.
     * Wird vom GameController (IControllerMuehle) aufgerufen, um die aktuelle Spielsituation darzustellen.
     * Die Methode {@link #drawPlayerMethod} überprüft die Parameter und zeichnet den entsprechenden Spieler.
     *
     * @see #drawPlayerMethod
     */
    public void drawPlayer() {


        drawPlayerMethod(1, height / 3, height / 10);
        drawPlayerMethod(2, height / 30 * 22, height / 10);
        drawPlayerMethod(3, height / 30 * 34, height / 10);
        drawPlayerMethod(4, height / 30 * 34, height / 10 * 5);
        drawPlayerMethod(5, height / 30 * 34, height / 10 * 9);
        drawPlayerMethod(6, height / 30 * 22, height / 10 * 9);
        drawPlayerMethod(7, height / 3, height / 10 * 9);
        drawPlayerMethod(8, height / 3, height / 10 * 5);

        drawPlayerMethod(9, height / 30 * 14, height / 30 * 7);
        drawPlayerMethod(10, height / 30 * 22, height / 30 * 7);
        drawPlayerMethod(11, height, height / 30 * 7);
        drawPlayerMethod(12, height, height / 10 * 5);
        drawPlayerMethod(13, height, height / 30 * 23);
        drawPlayerMethod(14, height / 30 * 22, height / 30 * 23);
        drawPlayerMethod(15, height / 30 * 14, height / 30 * 23);
        drawPlayerMethod(16, height / 30 * 14, height / 10 * 5);

        drawPlayerMethod(17, height / 30 * 18, height / 30 * 11);
        drawPlayerMethod(18, height / 30 * 22, height / 30 * 11);
        drawPlayerMethod(19, height / 30 * 26, height / 30 * 11);
        drawPlayerMethod(20, height / 30 * 26, height / 10 * 5);
        drawPlayerMethod(21, height / 30 * 26, height / 30 * 19);
        drawPlayerMethod(22, height / 30 * 22, height / 30 * 19);
        drawPlayerMethod(23, height / 30 * 18, height / 30 * 19);
        drawPlayerMethod(24, height / 30 * 18, height / 10 * 5);


    }

}