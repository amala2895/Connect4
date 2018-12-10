package edu.nyu.pqs.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestHumanPlayer {
  private Model model;
  private HumanPlayer player;

  @BeforeEach
  void setUp() throws Exception {
    model = new Model.Builder().build();
    player = new HumanPlayer(model, 1);
  }

  @Test
  void testButtonCorrectInput() {
    try {
      player.setTextField("1");
      player.setButtonFlag();
      player.buttonClicked();
      assertEquals(GameLogger.getInstance().lastLog, "Correct Input by player 1");
    } catch (Exception e) {
      fail("Failed button test correct input");
    }
  }

  @Test
  void testButtonWrongInput() {
    try {
      player.setTextField("a");
      player.setButtonFlag();
      player.buttonClicked();
      assertEquals(GameLogger.getInstance().lastLog, "Wrong input by Player 1");
    } catch (Exception e) {
      fail("Failed button test correct input");
    }
  }
}
