package com.example.seaBattle.objects;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    private boolean winner;

    @OneToMany(mappedBy="player")
    private Set<Cell> cellSet;

    @ManyToOne
    @JoinTable(name = "game_player",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "playerId")},
            inverseJoinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "gameId")})
    private Game game;

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

    public Set<Cell> getCellSet() {
        return cellSet;
    }

    public void setCellSet(Set<Cell> cellSet) {
        this.cellSet = cellSet;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
