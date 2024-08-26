package MuehleModel;
/**
 * Das Enum GameState repräsentiert die verschiedenen Zustände, die das Spiel annehmen kann.
 * - {@code START}: Der Startzustand des Spiels, in dem das Spiel noch nicht gestartet wurde.
 * - {@code PLAYING}: Der Zustand während des Spiels, in dem die Spieler aktiv am Spiel teilnehmen.
 * - {@code GAME_OVER}: Der Zustand, in dem das Spiel beendet wurde, entweder durch Sieg eines Spielers oder durch ein Unentschieden.
 *
 */
public enum GameState {
    START,PLAYING,GAME_OVER;
}
