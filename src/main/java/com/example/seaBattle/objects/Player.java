package com.example.seaBattle.objects;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    private boolean winner;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Cell> cellList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "game_player",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "playerId")},
            inverseJoinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "gameId")})
    private Game game;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "player_ship",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "playerId")},
            inverseJoinColumns = {@JoinColumn(name = "ship_id", referencedColumnName = "shipId")})
    private Set<Ship> shipSet;

    public Player() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Ship> getShipSet() {
        return shipSet;
    }

    public void setShipSet(Set<Ship> shipSet) {
        this.shipSet = shipSet;
    }

}
