package com.bea_dot_74.ituna;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GooseGame implements Serializable {

  // Stati del gioco
  public static final int PLAYING = 0;
  public static final int END = 1;

  public static final int MAXGIOCATORI = 4;
  public static final int MINGIOCATORI = 2;
  public static final int MINVAL = 1;
  public static final int MAXVAL = 6;
  public static final int WINPOS = 63;

  public static final String START = "START";

  public static int currentState;
  public static Player currentPlayer;
  public static Mossa currentMossa;

  public static Scanner in = new Scanner(System.in); // the input Scanner
  public static Game partita;

  static List<Integer> goose = Arrays.asList(5, 9, 14, 18, 23, 27);
  static Integer BRIDGE = 6;

  static final String noGoodValue = "Il dado ha un valore non ammesso: inserisci un nuovo numero";


  public static void main(String[] args) {
    // inizializzo il gioco
    initGame();
    // Il gioco parte
    do {
      preparaMossa();

      playerMove();

      updateGame(currentPlayer); // update currentState

      printBoard();

      // Print message if game-over
      if (currentState == END) {
        System.out.println("Bye!");
      }

    } while (currentState == PLAYING); // repeat if not game-over
  }

  /**
   * Inizializzo il gioco
   */
  public static void initGame() {
    partita = new Game();
    boolean ready = false;
    int numPlayer = 0;
    MyConsumer action = new MyConsumer();
    System.out.println("Per aggiungere un giocatore (min 2 - max 4): add player <nome giocatore>");
    System.out.println("Per far partire il gioco con meno di " + MAXGIOCATORI + ": " + START);
    System.out.println("CTRL+X per uscire dal gioco");

    do {
      String firstWord = in.next();

      if ((!START.equals(firstWord))) {
        in.next();
        String nome = in.next();

        Player currP = new Player();
        currP.setName(nome);
        currP.setPosizione(0);

        if (!partita.getPlayers().contains(currP)) {

          partita.getPlayers().add(currP);
          System.out.print("players: ");
          Iterator<Player> iter = partita.getPlayers().iterator();

          while (iter.hasNext()) {
            System.out.print(iter.next().getName());
            if (iter.hasNext()) {
              System.out.print(", ");
            }
          }
          System.out.println();

          numPlayer++;
          if (numPlayer == MAXGIOCATORI) {
            ready = Boolean.TRUE;
          }
        } else {
          System.out.println("Giocatore già esistente");
        }
      } else if (START.equals(firstWord) && numPlayer >= MINGIOCATORI) {
        ready = Boolean.TRUE;
      } else {
        System.out.println("Devi inserire almeno " + MINGIOCATORI + " giocatori");
      }
    }
    while (!ready);

    System.out.println("Riepilogo giocatori: ");
//       List<String> pL = partita.getPlayers().stream()
//           .map(Player::getName)
//           .collect(Collectors.toList());
//
//       pL.forEach(System.out::println);
    printBoard();

    currentState = PLAYING; // ready to play
  }

  /**
   * Preparo mossa
   */
  public static void preparaMossa() {
    boolean dado1Setted = false;
    boolean dado2Setted = false;
    Integer unDado = null;

    Mossa mossa = new Mossa();
    System.out.println("Per muovere: move <nome giocatore> <numero dado 1>, <numero dado 2>");
    in.next();

    String nome = in.next();

    mossa.setName(nome);
    currentPlayer = partita.getPlayerByName(nome);
    do {
      try {
        unDado = Integer.valueOf(in.next().replaceAll(",", "").trim());
        System.out.println("Dado 1: " + unDado);
        if (!(unDado < MINVAL || unDado > MAXVAL) && !dado1Setted) {
          mossa.setDado1(unDado.intValue());
          dado1Setted = true;
          unDado = null;
        } else {
          System.out.println(noGoodValue);
        }

        try {
          if (unDado == null) {
            unDado = Integer.valueOf(in.next().replaceAll(",", "").trim());
          }
          System.out.println("Dado 2: " + unDado);
          if (!(unDado < MINVAL || unDado > MAXVAL)) {
            mossa.setDado2(unDado.intValue());
            dado2Setted = true;
          } else {
            System.out.println(noGoodValue);
          }
        } catch (Exception ex) {
          System.out.println(noGoodValue);
        }
      } catch (Exception ex) {
        System.out.println(noGoodValue);
      }
    }
    while (!(dado1Setted && dado2Setted));

    currentMossa = mossa;
  }

  public static void playerMove() {
    Player pl = partita.getPlayerByName(currentMossa.getName());
    int muoviA = pl.getPosizione() + currentMossa.getDado1() + currentMossa.getDado2();

    System.out.println(
        currentMossa.getName() + " rolls " + currentMossa.getDado1() + ", " + currentMossa.getDado2() + ". " + currentMossa.getName() + " rolls from " + pl
            .getPosizione() + " to " + muoviA);
    partita.getPlayerByName(currentMossa.getName()).setPosizione(muoviA);
  }

  /**
   * Aggiorna lo stato della partita e del giocatore
   **/
  public static void updateGame(Player currentPlayer) {
    if (currentState == PLAYING) {
      if (currentPlayer.getPosizione().equals(WINPOS)) {
        System.out.println(currentPlayer.getName() + " wins!!");
        partita.setWinner(currentPlayer.getName());
        currentState = END;
      } else if (currentPlayer.getPosizione() > WINPOS) {
        int newPos = (WINPOS - (currentPlayer.getPosizione() - WINPOS));
        System.out.println(currentPlayer.getName() + " bounces! " + currentPlayer.getName() + " returns to " + newPos);
        currentPlayer.setPosizione(newPos);
      } else if (currentPlayer.getPosizione().equals(BRIDGE)) {
        int newPos = (BRIDGE * 2);
        System.out.println(currentPlayer.getName() + " jumps to " + newPos);
        currentPlayer.setPosizione(newPos);
      } else if (goose.contains(currentPlayer.getPosizione())) {
        int newPos = (currentPlayer.getPosizione() + currentMossa.getDado1() + currentMossa.getDado2());
        System.out.println(currentPlayer.getName() + " moves again and goes to " + newPos);
        currentPlayer.setPosizione(newPos);
        updateGame(currentPlayer);
      }
    }
  }

  /**
   * Print the game board
   */
  public static void printBoard() {
    List<String> pL = partita.getPlayers().stream()
        .map(Player::toString)
        .collect(Collectors.toList());
    pL.forEach(System.out::println);
  }
}


//Consumer implementation that can be reused
class MyConsumer implements Consumer<Player> {

  public void accept(Player t) {
    System.out.println("players: " + t);
  }
}