package edu.nyu.pqs.game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author amaladeshpande
 *
 *         This class is the Model of the app. This class holds the board, information about players
 *         and holds the main logic.
 */
final class Model {

  private int[][] board;
  private final int HEIGHT;
  private final int WIDTH;
  private int WINNER;
  private final int WIN;
  private boolean draw;
  private List<Player> players = new CopyOnWriteArrayList<Player>();


  /**
   * 
   * This is the Builder class used to set Parameters like width, height, win fields of model
   *
   */
  static class Builder {
    private int width = 7;
    private int height = 6;
    private int win = 4;


    Builder() {}

    Builder width(int w) {
      if (w < 3 || w > 20) {
        throw new IllegalArgumentException();
      }
      width = w;
      return this;
    }

    Builder height(int h) {
      if (h < 3 || h > 20) {
        throw new IllegalArgumentException();
      }
      height = h;
      return this;
    }

    Builder win(int w) {
      if (w < 3 || w > 10) {
        throw new IllegalArgumentException();
      }
      win = w;
      return this;
    }



    Model build() {
      return new Model(this);
    }
  }

  /**
   * The constructor initializes its fields using Builder And it initializes the board
   * 
   * @param builder
   */

  Model(Builder builder) {
    WIN = builder.win;
    HEIGHT = builder.height;
    WIDTH = builder.width;


    board = new int[HEIGHT][WIDTH];
    GameLogger.getInstance().log("Created Model");
  }

  /**
   * This method adds the player to the list
   * 
   * @param pla
   * @throws NullPointerException
   * @throws IllegalStateException
   * 
   */
  void addPlayer(Player pla) {
    if (pla == null)
      throw new NullPointerException();
    if (players.contains(pla))
      throw new IllegalStateException();
    players.add(pla);
  }

  /**
   * This method first checks weather two humans are playing or human and computer Then it
   * initializes players which are added to the list. In this we assume that only 2 players are
   * going to play and no more Then this method starts the game buy asking each player to enter its
   * move one after the other Between moves it also checks if there is winner or if the game is draw
   * 
   */
  void startGame() {

    initializeBoard();
    fireGameStartedEvent();
    fireDisplayBoardEvent();
    Player player1 = players.get(0);
    Player player2 = players.get(1);

    while (true) {

      fireYourTurnEvent(player1);
      fireDisplayBoardEvent();
      if (checkWinner()) {
        announceWinner();
        break;
      }
      if (checkDraw()) {
        announceWinner();
        break;
      }
      fireYourTurnEvent(player2);
      fireDisplayBoardEvent();
      if (checkWinner()) {

        announceWinner();
        break;
      }
      if (checkDraw()) {
        announceWinner();
        break;
      }
    }

  }

  /**
   * This method checks if game is draw
   * 
   * @return returns true if draw or false
   */
  boolean checkDraw() {
    boolean flag = true;
    for (int i = 0; i < WIDTH; i++) {
      if (board[0][i] == 0) {
        flag = false;
      }
    }
    if (flag) {
      GameLogger.getInstance().log("Game is Draw");
      draw = true;
    }
    return flag;
  }

  /**
   * This method fires the your turn event of player
   * 
   * @param pla
   */
  void fireYourTurnEvent(Player pla) {
    pla.yourTurn();
  }

  /**
   * This method fires game started event
   * 
   */
  void fireGameStartedEvent() {
    for (Player pla : players) {
      pla.gameStarted();
    }
  }

  /**
   * This method makes the next move by the player whose id is given in parameters. It first checks
   * if given column is in range 0 to 6. Then it also checks if there is space in the column. If the
   * column given is not valid it returns false. Else it updates the board and returns true
   * 
   * 
   * @param c - column in which coin is to be dropped
   * @param id - id of player
   * @return - returns true if successful drop of coin or false
   * 
   */
  boolean nextMoveColumn(int c, int id) {
    GameLogger.getInstance().log("Player " + id + " dropped in column " + c);
    if (c > 6 || c < 0) {
      GameLogger.getInstance().log("Wrong column value by Player " + id);
      return false;
    }
    boolean flag = false;
    for (int i = HEIGHT - 1; i >= 0; i--) {
      if (board[i][c] == 0) {
        board[i][c] = id;
        flag = true;
        break;
      }
    }
    if (!flag) {
      GameLogger.getInstance().log("No space in this column, cannot play move by Player " + id);
      return false;
    }
    return true;
  }

  /**
   * This method fires display board event
   * 
   */
  void fireDisplayBoardEvent() {
    for (Player pla : players) {
      pla.displayBoard(board);
    }
  }

  /**
   * This method sends the board back so that its state can be looked at
   * 
   * @return board
   */
  int[][] getBoard() {
    return board;
  }

  /**
   * This method is for testing purposes
   * 
   * @param b - board for testing
   */
  void setBoard(int[][] b) {
    this.board = b;
  }

  /**
   * This method returns the winner. It is made for testing purposes
   * 
   * @return WINNER
   */
  int getWinner() {
    return WINNER;
  }

  /**
   * This method checks if there is a vertical line of 4 coins below the cell given
   * 
   * @param r - row number
   * @param c - column number
   * @return - returns true if found a line else false
   */
  boolean checkForVertical(int r, int c) {
    for (int i = 0; i < this.WIN; i++) {
      if (board[r][c] != 0 && board[r + i][c] != 0 && (board[r][c] == board[r + i][c])) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if there is a horizontal line of 4 coins below the cell given
   * 
   * @param r - row number
   * @param c - column number
   * @return - returns true if found a line else false
   */
  boolean checkForHorizontal(int r, int c) {
    for (int i = 0; i < this.WIN; i++) {
      if (board[r][c] != 0 && board[r][c + i] != 0 && (board[r][c] == board[r][c + i])) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if there is a diagonal line left to right of 4 coins below the cell given
   * 
   * @param r - row number
   * @param c - column number
   * @return - returns true if found a line else false
   */
  boolean checkForDiagonalLR(int r, int c) {
    for (int i = 0; i < this.WIN; i++) {
      if (board[r][c] != 0 && board[r + i][c - i] != 0 && (board[r][c] == board[r + i][c - i])) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if there is a diagonal line right to left of 4 coins below the cell given
   * 
   * @param r - row number
   * @param c - column number
   * @return - returns true if found a line else false
   */
  boolean checkForDiagonalRL(int r, int c) {
    for (int i = 0; i < this.WIN; i++) {
      if (board[r][c] != 0 && board[r + i][c + i] != 0 && (board[r][c] == board[r + i][c + i])) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if there is a line of 4 coins
   * 
   * @return returns true if found a line
   */
  boolean checkWinner() {

    // check vertical
    for (int j = 0; j < WIDTH; j++) {
      for (int i = 0; i < HEIGHT / 2; i++) {

        if (checkForVertical(i, j)) {
          WINNER = board[i][j];
          return true;
        }
      }
    }
    // check horizontal
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH / 2 + 1; j++) {
        if (checkForHorizontal(i, j)) {
          // winner
          WINNER = board[i][j];
          return true;
        }
      }
    }
    // check diagonal left to right
    for (int i = 0; i < HEIGHT / 2; i++) {
      for (int j = WIDTH - 1; j >= WIDTH / 2; j--) {
        if (checkForDiagonalLR(i, j)) {
          WINNER = board[i][j];
          return true;
        }
      }
    }

    // check diagonal right to left
    for (int i = 0; i < HEIGHT / 2; i++) {
      for (int j = 0; j < WIDTH / 2 + 1; j++) {
        if (checkForDiagonalRL(i, j)) {
          WINNER = board[i][j];
          return true;
        }
      }
    }
    return false;
  }

  /**
   * This method fires draw event if game is draw or else fires winner event by announcing the
   * winner
   * 
   */
  void announceWinner() {
    if (draw)
      for (Player pla : players) {
        pla.draw();
      }
    else
      for (Player pla : players) {
        pla.winner(WINNER);
      }
  }

  /**
   * This method initializes the board
   */
  void initializeBoard() {
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        board[i][j] = 0;
      }
    }
  }
}
