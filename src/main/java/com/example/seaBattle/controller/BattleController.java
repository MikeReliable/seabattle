package com.example.seaBattle.controller;

import com.example.seaBattle.objects.Game;
import com.example.seaBattle.repos.GameRepo;
import com.example.seaBattle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    GameRepo gameRepo;

    @PostMapping(path = "/startGame")
    public Long startingGame() {
        Game game = battleService.gameActivate();
        System.out.println("Начало новой игры");
        Long id = game.getGameId();
        System.out.println("Идентификатор игры - " + id);
        return id;
    }

    @PostMapping(path = "/putShip")
    public String putShip(@RequestParam Long gameId,
                          @RequestParam Long playerId,
                          @RequestParam int coordinateX,
                          @RequestParam int coordinateY,
                          @RequestParam boolean horizontal) {
        return battleService.putPlayerShip(gameId, playerId, coordinateX, coordinateY, horizontal);
    }

    @PostMapping(path = "/shoot")
    public String shoot(@RequestParam Long gameId,
                        @RequestParam Long playerId,
                        @RequestParam Long enemyId,
                        @RequestParam int coordinateX,
                        @RequestParam int coordinateY) {
        return battleService.makeShoot(gameId, playerId, enemyId, coordinateX, coordinateY);
    }
}
