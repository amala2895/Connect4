package edu.nyu.pqs.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestModel {
  private Model model;
  private final int HEIGHT = 6;

  @BeforeEach

  public void setUp() throws Exception {
    model = new Model.Builder().build();

  }

  @Test
  public void testStartGame() {
    try {
      Player player1 = new Player() {
        @Override
        public void gameStarted() {}

        @Override
        public void yourTurn() {
          model.nextMoveColumn(1, 1);

        }

        @Override
        public void displayBoard(int[][] board) {}

        @Override
        public void winner(int winner) {}

        @Override
        public void draw() {

        }
      };

      Player player2 = new Player() {
        @Override
        public void gameStarted() {}

        @Override
        public void yourTurn() {
          model.nextMoveColumn(2, 2);
        }

        @Override
        public void displayBoard(int[][] board) {}

        @Override
        public void winner(int winner) {}

        @Override
        public void draw() {}
      };

      model.addPlayer(player1);
      model.addPlayer(player2);
      model.startGame();
      int winner = model.getWinner();
      assertEquals(winner, 1);
    } catch (Exception e) {
      fail("Failed Start Game test");
    }

  }

  @Test
  public void testAddNullPlayer() {
    try {
      model.addPlayer(null);
      fail("Player cannot be null");
    } catch (NullPointerException ex) {

    }
  }

  @Test
  public void testAddDuplPlayer() {

    try {
      model.addPlayer(new HumanPlayer(model, 1));

      fail("Player can not be duplicated");
    } catch (IllegalStateException ex) {

    }

  }

  @Test
  public void testFullColumn() {
    try {
      for (int i = 0; i <= HEIGHT; i++) {
        model.nextMoveColumn(1, 1);
      }
      assertEquals(GameLogger.getInstance().lastLog,
          "No space in this column, cannot play move by Player 1");
    } catch (Exception e) {
      fail("Full Column Test Failed");
    }

  }

  @Test
  public void testWrongColumnValueHigh() {
    try {
      model.nextMoveColumn(7, 1);
      assertEquals(GameLogger.getInstance().lastLog, "Wrong column value by Player 1");
    } catch (Exception e) {
      fail("Wrong Column Value High Test Failed");
    }
  }

  @Test
  public void testWrongColumnLow() {
    try {
      model.nextMoveColumn(-1, 1);
      assertEquals(GameLogger.getInstance().lastLog, "Wrong column value by Player 1");
    } catch (Exception e) {
      fail("Wrong Column Value Low Test Failed");
    }
  }

  @Test
  public void testCheckDraw() {
    try {
      int[][] b = {{1, 2, 1, 2, 1, 2, 1}, {2, 1, 2, 1, 2, 1, 2}, {2, 1, 2, 1, 2, 1, 2},
          {1, 2, 1, 2, 1, 2, 2}, {1, 2, 1, 2, 1, 2, 1}, {2, 1, 2, 1, 2, 1, 1}};
      model.setBoard(b);
      model.checkDraw();
      assertEquals(GameLogger.getInstance().lastLog, "Game is Draw");
    } catch (Exception e) {
      fail("Check Draw test Failed");
    }
  }

  @Test
  public void testCheckVerticalWin() {
    try {


      int[][] b = {{0, 0, 0, 0, 0, 0, 0}, {2, 1, 0, 1, 0, 0, 0}, {2, 1, 0, 1, 0, 0, 0},
          {1, 2, 0, 1, 0, 0, 0}, {1, 2, 0, 1, 1, 0, 0}, {2, 1, 0, 2, 2, 0, 0}};
      model.setBoard(b);
      model.checkWinner();

      assertEquals(model.getWinner(), 1);
    } catch (Exception e) {
      fail("Check Vertical Win Test Failed");
    }
  }

  @Test
  public void testCheckHorizontalWin() {
    try {


      int[][] b = {{0, 0, 0, 0, 0, 0, 0}, {2, 1, 0, 0, 0, 0, 0}, {2, 1, 0, 1, 0, 0, 0},
          {1, 2, 0, 1, 0, 0, 0}, {1, 2, 0, 1, 1, 0, 0}, {1, 2, 2, 2, 2, 0, 0}};
      model.setBoard(b);
      model.checkWinner();

      assertEquals(model.getWinner(), 2);
    } catch (Exception e) {
      fail("Check Horizontal Win Test Failed");
    }
  }

  @Test
  public void testCheckDiagonalRightLeftWin() {
    try {


      int[][] b = {{0, 0, 0, 0, 0, 0, 0}, {2, 1, 0, 0, 0, 0, 0}, {2, 1, 0, 1, 0, 0, 0},
          {1, 2, 0, 1, 0, 0, 0}, {1, 2, 2, 1, 1, 0, 0}, {1, 2, 1, 2, 2, 0, 0}};
      model.setBoard(b);
      model.checkWinner();

      assertEquals(model.getWinner(), 2);
    } catch (Exception e) {
      fail("Check Diagonal Right Left Win Test Failed");
    }
  }

  @Test
  public void testCheckDiagonalLeftRightWin() {
    try {


      int[][] b = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0},
          {0, 1, 1, 0, 1, 2, 0}, {1, 2, 2, 1, 1, 2, 0}, {1, 2, 1, 2, 1, 2, 0}};
      model.setBoard(b);
      model.checkWinner();

      assertEquals(model.getWinner(), 1);
    } catch (Exception e) {
      fail("Check Diagonal Left Right Win Test Failed");
    }
  }

}
