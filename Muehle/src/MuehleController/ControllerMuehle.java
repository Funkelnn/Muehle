package MuehleController;

import MuehleModel.IMuehleModel;
import MuehleView.IViewMuehle;
import MuehleModel.GameState;

/**
 * Der ControllerMuehle ist für die Verarbeitung von Benutzereingaben und die Aktualisierung
 * des Spielzustands verantwortlich. Er kommuniziert sowohl mit dem MuehleModel als auch mit
 * der ViewMuehle.
 */


public class ControllerMuehle implements IControllerMuehle {
    private IViewMuehle iViewMuehle;

    private IMuehleModel iMuehleModel;
    private int currentField;
    private int lastField;

    /**
     * Diese Methode setzt das MuehleModel-Objekt, um die Spiellogik zu steuern.
     * Sie wird in der Main-Methode aufgerufen, um den Controller mit dem MuehleModel zu verbinden.
     *
     * Beispielaufruf: gameController.setMuehleModel(muehleModel);
     *
     * @param iMuehleModel Das MuehleModel-Objekt, das die Spiellogik enthält.
     * @see MuehleModel.MuehleModel
     */
    public void setMuehleModel(IMuehleModel iMuehleModel) {
        this.iMuehleModel = iMuehleModel;
    }
    /**
     * Diese Methode setzt das ViewMuehle-Objekt, um die Benutzeroberfläche zu steuern.
     * Sie wird in der Main-Methode aufgerufen, um den Controller mit der ViewMuehle zu verbinden.
     *
     * @param iViewMuehle Das ViewMuehle-Objekt, das die Benutzeroberfläche repräsentiert.
     * @see MuehleView.ViewMuehle
     */
    public void setViewMuehle(IViewMuehle iViewMuehle) {
        this.iViewMuehle = iViewMuehle;
    }



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
    public void userInputMethod(int mouseX, int mouseY, int x, int y, int currentFieldUI, double circleRadius) {

        if (mouseX >= x - circleRadius && mouseX <= x + circleRadius) {
            if (mouseY >= y - circleRadius && mouseY <= y + circleRadius) {
                currentField = currentFieldUI;


            }
        }

    }
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
    public void handleInput(char restart) {

        switch (iMuehleModel.getGameState()) {
            case START -> {
                new Thread(() -> {
                    try {
                        iMuehleModel.startNewGame();
                        Thread.sleep(6500);
                        iMuehleModel.setGameState(GameState.PLAYING);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();


            }
            case PLAYING -> {

                if (iMuehleModel.isSetStone() && !iMuehleModel.isRemoveAvailible() && currentField != 0) {
                    iMuehleModel.setStone(currentField);
                } else if (iMuehleModel.isRemoveAvailible()) {
                    iMuehleModel.removeStone(currentField);
                } else if (!iMuehleModel.isSetStone()) {

                    iMuehleModel.moveStones(lastField, currentField);

                }

            }
            case GAME_OVER -> {
                if (restart == 'r') {
                    iMuehleModel.startNewGame();
                    iMuehleModel.setGameState(GameState.PLAYING);
                    currentField = 0;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + iMuehleModel.getGameState());
        }
    }
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

    public void drawNextFrame() {

        switch (iMuehleModel.getGameState()) {
            case START -> {
                iViewMuehle.drawStartScreen();
                handleInput('_');
            }
            case PLAYING -> {
                iViewMuehle.gameBoard();
                iViewMuehle.drawPlayer();
                if (iMuehleModel.isRemoveAvailible())
                    iViewMuehle.drawBoxPlayer(getActivePlayer(), "Remove Stone from Enemy", "Click on stone from enemy. Stone cannot be in a mill unless there are no other stones left.");

                if (iMuehleModel.isSetStone() && !iMuehleModel.isRemoveAvailible())
                    iViewMuehle.drawBoxPlayer(getActivePlayer(), "Set Stone on a free Field","Click on a free field. Each player has a total of 9 stones that he can place.");

                if (iMuehleModel.stoneCanMove() && !iMuehleModel.isRemoveAvailible() && !iMuehleModel.isSetStone()) {
                    if (iMuehleModel.getStonesPlayer_1() > 3 && getActivePlayer() == iMuehleModel.getPLAYER_1()) {
                        iViewMuehle.drawBoxPlayer(getActivePlayer(), "Move Stone on a free Field", "First click on the stone you want to move and then click on an adjacent empty field where you want to move the stone.");
                    } else if (iMuehleModel.getStonesPlayer_2() > 3 && getActivePlayer() == iMuehleModel.getPLAYER_2()) {
                        iViewMuehle.drawBoxPlayer(getActivePlayer(), "Move Stone on a free Field", "First click on the stone you want to move and then click on an adjacent empty field where you want to move the stone");
                    }
                }

                if (iMuehleModel.getStonesPlayer_1() == 3 && getActivePlayer() == iMuehleModel.getPLAYER_1() && iMuehleModel.stoneCanMove() && !iMuehleModel.isRemoveAvailible() && !iMuehleModel.isSetStone()){
                    iViewMuehle.drawBoxPlayer(getActivePlayer(), "Jump with the Stone on a free Field","First click on the stone you want to move and then click on an empty field where you want to move the stone");
                } else if (iMuehleModel.getStonesPlayer_2() == 3 && getActivePlayer() == iMuehleModel.getPLAYER_2() && iMuehleModel.stoneCanMove() && !iMuehleModel.isRemoveAvailible() && !iMuehleModel.isSetStone()) {
                    iViewMuehle.drawBoxPlayer(getActivePlayer(), "Jump with Stone on a free Field","First click on the stone you want to move and then click on an empty field where you want to move the stone");
                }

            }
            case GAME_OVER -> {
                if (iMuehleModel.getInactivePlayer() == iMuehleModel.getPLAYER_1()) {
                    iViewMuehle.playerOneHasWon();
                } else {
                    iViewMuehle.playerTwoHasWon();
                }

            }
        }
    }

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

    @Override
    public void userInput(int mouseX, int mouseY, int height, int width, double circleRadius) {
        userInputMethod(mouseX, mouseY, height / 3, height / 10, 1, circleRadius);
        userInputMethod(mouseX, mouseY, height / 3, height / 10 * 5, 8, circleRadius);
        userInputMethod(mouseX, mouseY, height / 3, height / 10 * 9, 7, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 22, height / 10, 2, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 22, height / 10 * 9, 6, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 22, height / 30 * 7, 10, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 22, height / 30 * 23, 14, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 22, height / 30 * 11, 18, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 22, height / 30 * 19, 22, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 34, height / 10, 3, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 34, height / 10 * 5, 4, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 34, height / 10 * 9, 5, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 14, height / 30 * 7, 9, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 14, height / 30 * 23, 15, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 14, height / 10 * 5, 16, circleRadius);
        ;
        userInputMethod(mouseX, mouseY, height, height / 30 * 7, 11, circleRadius);
        userInputMethod(mouseX, mouseY, height, height / 10 * 5, 12, circleRadius);
        userInputMethod(mouseX, mouseY, height, height / 30 * 23, 13, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 18, height / 30 * 11, 17, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 18, height / 30 * 19, 23, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 18, height / 10 * 5, 24, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 26, height / 30 * 11, 19, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 26, height / 10 * 5, 20, circleRadius);
        userInputMethod(mouseX, mouseY, height / 30 * 26, height / 30 * 19, 21, circleRadius);


        handleInput('_');
        lastField = currentField;

    }

    /**
     * Gibt das aktuelle Spielfeld als Zeichenkette (char-Array) zurück.
     * Diese Methode wird vom View aufgerufen, um die Spieler auf dem Spielbrett zu zeichnen.
     *
     * @return Das aktuelle Spielfeld als char-Array.
     */
    public char[] getGameBoard() {
        return iMuehleModel.getGameBoard();
    }
    /**
     * Gibt den aktiven Spieler zurück.
     * Diese Methode wird im View aufgerufen, um den aktuellen Spieler anzuzeigen, der am Zug ist.
     *
     * @return Der aktive Spieler als Zeichen (char 'X' oder 'O').
     */
    public char getActivePlayer() {
        return iMuehleModel.getActivePlayer();
    }
}

