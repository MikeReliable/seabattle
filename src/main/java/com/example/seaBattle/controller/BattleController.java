package com.example.seaBattle.controller;

import com.example.seaBattle.objects.Game;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.objects.Ship;
import com.example.seaBattle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping(path = "/startGame")
    public Long startingGame() {
        Game game = battleService.gameActivate();
        System.out.println("Начало новой игры");
        Long id = game.getGameId();
        System.out.println("Идентификатор игры - " + id);
        return id;
    }

    @PostMapping(path = "/putShip")
    public Ship putShip(@ModelAttribute Player player,
                        @ModelAttribute int coordinateX,
                        @ModelAttribute int coordinateY,
                        @ModelAttribute boolean horizontal) {
//        System.out.println("Установка корабля длиной" + shipLength + " клеток");
        Player actualPlayer = battleService.findPlayerById(player.getPlayerId());
        Ship ship = new Ship();

        ship.setAlive(true);

        return ship;
    }
}
