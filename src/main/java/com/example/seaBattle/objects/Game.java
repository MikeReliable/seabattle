package com.example.seaBattle.objects;

import javax.persistence.*;

@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    public Game() {
    }

    public Long getGameId() {
        return gameId;
    }

}
