package com.example.seaBattle.controller;

import com.example.seaBattle.objects.Game;
import com.example.seaBattle.repos.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleController {

    @Autowired
    private GameRepo gameRepo;

    @GetMapping(path = "/startGame")
    public Long startingGame() {
        Game game = new Game();
        gameRepo.save(game);
        System.out.println("Начало новой игры");
        Long id = game.getGameId();
        System.out.println("Идентификатор игры -" + id);
        return id;
    }
}
