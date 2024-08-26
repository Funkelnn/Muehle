import MuehleModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MuehleModelTest {
    MuehleModel muehleModel;
    @BeforeEach
    void setUp() {
        muehleModel = new MuehleModel();
        muehleModel.startNewGame();
    }

    @Test
    void startNewGame() {
        assertTrue(muehleModel.isSetStone());
        for (int i = 0; i < muehleModel.getGameBoard().length ; i++) {
            assertEquals(muehleModel.getGameBoard()[i] , '_');
        }
    }

    @Test
    void setStonePlayerOne() {

        muehleModel.setStone(1);
        assertTrue(muehleModel.getGameBoard()[1] == 'X');

    }

    @Test
    void playerOneHasMill() {

        muehleModel.setStone(1);
        muehleModel.setStone(9);
        muehleModel.setStone(2);
        muehleModel.setStone(10);
        muehleModel.setStone(3);

        assertTrue(muehleModel.hasMill());
    }
    @Test
    void playerTwoHasMill() {

        muehleModel.setStone(1);
        muehleModel.setStone(9);
        muehleModel.setStone(12);
        muehleModel.setStone(10);
        muehleModel.setStone(3);
        muehleModel.setStone(11);

        assertTrue(muehleModel.hasMill());
    }
    @Test
    void hasNoMill() {

        muehleModel.setStone(1);
        muehleModel.setStone(9);
        muehleModel.setStone(12);
        muehleModel.setStone(19);
        muehleModel.setStone(3);
        muehleModel.setStone(11);

        assertFalse(muehleModel.hasMill());
    }

    @Test
    void moveStone() {

        muehleModel.setStone(1);
        muehleModel.setStone(2);
        muehleModel.setStone(3);
        muehleModel.setStone(4);
        muehleModel.setStone(5);
        muehleModel.setStone(6);
        muehleModel.setStone(7);
        muehleModel.setStone(8);
        muehleModel.setStone(9);
        muehleModel.setStone(10);
        muehleModel.setStone(11);
        muehleModel.setStone(12);
        muehleModel.setStone(13);
        muehleModel.setStone(14);
        muehleModel.setStone(15);
        muehleModel.setStone(16);
        muehleModel.setStone(18);
        muehleModel.setStone(17);

        muehleModel.moveStones(18,19);

        assertEquals(muehleModel.getPLAYER_1(),muehleModel.getGameBoard()[19]);
    }

    @Test
    void stonCanNotMove() {
        muehleModel.setStone(1);
        muehleModel.setStone(2);
        muehleModel.setStone(3);
        muehleModel.setStone(4);
        muehleModel.setStone(5);
        muehleModel.setStone(6);
        muehleModel.setStone(7);
        muehleModel.setStone(8);
        muehleModel.setStone(9);
        muehleModel.setStone(10);
        muehleModel.setStone(11);
        muehleModel.setStone(12);
        muehleModel.setStone(13);
        muehleModel.setStone(14);
        muehleModel.setStone(15);
        muehleModel.setStone(16);
        muehleModel.setStone(17);
        muehleModel.setStone(18);

        muehleModel.removeStone(17);

        assertFalse(muehleModel.stoneCanMove());
    }
        @Test
        void stoneJump(){
            muehleModel.setStone(1);
            muehleModel.setStone(2);
            muehleModel.setStone(3);
            muehleModel.setStone(10);
            muehleModel.setStone(8);
            muehleModel.setStone(18);
            muehleModel.removeStone(3); //1
            muehleModel.setStone(7);
            muehleModel.removeStone(2);
            muehleModel.setStone(2);
            muehleModel.removeStone(1); //2
            muehleModel.setStone(1);
            muehleModel.removeStone(2);
            muehleModel.setStone(2);
            muehleModel.removeStone(1); //3
            muehleModel.setStone(1);
            muehleModel.removeStone(2);
            muehleModel.setStone(2);
            muehleModel.removeStone(1); //4
            muehleModel.setStone(1);
            muehleModel.removeStone(2);
            muehleModel.setStone(2);
            muehleModel.removeStone(1); //5
            muehleModel.setStone(1);
            muehleModel.removeStone(2);
            muehleModel.setStone(2);
            muehleModel.removeStone(1);
            muehleModel.setStone(19);

            muehleModel.moveStones(2,5);



            assertEquals(muehleModel.getPLAYER_2(), muehleModel.getGameBoard()[5]);
        }
    @Test
    void GameOverStonesEqualsTwo(){
        muehleModel.setStone(1);
        muehleModel.setStone(2);
        muehleModel.setStone(3);
        muehleModel.setStone(10);
        muehleModel.setStone(8);
        muehleModel.setStone(18);
        muehleModel.removeStone(3); //1
        muehleModel.setStone(7);
        muehleModel.removeStone(2);
        muehleModel.setStone(2);
        muehleModel.removeStone(1); //2
        muehleModel.setStone(1);
        muehleModel.removeStone(2);
        muehleModel.setStone(2);
        muehleModel.removeStone(1); //3
        muehleModel.setStone(1);
        muehleModel.removeStone(2);
        muehleModel.setStone(2);
        muehleModel.removeStone(1); //4
        muehleModel.setStone(1);
        muehleModel.removeStone(2);
        muehleModel.setStone(2);
        muehleModel.removeStone(1); //5
        muehleModel.setStone(1);
        muehleModel.removeStone(2);
        muehleModel.setStone(2);
        muehleModel.removeStone(1); //6
        muehleModel.setStone(1);
        muehleModel.removeStone(2);
        muehleModel.setStone(20);
        muehleModel.moveStones(1,9);
        muehleModel.moveStones(20,5);
        muehleModel.moveStones(9,1);
        muehleModel.removeStone(5);

        assertTrue(muehleModel.gameOver());
    }

    @Test
    void canNotRemoveStoneFromMill(){
        muehleModel.setStone(1);
        muehleModel.setStone(2);
        muehleModel.setStone(3);
        muehleModel.setStone(10);
        muehleModel.setStone(8);
        muehleModel.setStone(18);
        muehleModel.removeStone(3);
        muehleModel.setStone(3);
        muehleModel.setStone(20);
        muehleModel.setStone(7);
        muehleModel.removeStone(2);

        assertFalse(muehleModel.removeFromMillChecker());
    }

    @Test
    void isRemoveAvailible(){
        muehleModel.setStone(1);
        muehleModel.setStone(2);
        muehleModel.setStone(3);
        muehleModel.setStone(10);
        muehleModel.setStone(8);
        muehleModel.setStone(18);

        assertTrue(muehleModel.isRemoveAvailible());
    }
    @Test

    void gameStateGetter(){
        muehleModel = new MuehleModel();

        assertEquals(GameState.START,muehleModel.getGameState());
    }

    @Test
    void gameStateSetter(){
        muehleModel = new MuehleModel();
        muehleModel.setGameState(GameState.PLAYING);

        assertEquals(GameState.PLAYING,muehleModel.getGameState());
    }

    @Test
    void getPlayerOneStones(){
        muehleModel.setStone(6);
        muehleModel.setStone(12);
        muehleModel.setStone(17);
        muehleModel.setStone(9);
        muehleModel.setStone(11);
        muehleModel.setStone(15);
        muehleModel.setStone(22);
        muehleModel.setStone(13);
        muehleModel.setStone(2);
        muehleModel.setStone(19);

        assertEquals(5,muehleModel.getStonesPlayer_1());
    }
    @Test
    void getPlayerTwoStones(){
        muehleModel.setStone(1);
        muehleModel.setStone(9);
        muehleModel.setStone(3);
        muehleModel.setStone(12);
        muehleModel.setStone(2);
        muehleModel.removeStone(9);


        assertEquals(1, muehleModel.getStonesPlayer_2());
    }


}


