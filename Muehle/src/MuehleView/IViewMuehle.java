package MuehleView;
/**
 * Schnittstelle für die Darstellung des Mühle-Spiels für den Controller. Definiert Methoden zur Anzeige von Spielereignissen
 * und zur grafischen Darstellung des Spiels.
 *
 */
public interface IViewMuehle {
    /**
     * Die Methode gibt ein Bild (playerOneWon) auf dem Bildschirm aus, um anzuzeigen, dass Spieler 1 gewonnen hat.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */
    void playerOneHasWon();
    /**
     * Die Methode gibt ein Bild (playerTwoWon) auf dem Bildschirm aus, um anzuzeigen, dass Spieler s gewonnen hat.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */
    void playerTwoHasWon();
    /**
     * Zeichnet das Spielbrett und den Hintergrund auf den Bildschirm.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */
    void drawStartScreen();
    /**
     * Zeichnet die Spielfiguren (X oder O) an vordefinierten Positionen auf dem Spielbrett.
     * Wird vom GameController (IControllerMuehle) aufgerufen, um die aktuelle Spielsituation darzustellen.
     */
    void drawPlayer();
    /**
     * Zeichnet das Spielbrett und den Hintergrund auf den Bildschirm.
     * Wird vom GameController (IControllerMuehle) aufgerufen.
     */

    void gameBoard();
    /**
     * Zeichnet eine Informationsbox auf dem Bildschirm, die den aktuellen Spieler, die durchgeführte Aktion und eine Beschreibung anzeigt.
     * Diese Methode wird vom Controller (IControllerMuehle) aufgerufen, um während des Spiels wichtige Informationen darzustellen.
     *
     * @param player      Der aktuelle Spieler ('X' oder 'O'), dessen Symbol in der Informationsbox angezeigt wird.
     * @param action      Die durchgeführte Aktion, die in der Informationsbox angezeigt wird.
     * @param description Eine detaillierte Beschreibung der Aktion, wie sie durchgeführt wird, die in der Informationsbox angezeigt wird.
     */
    void drawBoxPlayer(char player,String action,String description);

}
