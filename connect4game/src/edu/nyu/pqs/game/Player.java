package edu.nyu.pqs.game;

/**
 * 
 * @author amaladeshpande
 *
 *         This interface holds the Player and its functions. This interface is implemented by Human
 *         and Computer players
 */
interface Player {
  /**
   * This method is called by model when the game starts
   * 
   */
  void gameStarted();

  /**
   * This method is called by model to indicate player it is his turn and to get a answer from the
   * player
   * 
   */
  void yourTurn();

  /**
   * This method displays the board currently
   * 
   * @param board
   */
  void displayBoard(int[][] board);

  /**
   * This method tells who the winner is
   * 
   * @param winner
   */
  void winner(int winner);

  /**
   * This method tells if the game was draw
   * 
   */
  void draw();
}
