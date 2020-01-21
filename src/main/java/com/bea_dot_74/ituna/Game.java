package com.bea_dot_74.ituna;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game implements Serializable {

  public Game() {

  }

  ;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date dataPartita;


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
}