package com.bea_dot_74.ituna;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer posizione;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name + ", posizione " + posizione;
    }

    public Integer getPosizione() {
        return posizione;
    }

    public void setPosizione(Integer posizione) {
        this.posizione = posizione;
    }

    @Override
    public boolean equals(Object player) {
        if (player == this) {
            return true;
        }
        if (!(player instanceof Player)) {
            return false;
        }

        return name.equals(((Player) player).getName());
    }
}