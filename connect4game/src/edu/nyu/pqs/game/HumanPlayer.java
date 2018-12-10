package edu.nyu.pqs.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author amaladeshpande
 *
 *
 *         This class is for Human Players. It also contains GUI. It implements the interface Player
 *
 *
 */
final class HumanPlayer implements Player {
  private Model model;
  private int id;
  private int column;
  private boolean turnbuttonFlag;
  private boolean buttonFlag;
  private JFrame frame = new JFrame("Connect4");
  private JButton button = new JButton("Submit");
  private JTextField textField = new JTextField();
  private JTextArea boardArea = new JTextArea(8, 8);
  private JTextArea logArea = new JTextArea();

  /**
   * The HumanPlayer constructor initializes model and id It then adds the player to model's list It
   * initializes the Java Swing elements
   * 
   * @param model
   * @param id
   * @throws NullPointerException - this comes if null player is added to model's list
   * @throws IllegalStateException - this comes if duplicate player is added to model's list
   */
  HumanPlayer(Model model, int id) throws NullPointerException, IllegalStateException {

    this.model = model;
    this.id = id;
    this.model.addPlayer(this);
    this.buttonFlag = false;
    this.turnbuttonFlag = false;

    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridLayout(3, 0));

    JPanel topPanel = new JPanel();
    topPanel.add(boardArea);
    frame.add(topPanel);

    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
    frame.getContentPane().add(centerPanel);


    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(button, BorderLayout.EAST);
    bottomPanel.add(textField, BorderLayout.CENTER);
    frame.getContentPane().add(bottomPanel);

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        buttonClicked();
      }
    });
    frame.setVisible(true);
    GameLogger.getInstance().log("Created View for Player " + this.id);

  }

  /**
   * This method is called whenever button clicks It is enabled only when buttonFlag is set(that is
   * only when it is this player's chance) The method checks if input in textField is Integer only
   * It keeps asking user to input till correct format input is made. This method also decrements
   * the input column by 1 so we can use it in the array
   * 
   */
  void buttonClicked() {

    if (this.buttonFlag) {
      this.buttonFlag = false;
      int column = 0;
      try {

        column = Integer.parseInt(textField.getText());

        textField.setText("");
        this.column = column - 1;
        this.turnbuttonFlag = true;
        GameLogger.getInstance().log("Correct Input by player " + id);
      } catch (NumberFormatException e) {
        logArea.append("Wrong input \n");
        GameLogger.getInstance().log("Wrong input by Player " + id);
        this.buttonFlag = true;
      }
    }
  }

  /**
   * This method is for testing purposes
   * 
   * @param s
   */
  void setTextField(String s) {
    textField.setText(s);
  }

  /**
   * This method is for testing purposes
   * 
   */

  void setButtonFlag() {
    this.buttonFlag = true;
  }

  /**
   * This method informs the GUI that game has started
   */
  @Override
  public void gameStarted() {
    logArea.append("Welcome Player " + id + "\n");
  }

  /**
   * This method asks the user to enter the column in which he wants to drop the coin It waits for
   * the user to enter. It checks if entered column value is INT only and if it is between 1 and 7.
   * It then also plays the move if it is valid by calling Model's nextMoveColumn function which
   * makes the changes in the board.
   * 
   */

  @Override
  public void yourTurn() {
    logArea.append("Your Turn Enter a number between 1 to 7" + "\n");
    GameLogger.getInstance().log("Player " + id + "'s turn");

    this.buttonFlag = true;// button enabled
    while (true) {
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      while (true) {
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (this.turnbuttonFlag)
          break;
      }
      this.turnbuttonFlag = false;

      if (model.nextMoveColumn(column, id)) {
        logArea.append("You dropped coin at column " + (column + 1) + "\n");
        break;

      } else {
        logArea.append("Unable to play this move, play again" + "\n");
        this.buttonFlag = true;
      }
    }
  }


  /**
   * This method displays the current board state in BoardArea in GUI
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
    boardArea.setText(str);
  }

  /**
   * This method displays the winner in the logArea in GUI
   * 
   * @param winner
   */
  @Override
  public void winner(int winner) {

    logArea.append("Player " + winner + " Won the game");
  }

  /**
   * This method displays in the logArea in GUI that game is draw
   * 
   */
  @Override
  public void draw() {
    logArea.append("Game Draw");

  }

}
