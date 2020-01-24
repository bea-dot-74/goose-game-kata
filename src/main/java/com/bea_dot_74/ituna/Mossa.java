package com.bea_dot_74.ituna;

import java.io.Serializable;

public class Mossa implements Serializable {

  public Mossa() {

  }

  ;

  private String name;

  private Integer dado1;

  private Integer dado2;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getDado1() {
    return dado1;
  }

  public void setDado1(Integer dado1) {
    this.dado1 = dado1;
  }

  public Integer getDado2() {
    return dado2;
  }

  public void setDado2(Integer dado2) {
    this.dado2 = dado2;
  }
}