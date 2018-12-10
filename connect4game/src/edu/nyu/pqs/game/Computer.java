package edu.nyu.pqs.game;

import java.util.Random;

/**
 * 
 * @author amaladeshpande
 * 
 * 
 *         This class is a Computer Player and implements Player. This class does not have a GUI and
 *         serves the purpose of playing with a computer
 *
 */
final class ComputerPlayer implements Player {
  private Model model;
  private int id;
  private int column;

  /**
   * Constructor of Computer class is used to add it to the Model's PlayerList
   * 
   * @param model
   * @param id
   */
  ComputerPlayer(Model model, int id) {
    this.model = model;
    this.id = id;
    this.model.addPlayer(this);
  }

  @Override
  public void gameStarted() {

  }

  /**
   * This method serves the purpose to give next turn for Computer Player. It will check if there is
   * a winning move for Computer and chose that winning move. Or it will chose a random valid column
   * 
   */
  @Override
  public void yourTurn() {
    GameLogger.getInstance().log("Computer's turn");
    Random r = new Random();
    int[][] board = model.getBoard();
    column = checkForNextMove(board);

    if (column == -1) {
      while (true) {
        column = r.nextInt(7);

        if (model.nextMoveColumn(column, id)) {
          GameLogger.getInstance().log("Computer Dropped " + column);
          break;
        } else {
          GameLogger.getInstance().log("Column full for Computer");

        }
      }
    } else {
      if (model.nextMoveColumn(column, id)) {
        GameLogger.getInstance().log("Computer Dropped " + column);

      }
    }
  }

  /**
   * This Method checks if the given board has a winner. It is used to check if making a move can
   * result in win by computer
   * 
   * @param board
   * @return it returns if there is a winning line
   */
  private boolean checkWinner(int[][] board) {

    // check vertical
    for (int j = 0; j < board[0].length; j++) {
      for (int i = 0; i < board.length / 2; i++) {

        if (model.checkForVertical(i, j)) {

          return true;
        }
      }
    }
    // check horizontal
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length / 2; j++) {
        if (model.checkForHorizontal(i, j)) {
          // winner

          return true;
        }
      }
    }
    // check diagonal left to right
    for (int i = 0; i < board.length / 2; i++) {
      for (int j = board[0].length - 1; j >= board[0].length / 2; j--) {
        if (model.checkForDiagonalLR(i, j)) {

          return true;
        }
      }
    }

    // check diagonal right to left
    for (int i = 0; i < board.length / 2; i++) {
      for (int j = 0; j < board[0].length / 2; j++) {
        if (model.checkForDiagonalRL(i, j)) {

          return true;
        }
      }
    }
    return false;
  }

  /**
   * This method checks if there is some move possible that will result in a win
   * 
   * @param board
   * @return it returns the column which can be played next to win or -1
   */
  private int checkForNextMove(int[][] board) {
    GameLogger.getInstance().log("Computer Checking Next Move");
    for (int c = 0; c < board[0].length; c++) {

      for (int i = board.length - 1; i >= 0; i--) {
        if (board[i][c] == 0) {
          board[i][c] = id;
          if (checkWinner(board)) {
            GameLogger.getInstance().log("Found Winning Move " + c);

            return c;
          }
          board[i][c] = 0;
        }
      }

    }

    return -1;
  }


  @Override
  public void displayBoard(int[][] board) {

  }


  @Override
  public void winner(int winner) {

  }


  @Override
  public void draw() {}

}
