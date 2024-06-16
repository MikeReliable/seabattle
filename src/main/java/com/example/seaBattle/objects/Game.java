package com.example.seaBattle.objects;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "game_player",
            joinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "gameId")},
            inverseJoinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "playerId")})
    private Set<Player> playerSet;

    public Game() {
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Player> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(Set<Player> playerSet) {
        this.playerSet = playerSet;
    }
}
