package MuehleModel;
/**
 * Schnittstelle für das Mühle-Modell für den Controller.
 *
 * Die Schnittstelle stellt Methoden bereit, um das Setzen, Entfernen und Bewegen von Steinen auf dem Spielfeld zu ermöglichen.
 * Sie enthält auch Funktionen zur Überprüfung des aktuellen Spielzustands und der Zugmöglichkeiten der Spieler.
 */
public interface IMuehleModel {
    /**
     * Diese Methode gibt zurück, ob das Setzen von Steinen möglich ist.
     *
     * Beispielaufruf: muehleModel.isSetStone()
     *
     * @return true, wenn das Setzen von Steinen möglich ist, andernfalls false.
     */
    boolean isSetStone();
    /**
     * Mit dieser Methode werden für das Spiel relevante Daten initialisiert, wie das Spielbrett,
     * welches durch ein 1D-Array repräsentiert wird.
     * Bei jedem neuen Spielstart muss diese Methode aufgerufen werden.
     * Beispiel für das Spielbrett, das initialisiert wird:
     *
     * _     _     _
     *    _   _   _
     *      _ _ _
     *  _ _ _   _ _ _
     *      _ _ _
     *    _   _   _
     *  _     _     _
     */
    void startNewGame();
    /**
     * Diese Methode setzt den Spielzustand auf den angegebenen Wert.
     * @param gameState Der neue Spielzustand.
     */
    void setGameState(GameState gameState);
    /**
     * Diese Methode gibt den aktuellen Spielzustand zurück.
     * @return Der aktuelle Spielzustand.
     */
    GameState getGameState();
    /**
     * Diese Methode gibt zurück, ob das Entfernen von Steinen möglich ist.
     *
     * @return true, wenn das Entfernen von Steinen möglich ist, andernfalls false.
     */
    boolean isRemoveAvailible();
    /**
     * Mit dieser Methode können Spielsteine auf das Spielbrett gesetzt werden,
     * welche durch ein 'X' oder 'O' repräsentiert werden.
     * 'X' ist dabei der erste Spieler, der immer anfängt, und
     * 'O' dementsprechend Spieler 2.
     * Ein Beispielaufruf könnte z.B. so aussehen: muehleModel.setStone(12);
     * Spielfeld Beispiel nach dem Setzen:
     *
     * _     _     _
     *    _   _   _
     *      _ _ _
     *  _ _ _   _ X _
     *      _ _ _
     *    _   _   _
     *  _     _     _
     *
     * @param field Gibt an, welches Feld man belegen möchte auf dem Spielfeld.
     *              Es dürfen nur Zahlen zwischen 1 und 24 verwendet werden.
     */
    void setStone(int field);
    /**
     * Diese Methode entfernt einen Stein vom Spielfeld, wenn der jeweilige Spieler eine Mühle hat.
     * Der entfernte Stein muss sich auf einem Spielfeld befinden, das nicht vom aktuellen Spieler besetzt ist,
     * nicht leer ist und nicht Teil einer Mühle ist. Es ist jedoch erlaubt, einen Stein aus der Mühle des Gegners
     * zu entfernen, wenn sonst keine anderen Steine zum Entfernen verfügbar sind.
     *
     * Beispielaufruf: muehleModel.removeStone(3)
     * Beispiel Spielbrett vorher:
     *
     * X     O     X
     *    _   O   _
     *      _ O _
     *  X _ _   _ _ _
     *      _ _ _
     *    _   _   _
     *  _     _     _
     *
     * Beispiel Spielbrett nachher:
     *
     * X     O     _
     *    _   O   _
     *      _ O _
     *  X _ _   _ _ _
     *      _ _ _
     *    _   _   _
     *  _     _     _
     *
     * @param field Das Spielfeld, von dem der Stein entfernt werden soll, als Ganzzahl zwischen 1 und 24 repräsentiert.
     */
    void removeStone(int field);
    /**
     * Nachdem alle 18 Steine von beiden Spielern gesetzt wurden und dementsprechend
     * setStone auf false ist, können jeweils die Steine im Spielfeld bewegt werden.
     * Zuerst gibt man das erste Feld ein, wo sich der aktuelle Stein befindet, den man bewegen will,
     * über currentField, und dann das zweite Feld, wohin man den Stein hinbewegen möchte, über den Parameter nextField.
     * Auch hier gilt, dass man bei beiden Parametern nur Zahlen zwischen 1 und 24 angeben darf.
     * Der Parameter nextField muss mit dem currentField benachbart sein (siehe millMatrix), mit der Ausnahme,
     * dass der jeweilige Spieler nur noch 3 Steine hat. Dann müssen die Felder nicht mehr benachbart sein,
     * und der Spieler kann mit seinem Stein überall hinspringen.
     * Zudem muss nextField EMPTY sein, und das Feld currentField muss mit dem aktuellen Spieler besetzt sein,
     * der gerade einen Zug machen will.
     * Beispielaufruf: muehleModel.moveStones(2, 3)
     * Beispiel Spielbrett davor:
     *
     * _     X     _
     *    X   O   O
     *      X X _
     *  X O O   X X _
     *      O _ O
     *    _   _   _
     *  _     _     _
     *
     * Spielbrett nach dem Aufruf:
     *
     * _     _     X
     *    X   O   O
     *      X X _
     *  X O O   X X _
     *      O _ O
     *    _   _   _
     *  _     _     _
     *
     * @param currentField Spielfeld mit dem aktuellen Spieler, den man woanders bewegen will,
     *                     als Ganzzahl zwischen 1 und 24 repräsentiert.
     * @param nextField Leeres Spielfeld, wohin man seinen Stein bewegen will,
     *                  als Ganzzahl zwischen 1 und 24 repräsentiert.
     */
    void moveStones(int currentField, int nextField);
    /**
     * Diese Methode überprüft, ob ein Spieler in der aktuellen Spielsituation einen seiner Steine bewegen kann.
     * Sie durchläuft die Verbindungsmatrix und das Spielfeld, um festzustellen, ob es ein Spielfeld gibt, auf dem sich
     * ein Stein des aktiven Spielers befindet und das Ziel-Spielfeld leer ist und durch die Matrix verbunden ist.
     *
     * Wird in der Methode moveStone aufgerufen.
     *
     * @return true, wenn der Spieler in der Lage ist, einen Stein zu bewegen, andernfalls false.
     */
    boolean stoneCanMove();
    /**
     * Diese Methode gibt die Anzahl der verbleibenden Steine für Spieler 1 zurück.
     * @return Die Anzahl der verbleibenden Steine für Spieler 1.
     */
    int getStonesPlayer_1();
    /**
     * Diese Methode gibt die Anzahl der verbleibenden Steine für Spieler 2 zurück.
     * @return Die Anzahl der verbleibenden Steine für Spieler 2.
     */
    int getStonesPlayer_2();
    /**
     * Diese Methode gibt das char Zeichen für Spieler 1 zurück.
     *
     * @return Das Zeichen für Spieler 1.
     */
    char getPLAYER_1();
    /**
     * Diese Methode gibt das char Zeichen für Spieler 2 zurück.
     *
     * @return Das Zeichen für Spieler 2.
     */
    char getPLAYER_2();
    /**
     * Diese Methode gibt den aktuellen Spieler zurück, basierend auf der Anzahl der bisherigen Züge.
     * Der Spieler wird durch das Zeichen 'X' für Spieler 1 und 'O' für Spieler 2 repräsentiert.
     *
     * @return Das Zeichen des aktuellen Spielers ('X' für Spieler 1, 'O' für Spieler 2).
     */
    char getActivePlayer();
    /**
     * Diese Methode gibt den inaktiven Spieler zurück, basierend auf der Anzahl der bisherigen Züge.
     * Der Spieler wird durch das Zeichen 'X' für Spieler 1 und 'O' für Spieler 2 repräsentiert.
     * @return Das Zeichen des inaktiven Spielers ('X' für Spieler 2, 'O' für Spieler 1).
     */
    char getInactivePlayer();
    /**
     * Diese Methode gibt das aktuelle Spielfeld als Zeichenarray zurück.
     *
     * @return Das aktuelle Spielfeld als Zeichenarray.
     */
    char[] getGameBoard();
}
