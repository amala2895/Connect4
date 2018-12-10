package edu.nyu.pqs.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestComputer {
  private Model model;

  private Player player;

  @BeforeEach

  public void setUp() throws Exception {

    model = new Model.Builder().height(6).width(7).build();

    player = new ComputerPlayer(model, 1);

  }

  @Test
  public void testComputerPlays() {
    try {
      int[][] b = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}};
      model.setBoard(b);
      player.yourTurn();
      String str = GameLogger.getInstance().lastLog;
      str = str.substring(0, str.length() - 1);
      assertEquals(str, "Computer Dropped ");
    } catch (Exception e) {
      fail("Computer Plays test failed");
    }
  }

  @Test
  public void testComputerPlaysWinningMove() {
    try {
      int[][] b = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0, 0}};
      model.setBoard(b);
      player.yourTurn();
      String str = GameLogger.getInstance().lastLog;

      assertEquals(str, "Computer Dropped 3");
    } catch (Exception e) {
      fail("Computer Plays test failed");
    }
  }

}
