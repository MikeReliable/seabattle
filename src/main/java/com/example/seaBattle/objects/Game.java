package com.example.seaBattle.objects;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    private Long whoseTurn;
    private boolean inactive;

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

    public Long getWhoseTurn() {
        return whoseTurn;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public void setWhoseTurn(Long whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    public Set<Player> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(Set<Player> playerSet) {
        this.playerSet = playerSet;
    }
}
