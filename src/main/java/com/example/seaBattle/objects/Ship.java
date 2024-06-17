package com.example.seaBattle.objects;

import javax.persistence.*;

@Entity
@Table
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipId;

    private byte length;
    private boolean alive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "player_ship",
            joinColumns = {@JoinColumn(name = "ship_id", referencedColumnName = "shipId")},
            inverseJoinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "playerId")})
    private Player player;

    public Ship() {
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
