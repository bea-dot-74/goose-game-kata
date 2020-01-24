package com.bea_dot_74.ituna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Game implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date dataPartita;

  private String winner;

  @OneToMany(targetEntity = Player.class, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "player_id")
  private List<Player> players = new ArrayList<Player>();


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDataPartita() {
    return dataPartita;
  }

  public void setDataPartita(Date dataPartita) {
    this.dataPartita = dataPartita;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public Player getPlayerByName(String name) {
    Player pL = this.getPlayers().stream()
        .filter(x -> name.equals(x.getName()))
        .findAny()
        .orElse(null);
    return pL;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }
}