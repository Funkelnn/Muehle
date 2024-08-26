package MuehleController;
/**
 * Schnittstelle für den Mühle-Spielcontroller. Definiert Methoden zur Verarbeitung von Benutzereingaben
 * und zum Zeichnen des Spiels basierend auf dem aktuellen Zustand.
 *
 * Die Schnittstelle folgt dem MVC-Prinzip, wobei der Controller als Vermittler zwischen
 * dem Model und dem View fungiert.
 *
 * @author Angel Tise
 * @version 1.0
 */
public interface IControllerMuehle {

    /**
     * Verarbeitet die Benutzereingabe basierend auf der mauszeiger Position auf dem Spielfeld.
     * Die erhaltenen Werte werden an die Methode `userInputMethod` weitergegeben, wo sie weiterverarbeitet werden, um zu prüfen,
     * ob ein Spielfeld angeklickt wurde. Das ausgewählte Spielfeld wird dann gesetzt.
     *
     * @param mouseX        Die X-Koordinate des Mauszeigers.
     * @param mouseY        Die Y-Koordinate des Mauszeigers.
     * @param height        Die Höhe des Spielfelds.
     * @param width         Die Breite des Spielfelds.
     * @param circleRadius  Der Radius des Kreises um das Spielfeld, der als gültiger Bereich betrachtet wird.
     */

    void userInput(int mouseX, int mouseY, int height, int width, double circleRadius);
    /**
     * Verarbeitet die Benutzereingabe anhand der mauszeiger Position auf dem Spielfeld.
     * Wenn sich der Mauszeiger innerhalb des gültigen Bereichs eines Spielfelds befindet, wird das entsprechende Spielfeld ausgewählt
     * und der aktuelle Wert von currentField auf den Wert zwischen 1 und 24 gesetzt.
     *
     * @param mouseX         Die X-Koordinate des Mauszeigers.
     * @param mouseY         Die Y-Koordinate des Mauszeigers.
     * @param x              Die X-Position des Spielfeldes.
     * @param y              Die Y-Position des Spielfeldes.
     * @param currentFieldUI Die interne Repräsentation des ausgewählten Spielfelds.
     * @param circleRadius   Der Radius des Kreises um das Spielfeld, der als gültiger Bereich betrachtet wird.
     */
     void userInputMethod(int mouseX, int mouseY, int x, int y, int currentFieldUI, double circleRadius);
    /**
     * Gibt das aktuelle Spielfeld als Zeichenkette (char-Array) zurück.
     * Diese Methode wird vom View aufgerufen, um die Spieler auf dem Spielbrett zu zeichnen.
     *
     * @return Das aktuelle Spielfeld als char-Array.
     */
     char[]getGameBoard();
    /**
     * Gibt den aktiven Spieler zurück.
     * Diese Methode wird im View aufgerufen, um den aktuellen Spieler anzuzeigen, der am Zug ist.
     *
     * @return Der aktive Spieler als Zeichen (char 'X' oder 'O').
     */
    char getActivePlayer();
    /**
     * Zeichnet den nächsten Frame basierend auf dem aktuellen Spielzustand.
     * Je nach Spielzustand werden entsprechende Aktionen ausgeführt und die Grafik im Spiel aktualisiert.
     *
     * Im Spielzustand START wird der Ladebildschirm gezeichnet.
     * Im Spielzustand PLAYING werden das Spielfeld und die Spielsteine/Pokemons gezeichnet. Zusätzlich werden
     * Informationen zu den möglichen Aktionen für den aktuellen Spieler angezeigt, wie das Setzen von Steinen,
     * Entfernen von Steinen oder das Bewegen von Steinen auf dem Spielfeld.
     * Im Spielzustand GAME_OVER wird der Gewinner angezeigt, und es besteht die Möglichkeit, ein neues Spiel zu starten,
     * indem die Taste 'r' oder 'R' gedrückt wird.
     */
    void drawNextFrame();
    /**
     * Verarbeitet die Benutzereingabe abhängig vom aktuellen Spielzustand:
     * - Im Zustand START der Zustand auf PLAYING gesetzt und der Thread ruft im Hintergrund die Methode startNewGame() auf.
     * - Im Zustand PLAYING:
     *   - Falls das Setzen eines Steins möglich ist und kein Entfernen möglich ist, wird ein Stein auf das ausgewählte Feld gesetzt.
     *   - Falls das Entfernen von Steinen möglich ist, wird ein Stein vom ausgewählten Feld entfernt.
     *   - Ansonsten erfolgt ein Steinzug, indem Steine von einem Feld zum anderen bewegt werden.
     * - Im Zustand GAME_OVER:
     *   - Durch Drücken der Taste 'r' oder 'R' wird ein neues Spiel gestartet, und der Zustand wird auf PLAYING gesetzt.
     *
     * @param restart Der Tastencode für den Neustart des Spiels ('r' oder 'R'), falls sich das Spiel im Zustand GAME_OVER befindet.
     */
    void handleInput(char restart);


}
