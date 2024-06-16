package com.example.seaBattle.controller;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Game;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.repos.CellRepo;
import com.example.seaBattle.repos.GameRepo;
import com.example.seaBattle.repos.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class BattleController {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private CellRepo cellRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @PostMapping(path = "/startGame")
    public Long startingGame() {
        Game game = new Game();
        Player player1 = playerActivate();
        Player player2 = playerActivate();
        Set<Player> playerSet = new HashSet<>();
        playerSet.add(player1);
        playerSet.add(player2);
        game.setPlayerSet(playerSet);
        gameRepo.save(game);
        System.out.println("Начало новой игры");
        Long id = game.getGameId();
        System.out.println("Идентификатор игры - " + id);
        return id;
    }

    private Player playerActivate() {
        Player player = new Player();
        playerRepo.save(player);
        Set<Cell> cells = new HashSet<>();
        int fieldSize = 10;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Cell cell = new Cell();
                cell.setCoordinateX(i + 1);
                cell.setCoordinateY(j + 1);
                cell.setStatus("*");
                cells.add(cell);
                cellRepo.save(cell);
            }
        }
        System.out.println(Arrays.toString(cells.toArray()));
        player.setCellSet(cells);
        playerRepo.save(player);
        return player;
    }


}
