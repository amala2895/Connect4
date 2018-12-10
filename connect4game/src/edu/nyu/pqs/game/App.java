package edu.nyu.pqs.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author amaladeshpande
 *
 *         This class is the main application class with main method. This class is run to play the
 *         game
 */
public class App {
  /**
   * This is the main method. It calls playWithComputer if button pressed is 1. It calls twoPlayer
   * if button pressed is 2. By default it calls two Player.
   * 
   * @param args
   */
  private static JFrame frame = new JFrame("Connect4 Game");
  private static JButton button1 = new JButton("One Player Game");
  private static JButton button2 = new JButton("Two Player game");

  private static int choice;
  private static boolean turnbuttonFlag = false;

  public static void main(String[] args) {

    frame.setSize(350, 100);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(button1, BorderLayout.EAST);
    bottomPanel.add(button2, BorderLayout.WEST);
    frame.add(bottomPanel);
    frame.setVisible(true);
    button1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        button1Clicked();
      }
    });
    button2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        button2Clicked();
      }
    });

    while (true) {
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {

      }
      if (turnbuttonFlag) {
        frame.setVisible(false);
        break;
      }
    }
    int i = choice;

    switch (i) {
      case 1:
        playWithComputer();
        break;
      case 2:
        twoPlayerGame();
        break;
      default:
        twoPlayerGame();
    }
  }


  /**
   * This is listener of button 1
   */
  static void button1Clicked() {
    choice = 1;
    turnbuttonFlag = true;
  }

  /**
   * This is listener of button 2
   */
  static void button2Clicked() {

    choice = 2;
    turnbuttonFlag = true;

  }

  /**
   * This method creates a Model with the default parameter and to play with computer. then this
   * method calls startGame method. It registers the players to the model
   * 
   */
  public static void playWithComputer() {

    GameLogger.getInstance().log("Player 2 is Computer");

    GameLogger.getInstance().log("Game is about to start");
    Model model = new Model.Builder().build();
    new HumanPlayer(model, 1);
    new ComputerPlayer(model, 2);

    model.addPlayer(GameLogger.getInstance());
    model.startGame();

  }

  /**
   * This method creates Model with the default two player parameter. This then starts the game.
   * This registers the players to the models
   */
  public static void twoPlayerGame() {
    GameLogger.getInstance().log("Game is about to start");
    Model model = new Model.Builder().build();

    new HumanPlayer(model, 1);
    new HumanPlayer(model, 2);
    model.addPlayer(GameLogger.getInstance());
    model.startGame();
  }
}
