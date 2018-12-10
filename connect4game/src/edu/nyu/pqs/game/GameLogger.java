package edu.nyu.pqs.game;

import java.util.logging.Logger;

/**
 * 
 * @author amaladeshpande
 *
 *         This class is a Logger class and it implements the Player Interface. It is also
 *         registered with Model. Hence reveives all notifications that players receive. It logs all
 *         those messages. This class is Singleton class.
 *
 */
final class GameLogger implements Player {
  private final static Logger LOGGER = Logger.getLogger(GameLogger.class.getName());
  private static GameLogger singleInstance;

  String lastLog;

  /**
   * The constructor is private so it can not be instantiated
   * 
   */
  private GameLogger() {}

  /**
   * This method creates an instance if null and if present sends the instance
   * 
   * @return it returns the single instance of the class
   */
  static GameLogger getInstance() {
    if (singleInstance == null) {
      singleInstance = new GameLogger();
    }
    return singleInstance;
  }

  /**
   * This method prints the text that comes to it
   * 
   * @param text - this the text which has to be logged
   */
  void log(String text) {
    // Logger.debug(text);
    LOGGER.info(text);

    lastLog = text;
  }


  @Override
  public void gameStarted() {
    log("Game Started");
  }


  @Override
  public void yourTurn() {}

  /**
   * This method displays the board to the console
   * 
   * @param board
   */
  @Override
  public void displayBoard(int[][] board) {
    String str = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        str = str + board[i][j];
      }
      str = str + '\n';
    }
    LOGGER.info(str);
  }

  /**
   * This method logs the winner of the game
   * 
   * @param winner
   * 
   */
  @Override
  public void winner(int winner) {
    log("Game won by player " + winner);
  }

  /**
   * This method logs that game is draw
   * 
   */
  @Override
  public void draw() {
    log("Game Draw");

  }
}
