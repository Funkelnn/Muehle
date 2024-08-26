package MuehleModel;


import java.util.Arrays;

/**
 * Die Muehle-Klasse enthält die Spiellogik des Spiels
 * und implementiert das Interface IMuehleModel.
 * Spiel: Mühle
 *
 */
public class MuehleModel implements IMuehleModel {
    private final char EMPTY = '_';

    private final char PLAYER_1 = 'X';
    private final char PLAYER_2 = 'O';
    private int moveCount = 0;
    private boolean setStone;
    private final char[] gameBoard = new char[25];
    private final char[] millBoard = new char[25];
    private final int[] currentMill = new int[16];
    private final int[] lastMillPlayer_1 = new int[16];
    private final int[] lastMillPlayer_2 = new int[16];
    private final int[][] adjacencMatrix = new int[25][25];

    private int stonesPlayer_1 = 0;
    private int stonesPlayer_2 = 0;

    private boolean removeAvailible;
    private GameState gameState;


    /**
     * Das ist der Konstruktor der Klasse, welcher den GameState auf START setzt.
     * In der Startphase kann das Spiel noch nicht gespielt werden; zuerst müssen einige
     * Daten in der Methode startNewGame() geladen werden.
     */
    public MuehleModel() {
        gameState = GameState.START;
    }

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
    public void startNewGame() {
        Arrays.fill(gameBoard, EMPTY);
        Arrays.fill(millBoard, EMPTY);
        adjacencyList();
        moveCount = 0;
        setStone = true;

    }

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
    public void setStone(int field) {

        if (!removeAvailible && isEmptyField(field) && setStone && gameBoard[field] == EMPTY && field != 0) {

            gameBoard[field] = getActivePlayer();

            if (setStone) stoneUpdate(getActivePlayer(), '+');
            if (moveCount >= 17) setStone = false;

            System.out.println(this );

            if (!hasMill()) {
                setLastMill(getActivePlayer());
                moveCount++;
            }
        }
    }

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
    public void moveStones(int currentField, int nextField) {

        if (!removeAvailible && getActivePlayer() == gameBoard[currentField] && gameBoard[nextField] == EMPTY) {

            if (stonesPlayer(getActivePlayer()) > 3 && adjacencMatrix[currentField][nextField] == 1) {
                gameBoard[currentField] = EMPTY;
                gameBoard[nextField] = getActivePlayer();

                if (!hasMill()) {
                    setLastMill(getActivePlayer());
                    moveCount++;
                }
                gameOver();

            } else if (stonesPlayer(getActivePlayer()) == 3) {
                jumpWithStones(currentField, nextField);

                if (!hasMill()) {
                    setLastMill(getActivePlayer());
                    moveCount++;
                }
                gameOver();

            }
            System.out.println(this);
        }


    }
    /**
     * Diese Methode ermöglicht einem Spieler, dessen Anzahl an verbleibenden Steinen auf 3 reduziert wurde,
     * mit einem Stein über das Spielfeld zu springen. Der Spieler gibt das aktuelle Spielfeld, auf dem sich
     * sein Stein befindet, über den Parameter currentField an, und das leere Zielfeld, auf das er seinen Stein
     * bewegen möchte, über den Parameter nextField. Beachte, dass der Spieler nur noch 3 Steine hat und daher
     * nicht mehr an die benachbarten Felder gebunden ist.
     *
     * Diese Methode wird in der Methode moveStone aufgerufen, wenn der entsprechende Spieler nur noch 3 Steine übrig hat
     * nachdem man alle seine 9 Steine gesetzt hat.
     * Beispiel Spielbrett davor:
     *
     * _     _     X
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
     *      X _ _
     *  X O O   X X _
     *      O X O
     *    _   _   _
     *  _     _     _
     *
     *
     * @param currentField Spielfeld mit dem aktuellen Spieler, der seinen Stein bewegen möchte,
     *                     als Ganzzahl zwischen 1 und 24 repräsentiert.
     * @param nextField Leeres Spielfeld, wohin der Spieler seinen Stein bewegen möchte,
     *                  als Ganzzahl zwischen 1 und 24 repräsentiert.
     */
    public void jumpWithStones(int currentField, int nextField) {
        if (stonesPlayer(getActivePlayer()) == 3) {
            if (isEmptyField(nextField) && gameBoard[currentField] == getActivePlayer()) {
                gameBoard[currentField] = EMPTY;
                gameBoard[nextField] = getActivePlayer();
            }
        }
    }

    /**
     * Diese Methode aktualisiert die Anzahl der verbleibenden Steine für den angegebenen Spieler.
     * Der Spieler und die gewünschte Operation (Hinzufügen oder Entfernen) werden durch die Parameter
     * player und operation festgelegt. Spieler 1 wird durch das Zeichen 'X' repräsentiert, und Spieler 2
     * durch das Zeichen 'O'.
     *
     * Beispielaufruf für das Hinzufügen eines Steins für Spieler 1: muehleModel.stoneUpdate('X', '+')
     * Beispielaufruf für das Entfernen eines Steins für Spieler 2: muehleModel.stoneUpdate('O', '-')
     *
     * @param player Zeichen, das den Spieler repräsentiert ('X' für Spieler 1, 'O' für Spieler 2).
     * @param operation Zeichen, das die gewünschte Operation angibt ('+' für Hinzufügen, '-' für Entfernen).
     */
    public void stoneUpdate(char player, char operation) {
        if (player == PLAYER_1 && operation == '+') stonesPlayer_1++;
        if (player == PLAYER_1 && operation == '-') stonesPlayer_1--;
        if (player == PLAYER_2 && operation == '+') stonesPlayer_2++;
        if (player == PLAYER_2 && operation == '-') stonesPlayer_2--;

    }


/**
 * Diese Methode initialisiert die Verbindungsmatrix für die Mühle-Spielbrettkonfiguration.
 * Die Matrix repräsentiert die erlaubten Verbindungen zwischen den Spielfeldern.
 * Die Spielfelder sind durchnummeriert von 1 bis 24. Jeder Eintrag in der Matrix mit dem Wert 1
 * zeigt an, dass es eine Verbindung zwischen den entsprechenden Spielfeldern gibt.
 **/
 public void adjacencyList() {
        int[][] connections = {
                {1, 2, 8}, {2, 1, 3, 10}, {3, 2, 4}, {4, 3, 5, 12},
                {5, 4, 6}, {6, 5, 7, 14}, {7, 6, 8}, {8, 1, 7, 16},
                {9, 10, 16}, {10, 2, 9, 11, 18}, {11, 10, 12}, {12, 4, 11, 13, 20},
                {13, 12, 14}, {14, 6, 13, 15, 22}, {15, 14, 16}, {16, 8, 9, 15, 24},
                {17, 18, 24}, {18, 10, 17, 19}, {19, 18, 20}, {20, 12, 19, 21},
                {21, 20, 22}, {22, 14, 21, 23}, {23, 22, 24}, {24, 16, 17, 23}

        };

        for (int[] connection : connections) {
            int from = connection[0];
            for (int i = 1; i < connection.length; i++) {
                int to = connection[i];
                adjacencMatrix[from][to] = 1;
            }
        }


    }
    /**
     * Überprüft, ob der aktive Spieler in der aktuellen Spielsituation die Möglichkeit hat, einen seiner Steine zu bewegen.
     * Diese Methode durchläuft die Verbindungsmatrix und das Spielfeld, um zu ermitteln, ob es ein Spielfeld gibt, auf dem sich
     * ein Stein des aktiven Spielers befindet und das Ziel-Spielfeld leer ist und durch die Matrix verbunden ist.
     *
     * Wird in der Methode "gameOver" aufgerufen, da ein Spieler, der keine Steine mehr hat, die er bewegen kann und vom
     * anderen Spieler umschlossen wurde, das Spiel verloren hat.
     *Gilt jedoch nicht, wenn der Spieler mit seinen Steinen springen kann.
     *
     *
     * @return true, wenn der Spieler in der Lage ist, einen Stein zu bewegen, andernfalls false.
     */

    public boolean stoneCanMove() {
        for (int i = 0; i < adjacencMatrix.length; i++) {
            for (int j = 0; j < adjacencMatrix[i].length; j++) {
                if (gameBoard[i] == getActivePlayer() && gameBoard[j] == EMPTY && adjacencMatrix[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Diese Methode gibt den aktuellen Spieler zurück, basierend auf der Anzahl der bisherigen Züge.
     * Der Spieler wird durch das Zeichen 'X' für Spieler 1 und 'O' für Spieler 2 repräsentiert.
     *
     * @return Das Zeichen des aktuellen Spielers ('X' für Spieler 1, 'O' für Spieler 2).
     */
    public char getActivePlayer() {

        return (moveCount % 2 == 0) ? PLAYER_1 : PLAYER_2;
    }

    /**
     * Diese Methode gibt den inaktiven Spieler zurück, basierend auf der Anzahl der bisherigen Züge.
     * Der Spieler wird durch das Zeichen 'X' für Spieler 1 und 'O' für Spieler 2 repräsentiert.
     * @return Das Zeichen des inaktiven Spielers ('X' für Spieler 2, 'O' für Spieler 1).
     */
    public char getInactivePlayer() {
        return (moveCount % 2 == 0) ? PLAYER_2 : PLAYER_1;
    }
    /**
     * Diese Methode überprüft, ob das angegebene Spielfeld leer ist.
     * @param field Das Spielfeld, das überprüft werden soll, als Ganzzahl zwischen 1 und 24 repräsentiert.
     * @return true, wenn das Spielfeld leer ist, andernfalls false.
     */
    public boolean isEmptyField(int field) {
        return gameBoard[field] == EMPTY;
    }

    /**
     * Diese Methode überprüft, ob der aktuelle Spieler eine Mühle hat.
     * Falls der aktive Spieler eine neue Mühle hat, wird geprüft, ob die Mühle identisch ist mit der vorherigen
     * Mühle des Spielers. Wenn dies nicht der Fall ist und die Mühle nicht leer ist, wird removeAvailable auf true gesetzt.
     *
     * @return true, wenn der aktuelle Spieler eine Mühle hat, andernfalls false.
     */
    public boolean hasMill() {
        millChecker(getActivePlayer());

        if (getActivePlayer() == PLAYER_1) {
            for (int i = 0; i < currentMill.length; i++) {
                if (currentMill[i] != lastMillPlayer_1[i] && currentMill[i] != 0) {
                    removeAvailible = true;
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentMill.length; i++) {
                if (currentMill[i] != lastMillPlayer_2[i] && currentMill[i] != 0) {
                    removeAvailible = true;
                    return true;
                }
            }
        }

        return false;
    }

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
    public void removeStone(int field) {


        if (removeAvailible && gameBoard[field] != getActivePlayer() && gameBoard[field] != EMPTY && millBoard[field] == EMPTY) {
            gameBoard[field] = EMPTY;
            removeAvailible = false;
            stoneUpdate(getInactivePlayer(), '-');
            setLastMill(getActivePlayer());
            moveCount++;


        } else if (removeFromMillChecker() && gameBoard[field] != getActivePlayer()) {

            gameBoard[field] = EMPTY;
            removeAvailible = false;
            stoneUpdate(getInactivePlayer(), '-');
            setLastMill(getActivePlayer());
            setLastMill(getInactivePlayer());
            moveCount++;


        }
        gameOver();
        System.out.println(this);

    }

    /**
     * Diese Methode überprüft, ob ein Spieler Steine aus einer gegnerischen Mühle entfernen kann.
     * Falls ein Spielerstein nicht Teil der Mühle ist, gibt die Methode false zurück, ansonsten true.
     *
     * @return true, wenn der Spieler Steine aus der gegnerischen Mühle entfernen kann, andernfalls false.
     * Diese Methode wird in der Methode removeStone aufgerufen, um zu überprüfen, ob das Entfernen aus einer Mühle möglich ist.
     */
    public boolean removeFromMillChecker() {
        for (int i = 1; i < gameBoard.length; i++) {
            if (gameBoard[i] == getInactivePlayer() && gameBoard[i] != millBoard[i] || millBoard[i] == getInactivePlayer() && gameBoard[i] != millBoard[i]) {

                return false;
            }
        }

        return true;

    }
    /**
     * Diese Methode gibt die Anzahl der verbleibenden Steine für den angegebenen Spieler zurück.
     * Der Spieler wird durch das Zeichen 'X' für Spieler 1 und 'O' für Spieler 2 repräsentiert.
     *
     * @param player Das Zeichen des Spielers, für den die Anzahl der Steine abgefragt wird ('X' für Spieler 1, 'O' für Spieler 2).
     * @return Die Anzahl der verbleibenden Steine des angegebenen Spielers.
     */
    public int stonesPlayer(char player) {
        return (player == PLAYER_1) ? stonesPlayer_1 : stonesPlayer_2;
    }
    /**
     * Diese Methode aktualisiert das Array der letzten Mühle für den angegebenen Spieler.
     * Sie leert zuerst das entsprechende Array (lastMillPlayer_1 oder lastMillPlayer_2),
     * ruft dann millChecker auf, um die aktuellen Mühleninformationen zu erhalten,
     * und kopiert schließlich die aktuellen Mühleninformationen in das leere Array.
     * Diese Methode wird gebraucht, um zu überprüfen, ob eine neue Mühle entstanden ist.
     *
     * @param player Das Zeichen des Spielers, für den die letzte Mühle aktualisiert wird ('X' für Spieler 1, 'O' für Spieler 2).
     */
    public void setLastMill(char player) {


        if (player == PLAYER_1) {
            Arrays.fill(lastMillPlayer_1, 0);
            millChecker(player);
            System.arraycopy(currentMill, 0, lastMillPlayer_1, 0, currentMill.length);
        } else if (player == PLAYER_2) {
            Arrays.fill(lastMillPlayer_2, 0);
            millChecker(player);
            System.arraycopy(currentMill, 0, lastMillPlayer_2, 0, currentMill.length);
        }

    }
    /**
     * Diese Methode überprüft, ob sich eine Mühle auf dem Spielfeld befindet.
     * Sie vergleicht die Spielfelder an den angegebenen Indizes und aktualisiert
     * die Arrays currentMill und millBoard entsprechend.
     *
     * Beispielaufruf: muehleModel.millCheckerMethod('X', 1, 1, 2, 3)
     *
     * @param player Das Zeichen des Spielers, für den die Mühle überprüft wird ('X' für Spieler 1, 'O' für Spieler 2).
     * @param millIndex Der Index der aktuellen Mühle im Array currentMill.
     * @param firstIndex Der Index des ersten Spielfelds der zu überprüfenden Mühle.
     * @param secondIndex Der Index des zweiten Spielfelds der zu überprüfenden Mühle.
     * @param thirdIndex Der Index des dritten Spielfelds der zu überprüfenden Mühle.
     */
    public void millCheckerMethod(char player, int millIndex, int firstIndex, int secondIndex, int thirdIndex) {

        char otherPlayer = (player == PLAYER_1) ? PLAYER_2 : PLAYER_1;

        if (gameBoard[firstIndex] == player && gameBoard[secondIndex] == player && gameBoard[thirdIndex] == player) {
            currentMill[millIndex - 1] = millIndex;

        }
        if (gameBoard[firstIndex] == otherPlayer && gameBoard[secondIndex] == otherPlayer && gameBoard[thirdIndex] == otherPlayer) {
            millBoard[firstIndex] = otherPlayer;
            millBoard[secondIndex] = otherPlayer;
            millBoard[thirdIndex] = otherPlayer;
        }


    }
    /**
     * Diese Methode aktualisiert die Mühleninformationen auf dem Spielfeld.
     * Sie füllt zuerst die Arrays millBoard und currentMill mit latzhaltern (EMPTY / '_' oder 0) und
     * ruft dann millCheckerMethod für jede der möglichen Mühlen auf.
     *
     * @param player Das Zeichen des Spielers, für den die Mühlen überprüft werden ('X' für Spieler 1, 'O' für Spieler 2).
     */
    public void millChecker(char player) {
        Arrays.fill(millBoard, '_');
        Arrays.fill(currentMill, 0);
        millCheckerMethod(player, 1, 1, 2, 3);
        millCheckerMethod(player, 2, 9, 10, 11);
        millCheckerMethod(player, 3, 17, 18, 19);
        millCheckerMethod(player, 4, 8, 16, 24);
        millCheckerMethod(player, 5, 20, 12, 4);
        millCheckerMethod(player, 6, 23, 22, 21);
        millCheckerMethod(player, 7, 15, 14, 13);
        millCheckerMethod(player, 8, 6, 5, 3);
        millCheckerMethod(player, 9, 1, 8, 7);
        millCheckerMethod(player, 10, 9, 16, 15);
        millCheckerMethod(player, 11, 17, 24, 23);
        millCheckerMethod(player, 12, 2, 10, 18);
        millCheckerMethod(player, 13, 22, 14, 6);
        millCheckerMethod(player, 14, 19, 20, 21);
        millCheckerMethod(player, 15, 11, 12, 13);
        millCheckerMethod(player, 16, 3, 4, 5);


    }
    /**
     * Diese Methode überprüft, ob das Spiel beendet ist.
     * Das Spiel endet, wenn einer der Spieler nur noch zwei Steine hat oder
     * kein Spieler mehr Steine bewegen kann, da er z.B. von vom Gegner umschlossen ist.
     *
     * @return true, wenn das Spiel beendet ist, andernfalls false.
     * Falls das Spiel beendet ist, wird auch die Gewinnermeldung auf der Konsole ausgegeben.
     */
    public boolean gameOver() {

        if (!setStone){
            if (stonesPlayer_1 == 2 || stonesPlayer_2 == 2 || !stoneCanMove() && stonesPlayer(getActivePlayer()) != 3) {
                if (getInactivePlayer() == PLAYER_1) {
                    System.out.println(PLAYER_1 + " has won!");
                } else {
                    System.out.println(PLAYER_2 + " has won!");
                }
                gameState = GameState.GAME_OVER;
                return true;

            }
        }
        return false;

    }

    /**
     * Diese Methode gibt zurück, ob das Setzen von Steinen möglich ist.
     *
     * Beispielaufruf: muehleModel.isSetStone()
     *
     * @return true, wenn das Setzen von Steinen möglich ist, andernfalls false.
     */
    public boolean isSetStone() {
        return setStone;
    }

    /**
     * Diese Methode gibt zurück, ob das Entfernen von Steinen möglich ist.
     *
     * @return true, wenn das Entfernen von Steinen möglich ist, andernfalls false.
     */
    public boolean isRemoveAvailible() {
        return removeAvailible;
    }

    /**
     * Diese Methode gibt das aktuelle Spielfeld als Zeichenarray zurück.
     *
     * @return Das aktuelle Spielfeld als Zeichenarray.
     */
    public char[] getGameBoard() {
        return gameBoard;
    }

    /**
     * Diese Methode gibt den aktuellen Spielzustand zurück.
     * @return Der aktuelle Spielzustand.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Diese Methode setzt den Spielzustand auf den angegebenen Wert.
     * @param gameState Der neue Spielzustand.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Diese Methode gibt die Anzahl der verbleibenden Steine für Spieler 1 zurück.
     * @return Die Anzahl der verbleibenden Steine für Spieler 1.
     */
    public int getStonesPlayer_1() {
        return stonesPlayer_1;
    }

    /**
     * Diese Methode gibt die Anzahl der verbleibenden Steine für Spieler 2 zurück.
     * @return Die Anzahl der verbleibenden Steine für Spieler 2.
     */
    public int getStonesPlayer_2() {
        return stonesPlayer_2;
    }

    /**
     * Diese Methode gibt das char Zeichen für Spieler 1 zurück.
     *
     * @return Das Zeichen für Spieler 1.
     */
    public char getPLAYER_1() {
        return PLAYER_1;
    }

    /**
     * Diese Methode gibt das char Zeichen für Spieler 2 zurück.
     *
     * @return Das Zeichen für Spieler 2.
     */
    public char getPLAYER_2() {
        return PLAYER_2;
    }
    /**
     * Diese Methode erstellt eine Zeichenkette, die das aktuelle Spielfeld repräsentiert.
     * Sie gibt außerdem die Anzahl der verbleibenden Steine für beide Spieler aus.
     *
     * @return Eine Zeichenkette, die das aktuelle Spielfeld repräsentiert.
     */
    // jshell --class-path .\out\production\Muehle
    //  import MuehleModel.MuehleModel;
    @Override
    public String toString() {

        System.out.println("Stones Player One: " + stonesPlayer_1 + " Stones Player Two: " + stonesPlayer_2);
        return String.format("%s %s %s \n %s %s %s \n %s %s %s \n %s %s %s  %s %s %s \n %s %s %s \n %s %s %s \n %s %s %s \n",
                " " + gameBoard[1] + "    ", gameBoard[2] + "    ", gameBoard[3],
                "  " + gameBoard[9] + "  ", gameBoard[10] + "  ", gameBoard[11],
                "    " + gameBoard[17], gameBoard[18], gameBoard[19] + "  ",
                gameBoard[8], gameBoard[16], gameBoard[24] + " ", gameBoard[20], gameBoard[12], gameBoard[4],
                "    " + gameBoard[23], gameBoard[22], gameBoard[21] + "  ",
                "  " + gameBoard[15] + "  ", gameBoard[14] + "  ", gameBoard[13],
                gameBoard[7] + "    ", gameBoard[6] + "    ", gameBoard[5]);
    }
}


